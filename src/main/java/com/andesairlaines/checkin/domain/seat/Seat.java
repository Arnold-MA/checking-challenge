package com.andesairlaines.checkin.domain.seat;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode(of = "seatId")
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Seat")
@Table(name = "seat")
public class Seat {

    @Id
    private Long seatId;
    private String seatColumn;
    private Integer seatRow;
    private Long seatTypeId;
    private Long airplaneId;

}
