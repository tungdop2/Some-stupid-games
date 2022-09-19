package bomberman.entities.bomb;

import bomberman.BombermanGame;
import bomberman.entities.AnimatedEntity;
import bomberman.entities.bomb.Bomb;
import bomberman.entities.mob.Bomber;
import bomberman.entities.mob.Mob;
import bomberman.entities.terrain.Brick;
import bomberman.graphics.Sprite;
import bomberman.musics.Sound;
import bomberman.musics.SoundEffect;
import javafx.scene.image.Image;

import java.util.List;

public class Flame extends Bomb {
    private Sprite flame1;
    private Sprite flame2;
    private Sprite flame3;
    protected Bomb parent;
    public Flame(int x, int y, Sprite flame1, Sprite flame2, Sprite flame3) {
        super(x, y, flame1.getFxImage());
        this.flame1 = flame1;
        this.flame2 = flame2;
        this.flame3 = flame3;
        BombermanGame.setEntityAt(x, y, ' ');
        BombermanGame.setBomb_cout(BombermanGame.getBomb_cout() + 1);
    }

    @Override
    public void update() {
        aliveTime = System.currentTimeMillis() - startTime;

        if (aliveTime >= 500) {
            remove = true;
        }
        else {
            List<Brick> bricks = BombermanGame.getBricks();
            bricks.forEach(b -> {
                if (b.getX() == x && b.getY() == y) {
                    this.setRemove(true);
                    b.setRemove(true);
                }
            });

            List<Mob> mobs = BombermanGame.getMobs();
            mobs.forEach(m -> {
                if (m.standOnObject(this)) {
                    m.setRemove(true);
                    if (m instanceof Bomber) {
                        SoundEffect.death.play();
                    }
                }
            });

            List<Bomb> bombs = BombermanGame.getBombs();
            bombs.forEach(b -> {
                if (!(b instanceof Flame) && b.getX() == x && b.getY() == y) {
                    b.setRemove(true);
                }
            });
        }
        img = Sprite.movingSprite(flame1, flame2, flame3, aliveTime, 500).getFxImage();
    }
}
