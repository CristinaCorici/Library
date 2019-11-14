package com.sda.project.entities;


public enum Category {

    LITERATURE("Literature"),
    RELIGION("Religion"),
    PSYCHOLOGY("Psychology"),
    HEALTH("Health");

    private final String displayValue;

    private Category(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

}
