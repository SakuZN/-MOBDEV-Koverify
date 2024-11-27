// ScreenParam.java
package com.example.koverify.database;

public class ScreenParam {
    private int limit;
    private int offset;

    // Constructors
    public ScreenParam(int limit, int offset) {
        this.limit = limit;
        this.offset = offset;
    }

    // Getters and Setters
    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }
}
