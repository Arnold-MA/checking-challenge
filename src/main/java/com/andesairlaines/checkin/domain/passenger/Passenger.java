package com.andesairlaines.checkin.domain.passenger;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode(of = "passengerId")
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Passenger")
@Table(name = "passenger")
public class Passenger {

    @Id
    private Long passengerId;
    private String dni;
    private String name;
    private Integer age;
    private String country;

}
