package com.luvina.la.service.impl;

import com.luvina.la.dto.DepartmentDTO;
import com.luvina.la.entity.Department;
import com.luvina.la.repository.DepartmentRepository;
import com.luvina.la.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class DepartmentServiceImpl implements IDepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

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
