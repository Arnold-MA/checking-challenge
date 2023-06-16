package com.andesairlaines.checkin.domain.passenger;

public record DataListPassenger(
        Long passengerId,
        Long dni,
        String name,
        Integer age,
        String country
) {

    public DataListPassenger(Passenger passenger) {
        this(
                passenger.getPassengerId(),
                Long.valueOf(passenger.getDni()),
                passenger.getName(),
                passenger.getAge(),
                passenger.getCountry()
        );
    }

}
