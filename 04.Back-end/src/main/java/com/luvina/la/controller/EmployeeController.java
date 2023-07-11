/**
 * Copyright(C) 2023 Luvina Software Company
 *
 * EmployeeController.java, June 13, 2023 nvthao
 */
package com.luvina.la.controller;
import com.luvina.la.common.Response;
import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.dto.EmployeeDetailDTO;
import com.luvina.la.entity.Employee;
import com.luvina.la.entity.EmployeeCertification;
import com.luvina.la.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * Controller của Employee
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    IEmployeeService iEmployeeService;
    /**
     * API search và sort employee theo các điều kiện
     * @param employee_name name truyền vào điều kiện search có thể rỗng
     * @param department_id id của department của điều kiện search có thể rỗng
     * @param offset trả về page thứ bao nhiêu giá trị mặc định page đầu tiên
     * @param limit số bản ghi tối đa của mỗi page mặc định 5
     * @param ord_employee_name điều kiện search theo tên giá trị ban đầu ASC sắp xếp tăng dần
     * @param ord_certification_name điều kiện search theo tên chứng chỉ giá trị ban đầu ASC sắp xếp tăng dần
     * @param ord_end_date điều kiện search theo ngày hết hạn giá trị ban đầu ASC sắp xếp tăng dần
     * @return mã code, tổng số bản ghi và list EmployeeDTO
     */
    @CrossOrigin
    @GetMapping
    ResponseEntity<Response> searchPagingAndSort(
                    @RequestParam(value = "employee_name",required = false) String employee_name,
                    @RequestParam(value = "department_id",required = false) Long department_id,
                    @RequestParam(value = "offset",defaultValue = "0") int offset,
                    @RequestParam(value = "limit",defaultValue = "5") int limit,
                    @RequestParam(value = "ord_employee_name", defaultValue = "ASC") String ord_employee_name,
                    @RequestParam(value = "ord_certification_name", defaultValue = "ASC") String ord_certification_name,
                    @RequestParam(value = "ord_end_date", defaultValue = "ASC") String ord_end_date) {
        Page<EmployeeDTO> listEmployee = iEmployeeService.searchEmployeeByNameOrDepartmentIdAndSort(
                employee_name, department_id, offset, limit,
                ord_employee_name, ord_certification_name, ord_end_date);
        return ResponseEntity.ok(
                new Response(HttpStatus.OK.toString(),(int)listEmployee.getTotalElements(),listEmployee.getContent()));
    }
    /**
     * API thêm mới nhân viên
     * @param employeeDetailDTO thông tin nhân viên truyền từ client
     */
    @CrossOrigin
    @PostMapping
    public void addEmployee(@RequestBody EmployeeDetailDTO employeeDetailDTO) {
        iEmployeeService.addEmployee(employeeDetailDTO);
    }



}
