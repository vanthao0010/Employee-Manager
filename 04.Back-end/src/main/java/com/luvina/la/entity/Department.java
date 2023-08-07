/**
 * Copyright(C) 2023 Luvina Software Company
 *
 * Department.java, June 29, 2023 nvthao
 */
package com.luvina.la.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.*;
/**
 * Mô tả cấu trúc entity Department
 * @author thaonv
 */
@Entity
@Table(name="departments")
@Getter
@Setter
public class Department {
    @Id
    @Column(name = "department_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentId;

    @Column(name = "department_name",length = 50)
    private String departmentName;

    @OneToMany(mappedBy = "department",cascade = CascadeType.ALL)
    @JsonManagedReference(value = "department")
    @ToString.Exclude
    private List<Employee> employee;


}
