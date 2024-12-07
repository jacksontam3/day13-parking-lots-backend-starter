package org.afs.pakinglot.controller;

import java.util.List;
import org.afs.pakinglot.domain.Car;
import org.afs.pakinglot.domain.ParkingLotDto;
import org.afs.pakinglot.domain.ParkingLotInfo;
import org.afs.pakinglot.domain.ParkingManager;
import org.afs.pakinglot.domain.Ticket;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parkinglot")
@CrossOrigin(origins = "http://localhost:3000")
public class ParkingLotController {

    private final ParkingManager parkingManager;

    public ParkingLotController(ParkingManager parkingManager) {
        this.parkingManager = parkingManager;
    }

    @GetMapping
    public ResponseEntity<List<ParkingLotInfo>> getAllParkingLots() {
        List<ParkingLotInfo> parkingLotInfos = parkingManager
                .getStandardParkingBoy()
                .getParkingLots()
                .stream().map(ParkingLotInfo::of)
                .toList();

        return ResponseEntity.ok(parkingLotInfos);
    }

    @PostMapping("/park")
    public ResponseEntity<Ticket> park(@RequestBody ParkingLotDto parkingLotDto) {
        Ticket ticket = parkingManager.park(parkingLotDto.getParkingStrategyType(), parkingLotDto.getPlateNumber());
        return ResponseEntity.ok(ticket);
    }

    @PostMapping("/fetch")
    public ResponseEntity<Car> fetch(@RequestBody ParkingLotDto parkingLotDto) {
        Ticket ticket = parkingManager.findTicketByPlateNumber(parkingLotDto.getPlateNumber());
        Car car = parkingManager.fetch(ticket);
        return ResponseEntity.ok(car);
    }

}