package com.luvina.la.controller;

import com.luvina.la.common.Response;
import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.entity.Employee;
import com.luvina.la.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    IEmployeeService iEmployeeService;


    @CrossOrigin
    @GetMapping
    ResponseEntity<Response> search(@RequestParam(value = "employee_name",required = false) String employee_name,
                                    @RequestParam(value = "department_id",required = false) Long department_id,
                                    @RequestParam(value = "offset",defaultValue = "0") int offset,
                                    @RequestParam(value = "limit",defaultValue = "5") int limit,
                                    @RequestParam(value = "ord_employee_name", defaultValue = "ASC") String ord_employee_name,
                                    @RequestParam(value = "ord_certification_name", defaultValue = "ASC") String ord_certification_name,
                                    @RequestParam(value = "ord_end_date", defaultValue = "ASC") String ord_end_date) {
        Page<EmployeeDTO> listEmployee = iEmployeeService.searchByNameOrDepartment(employee_name, department_id, offset, limit,
                ord_employee_name, ord_certification_name, ord_end_date);
        return ResponseEntity.ok(new Response(HttpStatus.OK.toString(),(int)listEmployee.getTotalElements(),listEmployee.getContent()));
    }



}
