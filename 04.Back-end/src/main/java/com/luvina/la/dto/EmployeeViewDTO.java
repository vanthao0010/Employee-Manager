package com.luvina.la.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeViewDTO {
    private Long employeeId;
    private String employeeName;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date employeeBirthDate;
    private Long departmentId;
    private String departmentName;
    private String employeeEmail;
    private String employeeTelephone;
    private String employeeNameKana;
    private String employeeLoginId;
    private List<CertificationViewDTO> certifications;
}
