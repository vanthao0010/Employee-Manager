package com.luvina.la.dto;
/*
 * Copyright(C) 2023 Luvina Software Company
 *
 * EmployeeDTO.java, June 30, 2023 nvthao
 */

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/*
 * class EmployeeDTO mô tả các thuộc tính cần thiết của employee
 * @author thaonv
 */
@Data
public class EmployeeDTO implements Serializable {

    private Long employeeId;
    private String employeeName;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date employeeBirthDate;
    private String departmentName;
    private String employeeEmail;
    private String employeePhone;
    private String certificateName;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date endDate;
    private BigDecimal score;

    public EmployeeDTO(Long employeeId, String employeeName,Date employeeBirthDate, String employeeEmail,
                       String employeePhone, String departmentName, Date endDate, String certificateName, BigDecimal score) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
        this.employeeBirthDate = employeeBirthDate;
        this.employeePhone = employeePhone;
        this.departmentName = departmentName;
        this.endDate = endDate;
        this.certificateName = certificateName;
        this.score = score;
    }

    public EmployeeDTO() {
    }
}
