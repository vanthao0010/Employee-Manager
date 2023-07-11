/**
 * Copyright(C) 2023 Luvina Software Company
 *
 * EmployeeRepository.java, June 30, 2023 nvthao
 */
package com.luvina.la.repository;

import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.entity.Employee;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
/**
 * class tầng repository liên kê database xử lí bảng employee
 * @author thaonv
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    /**
     * method lấy về employee theo login id
     */
    Optional<Employee> findByEmployeeLoginId(String employeeLoginId);
    Optional<Employee> findByEmployeeId(Long employeeId);


    /**
     * method giao tiếp database lấy về dữ liệu theo name hoặc phòng ban
     * nếu không truyền vào name hoặc departmentid thì lấy về tất cả
     * nếu chỉ nhâp name thì tìm kiếm theo name
     * nếu chỉ truyền vào departmentid thì tìm theo department
     * nếu truyền vào cả 2 thì tìm theo cả 2 thuộc tính
     */
    @Query("SELECT new com.luvina.la.dto.EmployeeDTO(e.employeeId, e.employeeName, e.employeeBirthDate, " +
            " d.departmentName, e.employeeEmail, e.employeeTelephone, c.certificationName, ec.certificationEndDate,ec.employeeCertificationScore) " +
                " FROM Employee e " +
                "INNER JOIN Department d ON e.department.departmentId = d.departmentId " +
                "LEFT JOIN EmployeeCertification ec ON e.employeeId = ec.employee.employeeId " +
                "LEFT JOIN Certification c ON ec.certification.certificationId = c.certificationId " +
            "Where ( :employeeName IS NULL OR e.employeeName LIKE %:employeeName% ) AND ( :departmentId IS NULL OR d.departmentId =:departmentId ) " )
    Page<EmployeeDTO> searchEmployeeByNameOrDepartmentIdAndSort(@Param("employeeName") String employeeName , @Param("departmentId") Long departmentId, Pageable pageable);


}
