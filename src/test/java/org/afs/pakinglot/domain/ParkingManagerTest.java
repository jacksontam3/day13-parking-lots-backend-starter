package org.afs.pakinglot.domain;

import org.afs.pakinglot.domain.enums.ParkingStrategyType;
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
        Ticket ticket = parkingManager.park(ParkingStrategyType.STANDARD, plateNumber);
        assertNotNull(ticket);
    }

    @Test
    void should_return_ticket_given_SMART_strategy_when_park_then_success() {
        String plateNumber = "DEF456";
        Ticket ticket = parkingManager.park(ParkingStrategyType.SMART, plateNumber);
        assertNotNull(ticket);
    }

    @Test
    void should_return_ticket_given_super_strategy_when_park_then_success() {
        String plateNumber = "GHI789";
        Ticket ticket = parkingManager.park(ParkingStrategyType.SUPER_SMART, plateNumber);
        assertNotNull(ticket);
    }

    @Test
    void should_throw_exception_given_invalid_strategy_when_park_then_fail() {
        String plateNumber = "JKL012";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            parkingManager.park(ParkingStrategyType.valueOf("INVALID"), plateNumber);
        });
        assertEquals("No enum constant org.afs.pakinglot.domain.enums.ParkingStrategyType.INVALID", exception.getMessage());
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
            parkingManager.park(ParkingStrategyType.valueOf(""), plateNumber);
        });
        assertEquals("No enum constant org.afs.pakinglot.domain.enums.ParkingStrategyType.", exception.getMessage());
    }

    @Test
    void should_return_car_given_valid_ticket_when_fetch_then_success() {
        // Given
        String plateNumber = "ABC123";
        Ticket ticket = parkingManager.park(ParkingStrategyType.STANDARD, plateNumber);
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
        Ticket ticket = parkingManager.park(ParkingStrategyType.SMART, plateNumber);
        parkingManager.fetch(ticket); // Use the ticket once
        // When
        // Then
        assertThrows(UnrecognizedTicketException.class, () -> parkingManager.fetch(ticket));
    }

}