package com.andesairlaines.checkin.domain.passenger;

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
}
