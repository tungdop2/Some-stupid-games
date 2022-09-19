package bomberman.entities.mob.enemy;

import bomberman.entities.mob.Mob;
import bomberman.graphics.Sprite;
import javafx.scene.image.Image;

public abstract class Enemy extends Mob {

    protected Sprite sprite;
    public Enemy(int x, int y, Image img) {
        super(x, y, img);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    @Override
    public void kill() {
        if (getDeadTime() == -1) {
            setDeadTime(System.currentTimeMillis());
        }
        if (System.currentTimeMillis() - getDeadTime() <= 400) {
            img = sprite.getFxImage();
        } else {
            img = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, System.currentTimeMillis() - getDeadTime(), 600).getFxImage();
        }
    }
}
