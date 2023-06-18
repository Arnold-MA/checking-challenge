package com.andesairlaines.checkin.controller;

import com.andesairlaines.checkin.domain.flight.DataFlight;
import com.andesairlaines.checkin.domain.flight.DataListFlight;
import com.andesairlaines.checkin.domain.flight.Flight;
import com.andesairlaines.checkin.domain.flight.FlightRepository;
import com.andesairlaines.checkin.domain.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/flight")
public class FlightController {

    @Autowired
    private FlightRepository flightRepository;

    /*@GetMapping
    public List<DataListFlight> listFlights() {
        return flightRepository.findAll().stream().map(DataListFlight::new).toList();
    }*/

    @GetMapping
    public ResponseData listFlights() {
        return new ResponseData(HttpStatus.OK.value(), flightRepository.findAll().stream().map(DataListFlight::new).toList());
    }

    @GetMapping("/{id}/passengers")
    public ResponseEntity<ResponseData> flight(@PathVariable Long id) {
        return ResponseEntity.ok(new ResponseData(HttpStatus.OK.value(), new DataFlight(flightRepository.getReferenceById(id))));
    }


}
