package com.luvina.la.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificationViewDTO {
    private Long certificationId;
    private String certificationName;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date startDate;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date endDate;
    private BigDecimal score;
}
