package com;

public enum Difficulty {
    EASY(10, 10, 10),
    NORMAL(8, 8, 3),
    HARD(8, 8, 5),
    NONE(0, 0, 0);



    private final int rows;
    private final int columns;
    private final int bombs;

    Difficulty(int rows, int columns, int bombs){
        this.rows = rows;
        this.columns = columns;
        this.bombs = bombs;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getBombs() {
        return bombs;
    }

}
