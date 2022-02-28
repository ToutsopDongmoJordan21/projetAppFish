package com.example.demo.files.entities.enumeration;

public enum EntityFileType {

    USER(0);

    private int value;

    EntityFileType(int value) {
        this.value = value;
    }

    public int getValue() { return value; }

    public void setValue(int value) { this.value = value; }

    public static EntityFileType forValue(int value) {
        for (EntityFileType docType : values()) {
            if (docType.getValue() == value) {
                return docType;
            }
        }
        return null;
    }

    public static EntityFileType forString(String value) {
        for (EntityFileType docType : values()) {
            if (docType.toString().equals(value)) {
                return docType;
            }
        }
        return null;
    }
}

