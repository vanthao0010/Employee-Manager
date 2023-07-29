/**
 * Copyright(C) 2023 Luvina Software Company
 *
 * EmployeeServiceImpl.java, June 30, 2023 nvthao
 */
package com.luvina.la.service.impl;
import com.luvina.la.Validation.ValidateParameter;
import com.luvina.la.dto.EmployeeCertificationDTO;
import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.dto.EmployeeDetailDTO;
import com.luvina.la.entity.Certification;
import com.luvina.la.entity.Department;
import com.luvina.la.entity.Employee;
import com.luvina.la.entity.EmployeeCertification;
import com.luvina.la.exception.ValidateException;
import com.luvina.la.repository.CertificationRepository;
import com.luvina.la.repository.DepartmentRepository;
import com.luvina.la.repository.EmployeeCertificationRepository;
import com.luvina.la.repository.EmployeeRepository;
import com.luvina.la.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * class triển khai các hàm xử lí logic trong employee
 * @author thaonv
 */
@Component
public class EmployeeServiceImpl implements IEmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    EmployeeCertificationRepository employeeCertificationRepository;
    @Autowired
    CertificationRepository certificationRepository;
    /**
     * method search theo tên hoặc departmentId và xử lí chức năng sort theo các trường tên,
     * chứng chỉ , ngày hết hạn chứng chỉ
     * @return list employeeDTO seach theo tên hoặc department đã có và theo điều kiện search
     */
    @Override
    public Page<EmployeeDTO> searchEmployeeByNameOrDepartmentIdAndSort(
                    String name, Long departmentId, int offset,
                    int limit,String ord_employee_name,
                    String ord_certification_name, String ord_end_date) {
        if(name != null && (name.equals("%") || name.equals("_"))) {
            name = "\\" + name;
        }
        Sort sort = null;
        if(ord_employee_name.equals("DESC") &&
                ord_certification_name.equals("ASC") &&
                ord_end_date.equals("ASC")) {
            sort = Sort.by(Sort.Order.desc("employeeName"),
                           Sort.Order.asc("c.certificationName"),
                           Sort.Order.asc("ec.employeeCertificationScore")
            );


        } else if (ord_employee_name.equals("DESC") &&
                   ord_certification_name.equals("DESC") &&
                   ord_end_date.equals("ASC")) {
            sort = Sort.by(Sort.Order.desc("employeeName"),
                           Sort.Order.desc("c.certificationName"),
                           Sort.Order.asc("ec.employeeCertificationScore")
            );
        } else if (ord_employee_name.equals("DESC") &&
                   ord_certification_name.equals("ASC") &&
                   ord_end_date.equals("DESC")) {
            sort = Sort.by(Sort.Order.desc("employeeName"),
                    Sort.Order.asc("c.certificationName"),
                    Sort.Order.desc("ec.employeeCertificationScore")
            );
        } else if (ord_employee_name.equals("DESC") &&
                   ord_certification_name.equals("DESC") &&
                   ord_end_date.equals("DESC")) {
            sort = Sort.by(Sort.Order.desc("employeeName"),
                    Sort.Order.desc("c.certificationName"),
                    Sort.Order.desc("ec.employeeCertificationScore")
            );
        } else if (ord_employee_name.equals("ASC") &&
                   ord_certification_name.equals("DESC") &&
                   ord_end_date.equals("ASC")) {
            sort = Sort.by(Sort.Order.asc("employeeName"),
                    Sort.Order.desc("c.certificationName"),
                    Sort.Order.asc("ec.employeeCertificationScore")
            );
        } else if (ord_employee_name.equals("ASC") &&
                   ord_certification_name.equals("ASC") &&
                   ord_end_date.equals("DESC")) {
            sort = Sort.by(Sort.Order.asc("employeeName"),
                    Sort.Order.asc("c.certificationName"),
                    Sort.Order.desc("ec.employeeCertificationScore")
            );
        } else if (ord_employee_name.equals("ASC") &&
                   ord_certification_name.equals("DESC") &&
                    ord_end_date.equals("DESC")) {
            sort = Sort.by(Sort.Order.asc("employeeName"),
                    Sort.Order.desc("c.certificationName"),
                    Sort.Order.desc("ec.employeeCertificationScore")
            );
        }
        else if (ord_employee_name.equals("ASC") &&
                 ord_certification_name.equals("ASC") &&
                 ord_end_date.equals("ASC")) {
            sort = Sort.by(Sort.Order.asc("employeeName"),
                    Sort.Order.asc("c.certificationName"),
                    Sort.Order.asc("ec.employeeCertificationScore")
            );
        }
        Pageable pageable = PageRequest.of(offset,limit,sort);
        return employeeRepository.searchEmployeeByNameOrDepartmentIdAndSort(name , departmentId, pageable);
    }
    /**
     *
     * @param employeeDetailDTO dữ liệu từ client gửi lên
     * method validate dữ liệu rồi thêm mới và0 database từ client gửi lên
     * @return ném ra ngoại lệ nếu có nếu không có ngoại lệ thì trả về  employee vừa thêm mới
     */
    @Override
    public Employee addEmployee(EmployeeDetailDTO employeeDetailDTO) {
        ValidateParameter validateParameter = new ValidateParameter(); //
        Map<String, Object> response = new HashMap<>();
        // check validate parameter departmentId
        Long departmentId = employeeDetailDTO.getDepartmentId();
        Optional<Department> department = departmentRepository.findById(departmentId);
        response = validateParameter.validateDepartmentId(departmentId, department);
        if(!response.isEmpty()) {
            throw new ValidateException(response);
        }
        //check validate parameter employee name
        String employeeName = employeeDetailDTO.getEmployeeName();
        response = validateParameter.validateEmployeeName(employeeName);
        if(!response.isEmpty()) {
            throw new ValidateException(response);
        }
        //check validate parameter employee name katakana
        String employeeNameKana = employeeDetailDTO.getEmployeeNameKana();
        response = validateParameter.validateEmployeeNameKana(employeeNameKana);
        if(!response.isEmpty()) {
            throw new ValidateException(response);
        }
        //check validate parameter employee birth date
        String employeeBirthDate = employeeDetailDTO.getEmployeeBirthDate();
        System.out.println(employeeBirthDate);
        response = validateParameter.validateEmployeeBirthDate(employeeBirthDate);
        if(!response.isEmpty()) {
            throw new ValidateException(response);
        }
        //check validate parameter employee email
        String employeeEmail = employeeDetailDTO.getEmployeeEmail();
        response = validateParameter.validateEmployeeEmail(employeeEmail);
        if(!response.isEmpty()) {
            throw new ValidateException(response);
        }
        //check validate parameter employee phone
        String employeeTelephone = employeeDetailDTO.getEmployeeTelephone();
        response = validateParameter.validateEmployeePhone(employeeTelephone);
        if(!response.isEmpty()) {
            throw new ValidateException(response);
        }
        //check validate parameter employee login id
        String employeeLoginId = employeeDetailDTO.getEmployeeLoginId();
        response = validateParameter
                .validateEmployeeLoginId(employeeLoginId, employeeRepository.findByEmployeeLoginId(employeeLoginId));
        if(!response.isEmpty()) {
            throw new ValidateException(response);
        }
        //check validate parameter employee login password
        String employeePassword = employeeDetailDTO.getEmployeeLoginPassword();
        response = validateParameter.validateEmployeeLoginPassword(employeePassword);
        if(!response.isEmpty()) {
            throw new ValidateException(response);
        }

        Employee employee = new Employee(null,department.get(),employeeName,employeeNameKana,
                                        convertStrToDate(employeeBirthDate), employeeEmail,employeeTelephone,
                                        employeeLoginId,employeePassword);
        // thêm mới employee vào database
        Employee savedEmployee = employeeRepository.save(employee);
        List<EmployeeCertificationDTO> employeeCertificationDTOList = employeeDetailDTO.getCertifications();
        //check có tồn tại thông tin chứng chỉ hay không
        if(!employeeCertificationDTOList.isEmpty()) {
            for(EmployeeCertificationDTO e:employeeCertificationDTOList) {
                //check validate parameter certification id
                Long certificationId = e.getCertificationId();
                Optional<Certification> certification = certificationRepository.findById(certificationId);
                response = validateParameter.
                        validateCertificationId(certificationId, certification);
                if(!response.isEmpty()) {
                    throw new ValidateException(response);
                }
                //check validate parameter employee start date
                String certificationStartDate = e.getCertificationStartDate();
                response = validateParameter.validateCertificationStartDate(certificationStartDate);
                if(!response.isEmpty()) {
                    throw new ValidateException(response);
                }
                //check validate parameter end date
                String certificationEndDate = e.getCertificationEndDate();
                response = validateParameter.validateCertificationEndDate(certificationEndDate);
                if(!response.isEmpty()) {
                    throw new ValidateException(response);
                }
                //check end date co lon hon start date
                response = validateParameter.checkEndDateGreaterStartDate(convertStrToDate(certificationEndDate),
                                                                        convertStrToDate(certificationStartDate));
                if(!response.isEmpty()) {
                    throw new ValidateException(response);
                }
                //check validate parameter employee certification score
                BigDecimal certificationScore = e.getEmployeeCertificationScore();
                response = validateParameter.validateScore(certificationScore);
                if(!response.isEmpty()) {
                    throw new ValidateException(response);
                }
                EmployeeCertification employeeCertification = new EmployeeCertification(null,employee,
                                                                certification.get(),
                                                                convertStrToDate(certificationStartDate),
                                                                convertStrToDate(certificationEndDate),
                                                                certificationScore);
                employeeCertificationRepository.save(employeeCertification);
            }
        }
        return savedEmployee;
    }

    /**
     * method convert string to date
     * @param dateString date dữ liệu kiểu string
     * @return date dữ liệu kiểu date
     */
    @Override
    public Date convertStrToDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
