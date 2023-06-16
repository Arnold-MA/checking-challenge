package com.andesairlaines.checkin.domain.flight;

import com.andesairlaines.checkin.domain.airplane.Airplane;
import com.andesairlaines.checkin.domain.airplane.DataListAirplane;

public record DataListFlight(
        Long flightId,
        Long takeoffDateTime,
        String takeoffAirport,
        Long landingDateTime,
        String landingAirport,
        DataListAirplane airplane
) {

    public DataListFlight(Flight flight) {
        this(
                flight.getFlightId(),
                flight.getTakeoffDateTime(),
                flight.getLandingAirport(),
                flight.getLandingDateTime(),
                flight.getLandingAirport(),
                new DataListAirplane(flight.getAirplane())
        );
    }

}
