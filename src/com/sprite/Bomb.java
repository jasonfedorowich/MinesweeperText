package com.sprite;

public class Bomb extends Sprite{

    public Bomb(int row, int column){
        super(row, column);
    }

    @Override
    public String draw() {
        if(this.getStatus().equals(Status.UNSEEN))
            return "?";
        return "*";
    }
}
