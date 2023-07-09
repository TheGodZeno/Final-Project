package com.example.spaceinvaders.alieninvaders.view;

import com.example.spaceinvaders.alieninvaders.model.EnemyShip;
import com.example.spaceinvaders.alieninvaders.model.SpaceInvadersWorld;
import com.example.spaceinvaders.alieninvaders.model.BadEnemyShip;
import com.example.spaceinvaders.alieninvaders.model.GoodEnemyShip;
import com.example.spaceinvaders.alieninvaders.model.Projectile;
import com.example.spaceinvaders.alieninvaders.model.Shield;
import com.example.spaceinvaders.alieninvaders.model.Ship;
import com.example.spaceinvaders.alieninvaders.model.ShipProjectile;
import com.example.spaceinvaders.alieninvaders.model.UglyEnemyShip;
import com.example.spaceinvaders.framework.Gdx;
import com.example.spaceinvaders.framework.Graphics;
import com.example.spaceinvaders.framework.Pixmap;

/*
 * The responsibility of this class is to draw the model representation of Alien Invaders world.
 * האחריות של המחלקה הזו היא לצייר את המודל המצייג את המחשק.
 */
public class SpaceInvadersWorldRenderer {
    // Each cell is a 4x4 matrix. The reason we need cells is that ship and aliens do move across a
    // cells requires 4 steps in horizontal and 4 in vertical.
    // כל תא הוא מטריצה של 4X4. והסיבה שאנו צריכים תאים היא בגלל ש-ship ו-aliens בכדיי שינעו הם ייצטרכו לעשות 4 צעדים horizontal ו- 4 צעדים vertical.
    static final int CELL_WIDTH_PIXEL = 5;
    static final int CELL_HEIGHT_PIXEL = 5;

    private int lastEnemyShipXPosition;
    private boolean enemyShipMove =false;

    private Pixmap enemyShipGood;
    private Pixmap enemyShipBad;
    private Pixmap enemyShipUgly;


    public SpaceInvadersWorldRenderer() {
        enemyShipGood = Assets.enemyShipGood1;
        enemyShipBad = Assets.enemyShipBad1;
        enemyShipUgly = Assets.enemyShipUgly1;
    }

    /*
     This method draw the model representation of Droids world.
     המתודה הבאה מציירת את המודל המייצג את המשחק.
     */
    public void draw() {
        SpaceInvadersWorld world = SpaceInvadersWorld.getInstance();
        Graphics g = Gdx.graphics;

        Ship ship = world.getShip();
        Gdx.graphics.drawPixmap(Assets.ship, ship.getX() * CELL_WIDTH_PIXEL, ship.getY() * CELL_HEIGHT_PIXEL);

        for (Shield shield : SpaceInvadersWorld.getInstance().getShields()) {
            if (shield.getSize().equals(Shield.ShieldSize.LARGE)) {
                g.drawPixmap(Assets.shieldLarge, shield.getX()*CELL_WIDTH_PIXEL, shield.getY()*CELL_HEIGHT_PIXEL);
            } else if (shield.getSize().equals(Shield.ShieldSize.MEDIUM)) {
                g.drawPixmap(Assets.shieldMedium, shield.getX()*CELL_WIDTH_PIXEL, shield.getY()*CELL_HEIGHT_PIXEL);
            } else if (shield.getSize().equals(Shield.ShieldSize.SMALL)) {
                g.drawPixmap(Assets.shieldSmall, shield.getX()*CELL_WIDTH_PIXEL, shield.getY()*CELL_HEIGHT_PIXEL);
            }
        }

        if (SpaceInvadersWorld.getInstance().getAliens().size()>0 &&
                SpaceInvadersWorld.getInstance().getAliens().get(0).getX() != lastEnemyShipXPosition) {
            if (enemyShipMove) {
                enemyShipGood = Assets.enemyShipGood2;
                enemyShipBad = Assets.enemyShipBad2;
                enemyShipUgly = Assets.enemyShipUgly2;
            } else {
                enemyShipGood = Assets.enemyShipGood1;
                enemyShipBad = Assets.enemyShipBad1;
                enemyShipUgly = Assets.enemyShipUgly1;
            }
            enemyShipMove =!enemyShipMove;
            lastEnemyShipXPosition = SpaceInvadersWorld.getInstance().getAliens().get(0).getX();
        }

        for (EnemyShip enemyShip : SpaceInvadersWorld.getInstance().getAliens()) {
            if (enemyShip instanceof GoodEnemyShip) {
                g.drawPixmap(enemyShipGood, enemyShip.getX()*CELL_WIDTH_PIXEL, enemyShip.getY()*CELL_HEIGHT_PIXEL);
            } else if (enemyShip instanceof BadEnemyShip) {
                g.drawPixmap(enemyShipBad, enemyShip.getX()*CELL_WIDTH_PIXEL, enemyShip.getY()*CELL_HEIGHT_PIXEL);
            } else if (enemyShip instanceof UglyEnemyShip) {
                g.drawPixmap(enemyShipUgly, enemyShip.getX()*CELL_WIDTH_PIXEL, enemyShip.getY()*CELL_HEIGHT_PIXEL);
            }
        }

        for (Projectile projectile : SpaceInvadersWorld.getInstance().getProjectiles()) {
            if (projectile instanceof ShipProjectile) {
                g.drawPixmap(Assets.shipProjectile, projectile.getX()*CELL_WIDTH_PIXEL, projectile.getY()*CELL_HEIGHT_PIXEL);
            } else {
                g.drawPixmap(Assets.enemyShipProjectile, projectile.getX()*CELL_WIDTH_PIXEL, projectile.getY()*CELL_HEIGHT_PIXEL);
            }
        }

        for (int i=1; i<ship.getLives();i++) {
            g.drawPixmap(Assets.shipLife, 13*i, 400);
        }
    }
}
