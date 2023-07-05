package com.luvina.la.entity;
/*
 * Copyright(C) 2023 Luvina Software Company
 *
 * Employee.java, June 29, 2023 nvthao
 */
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
@Entity
@Table(name = "employees")
@Data
public class Employee implements Serializable {

    private static final long serialVersionUID = 5771173953267484096L;

    @Id
    @Column(name = "employee_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @ManyToOne
    @JoinColumn(name = "department_id",referencedColumnName = "department_id",nullable = false)
    @JsonBackReference
    private Department department;


    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "employee_name_kana")
    private String employeeNameKana;

    @Column(name = "employee_birth_date")
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
    @JsonManagedReference
    private List<EmployeeCertification> employeeCertifications;
}
