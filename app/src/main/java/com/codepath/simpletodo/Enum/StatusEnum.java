package com.codepath.simpletodo.Enum;

/**
 * Created by BuuPV on 2/5/2017.
 */

public enum StatusEnum {
    DONE(0), ONGOING(1);

    private final int value;
    private StatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
