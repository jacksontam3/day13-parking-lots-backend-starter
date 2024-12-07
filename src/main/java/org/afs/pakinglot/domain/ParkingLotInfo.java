package org.afs.pakinglot.domain;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotInfo {

    private int id;
    private String name;
    private List<Ticket> tickets = new ArrayList<>();

    public ParkingLotInfo(int id, String name, List<Ticket> tickets) {
        this.id = id;
        this.name = name;
        this.tickets = tickets;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public static ParkingLotInfo of(ParkingLot parkingLot) {
        return new ParkingLotInfo(parkingLot.getId(), parkingLot.getName(), parkingLot.getTickets());
    }
}
