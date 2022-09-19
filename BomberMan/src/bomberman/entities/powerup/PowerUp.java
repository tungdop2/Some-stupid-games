package bomberman.entities.powerup;

import bomberman.entities.Entity;
import javafx.scene.image.Image;

public abstract class PowerUp extends Entity {

    public PowerUp(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {

    }
}
