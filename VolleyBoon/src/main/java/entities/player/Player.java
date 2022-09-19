package main.java.entities.player;

import main.java.Game;
import main.java.entities.Entity;
import main.java.graphics.Sprite;

import java.util.HashSet;

public class Player extends Entity {
    public int score = 0;
    String left;
    String right;
    String jump;
    float real_x;
    float real_y;
    float vx;
    float vy;
    float ax;
    float ay = 1024 * (float) Sprite.SCALE;
    float leftbound;
    float rightbound;
    float delta_time;
    long pre_time;
    HashSet<String> keys;

    public Player(int x, int y, String left, String right, String jump, int leftbound, int rightbound) {
        super(x, y, Sprite.player.getFxImage());
        real_x = (float) x;
        real_y = (float) y;
        this.left = left;
        this.right = right;
        this.jump = jump;
        this.leftbound = (float) leftbound;
        this.rightbound = (float) rightbound;
        pre_time = System.currentTimeMillis();
    }

    @Override
    public void update() {
        delta_time = (float) (System.currentTimeMillis() - pre_time) / 1000;
        pre_time = System.currentTimeMillis();
        keys = Game.getCurrentlyActiveKeys();
        move_horizonal();
        jump();
        x = (int) real_x;
        y = (int) real_y;
    }

    public void left() {
        if (real_x - (float) Sprite.SCALE * delta_time > leftbound) {
            vx = -(float) Sprite.SCALE * 2;
        } else {
            vx = 0;
        }
    }

    public void right() {
        if (real_x + (float) 10 * Sprite.SCALE < rightbound) {
            vx = (float) Sprite.SCALE * 2;
        } else {
            vx = 0;
        }
    }

    public void move_horizonal() {
        vx = 0;
        if (keys.contains(right)) {
            right();
        }

        if (keys.contains(left)) {
            left();
        }

        if (keys.isEmpty() || (keys.contains(right) && keys.contains(left))) {
            vx = 0;
        }

        real_x += vx;
    }

    public void jump() {
        if (keys.contains(jump)) {
            if (y == 4 * Sprite.SCALED_SIZE - Sprite.SCALE) {
                vy = 128 * (float) Sprite.SCALE;
            }
        }

        real_y -= vy * delta_time - ay * delta_time * delta_time / 2;
        if (real_y <= 4 * (float) Sprite.SCALED_SIZE - (float) Sprite.SCALE) {
            vy -= ay * delta_time;
        } else {
            real_y = 4 * (float) Sprite.SCALED_SIZE - (float) Sprite.SCALE;
            vy = 0;
        }
    }

    public float getVx() {
        return vx;
    }

    public float getVy() {
        return vy;
    }
}
