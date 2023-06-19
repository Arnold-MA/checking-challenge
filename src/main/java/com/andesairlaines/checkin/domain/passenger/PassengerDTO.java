package com.andesairlaines.checkin.domain.passenger;

import com.andesairlaines.checkin.domain.boardingPass.BoardingPass;

public record PassengerDTO(
        Long passengerId,
        //Long dni,
        //String name,
        Integer age,
        //String country,
        //Long boardingPassId,
        Long purchaseId,
        Long seatTypeId,
        Long seatId
) {

    public PassengerDTO(BoardingPass boardingPass) {
        this(
                boardingPass.getPassenger().getPassengerId(),
                //Long.valueOf(boardingPass.getPassenger().getDni()),
                //boardingPass.getPassenger().getName(),
                boardingPass.getPassenger().getAge(),
                //boardingPass.getPassenger().getCountry(),
                //boardingPass.getBoardingPassId(),
                boardingPass.getPurchase().getPurchaseId(),
                boardingPass.getSeatType().getSeatTypeId(),
                boardingPass.getSeat() != null ? boardingPass.getSeat().getSeatId() : null
        );
    }

    public PassengerDTO copy() {
        return new PassengerDTO(
                this.passengerId,
                //this.dni,
                //this.name,
                this.age,
                //this.country,
                //this.boardingPassId,
                this.purchaseId,
                this.seatTypeId,
                this.seatId
        );
    }

    public PassengerDTO copy(Long key) {
        return new PassengerDTO(
                this.passengerId,
                //this.dni,
                //this.name,
                this.age,
                //this.country,
                //this.boardingPassId,
                this.purchaseId,
                this.seatTypeId,
                key
        );
    }
}
