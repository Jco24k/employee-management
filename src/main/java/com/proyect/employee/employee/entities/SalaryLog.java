package com.proyect.employee.employee.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity
@Table
public class SalaryLog implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition="Decimal(10,2)")
    private BigDecimal previous_salary;

    @Column(nullable = false, columnDefinition="Decimal(10,2)")
    private BigDecimal new_salary;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "employee_id")
    private Long employeeId;

}
