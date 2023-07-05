package com.luvina.la.service;

import com.luvina.la.dto.DepartmentDTO;
import com.luvina.la.entity.Department;
import org.springframework.stereotype.Service;

import java.util.List;
/*
 * interface khai báo các hàm xử lí logic trong department
 * @author thaonv
 */
@Service
public interface IDepartmentService {
    public List<DepartmentDTO> getAll();


}
