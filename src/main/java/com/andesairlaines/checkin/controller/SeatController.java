package com.andesairlaines.checkin.controller;

import com.andesairlaines.checkin.domain.flight.DataListFlight;
import com.andesairlaines.checkin.domain.response.ResponseData;
import com.andesairlaines.checkin.domain.seat.DataListSeat;
import com.andesairlaines.checkin.domain.seat.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seat")
public class SeatController {

    /*@Autowired
    private SeatRepository seatRepository;


    @GetMapping("/{id}")
    public ResponseData listSeats(@PathVariable Long id) {;
        return new ResponseData(HttpStatus.OK.value(), seatRepository.findByAirplaneId(id).stream().map(DataListSeat::new).toList());
    }*/
}
