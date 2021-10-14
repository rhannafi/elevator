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
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestEntity {

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
    private long current_floor;    

    @NotNull
    @Column (nullable = false)
    private long sens;
    
}
