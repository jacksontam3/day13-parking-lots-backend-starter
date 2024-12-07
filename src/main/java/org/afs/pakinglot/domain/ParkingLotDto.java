package org.afs.pakinglot.domain;
import org.afs.pakinglot.domain.enums.ParkingStrategyType;

public class ParkingLotDto {
    String plateNumber;
    ParkingStrategyType parkingStrategyType;

    public ParkingLotDto(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public ParkingLotDto(String plateNumber, ParkingStrategyType parkingStrategyType) {
        this.plateNumber = plateNumber;
        this.parkingStrategyType = parkingStrategyType;
    }

    public ParkingLotDto() {
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public ParkingStrategyType getStrategyType() {
        return parkingStrategyType;
    }
}