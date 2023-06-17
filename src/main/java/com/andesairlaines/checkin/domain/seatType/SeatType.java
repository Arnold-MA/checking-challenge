package com.andesairlaines.checkin.domain.seatType;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode(of = "seatTypeId")
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "SeatType")
@Table(name = "seat_type")
public class SeatType {

    @Id
    private Long seatTypeId;
    private String name;

}
