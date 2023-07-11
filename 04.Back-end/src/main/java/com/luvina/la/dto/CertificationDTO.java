package com.luvina.la.dto;
/**
 * Copyright(C) 2023 Luvina Software Company
 *
 * CertificationDTO.java, July 8, 2023 nvthao
 */
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * class mô tả cấu trúc Certification cần thiệt
 * @author thaonv
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificationDTO {
    private Long certificationId;
    private String certificationName;
}
