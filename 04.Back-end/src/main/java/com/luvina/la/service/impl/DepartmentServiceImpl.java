package com.luvina.la.service.impl;
/**
 * Copyright(C) 2023 Luvina Software Company
 *
 * UserLogic.java, June 13, 2023 nvthao
 */
import com.luvina.la.dto.DepartmentDTO;
import com.luvina.la.entity.Department;
import com.luvina.la.repository.DepartmentRepository;
import com.luvina.la.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Tầng service xử lí logic với Department
 * @author thaonv
 */
@Component
public class DepartmentServiceImpl implements IDepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    /**
     * service thực hiện lấy ra list department
     * @return tất cả department
     */
    @Override
    public List<DepartmentDTO> getAll() {
        List<DepartmentDTO> departments = new ArrayList<>();
        List<Department> resDepartment = departmentRepository.findAll();
        for(Department d : resDepartment) {
            Long departmentId = d.getDepartmentId();
            String departmentName = d.getDepartmentName();
            DepartmentDTO department = new DepartmentDTO(departmentId,departmentName);
            departments.add(department);
        }
        return departments;
    }
}
