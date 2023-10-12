package com.proyect.employee.employee.entities.enums;

import lombok.Getter;
import java.util.Arrays;

@Getter
public enum EPermission {
        SUPER_ADMIN("PCALL01"),
        CREATE_EMPLOYEE("PCE001"),
        READ_EMPLOYEE("PRE001"),
        UPDATE_EMPLOYEE("PUE001"),
        DELETE_EMPLOYEE("PDE001"),

        CREATE_USER("PCU001"),
        READ_USER("PRU001"),
        UPDATE_USER("PUU001"),
        DELETE_USER("PDU001"),

        CREATE_ROLE("PCR001"),
        READ_ROLE("PRR001"),
        UPDATE_ROLE("PUR001"),
        DELETE_ROLE("PDR001"),

        READ_PERMIT("PRPM01"),
        UPDATE_PERMIT("PUPM01");

        private final String code;
        EPermission(String code) {
                this.code = code;
        }

        public static boolean contains(String codeToCheck) {
                return Arrays.stream(values())
                        .anyMatch(permit -> permit.getCode().equals(codeToCheck));
        }

        public static EPermission fromValue(String value) {
                return Arrays.stream(values())
                        .filter(permit -> permit.code.equals(value))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("No se encontró un enum con el valor: " + value));
        }

}
