package com.codepath.simpletodo.Enum;

/**
 * Created by BuuPV on 2/5/2017.
 */

public enum TaskPriorityEnum {
    LOW(0), NORMAL(1), HIGH(2);

    private final int value;
    private TaskPriorityEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
