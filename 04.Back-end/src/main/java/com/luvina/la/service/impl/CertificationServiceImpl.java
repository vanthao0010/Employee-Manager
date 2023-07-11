package com.luvina.la.service.impl;
/**
 * Copyright(C) 2023 Luvina Software Company
 *
 * CertificationServiceImpl.java, July 08, 2023 nvthao
 */
import com.luvina.la.dto.CertificationDTO;
import com.luvina.la.entity.Certification;
import com.luvina.la.repository.CertificationRepository;
import com.luvina.la.service.ICertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * class xử lí logic với Certification
 * @author thaonv
 */
@Component
public class CertificationServiceImpl implements ICertificationService {
    @Autowired
    CertificationRepository certificationRepository;
    /**
     * method xử lí lấy ra danh sách certification
     * @return 1 list certification với các thuộc tính cần thiết
     */
    @Override
    public List<CertificationDTO> listCertifications() {
        List<CertificationDTO> listCertification = new ArrayList<>();
        List<Certification> certificationDetail = certificationRepository.findAll();
        for(Certification c : certificationDetail) {
           Long id = c.getCertificationId();
           String name = c.getCertificationName();
           CertificationDTO certificationDTO = new CertificationDTO(id,name);
           listCertification.add(certificationDTO);
        }
        return listCertification;
    }
}
