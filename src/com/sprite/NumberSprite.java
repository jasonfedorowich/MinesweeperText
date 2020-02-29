package com.sprite;

public class NumberSprite extends Sprite {

    private int number;

    public NumberSprite(int row, int column){
        super(row, column);
        this.number = 1;
    }

    public void increment(){
        number++;
    }

    @Override
    public String draw() {
        if(this.getStatus().equals(Status.UNSEEN))
            return "?";
        return String.valueOf(number);
    }
}
