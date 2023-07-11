package com.luvina.la.dto;
/**
 * Copyright(C) 2023 Luvina Software Company
 *
 * DepartmentDTO.java, June 30, 2023 nvthao
 */
import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * class EmployeeDTO mô tả các thuộc tính cần thiết của department
 * @author thaonv
 */
@Data
@AllArgsConstructor
public class DepartmentDTO {
    private Long departmentId;
    private String departmentName;
}
