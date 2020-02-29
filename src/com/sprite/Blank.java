package com.sprite;

public class Blank extends Sprite{

    public Blank(int row, int column){
       super(row, column);
    }
    @Override
    public String draw() {
        if(this.getStatus().equals(Status.UNSEEN))
            return "?";
        return "O";
    }
}
