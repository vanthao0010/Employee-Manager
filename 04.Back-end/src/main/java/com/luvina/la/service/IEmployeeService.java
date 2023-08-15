package com.luvina.la.service;
/**
 * Copyright(C) 2023 Luvina Software Company
 *
 * IEmployeeService.java, June 30, 2023 nvthao
 */

import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.dto.EmployeeDetailDTO;
import com.luvina.la.dto.EmployeeViewDTO;
import com.luvina.la.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * interface khai báo các hàm xử lí logic trong employee
 * @author thaonv
 */
@Service
public interface IEmployeeService {

    Page<EmployeeDTO> searchEmployeeByNameOrDepartmentIdAndSort(String name , Long departmentId, int offset, int limit,
                                                                String ord_employee_name, String ord_certification_name,
                                                                String ord_end_date);

    public Employee addorUpdateEmployee(EmployeeDetailDTO employeeDetailDTO, Long employeeId);

    public EmployeeViewDTO viewEmployeeDetail(Long employeeId);

    public Date convertStrToDate(String dateString) ;

}
