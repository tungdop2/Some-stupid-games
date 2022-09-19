package main.java.entities.walls;

import javafx.scene.image.Image;
import main.java.entities.Entity;
import main.java.graphics.Sprite;

public class HorizontalWall extends Entity {
    public HorizontalWall(int x, int y) {
        super(x, y, Sprite.wall_hor.getFxImage());
    }

    @Override
    public void update() {

    }
}
