package main.java.entities.ball;

import main.java.Game;
import main.java.entities.Entity;
import main.java.entities.player.Player;
import main.java.graphics.Sprite;

public class Ball extends Entity {

    long birth_time;
    float delta_time;
    float real_x;
    float real_y;
    float vx = (float) 48 * (float) Sprite.SCALE;
    float vy = (float) 64 * (float) Sprite.SCALE;
    float ay = (float) 256 * (float) Sprite.SCALE;
    float gy = (float) 256 * (float) Sprite.SCALE;

    long pre_time;

    public Ball(float i) {
        super(3 * Sprite.SCALED_SIZE + 6 * Sprite.SCALE, 3 * Sprite.SCALED_SIZE - Sprite.SCALE * 25, Sprite.ball.getFxImage());
        real_x = (float) x;
        real_y = (float) y;
        vx *= i;
        birth_time = System.currentTimeMillis();
        pre_time = System.currentTimeMillis();
    }

    @Override
    public void update() {
//        System.out.println(vy + " " + real_y);
        delta_time = (float) (System.currentTimeMillis() - pre_time) / (float) 1000;

        if (System.currentTimeMillis() - birth_time > 1000) {
            ay = gy + (vy * (float) 0.05);
            real_x += vx * delta_time;
            real_y -= vy * delta_time - ay * delta_time * delta_time / (float) 2;
            vy -= ay * delta_time;

            physic();
        } else {
            delta_time = 0;
        }

        x = (int) real_x;
        y = (int) real_y;
        pre_time = System.currentTimeMillis();
    }

    public boolean between(int a, int b) {
        return (a <= real_x && real_x <= b)
                || (a <= real_x + Sprite.SCALE * 4 - 1 && real_x + Sprite.SCALE * 4 - 1 <= b);
    }

    public void meetPlayer(Player p) {
        if (between(p.getX(), p.getX() + Sprite.SCALE * 10 - 1)
                && vy < 0
                && real_y + Sprite.SCALE * 4 - 1 >= p.getY()
                && real_y < p.getY()) {

            if (vx * p.getVx() < 0) {
                vx= -vx;
            }

            if (p.getVy() <= 0)
            bounce(Sprite.SCALE * 4, p.getY(), 0);
            else {
                bounce(Sprite.SCALE * 4, p.getY(), p.getY() / 4);
            }
        }
    }

    public void meetVerWall() {
        if (real_x < Sprite.SCALE * 6) {
            real_x = 2 * Sprite.SCALE * 6 - real_x;
            vx = -vx;
        } else if (real_x + Sprite.SCALE * 4 > Sprite.SCALED_SIZE * 7 - 6 * Sprite.SCALE - 1) {
            real_x = real_x - 2 * ((real_x + Sprite.SCALE * 4) - (Sprite.SCALED_SIZE * 7 - 6 * Sprite.SCALE - 1));
            vx = -vx;
        } else if (vx > 0
                && real_y >= 3 * Sprite.SCALED_SIZE - Sprite.SCALE * 5
                && real_x <= 3 * Sprite.SCALED_SIZE + 5 * Sprite.SCALE - 1
                && real_x + Sprite.SCALE * 4 > 3 * Sprite.SCALED_SIZE + 5 * Sprite.SCALE - 1
        ) {
            real_x = real_x - 2 * ((real_x + Sprite.SCALE * 4) - (3 * Sprite.SCALED_SIZE + 5 * Sprite.SCALE - 1));
            vx = -vx;
        } else if (
                vx < 0
                        && real_y >= 3 * Sprite.SCALED_SIZE - Sprite.SCALE * 5
                        && real_x > 3 * Sprite.SCALED_SIZE + 5 * Sprite.SCALE - 1
                        && real_x < 3 * Sprite.SCALED_SIZE + 11 * Sprite.SCALE
        ) {
            real_x = 2 * (3 * Sprite.SCALED_SIZE + 11 * Sprite.SCALE) - real_x;
            vx = -vx;
        }
    }

    public void meerHorWall() {
        if (real_y < Sprite.SCALE * 6) {
            bounce(0, Sprite.SCALE * 6, 0);
        } else if (
                between(3 * Sprite.SCALED_SIZE + 5 * Sprite.SCALE, 3 * Sprite.SCALED_SIZE + 11 * Sprite.SCALE - 1)
                        && real_y + Sprite.SCALE * 4 > 3 * Sprite.SCALED_SIZE - Sprite.SCALE * 5 - 1
        ) {
            bounce((float) Sprite.SCALE * 4, 3 * Sprite.SCALED_SIZE - Sprite.SCALE * 5 - 1, 0);
        }
    }

    public void physic() {
        meetPlayer(Game.getP1());
        meetPlayer(Game.getP2());
        meetVerWall();
        meerHorWall();
    }

    public float getTime(float v, float a, float s) {
        float delta = v * v + 2 * a * s;
        if (-v - (float) Math.sqrt(delta) < 0) {
            return (-v + (float) Math.sqrt(delta)) / a;
        }
        return (-v - (float) Math.sqrt(delta)) / a;
    }

    private void bounce(float delta_y, float wall, float force_y) {
//        System.out.println(delta_time);
        float fake_vy = vy + ay * delta_time;
        float fake_y = real_y + (fake_vy * delta_time - ay * delta_time * delta_time / (float) 2);
        float t1 = getTime(-fake_vy, ay, wall - (fake_y + delta_y));
        float t2 = delta_time - t1;
//        System.out.println(t1 + " " + t2);
        fake_y -= fake_vy * t1 + -ay * t1 * t1 / (float) 2;
        fake_vy -= ay * t1;
        fake_vy = -fake_vy + force_y;
        ay = gy + (fake_vy * (float) 0.1);
        fake_y -= fake_vy * t2 + -ay * t2 * t2 / (float) 2;
        fake_vy -= ay * t2;
        real_y = fake_y;
        vy = fake_vy;
    }
}
