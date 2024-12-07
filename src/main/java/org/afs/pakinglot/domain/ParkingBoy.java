package org.afs.pakinglot.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.afs.pakinglot.domain.exception.UnrecognizedTicketException;
import org.afs.pakinglot.domain.strategies.ParkingStrategy;
import org.afs.pakinglot.domain.strategies.SequentiallyStrategy;

public class ParkingBoy {
    protected List<ParkingLot> parkingLots = new ArrayList<>();
    private ParkingStrategy parkingStrategy = new SequentiallyStrategy();

    public ParkingBoy(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
    }

    public ParkingBoy(ParkingLot parkingLot) {
        parkingLots.add(parkingLot);
    }

    public ParkingBoy(List<ParkingLot> parkingLots, ParkingStrategy parkingStrategy) {
        this.parkingLots = parkingLots;
        this.parkingStrategy = parkingStrategy;
    }

    public Ticket park(Car car) {
        return parkingStrategy.findParkingLot(parkingLots).park(car);
    }

    public Ticket park(String plateNumber) {
        return park(new Car(plateNumber));
    }

    public Car fetch(Ticket ticket) {
        ParkingLot parkingLotOfTheTicket = parkingLots.stream()
                .filter(parkingLot -> parkingLot.contains(ticket))
                .findFirst()
                .orElseThrow(UnrecognizedTicketException::new);
        return parkingLotOfTheTicket.fetch(ticket);
    }

    public Car fetch(String plateNumber) {
        for (ParkingLot parkingLot : parkingLots) {
            try {
                return parkingLot.fetch(plateNumber);
            } catch (UnrecognizedTicketException ignored) {
            }
        }
        throw new UnrecognizedTicketException();
    }

    public Map<Integer, Map<Integer, String>> getParkingLotsStatus() {
        return parkingLots.stream()
                .collect(Collectors.toMap(
                        ParkingLot::getId,
                        ParkingLot::getParkingStatus
                ));
    }

    public List<ParkingLot> getParkingLots() {
        return parkingLots;
    }
}