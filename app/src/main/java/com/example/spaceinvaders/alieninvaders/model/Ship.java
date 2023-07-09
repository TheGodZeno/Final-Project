package com.example.spaceinvaders.alieninvaders.model;

import com.example.spaceinvaders.framework.Actor;

public class Ship extends Actor {
    private static final long SHIP_EXPLOSION_TIME = 2;

    private int lives;
    private long lastShot;
    private long lastMovement;
    private float stateTime;
    private ShipState status;

    // the possible ship status values
    // ערכי ship status אפשריים
    public enum ShipState {
        Alive,
        Exploding
    }

    public Ship() {
        super(2* SpaceInvadersWorld.CELL_WIDTH,
                SpaceInvadersWorld.EARTH_LEVEL* SpaceInvadersWorld.CELL_HEIGHT,
                SpaceInvadersWorld.CELL_WIDTH+1,
                SpaceInvadersWorld.CELL_HEIGHT);
        lastShot = System.currentTimeMillis();
        lives = 3;
        status= ShipState.Alive;
    }

    public void exploding(float deltaTime) {
        if (status == ShipState.Exploding) {
            if (stateTime >= SHIP_EXPLOSION_TIME) {
                lives--;
                stateTime = 0;
                x=2* SpaceInvadersWorld.CELL_WIDTH;
                y=19* SpaceInvadersWorld.CELL_HEIGHT;
                status= ShipState.Alive;
            }
        }
        stateTime += deltaTime;
    }

    public void moveLeft() {
        if (status == ShipState.Alive) {
            if ((System.currentTimeMillis() - lastMovement) > 20) {
                if (x > 8) {
                    x -= 1;
                }
                lastMovement=System.currentTimeMillis();
            }
        }
    }

    public void moveRight() {
        if (status == ShipState.Alive) {
            if ((System.currentTimeMillis() - lastMovement) > 20) {
                if (x < SpaceInvadersWorld.WORLD_WIDTH - 8) {
                    x += 1;
                }
                lastMovement=System.currentTimeMillis();
            }
        }
    }

    public void  shoot() {
        if (status == ShipState.Alive) {
            if ((System.currentTimeMillis() - lastShot) > 320) {
                SpaceInvadersWorld.getInstance().getProjectiles().add(new ShipProjectile(x+2, y));
                lastShot = System.currentTimeMillis();
            }
        }
    }

    public boolean isAlive() {
        return lives > 0;
    }

    public boolean isExploding() {
        return status== ShipState.Exploding;
    }

    public void kill() {
        status= ShipState.Exploding;
    }

    public void destroy() {
        kill();
        lives=0;
    }

    public int getLives() {
        return lives;
    }
}
