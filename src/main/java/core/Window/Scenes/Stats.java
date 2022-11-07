package core.Window.Scenes;

import util.Const;

/**
 * player stats
 * Singleton implement
 */
public class Stats {
    private static final Stats stats = new Stats();
    private int HP;
    private int bombNumber, flameSize;
    private int currentLevel;
    private double speedMultiplier;
    private boolean pause, lose, win;

    private Stats() {
        HP = Const.INITIAL_HP;
        bombNumber = Const.INITIAL_BOMB_NUMBER;
        flameSize = Const.INITIAL_FLAME_SIZE;
        speedMultiplier = 1.0;
        currentLevel = Const.FIRST_LEVEL;
        pause = false;
        lose = false;
        win = false;
    }
    
    public void reset() {
//        HP = Const.INITIAL_HP;
        bombNumber = Const.INITIAL_BOMB_NUMBER;
        flameSize = Const.INITIAL_FLAME_SIZE;
        speedMultiplier = 1.0;
//        currentLevel = Const.FIRST_LEVEL;
    }

    public static Stats get() {
        return stats;
    }
    
    public static boolean isPause() {
        return stats.pause;
    }
    
    public static boolean isLose() {
        return stats.lose;
    }
    
    public static boolean isWin() {
        return stats.win;
    }
    
    public static int currentLevel() {
        return stats.currentLevel;
    }
    
    public static void setLose(boolean lose) {
        stats.lose = lose;
    }
    
    public static void setWin(boolean win) {
        stats.win = win;
    }
    
    public static void setPause(boolean pause) {
        stats.pause = pause;
    }
    
    public static void setLevel(int level) {
        stats.currentLevel = level;
    }

    public static void increaseHP() {
        stats.setHP(stats.getHP() + 1);
    }

    public static void increaseBombNumber() {
        stats.setBombNumber(stats.getBombNumber() + 1);
    }

    public static void increaseFlameSize() {
        stats.setFlameSize(stats.getFlameSize() + 1);
    }

    public static void increaseSpeedMultiplier() {
        stats.setSpeedMultiplier(stats.getSpeedMultiplier() + 0.3);
    }

    public static void decreaseHP() {
        stats.setHP(stats.getHP() - 1);
    }

    public static void decreaseBombNumber() {
        stats.setBombNumber(stats.getBombNumber() - 1);
    }

    public static void decreaseFlameSize() {
        stats.setFlameSize(stats.getFlameSize() - 1);
    }

    public static void decreaseSpeedMultiplier() {
        stats.setSpeedMultiplier(stats.getSpeedMultiplier() - 0.2);
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getBombNumber() {
        return bombNumber;
    }

    public void setBombNumber(int bombNumber) {
        this.bombNumber = bombNumber;
    }

    public int getFlameSize() {
        return flameSize;
    }

    public void setFlameSize(int flameSize) {
        this.flameSize = flameSize;
    }

    public double getSpeedMultiplier() {
        return speedMultiplier;
    }

    public void setSpeedMultiplier(double speedMultiplier) {
        this.speedMultiplier = speedMultiplier;
    }
}
