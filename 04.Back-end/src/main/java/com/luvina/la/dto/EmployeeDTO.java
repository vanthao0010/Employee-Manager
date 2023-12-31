package com.luvina.la.dto;
/**
 * Copyright(C) 2023 Luvina Software Company
 *
 * EmployeeDTO.java, June 30, 2023 nvthao
 */
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * class EmployeeDTO mô tả các thuộc tính cần thiết của employee để gửi về client
 * @author thaonv
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO implements Serializable {
    private Long employeeId;
    private String employeeName;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date employeeBirthDate;
    private String departmentName;
    private String employeeEmail;
    private String employeeTelephone;
    private String certificateName;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date certificationEndDate;
    private BigDecimal employeeCertificationScore;


}
