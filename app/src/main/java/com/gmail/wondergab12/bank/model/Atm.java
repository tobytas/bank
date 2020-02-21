package com.gmail.wondergab12.bank.model;

import java.io.Serializable;

public class Atm implements Serializable {

    private String id;
    private String area;
    private String cityType;
    private String city;
    private String addressType;
    private String address;
    private String house;
    private String installPlace;
    private String workTime;
    private String gpsX;
    private String gpsY;
    private String installPlaceFull;
    private String workTimeFull;
    private String atmType;
    private String atmError;
    private String currency;
    private String cashIn;
    private String atmPrinter;

    private Atm(String id, String area,
                String cityType, String city,
                String addressType, String address,
                String house, String installPlace,
                String workTime,
                String gpsX, String gpsY,
                String installPlaceFull, String workTimeFull,
                String atmType, String atm_error,
                String currency, String cashIn, String atmPrinter) {
        this.id = id;
        this.area = area;
        this.cityType = cityType;
        this.city = city;
        this.addressType = addressType;
        this.address = address;
        this.house = house;
        this.installPlace = installPlace;
        this.workTime = workTime;
        this.gpsX = gpsX;
        this.gpsY = gpsY;
        this.installPlaceFull = installPlaceFull;
        this.workTimeFull = workTimeFull;
        this.atmType = atmType;
        this.atmError = atm_error;
        this.currency = currency;
        this.cashIn = cashIn;
        this.atmPrinter = atmPrinter;
    }

    public String getId() {
        return id;
    }

    public String getArea() {
        return area;
    }

    public String getCityType() {
        return cityType;
    }

    public String getCity() {
        return city;
    }

    public String getAddressType() {
        return addressType;
    }

    public String getAddress() {
        return address;
    }

    public String getHouse() {
        return house;
    }

    public String getInstallPlace() {
        return installPlace;
    }

    public String getWorkTime() {
        return workTime;
    }

    public String getGpsX() {
        return gpsX;
    }

    public String getGpsY() {
        return gpsY;
    }

    public String getInstallPlaceFull() {
        return installPlaceFull;
    }

    public String getWorkTimeFull() {
        return workTimeFull;
    }

    public String getAtmType() {
        return atmType;
    }

    public String getAtmError() {
        return atmError;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCashIn() {
        return cashIn;
    }

    public String getAtmPrinter() {
        return atmPrinter;
    }

    public static class Builder {

        private String id;
        private String area;
        private String cityType;
        private String city;
        private String addressType;
        private String address;
        private String house;
        private String installPlace;
        private String workTime;
        private String gpsX;
        private String gpsY;
        private String installPlaceFull;
        private String workTimeFull;
        private String atmType;
        private String atmError;
        private String currency;
        private String cashIn;
        private String atmPrinter;

        public Builder() {
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setArea(String area) {
            this.area = area;
            return this;
        }

        public Builder setCityType(String cityType) {
            this.cityType = cityType;
            return this;
        }

        public Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public Builder setAddressType(String addressType) {
            this.addressType = addressType;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setHouse(String house) {
            this.house = house;
            return this;
        }

        public Builder setInstallPlace(String installPlace) {
            this.installPlace = installPlace;
            return this;
        }

        public Builder setWorkTime(String workTime) {
            this.workTime = workTime;
            return this;
        }

        public Builder setGpsX(String gpsX) {
            this.gpsX = gpsX;
            return this;
        }

        public Builder setGpsY(String gpsY) {
            this.gpsY = gpsY;
            return this;
        }

        public Builder setInstallPlaceFull(String installPlaceFull) {
            this.installPlaceFull = installPlaceFull;
            return this;
        }

        public Builder setWorkTimeFull(String workTimeFull) {
            this.workTimeFull = workTimeFull;
            return this;
        }

        public Builder setAtmType(String atmType) {
            this.atmType = atmType;
            return this;
        }

        public Builder setAtmError(String atm_error) {
            this.atmError = atm_error;
            return this;
        }

        public Builder setCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        public Builder setCashIn(String cashIn) {
            this.cashIn = cashIn;
            return this;
        }

        public Builder setAtmPrinter(String atmPrinter) {
            this.atmPrinter = atmPrinter;
            return this;
        }

        public Atm build() {
            return new Atm(id, area,
                    cityType, city,
                    addressType, address,
                    house, installPlace,
                    workTime,
                    gpsX, gpsY,
                    installPlaceFull, workTimeFull,
                    atmType, atmError,
                    currency, cashIn, atmPrinter);
        }
    }

}
