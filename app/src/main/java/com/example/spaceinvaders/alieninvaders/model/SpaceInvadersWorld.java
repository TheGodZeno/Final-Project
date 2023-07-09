package com.example.spaceinvaders.alieninvaders.model;

import com.example.spaceinvaders.framework.Rectangle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class SpaceInvadersWorld {
    public interface WorldListener {
        void explosion();
        void laserClash();
        void shieldImpact();
    }


    //  כל תא הוא מטריצה 4X4 של תיבות (מקומות/רווחים). הסיבה שאנחנו צריכים תאים היא בגלל שה-ship, aliens וה-projectiles נעים וייצרכו לנוע לאורך תיבות.
    public static final int CELL_WIDTH = 4;
    public static final int CELL_HEIGHT = 4;
    // The dimension of the matrix is 16x20 cells
    // המימדים של המטירצה הם 16X20 תאים.
    public static final int MATRIX_WIDTH = 16;
    public static final int MATRIC_HEIGHT = 20;
    // Alien Invaders world is a matrix of 64x80 boxes
    //ה-Alien Invaders world היא מטירצה של 64X80 תיבות.
    public static final int WORLD_WIDTH = MATRIX_WIDTH*CELL_WIDTH;
    public static final int WORLD_HEIGHT = MATRIC_HEIGHT*CELL_HEIGHT;
    // Earth level: the level where ship is
    // ה-Earth level היא ה-level איפה שה-ship נמצא
    public static final int EARTH_LEVEL = MATRIC_HEIGHT-1;

    // the possible game status values
    //ערכי מצב המשחק(game status) האפשריים
    public enum GameState {
        Ready,
        Running,
        Paused,
        GameOver
    }

    // the game status
    // מצב המשחק
    private GameState state = GameState.Ready;

    // the game level
    // ה-level של המשחק
    private int level;
    // the user score
    // ה-score של המשחק
    private int score;
    private int timer;

    // the aliens army
    // ה-aliens army
    private EnemyShipArmy enemyShipArmy;
    // the list of aliens
    // רשימת ה-aliens
    private List<EnemyShip> enemyShips;
    // the list of projectiles currently on the screen
    // רשימת ה-projectiles על המסך כרגע
    private List<Projectile> projectiles;
    // the list of maximum 4 shields
    // רשימת ה-shields המקסימלית היא 4
    private List<Shield> shields;
    // the ship
    // ה-ship
    private Ship ship;

    // the private static instance used to implement the Singleton pattern.
    // ה-private static instance המשמש ליישום ה-Singleton pattern (תבנית עיצוב)
    private static SpaceInvadersWorld instance = null;

    private WorldListener worldListener;

    public void setWorldListener(WorldListener worldListener) {
        this.worldListener = worldListener;
    }

    public List<Projectile> getProjectiles() {
        return projectiles;
    }
    public List<Shield> getShields() { return shields; }
    public List<EnemyShip> getAliens() { return enemyShips; }

    public Ship getShip() {
        return ship;
    }

    public int getLevel() {
        return level;
    }

    private SpaceInvadersWorld() {
        this.enemyShips =new ArrayList<>();
        this.enemyShipArmy =new EnemyShipArmy(enemyShips);
        this.projectiles=new ArrayList<>();
        this.shields=new ArrayList<>();
        clear();
    }

    public static SpaceInvadersWorld getInstance() {
        if (instance == null) {
            synchronized (SpaceInvadersWorld.class) {
                if (instance == null) {
                    instance = new SpaceInvadersWorld();
                }
            }
        }
        return instance;
    }

    public void update(float deltaTime) {
        if (state == GameState.GameOver)
            return;

        if (state == GameState.Running ) {
            timer += 1;

            if (ship.isExploding()) {
                ship.exploding(deltaTime);
                return;
            }

            //if ((timer % 40) == 0) {
                enemyShipArmy.move();
            //}

            if ((timer % 80) == 0) {
                if (enemyShips.size()!=0) {
                    invasion();
                }
            }

            for (Projectile projectile: projectiles) {
                // slow down a bit alien projectiles, we update them only once each two update
                if ((projectile instanceof EnemyShipProjectile) && ((timer % 8) != 0)) {
                    continue;
                }
                projectile.move();
                if (!getBounds().overlaps(projectile.getBounds())) {
                    projectile.kill();
                }
            }

            if ((timer % 2) == 0) {
                for (Projectile projectile: projectiles) {
                    projectile.move();
                    if (!getBounds().overlaps(projectile.getBounds())) {
                        projectile.kill();
                    }
                }
            }

            for (Iterator<Projectile> itr= projectiles.iterator(); itr.hasNext();) {
                Projectile projectile = itr.next();
                if (projectile.isInactive()) {
                    itr.remove();
                }
            }

            detectCollisions();

            if (!ship.isAlive()) {
                state= GameState.GameOver;
            }

            // If Alien Army arrived on earth, destroy the ship and the game is over.
            if (enemyShipArmy.isOnEarth()) {
                ship.destroy();
                worldListener.explosion();
            }

            if (enemyShips.size()==0) {
                resetLevel();
                ++level;
            }
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(0, 0, WORLD_WIDTH, WORLD_HEIGHT);
    }

    public void invasion() {
        Random rnd = new Random(System.currentTimeMillis());
        int i=rnd.nextInt(enemyShips.size());
        Projectile p = enemyShips.get(i).shoot();
        if (p != null) projectiles.add(p);
    }

    public void clear() {
        score = 0;
        timer = 0;
        level=1;
        state = GameState.Ready;
        ship=new Ship();
        resetLevel();
    }

    public void resetLevel() {
        enemyShips.clear();
        projectiles.clear();
        shields.clear();
        for (int i=0; i<10; ++i) {
            enemyShips.add(new UglyEnemyShip(i*CELL_WIDTH + 3*CELL_WIDTH, 7*CELL_HEIGHT));
            enemyShips.add(new BadEnemyShip(i*CELL_WIDTH + 3*CELL_WIDTH, 8*CELL_HEIGHT));
            enemyShips.add(new BadEnemyShip(i*CELL_WIDTH + 3*CELL_WIDTH, 9*CELL_HEIGHT));
            enemyShips.add(new GoodEnemyShip(i*CELL_WIDTH + 3*CELL_WIDTH, 10*CELL_HEIGHT));
            enemyShips.add(new GoodEnemyShip(i*CELL_WIDTH + 3*CELL_WIDTH, 11*CELL_HEIGHT));
        }
        enemyShipArmy.update();
        enemyShipArmy.reset();

        shields.add(new Shield(3*CELL_WIDTH, 17*CELL_HEIGHT));
        shields.add(new Shield(6*CELL_WIDTH, 17*CELL_HEIGHT));
        shields.add(new Shield(9 * CELL_WIDTH, 17 * CELL_HEIGHT));
        shields.add(new Shield(12 * CELL_WIDTH, 17 * CELL_HEIGHT));
    }

    private void detectCollisions() {
        // Check if aliens hit the ship
        // בודק אם ה-aliens פגעו ב-ship
        for (Iterator<EnemyShip> itr = enemyShips.iterator(); itr.hasNext();) {
            EnemyShip enemyShip = itr.next();
            if (ship.collide(enemyShip)) {
                ship.kill();
                itr.remove();
                worldListener.explosion();
                break;
            }
        }

        // Check if ship projectile hit an alien
        // בודק אם projectile של ship פגע ב-alien
        for (Iterator<Projectile> itrProjectile=projectiles.iterator(); itrProjectile.hasNext();) {
            Projectile projectile = itrProjectile.next();
            if (projectile instanceof ShipProjectile) {
                for (Iterator<EnemyShip> itrAlien = enemyShips.iterator(); itrAlien.hasNext();) {
                    EnemyShip enemyShip = itrAlien.next();
                    if (enemyShip.collide(projectile)) {
                        score+= enemyShip.getScore();
                        itrAlien.remove();
                        itrProjectile.remove();
                        worldListener.laserClash();
                        enemyShipArmy.increaseSpeed();
                        break;
                    }
                }
            }
        }

        // Check if alien projectile hit the ship
        // בודק אם projectile של alien פגע ב-ship
        for (Iterator<Projectile> itrProjectile= projectiles.iterator(); itrProjectile.hasNext();) {
            Projectile projectile = itrProjectile.next();
            if (projectile instanceof EnemyShipProjectile) {
                if (ship.collide(projectile)) {
                    ship.kill();
                    itrProjectile.remove();
                    worldListener.explosion();
                    break;
                }
            }
        }

        // Check if alien projectile hit a ship projectile
        // בודק אם projectile של alien פגע ב-projectile של ship
        Projectile alienProjectile=null;
        Projectile shipProjectile=null;
        search: {
            for (Projectile projectile1 : projectiles) {
                if (projectile1 instanceof EnemyShipProjectile) {
                    for (Projectile projectile2 : projectiles) {
                        if (projectile2 instanceof ShipProjectile) {
                            if (projectile1.collide(projectile2)) {
                                score+=10;
                                alienProjectile=projectile1;
                                shipProjectile=projectile2;
                                break search;
                            }
                        }
                    }
                }
            }
        }
        if (alienProjectile != null) projectiles.remove(alienProjectile);
        if (shipProjectile != null) projectiles.remove(shipProjectile);

        // Check if an alien projectile it a shield
        // בודק אם projectile של alien פגע ב-shield
        for (Iterator<Projectile> itrProjectile= projectiles.iterator(); itrProjectile.hasNext();) {
            Projectile projectile = itrProjectile.next();
            if (projectile instanceof EnemyShipProjectile) {
                for (Iterator<Shield> itrShield= shields.iterator(); itrShield.hasNext();) {
                    Shield shield = itrShield.next();
                    if (shield.collide(projectile)) {
                        itrProjectile.remove();
                        shield.reduce();
                        if (!shield.isAlive()) {
                            itrShield.remove();
                        }
                        worldListener.shieldImpact();
                        break;
                    }
                }
            }
        }

        for (Iterator<Projectile> itrProjectile= projectiles.iterator(); itrProjectile.hasNext();) {
            Projectile projectile = itrProjectile.next();
            if (projectile instanceof ShipProjectile) {
                for (Iterator<Shield> itrShield= shields.iterator(); itrShield.hasNext();) {
                    Shield shield = itrShield.next();
                    if (shield.collide(projectile)) {
                        itrProjectile.remove();
                        break;
                    }
                }
            }
        }
    }

    public GameState getState() {
        return state;
    }
    public void setState(GameState state) {
        this.state = state;
    }

    public int getScore() {
        return score;
    }
}
