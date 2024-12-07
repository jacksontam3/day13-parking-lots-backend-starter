package org.afs.pakinglot.domain;

import org.afs.pakinglot.domain.exception.UnrecognizedTicketException;
import org.afs.pakinglot.domain.strategies.AvailableRateStrategy;
import org.afs.pakinglot.domain.strategies.MaxAvailableStrategy;
import org.afs.pakinglot.domain.strategies.SequentiallyStrategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

@Component
public class ParkingManager {
    private final ParkingBoy standardParkingBoy;
    private final ParkingBoy smartParkingBoy;
    private final ParkingBoy superParkingBoy;

    private final Map<Integer, ParkingBoy> parkingBoys = new HashMap<>();
    private static final Pattern LICENSE_PLATE_PATTERN = Pattern.compile("^[A-Z]{2}-\\d{4}$");

    public ParkingManager() {
        ParkingLot plazaPark = new ParkingLot(1, "The Plaza Park", 9);
        ParkingLot cityMallGarage = new ParkingLot(2, "City Mall Garage", 12);
        ParkingLot officeTowerParking = new ParkingLot(3, "Office Tower Parking", 9);
        List<ParkingLot> parkingLots = List.of(plazaPark, cityMallGarage, officeTowerParking);
        this.standardParkingBoy = new ParkingBoy(parkingLots, new SequentiallyStrategy());
        this.smartParkingBoy = new ParkingBoy(parkingLots, new MaxAvailableStrategy());
        this.superParkingBoy = new ParkingBoy(parkingLots, new AvailableRateStrategy());
    }

    public void addParkingBoy(int id, ParkingBoy parkingBoy) {
        parkingBoys.put(id, parkingBoy);
    }

    public ParkingBoy getParkingBoy(int id) {
        return parkingBoys.get(id);
    }

    public Ticket park(String strategyType, String plateNumber) {
        Car car = new Car(plateNumber);
        if (strategyType == null) {
            throw new IllegalArgumentException("Invalid parking strategy type: null");
        }
        return switch (strategyType.toUpperCase()) {
            case "STANDARD" -> standardParkingBoy.park(car);
            case "SMART" -> smartParkingBoy.park(car);
            case "SUPER" -> superParkingBoy.park(car);
            default -> throw new IllegalArgumentException("Invalid parking strategy type: " + strategyType);
        };
    }

    public Car fetch(Ticket ticket) {
        for (ParkingBoy parkingBoy : List.of(standardParkingBoy, smartParkingBoy, superParkingBoy)) {
            try {
                return parkingBoy.fetch(ticket);
            } catch (UnrecognizedTicketException ignored) {
                // Continue to the next parking boy
            }
        }
        throw new UnrecognizedTicketException();
    }

    private void validateLicensePlate(String plateNumber) {
        if (plateNumber == null || !LICENSE_PLATE_PATTERN.matcher(plateNumber).matches()) {
            throw new IllegalArgumentException("Invalid license plate format");
        }
    }

    public Ticket findTicketByPlateNumber(String plateNumber) {
        return Stream.of(standardParkingBoy, smartParkingBoy, superParkingBoy)
                .map(parkingBoy -> parkingBoy.getParkingLots().stream()
                        .map(parkingLot -> parkingLot.findTicketByPlateNumber(plateNumber))
                        .filter(Objects::nonNull)
                        .findFirst()
                        .orElse(null))
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(UnrecognizedTicketException::new);
    }
    public ParkingBoy getStandardParkingBoy() {
        return standardParkingBoy;
    }
    public ParkingBoy getSmartParkingBoy() {
        return smartParkingBoy;
    }
    public ParkingBoy getSuperParkingBoy() {
        return superParkingBoy;
    }
}