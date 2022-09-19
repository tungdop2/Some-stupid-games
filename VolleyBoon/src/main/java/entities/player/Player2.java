package main.java.entities.player;

import main.java.graphics.Sprite;

public class Player2 extends Player{
    public Player2() {
        super(6 * Sprite.SCALED_SIZE + Sprite.SCALE, 4 * Sprite.SCALED_SIZE - Sprite.SCALE,
                "LEFT", "RIGHT", "UP",
                3 * Sprite.SCALED_SIZE + 10 * Sprite.SCALE, 7 * Sprite.SCALED_SIZE - 5 * Sprite.SCALE);
    }
}
