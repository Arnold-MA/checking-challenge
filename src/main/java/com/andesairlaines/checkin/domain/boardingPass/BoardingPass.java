package com.andesairlaines.checkin.domain.boardingPass;

import com.andesairlaines.checkin.domain.flight.Flight;
import com.andesairlaines.checkin.domain.passenger.Passenger;
import com.andesairlaines.checkin.domain.purchase.Purchase;
import com.andesairlaines.checkin.domain.seat.Seat;
import com.andesairlaines.checkin.domain.seatType.SeatType;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "purchaseId")
    private Purchase purchase;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "passengerId")
    private Passenger passenger;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seatTypeId")
    private SeatType seatType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seatId")
    private Seat seat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flightId")
    private Flight flight;

}
