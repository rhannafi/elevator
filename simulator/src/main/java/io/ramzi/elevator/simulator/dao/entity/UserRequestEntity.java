package io.ramzi.elevator.simulator.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@Entity
@AllArgsConstructor
public class UserRequestEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID" , nullable = false)
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column (nullable = false)
    private long timestamp;

    @NotNull
    @Column (nullable = false)
    private long building;

    @NotNull
    @Column (name="[GROUP]",nullable = false)
    private long group;

    @NotNull
    @Column (nullable = false)
    private long elevator;

    @NotNull
    @Column (nullable = false)
    private long sens;
    
}
