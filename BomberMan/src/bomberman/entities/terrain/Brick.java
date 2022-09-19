package bomberman.entities.terrain;

import bomberman.entities.AnimatedEntity;
import bomberman.entities.Entity;
import bomberman.entities.powerup.PowerUp;
import bomberman.graphics.Sprite;
import javafx.scene.image.Image;

public class Brick extends AnimatedEntity {
    private PowerUp hiddenEntity;
    private long breakTime = -1;

    public Brick(int x, int y, Image img) {
        super(x, y, img);
    }

    public long getBreakTime() {
        return breakTime;
    }

    public void setBreakTime(long breakTime) {
        this.breakTime = breakTime;
    }

    public PowerUp getHiddenEntity() {
        return hiddenEntity;
    }

    public void setHiddenEntity(PowerUp hiddenEntity) {
        this.hiddenEntity = hiddenEntity;
    }

    @Override
    public void update() {

    }

    public void kill() {
        if (getBreakTime() == -1 ) {
            setBreakTime(System.currentTimeMillis());
        }
        img = Sprite.movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2,
                (double) (System.currentTimeMillis() - getBreakTime()), 500).getFxImage();
    }
}
