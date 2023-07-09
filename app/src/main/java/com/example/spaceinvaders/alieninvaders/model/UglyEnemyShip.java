package com.example.spaceinvaders.alieninvaders.model;

public class UglyEnemyShip extends EnemyShip {
    public UglyEnemyShip(int x, int y) {
        super(x, y);
    }

    public int getScore() {
        return 30;
    }
}
