package bomberman.entities.mob;

import bomberman.BombermanGame;
import bomberman.entities.bomb.Bomb;
import bomberman.entities.powerup.*;
import bomberman.graphics.Sprite;
import bomberman.musics.SoundEffect;
import javafx.scene.image.Image;

public class Bomber extends Mob {
    Image stop = Sprite.player_down.getFxImage();
    private int animate = 0;

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
        setFat(Sprite.SCALED_SIZE * 5 / 8);
        setSpeed(BombermanGame.getSpeed());
    }

    @Override
    public void update() {
        move();
    }

    @Override
    public void move() {
        if (!isRemoved()) {
            setMovethruBomb(false);
            for (int i = 0; i < BombermanGame.getBombs().size(); i++) {
                Bomb b = BombermanGame.getBombs().get(i);
                if (standOnObject(b)) {
                    setMovethruBomb(true);
                }
            }
            play();
            SoundEffect.run.play();
            switch (getDirection()) {
                case 0: {
                    if (canMove(x + getSpeed() + getFat() - 1, y + Sprite.SCALED_SIZE / 8)
                            && canMove(x + getFat() + getSpeed() - 1, y + Sprite.SCALED_SIZE - 1)) {
                        x += getSpeed();
                    }
                    if (animate == 0) {
                        img = Sprite.player_right.getFxImage();
                    } else {
                        img = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, animate, 20 / getSpeed()).getFxImage();
                    }
                    setDirection(-1);
                    break;
                }
                case 1: {
                    if (canMove(x - getSpeed(), y + Sprite.SCALED_SIZE / 8)
                            && canMove(x - getSpeed(), y + Sprite.SCALED_SIZE - 1)) {
                        x -= getSpeed();
                    }
                    if (animate == 0) {
                        img = Sprite.player_left.getFxImage();
                    } else {
                        img = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, animate, 20 / getSpeed()).getFxImage();
                    }
                    setDirection(-1);
                    break;
                }
                case 2: {
                    if (canMove(x, y - getSpeed() + Sprite.SCALED_SIZE / 8)
                            && canMove(x + getFat() - 1, y - getSpeed() + Sprite.SCALED_SIZE / 8)) {
                        y -= getSpeed();
                    }
                    if (animate == 0) {
                        img = Sprite.player_up.getFxImage();
                    } else {
                        img = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, animate,20 / getSpeed()).getFxImage();
                    }
                    setDirection(-1);
                    break;
                }
                case 3: {
                    if (canMove(x, y + Sprite.SCALED_SIZE + getSpeed() - 1)
                            && canMove(x + getFat() - 1, y + Sprite.SCALED_SIZE + getSpeed() - 1)) {
                        y += getSpeed();
                    }
                    if (animate == 0) {
                        img = Sprite.player_down.getFxImage();
                    } else {
                        img = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, animate, 20 / getSpeed()).getFxImage();
                    }
                    setDirection(-1);
                    break;
                }
                default: {
                    SoundEffect.run.stop();
                    setDirection(-1);
                }
            }
        }

        for (int i = 0; i < BombermanGame.getPowers().size(); i++) {
            PowerUp p = BombermanGame.getPowers().get(i);
            if (standOnObject(p)) {
                if (p instanceof PowerItem) {
                    BombermanGame.setBomb_power(BombermanGame.getBomb_power() + 1);
                    p.setRemove(true);
                } else if (p instanceof SpeedItem) {
                    BombermanGame.setSpeed(BombermanGame.getSpeed() * 2);
                    p.setRemove(true);
                } else if (p instanceof BombItem) {
                    BombermanGame.setBomb_cout(BombermanGame.getBomb_cout() + 1);
                    p.setRemove(true);
                } else if (p instanceof Portal) {
                    if (BombermanGame.getMobs().size() == 1) {
                        p.setRemove(true);
                        BombermanGame.setEnd_level(true);
                    }
                }
            }
        }

        for (int i = 0; i < BombermanGame.getMobs().size(); i++) {
            if (!(BombermanGame.getMobs().get(i) instanceof Bomber) && !(BombermanGame.getMobs().get(i).isRemoved())) {
                Mob m = BombermanGame.getMobs().get(i);
                if (standOnObject(m)) {
                    this.setRemove(true);
                }
            }
        }
    }


    public void play() {
        if (BombermanGame.getKey().size() == 0) {
            animate = 0;
            img = stop;
        } else {
            animate++;
        }
        if (BombermanGame.getKey().contains("RIGHT")) {
            setDirection(0);
            stop = Sprite.player_right.getFxImage();
        } else if (BombermanGame.getKey().contains("LEFT")) {
            setDirection(1);
            stop = Sprite.player_left.getFxImage();
        } else if (BombermanGame.getKey().contains("UP")) {
            setDirection(2);
            stop = Sprite.player_up.getFxImage();
        } else if (BombermanGame.getKey().contains("DOWN")) {
            setDirection(3);
            stop = Sprite.player_down.getFxImage();
        }

        if (BombermanGame.getKey().contains("SPACE")) {
            int bx = x;
            int by = y;
            if (x % Sprite.SCALED_SIZE > Sprite.SCALED_SIZE / 2) {
                bx = x + Sprite.SCALED_SIZE;
            }
            if ((y + Sprite.SCALED_SIZE / 8) % Sprite.SCALED_SIZE > Sprite.SCALED_SIZE / 2) {
                by = y + Sprite.SCALED_SIZE;
            }
            if (BombermanGame.getBomb_cout() > 0 && BombermanGame.entityAt(bx, by + Sprite.SCALED_SIZE / 8) != '.') {
                BombermanGame.addBomb(new Bomb(bx / Sprite.SCALED_SIZE * Sprite.SCALED_SIZE, (by + Sprite.SCALED_SIZE / 8) / Sprite.SCALED_SIZE * Sprite.SCALED_SIZE, Sprite.bomb.getFxImage()));
            }
        }
    }

    @Override
    public void kill() {
        if (getDeadTime() == -1) {
            setDeadTime(System.currentTimeMillis());
        }
        img = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3,
                System.currentTimeMillis() - getDeadTime(), 1000).getFxImage();
    }

}
