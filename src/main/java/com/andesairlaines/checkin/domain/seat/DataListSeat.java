package com.andesairlaines.checkin.domain.seat;

public record DataListSeat(
        Long id,
        String column,
        Integer row,
        Long type
) {

    public DataListSeat(Seat seat) {
        this(
                seat.getSeatId(),
                seat.getSeatColumn(),
                seat.getSeatRow(),
                seat.getSeatTypeId()
        );
    }

}
