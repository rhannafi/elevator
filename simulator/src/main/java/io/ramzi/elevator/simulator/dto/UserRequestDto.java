package io.ramzi.elevator.simulator.dto;


import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequestDto {
    @JsonIgnore
    private long id;

    @NotNull
    private long timestamp;

    @NotNull
    private long building;

    @NotNull
    private long group;

    @NotNull
    private long elevator;

    @NotNull
    private long current_floor;    

    @NotNull
    private long sens;
    
}
