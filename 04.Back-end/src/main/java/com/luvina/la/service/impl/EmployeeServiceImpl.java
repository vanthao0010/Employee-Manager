package com.luvina.la.service.impl;
/*
 * Copyright(C) 2023 Luvina Software Company
 *
 * EmployeeServiceImpl.java, June 30, 2023 nvthao
 */
import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.entity.Employee;
import com.luvina.la.entity.EmployeeCertification;
import com.luvina.la.repository.EmployeeRepository;
import com.luvina.la.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/*
 * class triển khai các hàm xử lí logic trong employee
 * @author thaonv
 */
@Component
public class EmployeeServiceImpl implements IEmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    /*
     * method lấy về dữ liệu từ database xử lí trả về các thuôc tính cần thiết
     * @param
     * @return list employeeDTO các thuộc tính theo requirement
     */
    @Override
    public List<EmployeeDTO> getAll() {
        List<EmployeeDTO> listEmployee = new ArrayList<>();
        List<Employee> employees = employeeRepository.findAll();
        for(Employee e : employees) {
            Long id = e.getEmployeeId();
            String employeeName = e.getEmployeeName();
            String employeeEmail = e.getEmployeeEmail();
            Date employeeBirthDate = e.getEmployeeBirthDate();
            String employeePhone = e.getEmployeeTelephone();
            String departmentName = e.getDepartment().getDepartmentName();
            Date endDate = null;
            String certificateName = null;
            BigDecimal score = null;
            List<EmployeeCertification> employeeCertifications = e.getEmployeeCertifications();
            if(!employeeCertifications.isEmpty()) {
                endDate = employeeCertifications.get(0).getEndDate();
                certificateName = employeeCertifications.get(0).getCertification().getCertificationName();
                score = employeeCertifications.get(0).getScore();
            }
            EmployeeDTO newEmployeeDTO = new EmployeeDTO(id,employeeName,employeeBirthDate,employeeEmail,
                                                            employeePhone,departmentName,endDate,certificateName,score);
            listEmployee.add(newEmployeeDTO);
        }
        return listEmployee;
    }

    /*
     * method xử lí chức năng search employee
     * @return list employeeDTO
     */
    @Override
    public Page<EmployeeDTO> searchByNameOrDepartment(String name, Long departmentId, int offset, int limit,
                                                      String ord_employee_name, String ord_certification_name, String ord_end_date) {
        Sort sort = Sort.by(Sort.Order.asc("employeeName"),
                Sort.Order.asc("c.certificationName"),
                Sort.Order.asc("ec.endDate")
        );

        if(ord_employee_name.equals("DESC") && ord_certification_name.equals("ASC") && ord_end_date.equals("ASC")) {
            sort = Sort.by(Sort.Order.desc("employeeName"),
                           Sort.Order.asc("c.certificationName"),
                           Sort.Order.asc("ec.endDate")
            );


        } else if (ord_employee_name.equals("DESC") && ord_certification_name.equals("DESC") && ord_end_date.equals("ASC")) {
            sort = Sort.by(Sort.Order.desc("employeeName"),
                           Sort.Order.desc("c.certificationName"),
                           Sort.Order.asc("ec.endDate")
            );
        } else if (ord_employee_name.equals("DESC") && ord_certification_name.equals("ASC") && ord_end_date.equals("DESC")) {
            sort = Sort.by(Sort.Order.desc("employeeName"),
                    Sort.Order.asc("c.certificationName"),
                    Sort.Order.desc("ec.endDate")
            );
        } else if (ord_employee_name.equals("DESC") && ord_certification_name.equals("DESC") && ord_end_date.equals("DESC")) {
            sort = Sort.by(Sort.Order.desc("employeeName"),
                    Sort.Order.desc("c.certificationName"),
                    Sort.Order.desc("ec.endDate")
            );
        } else if (ord_employee_name.equals("ASC") && ord_certification_name.equals("DESC") && ord_end_date.equals("ASC")) {
            sort = Sort.by(Sort.Order.asc("employeeName"),
                    Sort.Order.desc("c.certificationName"),
                    Sort.Order.asc("ec.endDate")
            );
        } else if (ord_employee_name.equals("ASC") && ord_certification_name.equals("ASC") && ord_end_date.equals("DESC")) {
            sort = Sort.by(Sort.Order.asc("employeeName"),
                    Sort.Order.asc("c.certificationName"),
                    Sort.Order.desc("ec.endDate")
            );
        } else if (ord_employee_name.equals("ASC") && ord_certification_name.equals("DSC") && ord_end_date.equals("DESC")) {
            sort = Sort.by(Sort.Order.asc("employeeName"),
                    Sort.Order.desc("c.certificationName"),
                    Sort.Order.desc("ec.endDate")
            );
        }

        Pageable pageable = PageRequest.of(offset,limit,sort);
        return employeeRepository.findByNameOrDepartment(name , departmentId, pageable);
    }

    @Override
    public Page<EmployeeDTO> searchByNameOrDepartmentCertification(String name, Long departmentId, int offset, int limit,
                                                                   String ord_employee_name, String ord_certification_name, String ord_end_date) {
        Sort sort = null;
        if(ord_certification_name.equals("ASC") && ord_employee_name.equals("ASC") && ord_end_date.equals("ASC")) {
            sort = Sort.by(Sort.Order.asc("c.certificationName"),
                           Sort.Order.asc("employeeName"),
                           Sort.Order.asc("ec.endDate")
            );
        } else if(ord_certification_name.equals("ASC") && ord_employee_name.equals("DESC") && ord_end_date.equals("ASC")) {
            sort = Sort.by(Sort.Order.asc("c.certificationName"),
                    Sort.Order.desc("employeeName"),
                    Sort.Order.asc("ec.endDate")
            );
        } else if(ord_certification_name.equals("ASC") && ord_employee_name.equals("ASC") && ord_end_date.equals("DESC")) {
            sort = Sort.by(Sort.Order.asc("c.certificationName"),
                    Sort.Order.asc("employeeName"),
                    Sort.Order.desc("ec.endDate")
            );
        } else if(ord_certification_name.equals("ASC") && ord_employee_name.equals("DESC") && ord_end_date.equals("DESC")) {
            sort = Sort.by(Sort.Order.asc("c.certificationName"),
                    Sort.Order.desc("employeeName"),
                    Sort.Order.desc("ec.endDate")
            );
        } else if(ord_certification_name.equals("DESC") && ord_employee_name.equals("ASC") && ord_end_date.equals("ASC")) {
            sort = Sort.by(Sort.Order.desc("c.certificationName"),
                    Sort.Order.asc("employeeName"),
                    Sort.Order.asc("ec.endDate")
            );
        } else if(ord_certification_name.equals("DESC") && ord_employee_name.equals("DESC") && ord_end_date.equals("ASC")) {
            sort = Sort.by(Sort.Order.desc("c.certificationName"),
                    Sort.Order.desc("employeeName"),
                    Sort.Order.asc("ec.endDate")
            );
        } else if(ord_certification_name.equals("DESC") && ord_employee_name.equals("ASC") && ord_end_date.equals("DESC")) {
            sort = Sort.by(Sort.Order.desc("c.certificationName"),
                    Sort.Order.asc("employeeName"),
                    Sort.Order.desc("ec.endDate")
            );
        } else if(ord_certification_name.equals("DESC") && ord_employee_name.equals("DESC") && ord_end_date.equals("DESC")) {
            sort = Sort.by(Sort.Order.desc("c.certificationName"),
                    Sort.Order.desc("employeeName"),
                    Sort.Order.desc("ec.endDate")
            );
        }

        Pageable pageable = PageRequest.of(offset,limit,sort);
        return employeeRepository.findByNameOrDepartment(name , departmentId, pageable);
    }

    @Override
    public Page<EmployeeDTO> searchByNameOrDepartmentEndDate(String name, Long departmentId, int offset, int limit,
                                                             String ord_employee_name, String ord_certification_name, String ord_end_date) {
        Sort sort = null;
        if(ord_end_date.equals("ASC") && ord_employee_name.equals("ASC") && ord_certification_name.equals("ASC")) {
            sort = Sort.by(Sort.Order.asc("ec.endDate"),
                           Sort.Order.asc("employeeName"),
                           Sort.Order.desc("c.certificationName")
            );
        }else if(ord_end_date.equals("ASC") && ord_employee_name.equals("DESC") && ord_certification_name.equals("ASC")) {
            sort = Sort.by(Sort.Order.asc("ec.endDate"),
                    Sort.Order.desc("employeeName"),
                    Sort.Order.asc("c.certificationName")
            );
        } else if(ord_end_date.equals("ASC") && ord_employee_name.equals("DESC") && ord_certification_name.equals("DESC")) {
            sort = Sort.by(Sort.Order.asc("ec.endDate"),
                    Sort.Order.desc("employeeName"),
                    Sort.Order.desc("c.certificationName")
            );
        } else if(ord_end_date.equals("ASC") && ord_employee_name.equals("ASC") && ord_certification_name.equals("DESC")) {
            sort = Sort.by(Sort.Order.asc("ec.endDate"),
                    Sort.Order.desc("employeeName"),
                    Sort.Order.desc("c.certificationName")
            );
        } else if(ord_end_date.equals("DESC") && ord_employee_name.equals("ASC") && ord_certification_name.equals("ASC")) {
            sort = Sort.by(Sort.Order.desc("ec.endDate"),
                    Sort.Order.asc("employeeName"),
                    Sort.Order.asc("c.certificationName")
            );
        } else if(ord_end_date.equals("DESC") && ord_employee_name.equals("DESC") && ord_certification_name.equals("ASC")) {
            sort = Sort.by(Sort.Order.desc("ec.endDate"),
                    Sort.Order.desc("employeeName"),
                    Sort.Order.asc("c.certificationName")
            );
        } else if(ord_end_date.equals("DESC") && ord_employee_name.equals("ASC") && ord_certification_name.equals("DESC")) {
            sort = Sort.by(Sort.Order.desc("ec.endDate"),
                    Sort.Order.asc("employeeName"),
                    Sort.Order.asc("c.certificationName")
            );
        } else if(ord_end_date.equals("DESC") && ord_employee_name.equals("DESC") && ord_certification_name.equals("DESC")) {
            sort = Sort.by(Sort.Order.desc("ec.endDate"),
                    Sort.Order.desc("employeeName"),
                    Sort.Order.desc("c.certificationName")
            );
        }
        Pageable pageable = PageRequest.of(offset,limit,sort);
        return employeeRepository.findByNameOrDepartment(name , departmentId, pageable);
    }


}
