package com.andesairlaines.checkin.utils;

import com.andesairlaines.checkin.domain.passenger.PassengerDTO;
import com.andesairlaines.checkin.domain.seat.Seat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AirplaneObj {

    Map<Long, SeatObj> seatsAirplane;
    List<PassengerDTO> passengers;

    List<PassengerDTO> assignedPassengers = new ArrayList<>();

    public AirplaneObj(List<Seat> seats) {
        this.seatsAirplane = new HashMap<>();
        for (Seat seat : seats) {
            seatsAirplane.put(seat.getSeatId(), new SeatObj(seat.getSeatColumn(), seat.getSeatRow(), seat.getSeatTypeId()));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Long, SeatObj> seat : seatsAirplane.entrySet()) {
            sb.append(seat.getKey())
                    .append(" - ")
                    .append(seat.getValue().getColumn())
                    .append(seat.getValue().getRow())
                    .append(" type: ")
                    .append(seat.getValue().getType())
                    .append(" pass: ")
                    .append(seat.getValue().getPassenger())
                    .append("\n");
        }
        return sb.toString();
    }

    public void initPassengers(List<PassengerDTO> passengers) {
        this.passengers = passengers;
        for (PassengerDTO passenger : passengers) {
            if (passenger.seatId() != null) {
                seatsAirplane
                        .get(passenger.seatId())
                        .assignInitialPassenger(passenger);
            }
        }
    }

    public List<PassengerDTO> getPassengers() {
        return this.passengers;
    }

    public List<PassengerDTO> assign(List<PassengerDTO> list) {
        initPassengers(list);

        Integer iter = 0;
        for (Map.Entry<Long, SeatObj> seat : seatsAirplane.entrySet()) {
            if (!seat.getValue().isInmutable()) {
                iter = nextUnassigned(iter);
                if (iter == null) {
                    break;
                }
                seat.getValue().assignPassenger(passengers.get(iter));
                iter++;
            }
        }

        for (Map.Entry<Long, SeatObj> seat : seatsAirplane.entrySet()) {
            if (seat.getValue().getPassenger() != null) {
                assignedPassengers.add(seat.getValue().getPassenger()
                        .copy(seat.getKey(), seat.getValue().getColumn() + seat.getValue().getRow())
                );
            }
        }
        System.out.println(this);
        return this.assignedPassengers;
    }

    public Integer nextUnassigned(Integer iter) {
        for (int i = iter; i < passengers.size(); i++) {
            if (passengers.get(i).seatId() == null) {
                return i;
            }
        }
        return null;
    }
}
