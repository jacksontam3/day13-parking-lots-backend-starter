package org.afs.pakinglot.domain;

import org.afs.pakinglot.domain.enums.ParkingStrategyType;

public class ParkingLotDto {
    private String plateNumber;
    private ParkingStrategyType parkingStrategyType;

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

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public ParkingStrategyType getParkingStrategyType() {
        return parkingStrategyType;
    }

    public void setParkingStrategyType(ParkingStrategyType parkingStrategyType) {
        this.parkingStrategyType = parkingStrategyType;
    }

    public void setParkingStrategyType(String parkingStrategyType) {
        this.parkingStrategyType = ParkingStrategyType.valueOf(parkingStrategyType.toUpperCase());
    }
}