package util;

import java.util.Random;

public class RandomMove {
    private final static Random rnd = new Random();
    public static int randomDirection(int currentDir, boolean wasCollided, Box2D box, char[][] map) {
        return randomDirection(currentDir,wasCollided,getTurnBit(box,map));
    }
    public static int randomDirection(int prevDir, boolean wasCollided, int turnBit) {
        int dir = prevDir;
        if (wasCollided) {
            dir = switch (dir) {
                case 1 -> 2;
                case 2 -> 1;
                case 3 -> 4;
                case 4 -> 3;
                default -> 0;
            };
        } else {
            int p = Math.abs(rnd.nextInt()) % 100;
            //~5% to turn to one of 3 other direction
            //80% keep the dir
            if (p < 5 && (turnBit & 1) != 0 && dir != 2) dir = 1;
            else if (p < 10 && (turnBit & 2) != 0 && dir != 1) dir = 2;
            else if (p < 15 && (turnBit & 4) != 0 && dir != 4) dir = 3;
            else if (p < 20 && (turnBit & 8) != 0 && dir != 3) dir = 4;
        }
        return dir;
    }

    public static int getTurnBit(Box2D box, char[][] map) { //4 bit number, i-th bit == 1 <=> able to go that way
        int i = box.getCoordY();
        int j = box.getCoordX();
        if (!box.isInbound()) return 0;
        int bit1 = (map[i - 1][j] == ' ' ? 1 : 0);
        int bit2 = (map[i + 1][j] == ' ' ? 1 : 0) * 2;
        int bit3 = (map[i][j - 1] == ' ' ? 1 : 0) * 4;
        int bit4 = (map[i][j + 1] == ' ' ? 1 : 0) * 8;
        return bit1 + bit2 + bit3 + bit4;
    }
}
