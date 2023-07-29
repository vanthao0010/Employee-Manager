/**
 * Copyright(C) 2023 Luvina Software Company
 *
 * EmployeeDetailDTO.java, July 08, 2023 nvthao
 */
package com.luvina.la.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

/**
 * class EmployeeDTO mô tả các thuộc tính cần thiết của employee để thêm mới nhân viên
 * @author thaonv
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDetailDTO {
    private Long employeeId;
    private String employeeName;
    private String employeeNameKana;
    private String employeeBirthDate;
    private Long departmentId;
    private String employeeEmail;
    private String employeeTelephone;
    private String employeeLoginId;
    private String employeeLoginPassword;
    List<EmployeeCertificationDTO> certifications;
}


