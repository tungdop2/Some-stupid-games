package main.java.entities.walls;

import javafx.scene.image.Image;
import main.java.entities.Entity;
import main.java.graphics.Sprite;

public class VerticalWall extends Entity {

    public VerticalWall(int x, int y) {
        super(x, y, Sprite.wall_ver.getFxImage());
    }

    @Override
    public void update() {

    }
}
