package com.luvina.la.entity;
/*
 * Copyright(C) 2023 Luvina Software Company
 *
 * Certification.java, June 29, 2023 thaonv
 */
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import javax.persistence.*;
import java.util.Set;
@Entity
@Table(name = "certifications")
@Data
public class Certification {
    @Id
    @Column(name = "certification_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long certificationId;

    @Column(name = "certification_name",length = 50,nullable = false)
    private String certificationName;

    @Column(name = "certification_level",nullable = false)
    private Integer certificationLevel;

    @OneToMany(mappedBy = "certification",cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<EmployeeCertification> employeeCertifications;
}
