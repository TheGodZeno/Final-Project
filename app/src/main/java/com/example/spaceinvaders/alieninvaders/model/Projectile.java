package com.example.spaceinvaders.alieninvaders.model;

import com.example.spaceinvaders.framework.Actor;

public abstract class Projectile extends Actor {
    protected boolean active;

    public Projectile(int x, int y) {
        super(x, y);
        this.active = true;
    }

    public abstract void move();
    public void kill() {
        this.active = false;
    }
    public boolean isInactive() {
        return !this.active;
    }

    public boolean equals(Object object) {
        if (super.equals(object)) {
            if (object instanceof Projectile) {
                Projectile projectile = (Projectile) object;
                if (active==projectile.active) {
                    return true;
                }
            }
        }
        return false;
    }
}
