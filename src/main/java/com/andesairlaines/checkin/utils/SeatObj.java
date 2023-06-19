package com.andesairlaines.checkin.utils;

import com.andesairlaines.checkin.domain.passenger.PassengerDTO;

public class SeatObj {

    private String column;
    private Integer row;
    private Long type;
    private PassengerDTO passenger = null;
    private Boolean inmutable;
    private Long seatId;

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public SeatObj(String column, Integer row, Long type, Long seatId) {
        this.column = column;
        this.row = row;
        this.type = type;
        this.inmutable = false;
        this.seatId = seatId;
    }

    public Boolean isInmutable() {
        return inmutable;
    }

    public String getColumn() {
        return column;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public PassengerDTO getPassenger() {
        return this.passenger;
    }

    public void assignPassenger(PassengerDTO passenger) {
        this.passenger = passenger;
    }

    public void assignInitialPassenger(PassengerDTO passenger) {
        this.passenger = passenger;
        this.inmutable = true;
    }

    public void unassignPassenger(PassengerDTO passengerDTO) {
        this.passenger = null;
    }

    public void swapSeats(SeatObj seat) {
        PassengerDTO passengerTmp = this.passenger.copy();
        this.passenger = seat.passenger;
        seat.assignPassenger(passengerTmp);
    }
}
