package com.andesairlaines.checkin.domain.airplane;

import com.andesairlaines.checkin.domain.flight.Flight;
import com.andesairlaines.checkin.domain.seat.Seat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@EqualsAndHashCode(of = "airplaneId")
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Airplane")
@Table(name = "airplane")
public class Airplane {

    @Id
    private Long airplaneId;
    private String name;
    @OneToMany(mappedBy = "airplane")
    private List<Flight> flights;
    @OneToMany(mappedBy = "airplane")
    private List<Seat> seats;


}
