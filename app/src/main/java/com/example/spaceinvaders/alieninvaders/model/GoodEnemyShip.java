package com.example.spaceinvaders.alieninvaders.model;

public class GoodEnemyShip extends EnemyShip {
    public GoodEnemyShip(int x, int y) {
        super(x, y);
    }

    public int getScore() {
        return 10;
    }
}
