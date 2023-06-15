package com.andesairlaines.checkin.controller;

import com.andesairlaines.checkin.domain.passenger.DataListPassenger;
import com.andesairlaines.checkin.domain.passenger.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/passengers")
public class PassengerController {

    @Autowired
    private PassengerRepository passengerRepository;

    @GetMapping
    public List<DataListPassenger> listPassengers() {
        return passengerRepository.findAll().stream().map(DataListPassenger::new).toList();
    }
}
