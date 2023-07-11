package com.luvina.la.common;

import com.luvina.la.dto.CertificationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class ResponseCertification {
    private int code;
    private List<CertificationDTO> object;
}
