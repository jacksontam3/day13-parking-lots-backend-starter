package org.afs.pakinglot.domain;

import java.util.HashMap;
import java.util.Map;

public class ParkingManager {
    private final Map<Integer, ParkingBoy> parkingBoys = new HashMap<>();

    public void addParkingBoy(int id, ParkingBoy parkingBoy) {
        parkingBoys.put(id, parkingBoy);
    }

    public ParkingBoy getParkingBoy(int id) {
        return parkingBoys.get(id);
    }

    public Ticket park(int parkingBoyID, String plateNumber) {
        ParkingBoy parkingBoy = getParkingBoy(parkingBoyID);
        if (parkingBoy == null) {
            throw new IllegalArgumentException("Parking boy not found");
        }
        return parkingBoy.park(plateNumber);
    }

    public Car fetch(int parkingBoyID, String plateNumber) {
        ParkingBoy parkingBoy = getParkingBoy(parkingBoyID);
        if (parkingBoy == null) {
            throw new IllegalArgumentException("Parking boy not found");
        }
        return parkingBoy.fetch(plateNumber);
    }
}