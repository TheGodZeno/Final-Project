package com.example.spaceinvaders.alieninvaders.model;

import com.example.spaceinvaders.framework.Actor;

import java.util.Random;

public abstract class EnemyShip extends Actor {
    private long lastShot;

    public EnemyShip(int x, int y) {
        super(x, y, SpaceInvadersWorld.CELL_WIDTH, SpaceInvadersWorld.CELL_HEIGHT);
        this.lastShot = System.currentTimeMillis() + new Random().nextInt(15);
    }

    abstract public int getScore();

    void moveLeft() {
        x -= 1;
    }

    void moveRight() {
        x += 1;
    }

    public void moveForward() {
        y += 2;
    }

    public Projectile shoot() {
        if ((System.currentTimeMillis() - lastShot) > 320) {
            lastShot = System.currentTimeMillis();
            return new EnemyShipProjectile(x, y);
        }
        return null;
    }
}
