/**
 * Copyright(C) 2023 Luvina Software Company
 *
 * UserLogic.java, July 08, 2023 nvthao
 */
package com.luvina.la.service;

import com.luvina.la.dto.CertificationDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * interface khai báo các phương thức xử lí logic với Certification
 * @author thaonv
 */
@Service
public interface ICertificationService {
    public List<CertificationDTO> listCertifications();

    CertificationDTO getCertificationById(Long id);
}
