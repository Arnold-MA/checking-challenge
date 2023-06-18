package com.andesairlaines.checkin.utils;

import com.andesairlaines.checkin.domain.passenger.PassengerDTO;
import com.andesairlaines.checkin.domain.seat.Seat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AirplaneObj {

    private Map<Long, Map<Long, SeatObj>> seatsAirplane;
    private Map<Long, SeatObj> seatDemo = new HashMap<>();
    private List<PassengerDTO> passengers;
    private final Map<Long, Map<Long, List<PassengerDTO>>> seatTypesPurchases = new HashMap<>();
    private final List<PassengerDTO> assignedPassengers = new ArrayList<>();

    public AirplaneObj(List<Seat> seats) {
        this.seatsAirplane = new HashMap<>();
        for (Seat seat : seats) {
            if (!seatsAirplane.containsKey(seat.getSeatTypeId())) {
                seatsAirplane.put(seat.getSeatTypeId(), new HashMap<>());
            }
            seatsAirplane.get(seat.getSeatTypeId()).put(seat.getSeatId(), new SeatObj(seat.getSeatColumn(), seat.getSeatRow(), seat.getSeatTypeId()));
        }
    }

    public void initPassengers(List<PassengerDTO> passengers) {
        this.passengers = passengers;
        for (PassengerDTO passenger : passengers) {
            if (passenger.seatId() != null) {
                seatsAirplane
                        .get(passenger.seatTypeId())
                        .get(passenger.seatId())
                        .assignInitialPassenger(passenger);
            }
        }
    }

    public List<PassengerDTO> assign(List<PassengerDTO> list) {
        initPassengers(list);
        separateSeatType();
        assignBySeatType();
        //arbitredAssign();
        //System.out.println(this);
        return this.assignedPassengers;
    }

    private void assignBySeatType() {
        Map<Long, Map<Long, List<PassengerDTO>>> purchasesTmp = new HashMap<>(seatTypesPurchases);
        for (Map.Entry<Long, Map<Long, SeatObj>> seatType : seatsAirplane.entrySet()) {
            Integer iter = 0;
            Map<Long, List<PassengerDTO>> purchase = purchasesTmp.get(seatType.getKey());
            for (Map.Entry<Long, SeatObj> seat : seatType.getValue().entrySet()) {

            }
        }
    }



    private void arbitredAssign() {
        Integer iter = 0;
        for (Map.Entry<Long, SeatObj> seat : seatDemo.entrySet()) {
            if (!seat.getValue().isInmutable()) {
                iter = nextUnassigned(iter);
                if (iter == null) {
                    break;
                }
                seat.getValue().assignPassenger(passengers.get(iter));
                iter++;
            }
        }

        for (Map.Entry<Long, SeatObj> seat : seatDemo.entrySet()) {
            if (seat.getValue().getPassenger() != null) {
                assignedPassengers.add(seat.getValue().getPassenger()
                        .copy(seat.getKey(), seat.getValue().getColumn() + seat.getValue().getRow())
                );
            }
        }
    }

    private void separateSeatType() {
        for (PassengerDTO passenger : passengers) {
            if (!seatTypesPurchases.containsKey(passenger.seatTypeId())) {
                seatTypesPurchases.put(passenger.seatTypeId(), new HashMap<>());
            }
            if (!seatTypesPurchases.get(passenger.seatTypeId()).containsKey(passenger.purchaseId())) {
                seatTypesPurchases.get(passenger.seatTypeId()).put(passenger.purchaseId(), new ArrayList<>());
            }
            seatTypesPurchases.get(passenger.seatTypeId()).get(passenger.purchaseId()).add(passenger);
        }
    }

    public Integer nextUnassigned(Integer iter) {
        for (int i = iter; i < passengers.size(); i++) {
            if (passengers.get(i).seatId() == null) {
                return i;
            }
        }
        return null;
    }

    private void verifyPurchaseGroup() {
        Map<Long, List<PassengerDTO>> purchases = new HashMap<>();
        for (PassengerDTO passenger : passengers) {
            if (!purchases.containsKey(passenger.purchaseId())) {
                purchases.put(passenger.purchaseId(), new ArrayList<>());
            }
            purchases.get(passenger.purchaseId()).add(passenger);
        }
        for (Map.Entry<Long, List<PassengerDTO>> purchase : purchases.entrySet()) {
            Long seatTypeId = null;
            System.out.println("Compra: " + purchase.getKey() + " Tamaño: " + purchase.getValue().size());
            if (purchase.getValue().size() > 1) {
                for (PassengerDTO passengerDTO : purchase.getValue()) {
                    if (seatTypeId == null) {
                        seatTypeId = passengerDTO.seatTypeId();
                        System.out.println("\tEl mismo");
                    } else {
                        if (seatTypeId.equals(passengerDTO.seatTypeId())) {
                            System.out.println("\tEl mismo");
                        } else {
                            System.out.println("\tDiferente");
                        }
                    }
                }
            } else {
                System.out.println("\tÚnico");
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Long, SeatObj> seat : seatDemo.entrySet()) {
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
}
