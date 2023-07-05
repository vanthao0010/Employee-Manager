package com.luvina.la.service;
/**
 * Copyright(C) 2023 Luvina Software Company
 *
 * IEmployeeService.java, June 30, 2023 nvthao
 */

import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
/*
 * interface khai báo các hàm xử lí logic trong employee
 * @author thaonv
 */
@Service
public interface IEmployeeService {
    List<EmployeeDTO> getAll();
//    public List<EmployeeDTO> getInfo();
    Page<EmployeeDTO> searchByNameOrDepartment(String name , Long departmentId, int offset, int limit,
                                               String ord_employee_name, String ord_certification_name, String ord_end_date);
    Page<EmployeeDTO> searchByNameOrDepartmentCertification(String name , Long departmentId, int offset, int limit,
                                                            String ord_employee_name, String ord_certification_name, String ord_end_date);
    Page<EmployeeDTO> searchByNameOrDepartmentEndDate(String name , Long departmentId, int offset, int limit,
                                                            String ord_employee_name, String ord_certification_name, String ord_end_date);


}
