package com.luvina.la.entity;
/*
 * Copyright(C) 2023 Luvina Software Company
 *
 * Department.java, June 29, 2023 nvthao
 */
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import javax.persistence.*;
import java.util.*;
@Entity
@Table(name="departments")
@Data
public class Department {
    @Id
    @Column(name = "department_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentId;

    @Column(name = "department_name",length = 50)
    private String departmentName;

    @OneToMany(mappedBy = "department",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Employee> employee;


}
