package com.example.spaceinvaders.alieninvaders.model;

public class EnemyShipProjectile extends Projectile {
    public EnemyShipProjectile(int x, int y) {
        super(x, y);
    }

    public void move() {
        moveBy(0, 1);
    }

    public boolean equals(Object object) {
        if (super.equals(object)) {
            if (object instanceof EnemyShipProjectile) {
                return true;
            }
        }
        return false;
    }
}
