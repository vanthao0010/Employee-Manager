/**
 * Copyright(C) 2023 Luvina Software Company
 *
 * Employee_certification.java, June 29, 2023 thaonv
 */
package com.luvina.la.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Mô tả cấu trúc entity chi tiết chứng chỉ nhân viên
 * @author thaonv
 */
@Entity
@Table(name = "employee_certifications")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeCertification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_certification_id")
    private Long employeeCertificationId;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    @JsonBackReference(value = "employee")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "certification_id", nullable = false)
    @JsonBackReference (value = "certification")
    private Certification certification;

    @Column(name = "start_date", nullable = false)
    private Date certificationStartDate;

    @Column(name = "end_date", nullable = false)
    private Date certificationEndDate;

    @Column(name = "score", nullable = false)
    private BigDecimal employeeCertificationScore;

}
