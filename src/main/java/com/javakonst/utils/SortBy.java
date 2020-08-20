package com.javakonst.utils;

public enum SortBy {
    SECID(0),
    REGNUMBER(1),
    NAME(2),
    EMITENT_TITLE(3),
    TRADEDATE(4),
    NUMTRADES(5),
    OPEN(6),
    CLOSE(7);

    private final int index;

    private SortBy(int index) {
        this.index = index;
    }

    public int getValue() {
        return this.index;
    }
}
