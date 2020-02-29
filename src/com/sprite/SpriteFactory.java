package com.sprite;

public class SpriteFactory {

    public static Sprite newBomb(int row, int col){
        return new Bomb(row, col);
    }

    public static Sprite newNumberSprite(int row, int col){
        return new NumberSprite(row, col);
    }

    public static Sprite newBlank(int row, int col){
        return new Blank(row, col);
    }
}
