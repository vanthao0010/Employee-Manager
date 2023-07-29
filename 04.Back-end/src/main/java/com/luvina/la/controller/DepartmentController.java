/**
 * Copyright(C) 2023 Luvina Software Company
 *
 * DepartmentController.java, June 30, 2023 nvthao
 */
package com.luvina.la.controller;

import com.luvina.la.common.Response;
import com.luvina.la.dto.DepartmentDTO;
import com.luvina.la.entity.Department;
import com.luvina.la.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Controller khai báo đầu APi gọi đến Department Service lấy ra danh sách phòng ban
 */
@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    IDepartmentService iDepartmentService;
    /**
     * method lấy ra tất cả department vói các thuộc tính cần thiết
     * @return list phòng ban với các thuộc tính cần thiết
     */
    @GetMapping("")
    ResponseEntity<Response> getAll() {
        List<DepartmentDTO> listDepartment = iDepartmentService.getAll();
        return ResponseEntity.ok(new Response(HttpStatus.OK.toString(),listDepartment.size(),listDepartment));
    }
    /**
     * API lấy ra department theo id
     * @param id id gửi từ client gửi lên
     * @return phòng ban tìm được
     */
    @GetMapping("{id}")
    DepartmentDTO getDepartmentById(@PathVariable Long id) {
        return iDepartmentService.getDepartmentById(id);
    }
}
