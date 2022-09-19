package bomberman.entities;

import bomberman.BombermanGame;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import bomberman.graphics.Sprite;

public abstract class Entity {
    protected int x;
    protected int y;
    protected Image img;
    protected boolean remove = false;

    public Entity() {

    }

    public Entity( int x, int y, Image img) {
        this.x = x;
        this.y = y;
        this.img = img;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    public boolean isRemoved() {
        return remove;
    }
    public abstract void update();
}
