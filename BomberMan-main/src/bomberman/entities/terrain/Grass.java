package bomberman.entities.terrain;

import bomberman.entities.Entity;
import javafx.scene.image.Image;

import static bomberman.BombermanGame.*;

public class Grass extends Entity {

    public Grass(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
    }
}
