package com.andesairlaines.checkin.utils;

import com.andesairlaines.checkin.domain.passenger.PassengerDTO;
import com.andesairlaines.checkin.domain.seat.Seat;

import java.util.*;

public class AirplaneObj {

    private Map<Long, Map<Long, SeatObj>> seatsAirplane;
    private Map<Long, SeatObj> seatDemo = new HashMap<>();
    private List<PassengerDTO> passengers;
    private final Map<Long, List<PassengerDTO>> seatTypesPurchases = new HashMap<>();
    private final List<PassengerDTO> assignedPassengers = new ArrayList<>();

    public AirplaneObj(List<Seat> seats) {
        this.seatsAirplane = new HashMap<>();
        for (Seat seat : seats) {
            if (!seatsAirplane.containsKey(seat.getSeatTypeId())) {
                seatsAirplane.put(seat.getSeatTypeId(), new HashMap<>());
            }
            seatsAirplane.get(seat.getSeatTypeId()).put(seat.getSeatId(),
                    new SeatObj(seat.getSeatColumn(), seat.getSeatRow(), seat.getSeatTypeId(), seat.getSeatId())
            );
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
        System.out.println(this);
        return this.assignedPassengers;
    }

    private void assignBySeatType() {
        //Map<Long, Map<Long, List<PassengerDTO>>> purchasesTmp = new HashMap<>(seatTypesPurchases);
        for (Map.Entry<Long, Map<Long, SeatObj>> seatType : seatsAirplane.entrySet()) {
            List<PassengerDTO> passengerList = seatTypesPurchases.get(seatType.getKey());

            assignPassengers(seatType.getValue(), passengerList);

            /*for (Map.Entry<Long, SeatObj> seat : seatType.getValue().entrySet()) {

            }*/
        }
        copyPassengers();
    }

    private void assignPassengers(Map<Long, SeatObj> seatMap, List<PassengerDTO> passengerList) {
        List<PassengerDTO> passengersToAssign = new ArrayList<>();

        // Recopilar pasajeros a asignar
        /*for (List<PassengerDTO> passengers : passengerMap.values()) {
            passengersToAssign.addAll(passengers);
        }*/

        passengerList.sort(Comparator.comparing(PassengerDTO::purchaseId));
        for (PassengerDTO passenger : passengerList) {
            if (passenger.seatId() == null) {
                passengersToAssign.add(passenger);
            }
        }
        System.out.println("Pasajeros a asignar: " + passengersToAssign.size());

        // Map<purchaseId, seatId>
        Map<Long, Long> seatAssigned = new HashMap<>();
        Map<Long, SeatObj> seatUnassigned = new HashMap<>();
        for (SeatObj seat : seatMap.values()) {
            if (seat.getPassenger() != null) {
                seatAssigned.put(seat.getPassenger().purchaseId(), seat.getSeatId());
                System.out.println("Pasajero previamente asignado" + seat.getPassenger());
            } else {
                seatUnassigned.put(seat.getSeatId(), seat);
                System.out.println("Pasajero sin asignar" + seat.getPassenger());
            }
        }
        System.out.println("Tamaño: " + seatAssigned.size());

        for (PassengerDTO passenger : passengersToAssign) {
            if (passenger.seatId() != null) {
                if (seatAssigned.containsKey(passenger.purchaseId())) {
                    Long seatId = seatAssigned.get(passenger.purchaseId());
                    assignSameRow(passenger, seatId, seatUnassigned, seatMap.get(seatId).getRow(), seatMap.get(seatId).getColumn());
                }
            }
        }

    }

    private void assignSameRow(PassengerDTO passenger, Long seatId, Map<Long, SeatObj> seatUnassigned, Integer seatRow, String seatColumn) {

        Map<String, SeatObj> availableColumns = new HashMap<>();
        Map<Integer, SeatObj> availableRows = new HashMap<>();
        for (SeatObj seat : seatUnassigned.values()) {
            if (seat.getRow().equals(seatRow)) {
                availableColumns.put(seat.getColumn(), seat);
            }
            if (seat.getColumn().equals(seatColumn)) {
                availableRows.put(seat.getRow(), seat);
            }
        }

        Long seatIdToAssign = null;
        if (availableColumns.size() != 0) {
            seatIdToAssign = searchAdjacent(availableColumns, seatColumn);
        } else if (availableRows.size() != 0) {
            seatIdToAssign = searchAdjacent(availableRows, seatRow);
        } else {
            //seatIdToAssign = searchOptional(seatId, seatUnassigned);
        }
        seatUnassigned.get(seatIdToAssign).assignPassenger(passenger);
    }

    private Long searchOptional(Long seatId, Map<Long, SeatObj> seatUnassigned) {
        Long idTmp = null;
        Long distance = null;
        for (Long idToSearch : seatUnassigned.keySet()) {
            long distanceTmp = Math.abs(idToSearch - seatId);
            if (distance == null || distanceTmp < distance) {
                distance = distanceTmp;
                idTmp = idToSearch;
            }
        }
        return seatUnassigned.get(idTmp).getSeatId();
    }

    private Long searchAdjacent(Map<String, SeatObj> availableColumns, String seatColumn) {
        String columnTmp = null;
        Integer distance = null;
        for (String column : availableColumns.keySet()) {
            int distanceTmp = Math.abs(column.charAt(0) - seatColumn.charAt(0));
            if (columnTmp == null || distanceTmp < distance) {
                columnTmp = column;
                distance = distanceTmp;
            }
            if (distance == 1) {
                return availableColumns.get(columnTmp).getSeatId();
            }
        }
        return availableColumns.get(columnTmp).getSeatId();
    }

    private Long searchAdjacent(Map<Integer, SeatObj> availableRows, Integer seatRow) {
        Integer rowTmp = null;
        Integer distance = null;
        for (Integer row : availableRows.keySet()) {
            int distanceTmp = Math.abs(row - seatRow);
            if (rowTmp == null || distanceTmp < distance) {
                rowTmp = row;
                distance = distanceTmp;
            }
            if (distance == 1) {
                return availableRows.get(rowTmp).getSeatId();
            }
        }
        return availableRows.get(rowTmp).getSeatId();
    }


    private SeatObj findAvailableSeat(Map<Long, SeatObj> seatMap, Long purchaseId, Integer age) {
        SeatObj adjacentSeat = null;

        for (SeatObj seat : seatMap.values()) {
            if (!seat.isInmutable() && seat.getPassenger() == null) {
                if (seat.getPassenger() == null) {
                    // Si el asiento está vacío, se puede asignar directamente
                    return seat;
                } else if (seat.getPassenger().age() >= 18 && age < 18) {
                    // Si el asiento contiene un pasajero mayor de edad y el nuevo pasajero es menor de edad,
                    // se almacena el asiento para un posible emparejamiento
                    adjacentSeat = seat;
                }
            }
        }

        // Si no se encontró un asiento disponible, se intenta asignar junto a un asiento con un pasajero mayor de edad
        return adjacentSeat;
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
                        .copy(seat.getKey())
                );
            }
        }
    }

    private void separateSeatType() {
        for (PassengerDTO passenger : passengers) {
            if (!seatTypesPurchases.containsKey(passenger.seatTypeId())) {
                seatTypesPurchases.put(passenger.seatTypeId(), new ArrayList<>());
            }
            seatTypesPurchases.get(passenger.seatTypeId()).add(passenger);
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
        for (Map.Entry<Long, Map<Long, SeatObj>> seatType : seatsAirplane.entrySet()) {
            for (Map.Entry<Long, SeatObj> seat : seatType.getValue().entrySet()){
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
        }
        return sb.toString();
    }

    private void copyPassengers() {
        for (Map.Entry<Long, Map<Long, SeatObj>> seatType : seatsAirplane.entrySet()) {
            for (Map.Entry<Long, SeatObj> seat : seatType.getValue().entrySet()) {
                if (seat.getValue().getPassenger() != null) {
                    assignedPassengers.add(seat.getValue().getPassenger()
                            .copy(seat.getKey())
                    );
                }
            }
        }
    }
}
