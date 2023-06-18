package com.andesairlaines.checkin.domain.seat;

import com.andesairlaines.checkin.domain.airplane.Airplane;
import jakarta.persistence.*;
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "airplaneId")
    private Airplane airplane;

}
