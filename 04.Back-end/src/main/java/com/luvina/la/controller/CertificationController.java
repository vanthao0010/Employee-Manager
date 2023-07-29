package com.luvina.la.controller;

import com.luvina.la.common.ResponseCertification;
import com.luvina.la.dto.CertificationDTO;
import com.luvina.la.service.ICertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certifications")
public class CertificationController {
    @Autowired
    ICertificationService iCertificationService;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<ResponseCertification> getListCertificationDTO() {
        List<CertificationDTO> listCertifications = iCertificationService.listCertifications();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseCertification(HttpStatus.OK.value(), listCertifications));
    }

    /**
     * API lấy certification theo id gửi từ client
     * @param id gửi từ client
     * @return certification
     */
    @GetMapping("{id}")
    CertificationDTO getCertificationById(@PathVariable Long id) {
        return iCertificationService.getCertificationById(id);
    }
}
