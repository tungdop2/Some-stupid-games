package bomberman.entities.mob;

import bomberman.BombermanGame;
import bomberman.entities.AnimatedEntity;
import bomberman.entities.Entity;
import bomberman.entities.bomb.Bomb;
import bomberman.entities.bomb.Flame;
import bomberman.entities.mob.enemy.Balloom;
import bomberman.entities.terrain.Grass;
import bomberman.graphics.Sprite;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public abstract class Mob extends AnimatedEntity {

    protected int animate = 0;
    private int direction = -1;
    private int speed = BombermanGame.getSpeed();
    private int fat = Sprite.SCALED_SIZE;
    private long deadTime = -1;
    private boolean movethruBomb = false;
    private boolean movethruBrick = false;

    public Mob(int x, int y, Image img) {
        super(x, y, img);
    }

    public boolean isMovethruBomb() {
        return movethruBomb;
    }

    public void setMovethruBomb(boolean movethruBomb) {
        this.movethruBomb = movethruBomb;
    }

    public long getDeadTime() {
        return deadTime;
    }

    public void setDeadTime(long deadTime) {
        this.deadTime = deadTime;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean isMovethruBrick() {
        return movethruBrick;
    }

    public void setMovethruBrick(boolean movethruBrick) {
        this.movethruBrick = movethruBrick;
    }

    @Override
    public void update() {

    }

    public abstract void move();

    public boolean canMove(int x, int y) {
        if (BombermanGame.entityAt(x, y) == '#') {
            return false;
        }
        if (BombermanGame.entityAt(x, y) == '*' && isMovethruBrick() == false) {
            return false;
        }
        if (BombermanGame.entityAt(x, y) == '.' && isMovethruBomb() == false) {
            return false;
        }

        return true;
    }
    public abstract void kill();

    public boolean standOnObject(Entity b) {
        return (((int) (b.getX() / Sprite.SCALED_SIZE) == (int) (x / Sprite.SCALED_SIZE)
                || (int) (b.getX() / Sprite.SCALED_SIZE) == (int) ((x + getFat() - 1) / Sprite.SCALED_SIZE))
                && ((int) (b.getY() / Sprite.SCALED_SIZE) == (int) ((y + Sprite.SCALED_SIZE / 8) / Sprite.SCALED_SIZE)
                || (int) (b.getY() / Sprite.SCALED_SIZE) == (int) ((y + Sprite.SCALED_SIZE - 1) / Sprite.SCALED_SIZE))
        );
    }

    public boolean isSafe(int x, int y) {
        Mob m = new Balloom(x, y, Sprite.balloom_right1.getFxImage());
        List<Bomb> bombs = BombermanGame.getBombs();

        for (int i = 0; i < bombs.size(); i ++) {
            if (bombs.get(i) instanceof Bomb) {
                Bomb b = bombs.get(i);

                if (m.standOnObject(b)) {
                    return false;
                }

                int tx;
                int ty;
                int d;
                int power = b.getPower();

                tx = b.getX();
                ty = b.getY();
                d = 0;
                while (d < power
                        && BombermanGame.entityAt(tx - Sprite.SCALED_SIZE, ty) != '#'
                        && BombermanGame.entityAt(tx - Sprite.SCALED_SIZE, ty) != '*') {
                    if (m.standOnObject(new Grass(tx - Sprite.SCALED_SIZE, ty, Sprite.grass.getFxImage()))) {
                        return false;
                    }
                    d++;
                    tx -= Sprite.SCALED_SIZE;
                }

                tx = b.getX() + Sprite.SCALED_SIZE;
                ty = b.getY();
                d = 0;
                while (d < power
                        && BombermanGame.entityAt(tx, ty) != '#'
                        && BombermanGame.entityAt(tx, ty) != '*') {
                    if (m.standOnObject(new Grass(tx, ty, Sprite.grass.getFxImage()))) {
                        return false;
                    }
                    d++;
                    tx += Sprite.SCALED_SIZE;
                }

                tx = b.getX();
                ty = b.getY() + Sprite.SCALED_SIZE;
                d = 0;
                while (d < power
                        && BombermanGame.entityAt(tx, ty) != '#'
                        && BombermanGame.entityAt(tx, ty) != '*') {
                    d++;
                    if (m.standOnObject(new Grass(tx, ty, Sprite.grass.getFxImage()))) {
                        return false;
                    }
                    ty += Sprite.SCALED_SIZE;
                }

                tx = b.getX();
                ty = b.getY();
                d = 0;
                while (d < power
                        && BombermanGame.entityAt(tx, ty - Sprite.SCALED_SIZE) != '#'
                        && BombermanGame.entityAt(tx, ty - Sprite.SCALED_SIZE) != '*') {
                    if (m.standOnObject(new Grass(tx, ty - Sprite.SCALED_SIZE, Sprite.grass.getFxImage()))) {
                        return false;
                    }
                    d++;
                    ty -= Sprite.SCALED_SIZE;
                }
            }
        }
        return true;
    }

    public void randomMove() {
        ArrayList<Integer> _direction = new ArrayList<>();
        if (canMove(x - getSpeed(), y)) _direction.add(1);
        if (canMove(x + getFat() - 1 + getSpeed(), y)) _direction.add(0);
        if (canMove(x, y + Sprite.SCALED_SIZE - 1 + getSpeed())) _direction.add(3);
        if (canMove(x, y - getSpeed())) _direction.add(2);
        if (_direction.size() == 0) {
            setDirection(-1);
        } else {
            double index = Math.random() * (_direction.size());
            setDirection(_direction.get((int) index));
        }
    }
}
