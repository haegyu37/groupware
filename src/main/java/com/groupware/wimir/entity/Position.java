package com.groupware.wimir.entity;

public enum Position {
    사원(9),
    대리(8),
    과장(7),
    차장(6),
    부장(5),
    상무(4),
    전무(3),
    부사장(2),
    사장(1);

    private final int value;

    Position(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
