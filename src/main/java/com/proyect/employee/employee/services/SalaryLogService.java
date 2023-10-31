package com.proyect.employee.employee.services;

import com.proyect.employee.employee.dtos.response.SalaryLogAndUserDto;
import com.proyect.employee.employee.exception.HandleExcepcion;
import com.proyect.employee.employee.repositories.SalaryLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class SalaryLogService {

    private final SalaryLogRepository repository;

    @Transactional(readOnly = true)
    public Collection<SalaryLogAndUserDto> findAllSalariesWithUsers() {
        return repository.findAllSalariesWithUsers();
    }

    @Transactional
    public void create(BigDecimal previous_salary,BigDecimal new_salary,Long id_user) throws Exception {
        try {
            repository.saveLog(previous_salary, new_salary, id_user);
            log.info("SalaryLog registered successfully!");
        } catch (
                DataIntegrityViolationException ex) {
            HandleExcepcion.SqlError(ex, "SalaryLog");
        }
    }

}
