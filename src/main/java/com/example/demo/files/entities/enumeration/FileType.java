package com.example.demo.files.entities.enumeration;

/**
 * The enum LpFile type.
 */
public enum FileType {

    /**
     * Photo file type.
     */
    PHOTO(0),
    /**
     * Pdf file type.
     */
    PDF(1), //For the proof document
    /**
     * Video file type.
     */
    VIDEO(2);

    private int value;

    FileType(int value) {
        this.value = value;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public int getValue() { return value; }

    /**
     * Sets value.
     *
     * @param value the value
     */
    public void setValue(int value) { this.value = value; }

    /**
     * For value file type.
     *
     * @param value the value
     * @return the file type
     */
    public static FileType forValue(int value) {
        for (FileType fileType : values()) {
            if (fileType.getValue() == value) {
                return fileType;
            }
        }
        return null;
    }

    /**
     * For string file type.
     *
     * @param value the value
     * @return the file type
     */
    public static FileType forString(String value) {
        for (FileType fileType : values()) {
            if (fileType.toString().equals(value)) {
                return fileType;
            }
        }
        return null;
    }
}

