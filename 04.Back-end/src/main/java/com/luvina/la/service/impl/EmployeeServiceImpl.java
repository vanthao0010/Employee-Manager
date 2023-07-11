/**
 * Copyright(C) 2023 Luvina Software Company
 *
 * EmployeeServiceImpl.java, June 30, 2023 nvthao
 */
package com.luvina.la.service.impl;
import com.luvina.la.dto.EmployeeCertificationDTO;
import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.dto.EmployeeDetailDTO;
import com.luvina.la.entity.Department;
import com.luvina.la.entity.Employee;
import com.luvina.la.entity.EmployeeCertification;
import com.luvina.la.repository.CertificationRepository;
import com.luvina.la.repository.DepartmentRepository;
import com.luvina.la.repository.EmployeeCertificationRepository;
import com.luvina.la.repository.EmployeeRepository;
import com.luvina.la.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
/**
 * class triển khai các hàm xử lí logic trong employee
 * @author thaonv
 */
@Component
public class EmployeeServiceImpl implements IEmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    EmployeeCertificationRepository employeeCertificationRepository;
    @Autowired
    CertificationRepository certificationRepository;
    /**
     * method search theo tên hoặc departmentId và xử lí chức năng sort theo các trường tên,
     * chứng chỉ , ngày hết hạn chứng chỉ
     * @return list employeeDTO seach theo tên hoặc department đã có và theo điều kiện search
     */
    @Override
    public Page<EmployeeDTO> searchEmployeeByNameOrDepartmentIdAndSort(
                    String name, Long departmentId, int offset,
                    int limit,String ord_employee_name,
                    String ord_certification_name, String ord_end_date) {
        Sort sort = null;

        if(ord_employee_name.equals("DESC") &&
                ord_certification_name.equals("ASC") &&
                ord_end_date.equals("ASC")) {
            sort = Sort.by(Sort.Order.desc("employeeName"),
                           Sort.Order.asc("c.certificationName"),
                           Sort.Order.asc("ec.employeeCertificationScore")
            );


        } else if (ord_employee_name.equals("DESC") &&
                   ord_certification_name.equals("DESC") &&
                   ord_end_date.equals("ASC")) {
            sort = Sort.by(Sort.Order.desc("employeeName"),
                           Sort.Order.desc("c.certificationName"),
                           Sort.Order.asc("ec.employeeCertificationScore")
            );
        } else if (ord_employee_name.equals("DESC") &&
                   ord_certification_name.equals("ASC") &&
                   ord_end_date.equals("DESC")) {
            sort = Sort.by(Sort.Order.desc("employeeName"),
                    Sort.Order.asc("c.certificationName"),
                    Sort.Order.desc("ec.employeeCertificationScore")
            );
        } else if (ord_employee_name.equals("DESC") &&
                   ord_certification_name.equals("DESC") &&
                   ord_end_date.equals("DESC")) {
            sort = Sort.by(Sort.Order.desc("employeeName"),
                    Sort.Order.desc("c.certificationName"),
                    Sort.Order.desc("ec.employeeCertificationScore")
            );
        } else if (ord_employee_name.equals("ASC") &&
                   ord_certification_name.equals("DESC") &&
                   ord_end_date.equals("ASC")) {
            sort = Sort.by(Sort.Order.asc("employeeName"),
                    Sort.Order.desc("c.certificationName"),
                    Sort.Order.asc("ec.employeeCertificationScore")
            );
        } else if (ord_employee_name.equals("ASC") &&
                   ord_certification_name.equals("ASC") &&
                   ord_end_date.equals("DESC")) {
            sort = Sort.by(Sort.Order.asc("employeeName"),
                    Sort.Order.asc("c.certificationName"),
                    Sort.Order.desc("ec.employeeCertificationScore")
            );
        } else if (ord_employee_name.equals("ASC") &&
                   ord_certification_name.equals("DESC") &&
                    ord_end_date.equals("DESC")) {
            sort = Sort.by(Sort.Order.asc("employeeName"),
                    Sort.Order.desc("c.certificationName"),
                    Sort.Order.desc("ec.employeeCertificationScore")
            );
        }
        else if (ord_employee_name.equals("ASC") &&
                 ord_certification_name.equals("ASC") &&
                 ord_end_date.equals("ASC")) {
            sort = Sort.by(Sort.Order.asc("employeeName"),
                    Sort.Order.asc("c.certificationName"),
                    Sort.Order.asc("ec.employeeCertificationScore")
            );
        }
        Pageable pageable = PageRequest.of(offset,limit,sort);
        return employeeRepository.searchEmployeeByNameOrDepartmentIdAndSort(name , departmentId, pageable);
    }
    /**
     *
     * @param employeeDetailDTO dữ liệu từ client gửi lên
     * method thêm mới dữ liệu và database từ client gửi lên
     * @return
     */
    @Override
    public void addEmployee(EmployeeDetailDTO employeeDetailDTO) {
        Employee employee = new Employee();
        employee.setDepartment(departmentRepository.findById(employeeDetailDTO.getDepartmentId()).get());
        employee.setEmployeeName(employeeDetailDTO.getEmployeeName());
        employee.setEmployeeNameKana(employeeDetailDTO.getEmployeeNameKana());
        employee.setEmployeeBirthDate(employeeDetailDTO.getEmployeeBirthDate());
        employee.setEmployeeEmail(employeeDetailDTO.getEmployeeEmail());
        employee.setEmployeeTelephone(employeeDetailDTO.getEmployeeTelephone());
        employee.setEmployeeLoginId(employeeDetailDTO.getEmployeeLoginId());
        employee.setEmployeeLoginPassword(employeeDetailDTO.getEmployeeLoginPassword());
        employeeRepository.save(employee);
        List<EmployeeCertificationDTO> employeeCertificationDTOList = employeeDetailDTO.getCertifications();
        List<EmployeeCertification> employeeCertificationList = new ArrayList<>();
        if(!employeeCertificationDTOList.isEmpty()) {
            for(EmployeeCertificationDTO e:employeeCertificationDTOList) {
                EmployeeCertification employeeCertification = new EmployeeCertification();
                employeeCertification.setEmployee(employee);
                employeeCertification.setCertificationStartDate(e.getCertificationStartDate());
                employeeCertification.setCertificationEndDate(e.getCertificationEndDate());
                employeeCertification.setEmployeeCertificationScore(e.getEmployeeCertificationScore());
                employeeCertification.setCertification(certificationRepository.findById(e.getCertificationId()).get());
                employeeCertificationRepository.save(employeeCertification);
            }
        }
    }
}
