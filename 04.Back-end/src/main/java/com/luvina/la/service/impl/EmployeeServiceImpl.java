/**
 * Copyright(C) 2023 Luvina Software Company
 *
 * EmployeeServiceImpl.java, June 30, 2023 nvthao
 */
package com.luvina.la.service.impl;
import com.luvina.la.Validation.ValidateParameter;
import com.luvina.la.dto.*;
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
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public Employee addorUpdateEmployee(EmployeeDetailDTO employeeDetailDTO, Long employeeId) {
        Employee employeeUpdate = new Employee();
        if(employeeId != null) {
            Optional<Employee> employeeOptional = employeeRepository.findByEmployeeId(employeeId);
            if(employeeOptional.isPresent()) {
                employeeUpdate = employeeOptional.get();
            } else {
                throw new RuntimeException("Id khong ton tai");
            }
        }
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
        //check validate parameter employee birth date
        String employeeBirthDate = employeeDetailDTO.getEmployeeBirthDate();
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
        if(!employeeLoginId.equals(employeeUpdate.getEmployeeLoginId())) {
            response = validateParameter
                    .validateEmployeeLoginId(employeeLoginId, employeeRepository.findByEmployeeLoginId(employeeLoginId));
            if(!response.isEmpty()) {
                throw new ValidateException(response);
            }
        }
        //check validate parameter employee login password
        String employeePassword = employeeDetailDTO.getEmployeeLoginPassword();
        response = validateParameter.validateEmployeeLoginPassword(employeePassword);
        if(!response.isEmpty()) {
            throw new ValidateException(response);
        }
        employeeUpdate.setDepartment(department.get());
        employeeUpdate.setEmployeeName(employeeName);
        employeeUpdate.setEmployeeNameKana(employeeNameKana);
        employeeUpdate.setEmployeeBirthDate(convertStrToDate(employeeBirthDate));
        employeeUpdate.setEmployeeEmail(employeeEmail);
        employeeUpdate.setEmployeeTelephone(employeeTelephone);
        employeeUpdate.setEmployeeLoginId(employeeLoginId);
        employeeUpdate.setEmployeeLoginPassword(employeePassword);
        // thêm mới employee vào database
        Employee savedEmployee = employeeRepository.save(employeeUpdate);
//        List<EmployeeCertificationDTO> employeeCertificationDTOList = employeeDetailDTO.getCertifications();
//        //check có tồn tại thông tin chứng chỉ hay không
//        if(employeeCertificationDTOList != null) {
//            for(EmployeeCertificationDTO e:employeeCertificationDTOList) {
//                //check validate parameter certification id
//                Long certificationId = e.getCertificationId();
//                Optional<Certification> certification = certificationRepository.findById(certificationId);
//                response = validateParameter.
//                        validateCertificationId(certificationId, certification);
//                if(!response.isEmpty()) {
//                    throw new ValidateException(response);
//                }
//                //check validate parameter employee start date
//                String certificationStartDate = e.getCertificationStartDate();
//                response = validateParameter.validateCertificationStartDate(certificationStartDate);
//                if(!response.isEmpty()) {
//                    throw new ValidateException(response);
//                }
//                //check validate parameter end date
//                String certificationEndDate = e.getCertificationEndDate();
//                response = validateParameter.validateCertificationEndDate(certificationEndDate);
//                if(!response.isEmpty()) {
//                    throw new ValidateException(response);
//                }
//                //check end date co lon hon start date
//                response = validateParameter.checkEndDateGreaterStartDate(convertStrToDate(certificationEndDate),
//                                                                        convertStrToDate(certificationStartDate));
//                if(!response.isEmpty()) {
//                    throw new ValidateException(response);
//                }
//                //check validate parameter employee certification score
//                BigDecimal certificationScore = e.getEmployeeCertificationScore();
//                response = validateParameter.validateScore(certificationScore);
//                if(!response.isEmpty()) {
//                    throw new ValidateException(response);
//                }
//                EmployeeCertification employeeCertification = new EmployeeCertification(null,employeeUpdate,
//                                                                certification.get(),
//                                                                convertStrToDate(certificationStartDate),
//                                                                convertStrToDate(certificationEndDate),
//                                                                certificationScore);
//                employeeCertificationRepository.save(employeeCertification);
//            }
//        }
        List<EmployeeCertification> certifications = employeeCertificationRepository.findByEmployee(employeeUpdate);
        System.out.println(certifications==null);
        List<EmployeeCertificationDTO> certificationDTOs = employeeDetailDTO.getCertifications();
        System.out.println(certificationDTOs==null);
        if(certifications == null) {
            if(certificationDTOs != null) {
                EmployeeCertificationDTO employeeCertificationDTO = certificationDTOs.get(0);
                Date startDate = convertStrToDate(employeeCertificationDTO.getCertificationStartDate());
                Date endDate = convertStrToDate(employeeCertificationDTO.getCertificationEndDate());
                BigDecimal score = employeeCertificationDTO.getEmployeeCertificationScore();
                Certification certification = certificationRepository.findByCertificationId(
                        employeeCertificationDTO.getCertificationId()).get();
                certifications.add(new EmployeeCertification(
                        null,employeeUpdate, certification, startDate, endDate, score));
                employeeCertificationRepository.saveAll(certifications);
            }
        } else {
            if(certificationDTOs == null) {
                employeeCertificationRepository.deleteAllInBatch(certifications);
            } else {
                EmployeeCertification certification = certifications.get(0);
                EmployeeCertificationDTO employeeCertificationDTO = certificationDTOs.get(0);
                certification.setCertification(certificationRepository.
                        findByCertificationId(employeeCertificationDTO.getCertificationId()).get());
                certification.setEmployee(employeeUpdate);
                certification.setCertificationStartDate(convertStrToDate(employeeCertificationDTO.getCertificationStartDate()));
                certification.setCertificationEndDate(convertStrToDate(employeeCertificationDTO.getCertificationEndDate()));
                certification.setEmployeeCertificationScore(employeeCertificationDTO.getEmployeeCertificationScore());
                employeeCertificationRepository.save(certification);
            }
        }
        return savedEmployee;
    }



    @Override
    public EmployeeViewDTO viewEmployeeDetail(Long employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId).get();
        EmployeeViewDTO employeeDetail = new EmployeeViewDTO();
        if(employee == null) {
            Map<String, Object> errorMessage = new HashMap<>();
            errorMessage.put("error","Id not found");
            throw new ValidateException(errorMessage);
        } else {
            employeeDetail.setEmployeeId(employee.getEmployeeId());
            employeeDetail.setEmployeeName(employee.getEmployeeName());
            employeeDetail.setEmployeeBirthDate(employee.getEmployeeBirthDate());
            employeeDetail.setDepartmentId(employee.getDepartment().getDepartmentId());
            employeeDetail.setDepartmentName(employee.getDepartment().getDepartmentName());
            employeeDetail.setEmployeeEmail(employee.getEmployeeEmail());
            employeeDetail.setEmployeeTelephone(employee.getEmployeeTelephone());
            employeeDetail.setEmployeeNameKana(employee.getEmployeeNameKana());
            employeeDetail.setEmployeeLoginId(employee.getEmployeeLoginId());
            List<EmployeeCertification> certifications = employeeCertificationRepository.findByEmployee(employee);
            List<CertificationViewDTO> employeeCertifications = new ArrayList<>();
            if(!certifications.isEmpty()) {
                for(EmployeeCertification c : certifications) {
                    Long certificationId = c.getCertification().getCertificationId();
                    String certificationName = c.getCertification().getCertificationName();
                    Date startDate = c.getCertificationStartDate();
                    Date endDate = c.getCertificationEndDate();
                    BigDecimal score = c.getEmployeeCertificationScore();
                    employeeCertifications.add(
                            new CertificationViewDTO(certificationId,certificationName,startDate,endDate,score));
                }
            }
            employeeDetail.setCertifications(employeeCertifications);
        }

        return employeeDetail;
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
