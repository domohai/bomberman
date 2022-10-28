package util;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class PathFinder {
    public static int pathFinder(int si, int sj, char[][] map) {
        int n = map.length;
        int m = map[0].length;
        int ti = si, tj = sj;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                if (map[i][j] == 'p') {
                    ti = i;
                    tj = j;
                }
        if (si == ti && sj == tj) return 0;
//        System.out.println("Going from " + si + " " + sj + " to " + ti + " " + tj);
        //min-path value
        int[][] f = new int[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                f[i][j] = m * n + 7; // set to a large number (for path length)
        f[si][sj] = 0;
        // min-path trace: trace[i][j] = previous direction to go to the current i j
        int[][] trace = new int[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                trace[i][j] = -1;
        trace[si][sj] = 0;
        PriorityQueue<Waypoint> pq = new PriorityQueue<>(new PQcmp());
        pq.add(new Waypoint(si, sj, 0));
        map[ti][tj] = ' '; // for easier checking legal path
        while (!pq.isEmpty()) {
            Waypoint x = pq.poll();
            if (x.d > f[x.i][x.j]) continue;
            // !!
            if (x.i == ti && x.j == tj) break;
            if (x.i - 1 >= 0 && map[x.i - 1][x.j] == ' ' && f[x.i - 1][x.j] > x.d + 1) {
                pq.add(new Waypoint(x.i - 1, x.j, x.d + 1));
                f[x.i - 1][x.j] = x.d + 1;
                trace[x.i - 1][x.j] = 1;
            }
            if (x.i + 1 < n && map[x.i + 1][x.j] == ' ' && f[x.i + 1][x.j] > x.d + 1) {
                pq.add(new Waypoint(x.i + 1, x.j, x.d + 1));
                f[x.i + 1][x.j] = x.d + 1;
                trace[x.i + 1][x.j] = 2;
            }
            if (x.j - 1 >= 0 && map[x.i][x.j - 1] == ' ' && f[x.i][x.j - 1] > x.d + 1) {
                pq.add(new Waypoint(x.i, x.j - 1, x.d + 1));
                f[x.i][x.j - 1] = x.d + 1;
                trace[x.i][x.j - 1] = 3;
            }
            if (x.j + 1 < m && map[x.i][x.j + 1] == ' ' && f[x.i][x.j + 1] > x.d + 1) {
                pq.add(new Waypoint(x.i, x.j + 1, x.d + 1));
                f[x.i][x.j + 1] = x.d + 1;
                trace[x.i][x.j + 1] = 4;
            }
        }
        map[ti][tj] = 'p';

        int dir = 0;
        while (ti != si || tj != sj) {
            dir = trace[ti][tj];
            if (dir == -1) return 0;
            if (dir == 1) ti++;
            if (dir == 2) ti--;
            if (dir == 3) tj++;
            if (dir == 4) tj--;
        }
//        System.out.println("with dir = " + dir + ".......................");
        return dir;
    }

    public static class Waypoint {
        int i;
        int j;
        int d;

        public Waypoint(int i, int j, int d) {
            this.i = i;
            this.j = j;
            this.d = d;
        }
    }

    public static class PQcmp implements Comparator<Waypoint> {
        @Override
        public int compare(Waypoint w1, Waypoint w2) {
            return Integer.compare(w1.d, w2.d);
        }
    }

    public static int pathFinderBFS(int si, int sj, char[][] map) {
        int n = map.length;
        int m = map[0].length;
        //find the player pos
        int ti = si, tj = sj;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                if (map[i][j] == 'p') {
                    ti = i;
                    tj = j;
                }
        if (si == ti && sj == tj) return 0;
        //min-path value: f[i][j] = min-path from (si,sj) to (i,j)
        int[][] f = new int[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                f[i][j] = m * n + 7; //set to a large number (for path length)
        f[si][sj] = 0;
        //min-path trace: trace[i][j] = previous direction to go to the current i j
        int[][] trace = new int[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                trace[i][j] = -1;
        trace[si][sj] = 0;
        Queue<Waypoint> pq = new LinkedList<>();
        pq.add(new Waypoint(si, sj, 0));
        map[ti][tj] = ' ';//for easier checking legal path
        while (!pq.isEmpty()) {
            Waypoint x = pq.poll();
            if (x.d > f[x.i][x.j]) continue;
            //!!
            if (x.i == ti && x.j == tj) break;
            if (x.i - 1 >= 0 && map[x.i - 1][x.j] == ' ' && f[x.i - 1][x.j] > x.d + 1) {
                pq.add(new Waypoint(x.i - 1, x.j, x.d + 1));
                f[x.i - 1][x.j] = x.d + 1;
                trace[x.i - 1][x.j] = 1;
            }
            if (x.i + 1 < n && map[x.i + 1][x.j] == ' ' && f[x.i + 1][x.j] > x.d + 1) {
                pq.add(new Waypoint(x.i + 1, x.j, x.d + 1));
                f[x.i + 1][x.j] = x.d + 1;
                trace[x.i + 1][x.j] = 2;
            }
            if (x.j - 1 >= 0 && map[x.i][x.j - 1] == ' ' && f[x.i][x.j - 1] > x.d + 1) {
                pq.add(new Waypoint(x.i, x.j - 1, x.d + 1));
                f[x.i][x.j - 1] = x.d + 1;
                trace[x.i][x.j - 1] = 3;
            }
            if (x.j + 1 < m && map[x.i][x.j + 1] == ' ' && f[x.i][x.j + 1] > x.d + 1) {
                pq.add(new Waypoint(x.i, x.j + 1, x.d + 1));
                f[x.i][x.j + 1] = x.d + 1;
                trace[x.i][x.j + 1] = 4;
            }
        }
        map[ti][tj] = 'p';

        int dir = 0;
        while (ti != si || tj != sj) {
            dir = trace[ti][tj];
            if (dir == -1) return 0;
            if (dir == 1) ti++;
            if (dir == 2) ti--;
            if (dir == 3) tj++;
            if (dir == 4) tj--;
        }
        return dir;
    }
}
