/**
 * Copyright(C) 2023 Luvina Software Company
 *
 * EmployeeCertificationRepository.java, July 8, 2023 nvthao
 */
package com.luvina.la.repository;

import com.luvina.la.entity.EmployeeCertification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * class tầng repository liên kê database xử lí bảng employee_certification
 * @author thaonv
 */
@Repository
public interface EmployeeCertificationRepository extends JpaRepository<EmployeeCertification,Long> {
}
