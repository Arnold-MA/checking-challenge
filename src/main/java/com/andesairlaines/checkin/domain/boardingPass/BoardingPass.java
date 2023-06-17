package com.andesairlaines.checkin.domain.boardingPass;

import com.andesairlaines.checkin.domain.flight.Flight;
import com.andesairlaines.checkin.domain.passenger.Passenger;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode(of = "boardingPassId")
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "BoardingPass")
@Table(name = "boarding_pass")
public class BoardingPass {

    @Id
    private Long boardingPassId;
    private Long purchaseId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "passengerId")
    private Passenger passenger;
    private Long seatTypeId;
    private Long seatId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flightId")
    private Flight flight;

}
