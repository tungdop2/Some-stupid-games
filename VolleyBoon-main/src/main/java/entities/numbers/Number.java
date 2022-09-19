package main.java.entities.numbers;

import main.java.entities.Entity;
import main.java.graphics.Sprite;

import javafx.scene.image.*;

public class Number extends Entity {

    Image getImg(int i) {
        Image img = null;
        if (i == 0)
            img = Sprite.number_0.getFxImage();
        else
        if (i == 1)
            img = Sprite.number_1.getFxImage();
        else
        if (i == 2)
            img = Sprite.number_2.getFxImage();
        else
        if (i == 3)
            img = Sprite.number_3.getFxImage();
        else
        if (i == 4)
            img = Sprite.number_4.getFxImage();
        else
        if (i == 5)
            img = Sprite.number_5.getFxImage();
        else
        if (i == 6)
            img = Sprite.number_6.getFxImage();
        else
        if (i == 7)
            img = Sprite.number_7.getFxImage();

        return img;
    }

    public Number(int x, int y) {
        super(x, y, Sprite.number_0.getFxImage());
    }

    public void setImgg(int i) {
        setImg(getImg(i));
    }

    @Override
    public void update() {

    }
}
