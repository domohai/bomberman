package core.Window.Scenes;

// player stats
// Singleton implement
public class Stats {
    private static final Stats stats = new Stats();
    private int HP;
    private int bombNumber;
    private int flameSize;
    private double speedMultiplier;

    private Stats() {
        HP = 3;
        bombNumber = 1;
        flameSize = 1;
        speedMultiplier = 1.0;
    }

    public static Stats get() {
        return stats;
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
        stats.setSpeedMultiplier(stats.getSpeedMultiplier() + 0.2);
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
