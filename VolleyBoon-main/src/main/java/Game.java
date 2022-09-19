package main.java;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import main.java.entities.*;
import main.java.entities.background.Background;
import main.java.entities.ball.Ball;
import main.java.entities.numbers.*;
import main.java.entities.player.*;
import main.java.entities.walls.*;
import main.java.graphics.Sprite;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Game extends Application {

    private static HashSet<String> currentlyActiveKeys = new HashSet<>();
    private final int HEIGHT_SIZE = 5;
    private final int WIDTH_SIZE = 7;
    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    private static Player1 p1 = new Player1();
    private static Player2 p2 = new Player2();
    private Ball b = new Ball(1);
    private Score1 score1 = new Score1();
    private Score2 score2 = new Score2();

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH_SIZE, Sprite.SCALED_SIZE * HEIGHT_SIZE);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);
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
        createMap();
    }

    public void createMap() {
        stillObjects.clear();
        for (int i = 0; i < HEIGHT_SIZE; i++) {
            for (int j = 0; j < WIDTH_SIZE; j++) {
                Entity object;
                object = new Background(j, i, Sprite.background.getFxImage());
                stillObjects.add(object);
            }
        }

        for (int j = 0; j < WIDTH_SIZE; j++) {
            Entity object;
            object = new HorizontalWall(j * Sprite.SCALED_SIZE, 0);
            stillObjects.add(object);
        }

        for (int j = 0; j < WIDTH_SIZE; j++) {
            Entity object;
            object = new HorizontalWall(j * Sprite.SCALED_SIZE, (HEIGHT_SIZE - 1)  * Sprite.SCALED_SIZE + Sprite.SCALE * (Sprite.DEFAULT_SIZE - Sprite.wall_hor.get_realHeight()));
            stillObjects.add(object);
        }

        for (int i = 0; i < HEIGHT_SIZE; i++) {
            Entity object;
            object = new VerticalWall(0, i * Sprite.SCALED_SIZE);
            stillObjects.add(object);
        }

        for (int i = 0; i < HEIGHT_SIZE; i++) {
            Entity object;
            object = new VerticalWall((WIDTH_SIZE - 1) * Sprite.SCALED_SIZE + Sprite.SCALE * (Sprite.DEFAULT_SIZE - Sprite.wall_ver.get_realWidth()), i * Sprite.SCALED_SIZE);
            stillObjects.add(object);
        }

        stillObjects.add(new MiddleWall(3 * Sprite.SCALED_SIZE, 3 * Sprite.SCALED_SIZE - Sprite.SCALE * 5));
        stillObjects.add(new MiddleWall(3 * Sprite.SCALED_SIZE, 4 * Sprite.SCALED_SIZE - Sprite.SCALE * 5));

    }

    public void update() {
        p1.update();
        p2.update();
        b.update();

        if (b.getY() >= (HEIGHT_SIZE - 1)  * Sprite.SCALED_SIZE + Sprite.SCALE * (Sprite.DEFAULT_SIZE - Sprite.wall_hor.get_realHeight())) {

            if (b.getX() > Sprite.SCALED_SIZE * WIDTH_SIZE / 2) {
                b = new Ball(-1);
                p1.score ++;
            } else {
                b = new Ball(1);
                p2.score ++;
            }
        }

        score1.setImgg(p1.score % 8);
        score2.setImgg(p2.score % 8);
    }

    public void render() {
        gc.clearRect(0, 0, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
        stillObjects.forEach(g -> g.render(gc));
        score1.render(gc);
        score2.render(gc);
        p1.render(gc);
        p2.render(gc);
        b.render(gc);
    }

    public static HashSet<String> getCurrentlyActiveKeys() {
        return currentlyActiveKeys;
    }

    public static Player1 getP1() {
        return p1;
    }

    public static Player2 getP2() {
        return p2;
    }

}
