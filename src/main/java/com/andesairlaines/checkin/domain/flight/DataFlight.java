package com.andesairlaines.checkin.domain.flight;

import com.andesairlaines.checkin.domain.passenger.PassengerDTO;
import com.andesairlaines.checkin.utils.AirplaneObj;

import java.util.List;

public record DataFlight(
        Long flightId,
        Long takeoffDateTime,
        String takeoffAirport,
        Long landingDateTime,
        String landingAirport,
        Long airplaneId,
        List<PassengerDTO> passengers
) {

    public DataFlight(Flight flight) {
        this(
                flight.getFlightId(),
                flight.getTakeoffDateTime(),
                flight.getTakeoffAirport(),
                flight.getLandingDateTime(),
                flight.getLandingAirport(),
                flight.getAirplane().getAirplaneId(),
                new AirplaneObj(flight.getAirplane().getSeats())
                        .assign(flight.getBoardingPassList().stream().map(PassengerDTO::new).toList())
        );
    }

    /*public List<PassengerDTO> setSeats(Flight flight) {
        AirplaneObj airplaneSeats = new AirplaneObj(flight.getAirplane().getSeats());
        airplaneSeats.initPassengers(flight.getBoardingPassList().stream().map(PassengerDTO::new).toList());
        System.out.println(airplaneSeats);
        return airplaneSeats.getPassengers();
    }*/
}
