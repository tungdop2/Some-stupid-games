package bomberman.entities.mob.enemy;

import bomberman.BombermanGame;
import bomberman.entities.mob.Mob;
import bomberman.graphics.Sprite;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class Balloom extends Enemy {

    public Balloom(int x, int y, Image img) {
        super(x, y, img);
        setSpeed(1);
        setSprite(Sprite.balloom_dead);
    }

    @Override
    public void update() {
        move();
    }

    @Override
    public void move() {
        if (!isRemoved()) {
            if (x % Sprite.SCALED_SIZE == 0 && y % Sprite.SCALED_SIZE == 0) {
                play();
                animate = 0;
            } else {
                animate++;
            }

            switch (getDirection()) {
                case 0: {
                    img = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, animate, 30).getFxImage();
                    x += getSpeed();
                    break;
                }
                case 1: {
                    img = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, animate, 30).getFxImage();
                    x -= getSpeed();
                    break;
                }
                case 2: {
                    y -= getSpeed();
                    break;
                }
                case 3: {
                    y += getSpeed();
                    break;
                }
                default: {
                    break;
                }
            }
        }
    }

    public void play() {
        randomMove();
    }
}
