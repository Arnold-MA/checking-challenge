package com.andesairlaines.checkin.domain.flight;

import com.andesairlaines.checkin.domain.airplane.DataListAirplane;
import com.andesairlaines.checkin.domain.boardingPass.DataListBoardingPass;
import com.andesairlaines.checkin.domain.passenger.Passenger;
import com.andesairlaines.checkin.domain.passenger.PassengerCheckinData;

import java.util.List;

public record DataFlight(
        Long flightId,
        Long takeoffDateTime,
        String takeoffAirport,
        Long landingDateTime,
        String landingAirport,
        Long airplaneId,
        List<PassengerCheckinData> passengers
) {

    public DataFlight(Flight flight) {
        this(
                flight.getFlightId(),
                flight.getTakeoffDateTime(),
                flight.getTakeoffAirport(),
                flight.getLandingDateTime(),
                flight.getLandingAirport(),
                flight.getAirplane().getAirplaneId(),
                flight.getBoardingPassList().stream().map(PassengerCheckinData::new).toList()
        );
    }

}
