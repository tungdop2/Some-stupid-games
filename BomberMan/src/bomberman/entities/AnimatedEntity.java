package bomberman.entities;

import bomberman.BombermanGame;
import bomberman.entities.bomb.Bomb;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class AnimatedEntity extends Entity {

    public AnimatedEntity(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
    }
}
