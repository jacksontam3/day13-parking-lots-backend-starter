package org.afs.pakinglot.domain;

import org.afs.pakinglot.domain.exception.NoAvailablePositionException;
import org.afs.pakinglot.domain.exception.UnrecognizedTicketException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParkingBoyTest {
    @Test
    void should_find_two_cars_when_fetch_the_car_twice_with_two_ticket() {
        // Given
        ParkingLot parkingLot1 = new ParkingLot();
        ParkingLot parkingLot2 = new ParkingLot();
        Car car1 = new Car(CarPlateGenerator.generatePlate());
        Car car2 = new Car(CarPlateGenerator.generatePlate());
        Ticket ticket1 = parkingLot1.park(car1);
        Ticket ticket2 = parkingLot1.park(car2);
        ParkingBoy parkingBoy = new ParkingBoy(List.of(parkingLot1, parkingLot2));
        // When
        var fetchedCar1 = parkingBoy.fetch(ticket1);
        var fetchedCar2 = parkingBoy.fetch(ticket2);
        // Then
        assertEquals(car1, fetchedCar1);
        assertEquals(car2, fetchedCar2);
    }

    @Test
    void should_find_two_cars_when_fetch_the_car_twice_with_two_ticket_in_different_parking_lots() {
        // Given
        ParkingLot parkingLot1 = new ParkingLot();
        ParkingLot parkingLot2 = new ParkingLot();
        Car car1 = new Car(CarPlateGenerator.generatePlate());
        Car car2 = new Car(CarPlateGenerator.generatePlate());
        Ticket ticket1 = parkingLot1.park(car1);
        Ticket ticket2 = parkingLot1.park(car2);
        ParkingBoy parkingBoy = new ParkingBoy(List.of(parkingLot1, parkingLot2));
        // When
        var fetchedCar1 = parkingBoy.fetch(ticket1);
        var fetchedCar2 = parkingBoy.fetch(ticket2);
        // Then
        assertEquals(car1, fetchedCar1);
        assertEquals(car2, fetchedCar2);
    }

    @Test
    void should_return_nothing_with_error_message_when_fetch_given_2_parking_lots_and_an_unrecognized_ticket(){
        // Given
        ParkingLot parkingLot1 = new ParkingLot();
        ParkingLot parkingLot2 = new ParkingLot();
        parkingLot1.park(new Car(CarPlateGenerator.generatePlate()));
        parkingLot2.park(new Car(CarPlateGenerator.generatePlate()));
        Ticket wrongTicket = new Ticket(CarPlateGenerator.generatePlate(), 1, 1 );
        ParkingBoy parkingBoy = new ParkingBoy(List.of(parkingLot1, parkingLot2));
        // When
        // Then
        assertThrows(UnrecognizedTicketException.class, () -> parkingBoy.fetch(wrongTicket));
    }

    @Test
    void should_return_nothing_with_error_message_when_fetch_given_2_parking_lots_and_an_used_ticket(){
        // Given
        ParkingLot parkingLot1 = new ParkingLot();
        ParkingLot parkingLot2 = new ParkingLot();
        Ticket ticket1 = parkingLot1.park(new Car(CarPlateGenerator.generatePlate()));
        ParkingBoy parkingBoy = new ParkingBoy(List.of(parkingLot1, parkingLot2));
        // When
        // Then
        assertNotNull(parkingBoy.fetch(ticket1));
        assertThrows(UnrecognizedTicketException.class, () -> parkingBoy.fetch(ticket1));
    }

    @Test
    void should_return_no_available_position_error_when_park_given_all_parking_lots_are_full(){
        // Given
        ParkingLot parkingLot1 = new ParkingLot();
        for (int i = 0; i < parkingLot1.getCapacity(); i++) {
            parkingLot1.park(new Car(CarPlateGenerator.generatePlate() + i));
        }
        ParkingLot parkingLot2 = new ParkingLot();
        for (int i = 0; i < parkingLot1.getCapacity(); i++) {
            parkingLot2.park(new Car(CarPlateGenerator.generatePlate() + i));
        }
        ParkingBoy parkingBoy = new ParkingBoy(List.of(parkingLot1, parkingLot2));
        Car car = new Car(CarPlateGenerator.generatePlate());
        // When
        // Then
        assertThrows(NoAvailablePositionException.class, () -> parkingBoy.park(car));
    }

    @Test
    void should_return_aggregated_parking_lots_status() {
        // Given
        ParkingLot parkingLot1 = new ParkingLot(1, "Lot 1", 10);
        ParkingLot parkingLot2 = new ParkingLot(2, "Lot 2", 10);
        Car car1 = new Car("ABC123");
        Car car2 = new Car("XYZ789");
        parkingLot1.park(car1);
        parkingLot2.park(car2);
        ParkingBoy parkingBoy = new ParkingBoy(List.of(parkingLot1, parkingLot2));
        // When
        Map<Integer, Map<Integer, String>> status = parkingBoy.getParkingLotsStatus();
        // Then
        assertEquals(2, status.size());
        assertEquals("ABC123", status.get(1).get(1));
        assertEquals("XYZ789", status.get(2).get(1));
    }

    @Test
    void should_return_empty_status_when_no_cars_parked_in_any_lot() {
        // Given
        ParkingLot parkingLot1 = new ParkingLot(1, "Lot 1", 10);
        ParkingLot parkingLot2 = new ParkingLot(2, "Lot 2", 10);
        ParkingBoy parkingBoy = new ParkingBoy(List.of(parkingLot1, parkingLot2));
        // When
        Map<Integer, Map<Integer, String>> status = parkingBoy.getParkingLotsStatus();
        // Then
        assertTrue(status.get(1).isEmpty());
        assertTrue(status.get(2).isEmpty());
    }




}
