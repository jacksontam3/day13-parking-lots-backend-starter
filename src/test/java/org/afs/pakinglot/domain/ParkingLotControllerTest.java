package org.afs.pakinglot.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;
import org.afs.pakinglot.controller.ParkingLotController;
import org.afs.pakinglot.domain.enums.ParkingStrategyType;
import org.afs.pakinglot.domain.exception.UnrecognizedTicketException;
import org.afs.pakinglot.domain.strategies.SequentiallyStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

class ParkingLotControllerTest {

    @Mock
    private ParkingManager parkingManager;

    @InjectMocks
    private ParkingLotController parkingLotController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ParkingLot plazaPark = new ParkingLot(1, "The Plaza Park", 9);
        ParkingLot cityMallGarage = new ParkingLot(2, "City Mall Garage", 12);
        ParkingLot officeTowerParking = new ParkingLot(3, "Office Tower Parking", 9);
        List<ParkingLot> parkingLots = List.of(plazaPark, cityMallGarage, officeTowerParking);
        ParkingBoy standardParkingBoy = new ParkingBoy(parkingLots, new SequentiallyStrategy());
        when(parkingManager.getStandardParkingBoy()).thenReturn(standardParkingBoy);
    }

    @Test
    void should_return_parking_lot_info_given_get_all_parking_lots_when_success() {
        // Given
        List<ParkingLot> parkingLotInfos = List.of(
                new ParkingLot(1, "The Plaza Park", 9),
                new ParkingLot(2, "City Mall Garage", 12),
                new ParkingLot(3, "Office Tower Parking", 9)
        );

        // When
        ResponseEntity<List<ParkingLotInfo>> response = parkingLotController.getAllParkingLots();

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(parkingLotInfos.size(), response.getBody().size());
    }

    @Test
    void should_return_ticket_given_valid_strategy_and_plate_number_when_park_then_success() {
        // Given
        String plateNumber = "ABC123";
        Ticket ticket = new Ticket(plateNumber, 1, 1);
        when(parkingManager.park(ParkingStrategyType.STANDARD, plateNumber)).thenReturn(ticket);
        ParkingLotDto parkingLotDto = new ParkingLotDto(plateNumber, ParkingStrategyType.STANDARD);

        // When
        ResponseEntity<Ticket> response = parkingLotController.park(parkingLotDto);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(ticket, response.getBody());
    }

    @Test
    void should_return_car_given_valid_plate_number_when_fetch_then_success() {
        // Given
        String plateNumber = "ABC123";
        Ticket ticket = new Ticket(plateNumber, 1, 1);
        Car car = new Car(plateNumber);
        when(parkingManager.findTicketByPlateNumber(plateNumber)).thenReturn(ticket);
        when(parkingManager.fetch(ticket)).thenReturn(car);
        ParkingLotDto parkingLotDto = new ParkingLotDto(plateNumber);

        // When
        ResponseEntity<Car> response = parkingLotController.fetch(parkingLotDto);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(car, response.getBody());
    }

    @Test
    void should_throw_exception_given_invalid_plate_number_when_fetch_then_fail() {
        // Given
        String plateNumber = "INVALID";
        when(parkingManager.findTicketByPlateNumber(plateNumber)).thenThrow(UnrecognizedTicketException.class);
        ParkingLotDto parkingLotDto = new ParkingLotDto(plateNumber);
        // When
        // Then
        assertThrows(UnrecognizedTicketException.class, () -> parkingLotController.fetch(parkingLotDto));
    }
}