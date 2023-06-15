package com.andesairlaines.checkin.domain.passenger;

public record DataListPassenger(
        Long passengerId,
        String dni,
        String name,
        Integer age,
        String country
) {

    public DataListPassenger(Passenger passenger) {
        this(passenger.getPassengerId(), passenger.getDni(), passenger.getName(), passenger.getAge(), passenger.getCountry());
    }

}
