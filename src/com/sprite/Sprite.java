package com.sprite;

public abstract class Sprite {


    private final int row;



    private final int column;
    private Status status;

    public Sprite(int row, int column){
        this.status = Status.UNSEEN;
        this.row = row;
        this.column = column;

    }

   public abstract String draw();

    public void flip(){
        status = Status.SEEN;
    }


    public Status getStatus() {
        return status;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
