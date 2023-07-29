/**
 * Copyright(C) 2023 Luvina Software Company
 *
 * Employee.java, June 29, 2023 nvthao
 */
package com.luvina.la.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Mô tả cấu trúc entity Employee
 * @author thaonv
 */
@Entity
@Table(name = "employees")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @Column(name = "employee_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @ManyToOne
    @JoinColumn(name = "department_id",referencedColumnName = "department_id",nullable = false)
    @JsonBackReference(value = "department")
    private Department department;


    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "employee_name_kana")
    private String employeeNameKana;

    @Column(name = "employee_birth_date")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date employeeBirthDate;

    @Column(name = "employee_email",nullable = false)
    private String employeeEmail;

    @Column(name = "employee_telephone",length = 50)
    private String employeeTelephone;

    @Column(name = "employee_login_id",nullable = false,length = 50)
    private String employeeLoginId;

    @Column(name = "employee_login_password",length = 100)
    private String employeeLoginPassword;

    @OneToMany(mappedBy = "employee",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonManagedReference(value = "employee")
    private List<EmployeeCertification> employeeCertifications;

    public Employee(Long employeeId, Department department, String employeeName, String employeeNameKana,
                    Date employeeBirthDate, String employeeEmail, String employeeTelephone, String employeeLoginId,
                    String employeeLoginPassword) {
        this.employeeId = employeeId;
        this.department = department;
        this.employeeName = employeeName;
        this.employeeNameKana = employeeNameKana;
        this.employeeBirthDate = employeeBirthDate;
        this.employeeEmail = employeeEmail;
        this.employeeTelephone = employeeTelephone;
        this.employeeLoginId = employeeLoginId;
        this.employeeLoginPassword = employeeLoginPassword;
    }
}
