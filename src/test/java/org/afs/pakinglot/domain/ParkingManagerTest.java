package org.afs.pakinglot.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParkingManagerTest {

    private ParkingManager parkingManager;
    private ParkingBoy parkingBoy1;
    private ParkingBoy parkingBoy2;

    @BeforeEach
    void setUp() {
        parkingManager = new ParkingManager();
        parkingBoy1 = new ParkingBoy(List.of(new ParkingLot(1, "Lot 1", 10)));
        parkingBoy2 = new ParkingBoy(List.of(new ParkingLot(2, "Lot 2", 10)));
        parkingManager.addParkingBoy(1, parkingBoy1);
        parkingManager.addParkingBoy(2, parkingBoy2);
    }

    @Test
    void testParkCar() {
        // Given
        String plateNumber = "ABC123";

        // When
        Ticket ticket = parkingManager.park(1, plateNumber);

        // Then
        assertNotNull(ticket);
        assertEquals(plateNumber, ticket.plateNumber());
        assertEquals(1, ticket.parkingLot());
    }

    @Test
    void testFetchCar() {
        // Given
        String plateNumber = "ABC123";
        Ticket ticket = parkingManager.park(1, plateNumber);

        // When
        Car fetchedCar = parkingManager.fetch(1, plateNumber);

        // Then
        assertNotNull(fetchedCar);
        assertEquals(plateNumber, fetchedCar.plateNumber());
    }

    @Test
    void testParkCarWithInvalidParkingBoy() {
        // Given
        String plateNumber = "XYZ789";

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> parkingManager.park(3, plateNumber));
    }

    @Test
    void testFetchCarWithInvalidParkingBoy() {
        // Given
        String plateNumber = "XYZ789";

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> parkingManager.fetch(3, plateNumber));
    }
}