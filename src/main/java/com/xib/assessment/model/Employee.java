package com.xib.assessment.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;
import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@SuperBuilder
@MappedSuperclass
public abstract class Employee {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column(unique = true)
    @NonNull
    private String idNumber;

    public Employee() {
    }

    public Employee(String firstName, String lastName, String idNumber) {
        this.firstName=firstName;
        this.lastName=lastName;
        this.idNumber=idNumber;
    }

    @Override
    public boolean equals(Object object){
        return object != null &&
            object instanceof Employee &&
            ((Employee)object).getIdNumber().equals(this.idNumber);
    }

}

