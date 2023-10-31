package com.proyect.employee.employee.repositories;

import com.proyect.employee.employee.dtos.response.SalaryLogAndUserDto;
import com.proyect.employee.employee.entities.SalaryLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;

@Repository
public interface SalaryLogRepository extends JpaRepository<SalaryLog, Long> {

    @Modifying
    @Query(value = "INSERT INTO salary_log ( previous_salary, new_salary, employee_id, created_at)" +
            " VALUES (:previous_salary, :new_salary, :employee_id, CURRENT_TIMESTAMP)",
            nativeQuery = true
    )
    void saveLog(@Param("previous_salary") BigDecimal previous_salary,
                      @Param("new_salary") BigDecimal new_salary,
                      @Param("employee_id") Long employee_id
    );

    @Query(value="SELECT s.id as id, s.previous_salary as previous_salary, s.new_salary as new_salary, " +
            "s.createdAt as createdAt, u as employee "+
            "FROM SalaryLog s JOIN User u ON s.employeeId = u.id")
    Collection<SalaryLogAndUserDto> findAllSalariesWithUsers();
}
