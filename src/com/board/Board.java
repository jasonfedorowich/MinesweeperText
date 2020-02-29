package com.board;

import com.sprite.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Board {


    private final int numberOfBombs;
    private final int rows;
    private final int columns;
    private int numberOfUnseenCells;
    private Sprite[][] grid;



    private List<Sprite> bombs;

    public Board(int rows, int columns, int numberOfBombs){
        bombs = new ArrayList<>();
        grid = new Sprite[rows][columns];
        this.numberOfBombs = numberOfBombs;
        this.rows = rows;
        this.columns = columns;
        this.numberOfUnseenCells = rows*columns;

    }

    public int getNumberOfBombs() {
        return numberOfBombs;
    }


    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public List<Sprite> getBombs() {
        return bombs;
    }

    public Sprite getSprite(int row, int col) {
        if(row > rows || row < 0 ||
                col > columns || col < 0)
            throw new IllegalArgumentException(String.format("Row: %d, Column: %d is out of bounds", row, col));
        return grid[row][col];
    }

    public void add(int row, int col, Sprite sprite) {
        if(row > rows || row < 0 ||
                col > columns || col < 0)
            throw new IllegalArgumentException(String.format("Row: %d, Column: %d is out of bounds", row, col));
        grid[row][col] = sprite;
    }

    public void addBomb(Sprite sprite) {
        if(bombs.size() == numberOfBombs)
            throw new IllegalArgumentException("Cannot add anymore bombs");
        bombs.add(sprite);
    }

    //TODO will not work in framwork
    public void draw() {
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                System.out.print(grid[i][j].draw());
            }
            System.out.println();
        }

    }

    public boolean allExposed() {
        return numberOfBombs == numberOfUnseenCells;
    }

    private void flip(Sprite sprite) {
        sprite.flip();
        numberOfUnseenCells--;

    }


    public void reset() {
        for(int i = 0; i < rows; i++)
            for(int j = 0; j < columns; j++)
               grid[i][j] = null;

        bombs.clear();
        bombs = null;
        grid = null;
        System.gc();


    }




    public static void search(Sprite sprite, Board board) {
        int[][] cellOffsets = {{1, 1}, {-1, -1}, {1, -1}, {-1, 1}, {1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        if (sprite instanceof NumberSprite)
            board.flip(sprite);
        else if (sprite instanceof Blank) {
            board.flip(sprite);
            for (int i = 0; i < cellOffsets.length; i++) {
                int[] cellOffset = cellOffsets[i];
                int row = sprite.getRow() + cellOffset[0];
                int col = sprite.getColumn() + cellOffset[1];
                if (row >= board.getRows() || row < 0 ||
                        col >= board.getColumns() || col <0)
                    continue;

                Sprite candidate = board.getSprite(row, col);
                if (candidate.getStatus() == Status.UNSEEN)
                    search(candidate, board);


            }
        }

    }


}
