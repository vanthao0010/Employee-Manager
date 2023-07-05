package com.luvina.la.entity;
/*
 * Copyright(C) 2023 Luvina Software Company
 *
 * Employee_certification.java, June 29, 2023 thaonv
 */
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
@Entity
@Table(name = "employee_certifications")
@Data
public class EmployeeCertification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_certification_id")
    private Long employeeCertificationId;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    @JsonBackReference
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "certification_id", nullable = false)
    @JsonBackReference
    private Certification certification;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "score", nullable = false)
    private BigDecimal score;

}
