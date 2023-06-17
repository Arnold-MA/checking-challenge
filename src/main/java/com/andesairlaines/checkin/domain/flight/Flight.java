package com.andesairlaines.checkin.domain.flight;

import com.andesairlaines.checkin.domain.airplane.Airplane;
import com.andesairlaines.checkin.domain.boardingPass.BoardingPass;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@EqualsAndHashCode(of = "flightId")
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Flight")
@Table(name = "flight")
public class Flight {

    @Id
    private Long flightId;
    private Long takeoffDateTime;
    private String takeoffAirport;
    private Long landingDateTime;
    private String landingAirport;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airplaneId")
    private Airplane airplane;
    @OneToMany(mappedBy = "flight")
    private List<BoardingPass> boardingPassList;
}
