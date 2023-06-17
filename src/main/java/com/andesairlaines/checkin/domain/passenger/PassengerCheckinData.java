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
        String seatType,
        Long seatId,
        String seat
) {

    public PassengerCheckinData(BoardingPass boardingPass) {
        this(
                boardingPass.getPassenger().getPassengerId(),
                Long.valueOf(boardingPass.getPassenger().getDni()),
                boardingPass.getPassenger().getName(),
                boardingPass.getPassenger().getAge(),
                boardingPass.getPassenger().getCountry(),
                boardingPass.getBoardingPassId(),
                boardingPass.getPurchase().getPurchaseId(),
                boardingPass.getSeatType().getSeatTypeId(),
                boardingPass.getSeatType().getName(),
                boardingPass.getSeat() != null ? boardingPass.getSeat().getSeatId() : null,
                boardingPass.getSeat() != null ? boardingPass.getSeat().getSeatColumn() + boardingPass.getSeat().getSeatRow() : null
        );
    }

}
