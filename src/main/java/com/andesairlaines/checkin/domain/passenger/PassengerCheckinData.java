package com.andesairlaines.checkin.domain.passenger;

import com.andesairlaines.checkin.domain.boardingPass.BoardingPass;

public record PassengerCheckinData(
        Long passengerId,
        Long dni,
        String name,
        Integer age,
        String country,
        Long boardingPassId,
        Long purchaseId,
        Long seatTypeId,
        Long seatId
) {

    public PassengerCheckinData(BoardingPass boardingPass) {
        this(
                boardingPass.getPassenger().getPassengerId(),
                Long.valueOf(boardingPass.getPassenger().getDni()),
                boardingPass.getPassenger().getName(),
                boardingPass.getPassenger().getAge(),
                boardingPass.getPassenger().getCountry(),
                boardingPass.getBoardingPassId(),
                boardingPass.getPurchaseId(),
                boardingPass.getSeatTypeId(),
                boardingPass.getSeatId()
        );
    }

}
