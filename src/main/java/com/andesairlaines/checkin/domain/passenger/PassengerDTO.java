package com.andesairlaines.checkin.domain.passenger;

import com.andesairlaines.checkin.domain.boardingPass.BoardingPass;

public record PassengerDTO(
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

    public PassengerDTO(BoardingPass boardingPass) {
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

    public PassengerDTO copy() {
        return new PassengerDTO(
                this.passengerId,
                this.dni,
                this.name,
                this.age,
                this.country,
                this.boardingPassId,
                this.purchaseId,
                this.seatTypeId,
                this.seatType,
                this.seatId,
                this.seat
        );
    }

    public PassengerDTO copy(Long key, String s) {
        return new PassengerDTO(
                this.passengerId,
                this.dni,
                this.name,
                this.age,
                this.country,
                this.boardingPassId,
                this.purchaseId,
                this.seatTypeId,
                this.seatType,
                key,
                s
        );
    }
}
