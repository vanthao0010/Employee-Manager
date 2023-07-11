/**
 * Copyright(C) 2023 Luvina Software Company
 *
 * DepartmentRepository.java, July 03, 2023 nvthao
 */
package com.luvina.la.repository;

import com.luvina.la.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * class tầng repository liên kê database xử lí bảng department
 * @author thaonv
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long> {

    Optional<Department> findById(Long departmentId);
}
