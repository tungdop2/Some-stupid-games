package bomberman.entities.bomb;

import bomberman.BombermanGame;
import bomberman.entities.AnimatedEntity;
import bomberman.graphics.Sprite;
import bomberman.musics.Sound;
import bomberman.musics.SoundEffect;
import javafx.scene.image.Image;

public class Bomb extends AnimatedEntity {
    protected long startTime;
    protected long aliveTime;
    private int power;

    public Bomb(int x, int y, Image img) {
        super(x, y, img);
        startTime = System.currentTimeMillis();
        power = BombermanGame.getBomb_power();
        BombermanGame.setEntityAt(x, y, '.');
        BombermanGame.setBomb_cout(BombermanGame.getBomb_cout() - 1);
    }

    @Override
    public void update() {
        aliveTime = System.currentTimeMillis() - startTime;

        if (aliveTime >= 2000) {
            new Sound("musics/explode.wav", 0).play();
            remove = true;
        }

        img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, ((double) aliveTime) * 60.0 / 1000.0, 30).getFxImage();

    }

    public void explode() {
        int tx, ty, d;

        BombermanGame.addBomb(new Flame(x, y, Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2));

        tx = x;
        ty = y;
        d = 0;
        while (d < power
                && BombermanGame.entityAt(tx - Sprite.SCALED_SIZE, ty) != '#') {
            d++;
            tx -= Sprite.SCALED_SIZE;
            if (BombermanGame.entityAt(tx, ty) == '*') {
                BombermanGame.addBomb(new Flame(tx, ty, Sprite.explosion_horizontal, Sprite.explosion_horizontal1, Sprite.explosion_horizontal2));
                break;
            } else {
                if (d == power
                        || BombermanGame.entityAt(tx - Sprite.SCALED_SIZE, ty) == '#') {
                    BombermanGame.addBomb(new Flame(tx, ty, Sprite.explosion_horizontal_left_last, Sprite.explosion_horizontal_left_last1, Sprite.explosion_horizontal_left_last2));
                } else {
                    BombermanGame.addBomb(new Flame(tx, ty, Sprite.explosion_horizontal, Sprite.explosion_horizontal1, Sprite.explosion_horizontal2));
                }
            }
        }

        tx = x + Sprite.SCALED_SIZE;
        ty = y;
        d = 0;
        while (d < power
                && BombermanGame.entityAt(tx, ty) != '#') {
            d++;
            if (BombermanGame.entityAt(tx, ty) == '*') {
                BombermanGame.addBomb(new Flame(tx, ty, Sprite.explosion_horizontal, Sprite.explosion_horizontal1, Sprite.explosion_horizontal2));
                break;
            } else {
                if (d == power
                        || BombermanGame.entityAt(tx + Sprite.SCALED_SIZE, ty) == '#') {
                    BombermanGame.addBomb(new Flame(tx, ty, Sprite.explosion_horizontal_right_last, Sprite.explosion_horizontal_right_last1, Sprite.explosion_horizontal_right_last2));
                } else {
                    BombermanGame.addBomb(new Flame(tx, ty, Sprite.explosion_horizontal, Sprite.explosion_horizontal1, Sprite.explosion_horizontal2));
                }
            }
            tx += Sprite.SCALED_SIZE;
        }

        tx = x;
        ty = y + Sprite.SCALED_SIZE;
        d = 0;
        while (d < power
                && BombermanGame.entityAt(tx, ty) != '#') {
            d++;
            if (BombermanGame.entityAt(tx, ty) == '*') {
                BombermanGame.addBomb(new Flame(tx, ty, Sprite.explosion_vertical, Sprite.explosion_vertical1, Sprite.explosion_vertical2));
                break;
            } else {
                if (d == power
                        || BombermanGame.entityAt(tx, ty + Sprite.SCALED_SIZE) == '#') {
                    BombermanGame.addBomb(new Flame(tx, ty, Sprite.explosion_vertical_down_last, Sprite.explosion_vertical_down_last1, Sprite.explosion_vertical_down_last2));
                } else {
                    BombermanGame.addBomb(new Flame(tx, ty, Sprite.explosion_vertical, Sprite.explosion_vertical1, Sprite.explosion_vertical2));
                }
            }
            ty += Sprite.SCALED_SIZE;
        }

        tx = x;
        ty = y;
        d = 0;
        while (d < power
                && BombermanGame.entityAt(tx, ty - Sprite.SCALED_SIZE) != '#') {
            d++;
            ty -= Sprite.SCALED_SIZE;
            if (BombermanGame.entityAt(tx, ty) == '*') {
                BombermanGame.addBomb(new Flame(tx, ty, Sprite.explosion_vertical, Sprite.explosion_vertical1, Sprite.explosion_vertical2));
                break;
            } else {
                if (d == power
                        || BombermanGame.entityAt(tx, ty - Sprite.SCALED_SIZE) == '#') {
                    BombermanGame.addBomb(new Flame(tx, ty, Sprite.explosion_vertical_top_last, Sprite.explosion_vertical_top_last1, Sprite.explosion_vertical_top_last2));
                } else {
                    BombermanGame.addBomb(new Flame(tx, ty, Sprite.explosion_vertical, Sprite.explosion_vertical1, Sprite.explosion_vertical2));
                }
            }
        }
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }
}
