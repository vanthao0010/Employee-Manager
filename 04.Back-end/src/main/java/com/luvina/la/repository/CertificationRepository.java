/**
 * Copyright(C) 2023 Luvina Software Company
 *
 * CertificationRepository.java, July 8, 2023 nvthao
 */
package com.luvina.la.repository;

import com.luvina.la.entity.Certification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
/**
 * Tầng Repository để giao tiếp với bảng Certification
 * @author thaonv
 */
@Repository
public interface CertificationRepository extends JpaRepository<Certification,Long> {
    Optional<Certification> findByCertificationId(Long id);

}
