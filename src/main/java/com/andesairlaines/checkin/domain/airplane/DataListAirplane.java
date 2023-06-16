package com.andesairlaines.checkin.domain.airplane;

public record DataListAirplane(
        Long airplaneId,
        String name
) {
    public DataListAirplane(Airplane airplane) {
        this(airplane.getAirplaneId(), airplane.getName());
    }
}
