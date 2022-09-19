package main.java.maze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import static java.lang.Integer.min;

public class MazeGenerator {
    private int x;
    private int y;
    private int[][] maze;
    private ArrayList<Integer> out = new ArrayList<>();

    public MazeGenerator(int x, int y) {
        this.x = x;
        this.y = y;
        maze = new int[this.x][this.y];
        generateMaze(0, 0);
        Random rand = new Random(System.currentTimeMillis());
        int o = 2 + rand.nextInt(3);
        for(int i = 0; i < o; ++ i) {
            int tmp = rand.nextInt(x);
            out.add(tmp);
            maze[tmp][y - 1] |= 2;
        }
        display();
        int d = 0;
        while (d < min(x, y)) {
            int cx = 1 + rand.nextInt(x - 2);
            int cy = 1 + rand.nextInt(y - 2);
            DIR[] dirs = DIR.values();
            Collections.shuffle(Arrays.asList(dirs));
            for (DIR dir : dirs) {
                int nx = cx + dir.dx;
                int ny = cy + dir.dy;
                if ((maze[cx][cy] & dir.bit) == 0) {
                    maze[cx][cy] |= dir.bit;
                    maze[nx][ny] |= dir.opposite.bit;
                    d ++;
                    break;
                }
            }
        }
        display();
    }

    public ArrayList<Integer> getOut() {
        return out;
    }

    private static boolean between(int v, int downer, int upper) {
        return (v > downer) && (v < upper);
    }

    public int[][] getMaze() {
        return maze;
    }

    public void printMaze() {
        for (int i = 0; i < y; ++i) {
            for (int j = 0; j < x; ++j) {
                System.out.print((maze[j][i]) + " ");
            }
            System.out.println();
        }
    }

    public void display() {
//        printMaze();
        for (int i = 0; i < y; i++) {
            // draw the north edge
            for (int j = 0; j < x; j++) {
                System.out.print((maze[j][i] & 1) == 0 ? "+---" : "+   ");
            }
            System.out.println("+");
            // draw the west edge
            for (int j = 0; j < x; j++) {
                System.out.print((maze[j][i] & 8) == 0 ? "|   " : "    ");
            }
            System.out.println("|");
        }

    }

    private void generateMaze(int cx, int cy) {
        DIR[] dirs = DIR.values();
        Collections.shuffle(Arrays.asList(dirs));
        for (DIR dir : dirs) {
            int nx = cx + dir.dx;
            int ny = cy + dir.dy;
            if (between(nx, -1, x) && between(ny, -1, y)
                    && (maze[nx][ny] == 0)) {
                maze[cx][cy] |= dir.bit;
                maze[nx][ny] |= dir.opposite.bit;
                generateMaze(nx, ny);
            }
        }
    }

}