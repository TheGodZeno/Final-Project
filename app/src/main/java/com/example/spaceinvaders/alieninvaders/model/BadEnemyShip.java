package com.example.spaceinvaders.alieninvaders.model;

public class BadEnemyShip extends EnemyShip {
    public BadEnemyShip(int x, int y){
        super(x, y);
    }

    public int getScore() {
        return 20;
    }
}
