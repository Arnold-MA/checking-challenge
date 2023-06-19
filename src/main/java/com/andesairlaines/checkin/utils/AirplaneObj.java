package com.andesairlaines.checkin.utils;

import com.andesairlaines.checkin.domain.passenger.PassengerDTO;
import com.andesairlaines.checkin.domain.seat.Seat;

import java.util.*;

public class AirplaneObj {

    private Map<Long, Map<Long, SeatObj>> seatsBySeatType;
    private List<PassengerDTO> passengers;
    private final Map<Long, List<PassengerDTO>> passengersBySeatType = new HashMap<>();
    private final List<PassengerDTO> assignedPassengers = new ArrayList<>();

    public AirplaneObj(List<Seat> seats) {
        this.seatsBySeatType = new HashMap<>();
        for (Seat seat : seats) {
            if (!seatsBySeatType.containsKey(seat.getSeatTypeId())) {
                seatsBySeatType.put(seat.getSeatTypeId(), new HashMap<>());
            }
            seatsBySeatType.get(seat.getSeatTypeId()).put(seat.getSeatId(),
                    new SeatObj(seat.getSeatColumn(), seat.getSeatRow(), seat.getSeatTypeId(), seat.getSeatId())
            );
        }
    }

    public void initPassengers(List<PassengerDTO> passengers) {
        this.passengers = passengers;
        for (PassengerDTO passenger : passengers) {
            if (passenger.seatId() != null) {
                seatsBySeatType
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
        //System.out.println(this);
        assignedPassengers.sort(Comparator.comparing(PassengerDTO::purchaseId));
        return this.assignedPassengers;
    }

    private void assignBySeatType() {
        for (Map.Entry<Long, Map<Long, SeatObj>> seatType : seatsBySeatType.entrySet()) {
            List<PassengerDTO> passengerList = passengersBySeatType.get(seatType.getKey());
            assignPassengers(seatType.getValue(), passengerList);
        }
        confirmAge();
        //confirmAge();
        copyPassengers();
    }

    private void assignPassengers(Map<Long, SeatObj> seatMap, List<PassengerDTO> passengerList) {
        List<PassengerDTO> passengersToAssign = new ArrayList<>();


        for (PassengerDTO passenger : passengerList) {
            if (passenger.seatId() == null) {
                passengersToAssign.add(passenger);
            }
        }
        passengersToAssign.sort(Comparator.comparing(PassengerDTO::purchaseId));
        Map<Long, Long> seatAssigned = new HashMap<>();
        Map<Long, SeatObj> seatUnassigned = new HashMap<>();
        for (SeatObj seat : seatMap.values()) {
            if (seat.getPassenger() != null) {
                seatAssigned.put(seat.getPassenger().purchaseId(), seat.getSeatId());
            } else {
                seatUnassigned.put(seat.getSeatId(), seat);
            }
        }

        Map<Long, SeatObj> seatUnassignedResidual = new HashMap<>(seatUnassigned);
        List<PassengerDTO> passengerListResidual = new ArrayList<>(passengersToAssign);
        for (PassengerDTO passenger : passengersToAssign) {
            if (seatAssigned.containsKey(passenger.purchaseId())) {
                Long seatId = seatAssigned.get(passenger.purchaseId());
                Long idToRemove = assignSameRowOrColumn(passenger, seatId, seatUnassignedResidual, seatMap.get(seatId).getRow(), seatMap.get(seatId).getColumn());
                passengerListResidual.remove(passenger);
                seatUnassignedResidual.remove(idToRemove);
            }
        }

        List<SeatObj> seats = new ArrayList<>(seatUnassignedResidual.values().stream().toList());
        seats.sort(Comparator.comparing(SeatObj::getRow));

        int iter = 0;
        for (SeatObj seat : seats) {
            if (iter < passengerListResidual.size()){
                seat.assignPassenger(passengerListResidual.get(iter));
            } else {
                break;
            }
            iter++;
        }

    }

    private Long assignSameRowOrColumn(PassengerDTO passenger, Long seatId, Map<Long, SeatObj> seatUnassigned, Integer seatRow, String seatColumn) {

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

        Long seatIdToAssign;
        if (availableColumns.size() != 0) {
            seatIdToAssign = searchAdjacent(availableColumns, seatColumn);
        } else if (availableRows.size() != 0) {
            seatIdToAssign = searchAdjacent(availableRows, seatRow);
        } else {
            seatIdToAssign = searchOptional(seatId, seatUnassigned);
        }
        seatUnassigned.get(seatIdToAssign).assignPassenger(passenger);

        return seatIdToAssign;
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

    private void separateSeatType() {
        for (PassengerDTO passenger : passengers) {
            if (!passengersBySeatType.containsKey(passenger.seatTypeId())) {
                passengersBySeatType.put(passenger.seatTypeId(), new ArrayList<>());
            }
            passengersBySeatType.get(passenger.seatTypeId()).add(passenger);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Long, Map<Long, SeatObj>> seatType : seatsBySeatType.entrySet()) {
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
        for (Map.Entry<Long, Map<Long, SeatObj>> seatType : seatsBySeatType.entrySet()) {
            for (Map.Entry<Long, SeatObj> seat : seatType.getValue().entrySet()) {
                if (seat.getValue().getPassenger() != null) {
                    assignedPassengers.add(seat.getValue().getPassenger()
                            .copy(seat.getKey(), seat.getValue().getColumn()+seat.getValue().getRow())
                    );
                }
            }
        }
    }

    private void confirmAge() {
        for (Map<Long, SeatObj> seats : seatsBySeatType.values()) {
            for (SeatObj seat : seats.values()) {
                if (seat.getPassenger() != null && seat.getPassenger().age() < 18) {
                    evaluateCorrect(seat, seats);
                }
            }
        }
    }

    private void evaluateCorrect(SeatObj seat, Map<Long, SeatObj> seats) {
        String column = seat.getColumn();
        Integer row = seat.getRow();
        Long purchaseId = seat.getPassenger().purchaseId();

        existAdjacent(purchaseId, column, row, seats, seat);
    }

    private void existAdjacent(Long purchaseId, String column, Integer row, Map<Long, SeatObj> seats, SeatObj seatChilldren) {

        for (SeatObj seat : seats.values()) {
            if (seat.getPassenger() != null
                    && purchaseId.equals(seat.getPassenger().purchaseId())
                    && seat.getPassenger().age() >= 18
                    && row.equals(seat.getRow())
                    && Math.abs(column.charAt(0) - seat.getColumn().charAt(0)) == 1
            ) {
                return;
            }
        }
        searchSamePurchase(seatChilldren, seats);
    }

    private void searchSamePurchase(SeatObj seatChilldren, Map<Long, SeatObj> seats) {
        List<Long> idsSamePurchase = new ArrayList<>();
        for (SeatObj seat : seats.values()) {
            if (seat.getPassenger() != null
                    && seat.getPassenger().purchaseId().equals(seatChilldren.getPassenger().purchaseId())
                    && seat.getPassenger().age() > 18
            ) {
                idsSamePurchase.add(seat.getSeatId());
            }
        }

        for (Long idSamePurchase : idsSamePurchase) {
            SeatObj rightSeat = getSeatByColumnAndRow(
                    String.valueOf((char)(seats.get(idSamePurchase).getColumn().charAt(0) + 1)),
                    seats.get(idSamePurchase).getRow(),
                    seats
            );
            SeatObj leftSeat = getSeatByColumnAndRow(
                    String.valueOf((char)(seats.get(idSamePurchase).getColumn().charAt(0) - 1)),
                    seats.get(idSamePurchase).getRow(),
                    seats
            );

            if (rightSeat != null
                    && (rightSeat.getPassenger() == null
                    || (!rightSeat.isInmutable() && rightSeat.getPassenger().age() >= 18))
            ) {
                rightSeat.swapSeats(seatChilldren);
            } else if (leftSeat != null
                    && (leftSeat.getPassenger() == null
                    || (!leftSeat.isInmutable() && leftSeat.getPassenger().age() >= 18))
            ) {
                leftSeat.swapSeats(seatChilldren);
            }
        }
    }

    private SeatObj getSeatByColumnAndRow(String column, Integer row, Map<Long, SeatObj> seats) {
        for (SeatObj seat : seats.values()) {
            if (seat.getRow().equals(row) && seat.getColumn().equals(column)){
                return seat;
            }
        }
        return null;
    }
}
