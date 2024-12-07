package org.afs.pakinglot.domain;

import org.afs.pakinglot.domain.exception.UnrecognizedTicketException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParkingManagerTest {

    private ParkingManager parkingManager;

    @BeforeEach
    void setUp() {
        parkingManager = new ParkingManager();
    }

    @Test
    void should_return_ticket_given_standard_strategy_when_park_then_success() {
        String plateNumber = "ABC123";
        Ticket ticket = parkingManager.park("STANDARD", plateNumber);
        assertNotNull(ticket);
    }

    @Test
    void should_return_ticket_given_smart_strategy_when_park_then_success() {
        String plateNumber = "DEF456";
        Ticket ticket = parkingManager.park("SMART", plateNumber);
        assertNotNull(ticket);
    }

    @Test
    void should_return_ticket_given_super_strategy_when_park_then_success() {
        String plateNumber = "GHI789";
        Ticket ticket = parkingManager.park("SUPER", plateNumber);
        assertNotNull(ticket);
    }

    @Test
    void should_throw_exception_given_invalid_strategy_when_park_then_fail() {
        String plateNumber = "JKL012";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            parkingManager.park("INVALID", plateNumber);
        });
        assertEquals("Invalid parking strategy type: INVALID", exception.getMessage());
    }

    @Test
    void should_throw_exception_given_null_strategy_when_park_then_fail() {
        String plateNumber = "MNO345";
        Exception exception = assertThrows(Exception.class, () -> {
            parkingManager.park(null, plateNumber);
        });
        assertEquals("Invalid parking strategy type: null", exception.getMessage());
    }

    @Test
    void should_throw_exception_given_empty_strategy_when_park_then_fail() {
        String plateNumber = "PQR678";
        Exception exception = assertThrows(Exception.class, () -> {
            parkingManager.park("", plateNumber);
        });
        assertEquals("Invalid parking strategy type: ", exception.getMessage());
    }

    @Test
    void should_return_car_given_valid_ticket_when_fetch_then_success() {
        // Given
        String plateNumber = "ABC123";
        Ticket ticket = parkingManager.park("STANDARD", plateNumber);
        // When
        Car car = parkingManager.fetch(ticket);
        // Then
        assertNotNull(car);
        assertEquals(plateNumber, car.plateNumber());
    }

    @Test
    void should_throw_exception_given_invalid_ticket_when_fetch_then_fail() {
        // Given
        Ticket invalidTicket = new Ticket("INVALID", 1, 1);
        // When
        // Then
        assertThrows(UnrecognizedTicketException.class, () -> parkingManager.fetch(invalidTicket));
    }

    @Test
    void should_throw_exception_given_null_ticket_when_fetch_then_fail() {
        // Given
        Ticket nullTicket = null;
        // When
        // Then
        assertThrows(UnrecognizedTicketException.class, () -> parkingManager.fetch(nullTicket));
    }

    @Test
    void should_throw_exception_given_used_ticket_when_fetch_then_fail() {
        // Given
        String plateNumber = "DEF456";
        Ticket ticket = parkingManager.park("SMART", plateNumber);
        parkingManager.fetch(ticket); // Use the ticket once
        // When
        // Then
        assertThrows(UnrecognizedTicketException.class, () -> parkingManager.fetch(ticket));
    }

}