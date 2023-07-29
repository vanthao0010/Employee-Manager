package com.luvina.la.dto;
/**
 * Copyright(C) 2023 Luvina Software Company
 *
 * EmployeeCertificationDTO.java, July 8, 2023 nvthao
 */
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
/**
 * class EmployeeDTO mô tả các thuộc tính cần thiết chi tiết chứng chỉ nhân viên
 * @author thaonv
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeCertificationDTO {
    private Long certificationId;
    private String certificationStartDate;
    private String certificationEndDate;
    private BigDecimal employeeCertificationScore;
}
