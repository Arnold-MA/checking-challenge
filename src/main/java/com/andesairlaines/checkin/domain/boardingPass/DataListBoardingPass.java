package com.andesairlaines.checkin.domain.boardingPass;

import com.andesairlaines.checkin.domain.flight.Flight;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public record DataListBoardingPass(
        Long boardingPassId,
        Long purchaseId,
        Long passengerId,
        Long seatTypeId,
        Long seatId,
        Long flightId
) {

    public DataListBoardingPass(BoardingPass boardingPass) {
        this(
                boardingPass.getBoardingPassId(),
                boardingPass.getPurchaseId(),
                boardingPass.getPassenger().getPassengerId(),
                boardingPass.getSeatTypeId(),
                boardingPass.getSeatId(),
                boardingPass.getFlight().getFlightId()
        );
    }

}
