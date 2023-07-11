package com.luvina.la.dto;
/**
 * Copyright(C) 2023 Luvina Software Company
 *
 * EmployeeCertificationDTO.java, July 8, 2023 nvthao
 */
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;
/**
 * class EmployeeDTO mô tả các thuộc tính cần thiết chi tiết chứng chỉ nhân viên
 * @author thaonv
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeCertificationDTO {
    private Long certificationId;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date certificationStartDate;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date certificationEndDate;
    private BigDecimal employeeCertificationScore;
}
