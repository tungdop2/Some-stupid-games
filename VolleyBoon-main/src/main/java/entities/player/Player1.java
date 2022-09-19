package main.java.entities.player;

import main.java.graphics.Sprite;

public class Player1 extends Player{
    public Player1() {
        super(5 * Sprite.SCALE, 4 * Sprite.SCALED_SIZE - Sprite.SCALE,
                "A", "D", "W",
                4 * Sprite.SCALE, 4 * Sprite.SCALED_SIZE - 11 * Sprite.SCALE);
    }
}
