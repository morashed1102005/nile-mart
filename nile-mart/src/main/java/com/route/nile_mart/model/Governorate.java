package com.route.nile_mart.model;

import lombok.Getter;

public enum Governorate {
    CAIRO(Zone.GREATER_CAIRO, "Cairo"),
    GIZA(Zone.GREATER_CAIRO, "Giza"),
    QALYUBIA(Zone.GREATER_CAIRO, "Qalyubia"),

    ALEXANDRIA(Zone.ALEX_DELTA, "Alexandria"),
    BEHEIRA(Zone.ALEX_DELTA, "Beheira"),
    KAFR_EL_SHEIKH(Zone.ALEX_DELTA, "Kafr El Sheikh"),
    DAKAHLIA(Zone.ALEX_DELTA, "Dakahlia"),
    DAMIETTA(Zone.ALEX_DELTA, "Damietta"),
    GHARBIA(Zone.ALEX_DELTA, "Gharbia"),
    MONUFIA(Zone.ALEX_DELTA, "Monufia"),
    SHARQIA(Zone.ALEX_DELTA, "Sharqia"),

    PORT_SAID(Zone.CANAL, "Port Said"),
    ISMAILIA(Zone.CANAL, "Ismailia"),
    SUEZ(Zone.CANAL, "Suez"),

    FAIYUM(Zone.UPPER_EGYPT, "Faiyum"),
    BENI_SUEF(Zone.UPPER_EGYPT, "Beni Suef"),
    MINYA(Zone.UPPER_EGYPT, "Minya"),
    ASYUT(Zone.UPPER_EGYPT, "Asyut"),
    SOHAG(Zone.UPPER_EGYPT, "Sohag"),
    QENA(Zone.UPPER_EGYPT, "Qena"),
    LUXOR(Zone.UPPER_EGYPT, "Luxor"),
    ASWAN(Zone.UPPER_EGYPT, "Aswan"),

    MATROUH(Zone.REMOTE, "Matrouh"),
    NORTH_SINAI(Zone.REMOTE, "North Sinai"),
    SOUTH_SINAI(Zone.REMOTE, "South Sinai"),
    RED_SEA(Zone.REMOTE, "Red Sea"),
    NEW_VALLEY(Zone.REMOTE, "New Valley");

    private final Zone zone;
    @Getter
    private final String label;

    Governorate(Zone zone, String label) {
        this.zone = zone;
        this.label = label;
    }

    public Zone zone() {
        return zone;
    }
}