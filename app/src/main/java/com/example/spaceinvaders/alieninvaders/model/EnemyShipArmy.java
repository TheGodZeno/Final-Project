package com.example.spaceinvaders.alieninvaders.model;

import com.example.spaceinvaders.framework.Actor;

import java.util.List;

public class EnemyShipArmy extends Actor {
    private float speedX, speedY;
    private List<EnemyShip> enemyShips;
    private String direction;

    private int speedTickMove=50;
    private int tick=0;

    public EnemyShipArmy(List<EnemyShip> enemyShips) {
        super(Integer.MAX_VALUE, Integer.MAX_VALUE);
        this.speedX = 1;
        this.speedY = 2;
        this.enemyShips = enemyShips;
        this.direction = "right";
        update();
    }

    public void move() {
        if (tick>speedTickMove) {
            if (direction.equals("left")) {
                moveLeft();
            } else {
                moveRight();
            }
            tick=0;
        }
        ++tick;
    }

    private void moveLeft() {
        update();
        if (x > SpaceInvadersWorld.CELL_WIDTH) {
            for (EnemyShip enemyShip : enemyShips) {
                enemyShip.moveLeft();
            }
        } else {
            changeDirection();
        }
    }

    private void moveRight() {
        update();
        if ((x + width) < (SpaceInvadersWorld.WORLD_WIDTH - SpaceInvadersWorld.CELL_WIDTH)) {
            for (EnemyShip enemyShip : enemyShips) {
                enemyShip.moveRight();
            }
        } else {
            changeDirection();
        }
    }

    private void changeDirection() {
        //y += speedY;
        direction = (direction.equals("left")) ? "right" : "left";
        for (EnemyShip enemyShip : enemyShips) {
            enemyShip.moveForward();
        }
    }

    public void update() {
        int xmax=Integer.MIN_VALUE, ymax=Integer.MIN_VALUE;

        this.x=Integer.MAX_VALUE;
        this.y=Integer.MAX_VALUE;

        if (enemyShips.size()!=0) {
            for (EnemyShip enemyShip : enemyShips) {
                if (enemyShip.getX()<x)
                    this.x= enemyShip.getX();
                if (enemyShip.getY()<y)
                    this.y= enemyShip.getY();
                if ((enemyShip.getX()+ enemyShip.getWidth())>xmax)
                    xmax= enemyShip.getX()+ enemyShip.getWidth();
                if ((enemyShip.getY()+ enemyShip.getHeight())>ymax)
                    ymax= enemyShip.getY()+ enemyShip.getHeight();
            }
            this.width=xmax-this.x;
            this.height=ymax-this.y;
        } else {
            this.x=0;
            this.y=0;
            this.width=0;
            this.height=0;
        }
    }

    public boolean isOnEarth() {
        return (this.y+this.height)>(SpaceInvadersWorld.EARTH_LEVEL* SpaceInvadersWorld.CELL_HEIGHT);
    }

    public void increaseSpeed() {
        speedTickMove=speedTickMove==0?0:speedTickMove-1;
    }

    public void reset() {
        speedTickMove=50;
    }
}
