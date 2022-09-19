package main.java;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import main.java.animation.*;
import main.java.graphics.Sprite;
import main.java.maze.MazeGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class MazeGame extends Application {
    public static final int MAZE_SIZE = 15;
    public static int TIME_LIMIT = 30000;
    private static HashSet<String> currentlyActiveKeys = new HashSet<>();
    private static int[][] map;
    private static int[][] bigmap;
    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    private MazeGenerator curMaze;
    private MazeGenerator tmpMaze;
    private ArrayList<Integer> exit = new ArrayList<>();
    Noob p = new Noob(0, 0, Sprite.noob_down.getFxImage());
    long startTime;
    Text clock = new Text(Sprite.SCALED_SIZE, Sprite.SCALED_SIZE * (MAZE_SIZE + 3), "Time: ");
    int scale = Sprite.SCALED_SIZE / Sprite.DEFAULT_SIZE;
    private static boolean end = false;

    public static boolean isEnd() {
        return end;
    }

    public static void setEnd(boolean end) {
        MazeGame.end = end;
    }

    public static HashSet<String> getCurrentlyActiveKeys() {
        return currentlyActiveKeys;
    }

    public static void main(String[] args) {
        Application.launch(MazeGame.class);
    }

    public static int[][] getMap() {
        return map;
    }

    public static int[][] getBigmap() {
        return bigmap;
    }

    long getTime() {
        return System.currentTimeMillis() - startTime;
    }
    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * MAZE_SIZE, Sprite.SCALED_SIZE * (MAZE_SIZE + 3));
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);
        root.getChildren().add(clock);
        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        scene.setOnKeyPressed(keyEvent -> {
            currentlyActiveKeys.add(keyEvent.getCode().toString());
        });

        scene.setOnKeyReleased(keyEvent -> {
            currentlyActiveKeys.remove(keyEvent.getCode().toString());
        });

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
                render();
            }
        };
        timer.start();
        tmpMaze = new MazeGenerator(MAZE_SIZE, MAZE_SIZE);
        restart();
        createMap();
    }

    public void restart() {
        curMaze = tmpMaze;
        tmpMaze = new MazeGenerator(MAZE_SIZE, MAZE_SIZE);
        map = new int[MAZE_SIZE + 2][MAZE_SIZE];

        exit = curMaze.getOut();

        for (int j = 0; j < MAZE_SIZE; j ++) {
            map[0][j] += 13;
        }

        for (int i = 1; i <= MAZE_SIZE; i++) {
            for (int j = 0; j < MAZE_SIZE; ++j) {
                map[i][j] = curMaze.getMaze()[j][i - 1];
            }
        }
        Random rand = new Random(System.currentTimeMillis());
        int o = 2 + rand.nextInt(3);
        for(int i = 0; i < o; ++ i) {
            int tmp = rand.nextInt(MAZE_SIZE);
            map[1][i] |= 1;
        }
    }

    public void connectMaze(int c) {
        MazeGenerator repMaze = new MazeGenerator(MAZE_SIZE, c - 1);
        for (int i = c; i <= MAZE_SIZE; i++) {
            for (int j = 0; j < MAZE_SIZE; ++j) {
                map[i - c + 1][j] = map[i][j];
            }
        }
        for (int i = 1; i <= c - 1; i++) {
            for (int j = 0; j < MAZE_SIZE; ++j) {
                map[i + MAZE_SIZE - c + 1][j] = repMaze.getMaze()[j][i - 1];
            }
        }
        for (int j : exit) {
            map[MAZE_SIZE - c + 2][j] |= 1;
        }
        exit = repMaze.getOut();
    }

    public void createMap() {
        stillObjects.clear();
        startTime = System.currentTimeMillis();
        bigmap = new int[(MAZE_SIZE + 2) * Sprite.SCALED_SIZE][MAZE_SIZE * Sprite.SCALED_SIZE];
        for (int j = 0; j < MAZE_SIZE; j ++) {
            map[0][j] = 13;
            if ((map[1][j] & 1) != 0) {
                map[0][j] |= 2;
            }
        }
//        for(int j = 0; j < MAZE_SIZE; j ++) {
//            Wc wc = new Wc(j, MAZE_SIZE + 1, Sprite.wc.getFxImage());
//            stillObjects.add(wc);
//        }

        for (int i = 0; i < MAZE_SIZE + 1; i++) {
            for (int j = 0; j < MAZE_SIZE; j++) {
                Entity object;
                object = new Grass(j, i, Sprite.grass.getFxImage());
                stillObjects.add(object);

                Entity wall;

                if ((map[i][j] & 8) == 0) {
                    for(int t = 0; t < scale; t ++) {
                        for (int k = scale; k < Sprite.SCALED_SIZE - scale; k++) {
                            bigmap[i * Sprite.SCALED_SIZE + k][j * Sprite.SCALED_SIZE + t] = 1;
                        }
                    }
                    wall = new Wall(j, i, Sprite.wall_left.getFxImage());
                    stillObjects.add(wall);
                }
                if ((map[i][j] & 4) == 0) {
                    for(int t = 0; t < scale; t ++) {
                        for (int k = scale; k < Sprite.SCALED_SIZE - scale; k++) {
                            bigmap[i * Sprite.SCALED_SIZE + k][(j + 1) * Sprite.SCALED_SIZE - 1 - t] = 1;
                        }
                    }
                    wall = new Wall(j, i, Sprite.wall_right.getFxImage());
                    stillObjects.add(wall);
                }
                if ((map[i][j] & 1) == 0) {
                    for(int t = 0; t < scale; t ++) {
                        for (int k = scale; k < Sprite.SCALED_SIZE - scale; k++) {
                            bigmap[i * Sprite.SCALED_SIZE + t][j * Sprite.SCALED_SIZE + k] = 1;
                        }
                    }
                    wall = new Wall(j, i, Sprite.wall_top.getFxImage());
                    stillObjects.add(wall);
                }
                if ((map[i][j] & 2) == 0) {
                    for(int t = 0; t < scale; t ++) {
                        for (int k = scale; k < Sprite.SCALED_SIZE - scale; k++) {
                            bigmap[(i + 1) * Sprite.SCALED_SIZE - 1 - t][j * Sprite.SCALED_SIZE + k] = 1;
                        }
                    }
                    wall = new Wall(j, i, Sprite.wall_down.getFxImage());
                    stillObjects.add(wall);
                }
            }
        }
    }

    public void showMap() {
        for (int i = 0; i < MAZE_SIZE + 2; i++) {
            // draw the north edge
            for (int j = 0; j < MAZE_SIZE; j++) {
                System.out.print((map[i][j] & 1) == 0 ? "+---" : "+   ");
            }
            System.out.println("+");
            // draw the west edge
            for (int j = 0; j < MAZE_SIZE; j++) {
                System.out.print((map[i][j] & 8) == 0 ? "|   " : "    ");
            }
            System.out.println("|");
        }
    }

    public void updateMap(int c) {
        if (c > MAZE_SIZE / 3) {
            connectMaze(c);
        } else {
            restart();
        }
    }

    public void update() {
        clock.setText("Time: " + Float.toString((float) (TIME_LIMIT - getTime()) / 1000));
        if (getTime() * 3 > TIME_LIMIT * 2) {
            clock.setFill(Color.RED);
        }
        else {
            clock.setFill(Color.BLACK);
        }
        if (p.getY() /  Sprite.SCALED_SIZE == MAZE_SIZE + 1) {
            TIME_LIMIT = TIME_LIMIT * 3 / 4;
            restart();
            createMap();
            p.setY(p.getY() % Sprite.SCALED_SIZE);
        } else {
            if (getTime() >= TIME_LIMIT) {
                updateMap(p.getY() / Sprite.SCALED_SIZE);
                createMap();
                p.setY(p.getY() % Sprite.SCALED_SIZE);
            }
        }
        p.update();
    }

    public void render() {
        gc.clearRect(0, 0, Sprite.SCALED_SIZE * MAZE_SIZE, Sprite.SCALED_SIZE * (MAZE_SIZE + 3));
        stillObjects.forEach(g -> g.render(gc));
        p.render(gc);
    }
}
