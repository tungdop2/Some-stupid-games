package main.java.animation;

import javafx.scene.image.Image;
import main.java.MazeGame;
import main.java.graphics.Sprite;

import java.util.HashSet;
import java.util.LinkedList;


public class Noob extends Entity {
    private int fat = 6;
    float speed = 1;
    int scale = Sprite.SCALED_SIZE / Sprite.DEFAULT_SIZE;
    LinkedList<HashSet<String>> listKeys = new LinkedList<>();
    public Noob(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update() {
        move();
    }

    public void move() {
        HashSet<String>  keys = MazeGame.getCurrentlyActiveKeys();
        HashSet<String> lastKeys = new HashSet<>();
        if (listKeys.size() > 10) {
            listKeys.remove(0);
        }
        if (listKeys.size() > 0) {
            lastKeys = listKeys.getLast();
        }
        if (!keys.isEmpty())
            listKeys.addLast((HashSet<String>) keys.clone());
        if (!keys.isEmpty() && lastKeys.equals(keys)) {
            speed += 0.05;
        }
        else speed = 1;
        for(int t = 0; t < speed; t ++) {
        if (keys.contains("RIGHT")) {
            img = Sprite.noob_right.getFxImage();
            right();
        } 
        if (keys.contains("LEFT")) {
            img = Sprite.noob_left.getFxImage();
            left();
        } 
        if (keys.contains("UP")) {
            img = Sprite.noob_up.getFxImage();
            up();
        }
        if (keys.contains("DOWN")) {
            img = Sprite.noob_down.getFxImage();
            down();
        }}
    }

    public void right() {
        if (x + fat * scale < MazeGame.MAZE_SIZE * Sprite.SCALED_SIZE
                && !(MazeGame.getBigmap()[y][x + fat * scale] == 1)
                && !(MazeGame.getBigmap()[y + fat * scale - 1][x + fat * scale] == 1)
                && !( (int)(y / Sprite.SCALED_SIZE) != (int)((y + fat * scale) / Sprite.SCALED_SIZE) &&
                (MazeGame.getBigmap()[(int)(y / Sprite.SCALED_SIZE + 1) * Sprite.SCALED_SIZE - 1][x + fat * scale] == 1
                || MazeGame.getBigmap()[(int)(y / Sprite.SCALED_SIZE + 1) * Sprite.SCALED_SIZE][x + fat * scale] == 1
                )
            )
        ) {
            x += 1;
        }
    }

    public void left() {
        if (x - 1 >= 0
                && MazeGame.getBigmap()[y][x - 1] != 1
                && MazeGame.getBigmap()[y + fat * scale - 1][x - 1] != 1
                && !( (int)(y / Sprite.SCALED_SIZE) != (int)((y + fat * scale) / Sprite.SCALED_SIZE) &&
                (MazeGame.getBigmap()[(int)(y / Sprite.SCALED_SIZE + 1) * Sprite.SCALED_SIZE - 1][x - 1] == 1
                        || (MazeGame.getBigmap()[(int)(y / Sprite.SCALED_SIZE + 1) * Sprite.SCALED_SIZE][x - 1] == 1)))
        ) {
            x -= 1;
        }
    }

    public void down() {
        if (    y + fat * scale < (MazeGame.MAZE_SIZE + 2) * Sprite.SCALED_SIZE - 1
                && MazeGame.getBigmap()[y + fat * scale][x] != 1
                && MazeGame.getBigmap()[y + fat * scale][x + fat * scale - 1] != 1
                && !( (int)(x / Sprite.SCALED_SIZE) != (int)((x + fat * scale - 1) / Sprite.SCALED_SIZE) &&
                (MazeGame.getBigmap()[y + fat * scale][(int)(x / Sprite.SCALED_SIZE + 1) * Sprite.SCALED_SIZE - 1] == 1
                        || MazeGame.getBigmap()[y + fat * scale][(int)(x / Sprite.SCALED_SIZE + 1) * Sprite.SCALED_SIZE] == 1))
        ) {
            y += 1;
            img = Sprite.noob_down.getFxImage();
        }
    }

    public void up() {
        if (    y - 1 >= 0
                && MazeGame.getBigmap()[y - 1][x] != 1
                && MazeGame.getBigmap()[y - 1][x + fat * scale - 1] != 1
                && !( (int)(x / Sprite.SCALED_SIZE) != (int)((x + fat * scale - 1) / Sprite.SCALED_SIZE) &&
                (MazeGame.getBigmap()[y - 1][(int)(x / Sprite.SCALED_SIZE + 1) * Sprite.SCALED_SIZE - 1] == 1
                        || MazeGame.getBigmap()[y - 1][(int)(x / Sprite.SCALED_SIZE + 1) * Sprite.SCALED_SIZE] == 1))
        ) {
            y -= 1;
            img = Sprite.noob_up.getFxImage();
        }
    }
}
