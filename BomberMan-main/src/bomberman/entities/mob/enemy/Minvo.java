package bomberman.entities.mob.enemy;

import bomberman.BombermanGame;
import bomberman.entities.bomb.Bomb;
import bomberman.entities.mob.Bomber;
import bomberman.entities.mob.Mob;
import bomberman.graphics.Sprite;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Minvo extends Enemy {

    int MAX = BombermanGame.HEIGHT * BombermanGame.WIDTH;
    boolean visited[] = new boolean[MAX];
    int path[] = new int[MAX];
    ArrayList<Integer>[] graph = new ArrayList[MAX];
    char[][] map = BombermanGame.getMap();

    public Minvo(int x, int y, Image img) {
        super(x, y, img);
        setSprite(Sprite.minvo_dead);
        setSpeed(1);
        setMovethruBrick(true);
    }

    @Override
    public void update() {
        move();
    }

    @Override
    public void move() {
        if (!isRemoved()) {
            if (x % Sprite.SCALED_SIZE == 0 && y % Sprite.SCALED_SIZE == 0) {
                play();
                animate = 0;
            } else {
                animate++;
            }

            switch (getDirection()) {
                case 0: {
                    img = Sprite.movingSprite(Sprite.minvo_right1, Sprite.minvo_right2, Sprite.minvo_right3, animate, 30).getFxImage();
                    x += getSpeed();
                    break;
                }
                case 1: {
                    img = Sprite.movingSprite(Sprite.minvo_left1, Sprite.minvo_left2, Sprite.minvo_left3, animate, 30).getFxImage();
                    x -= getSpeed();
                    break;
                }
                case 2: {
                    y -= getSpeed();
                    break;
                }
                case 3: {
                    y += getSpeed();
                    break;
                }
                default: {
                    break;
                }
            }
        }
    }

    public void play() {

        for (int i = 1; i < BombermanGame.HEIGHT - 1; i ++) {
            for (int j = 1; j < BombermanGame.WIDTH - 1; j ++) {
                graph[i * BombermanGame.WIDTH + j] = new ArrayList<>();
            }
        }

        for (int i = 1; i < BombermanGame.HEIGHT - 1; i ++) {
            for (int j = 1; j < BombermanGame.WIDTH - 1; j ++) {
                if (canMove(j * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE)) {
                    if (canMove((j - 1) * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE)) {
                        int ts = i * BombermanGame.WIDTH + j;
                        int tt = i * BombermanGame.WIDTH + j - 1;
                        graph[ts].add(tt);
                    }

                    if (canMove((j + 1) * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE)) {
                        int ts = i * BombermanGame.WIDTH + j;
                        int tt = i * BombermanGame.WIDTH + j + 1;
                        graph[ts].add(tt);
                    }

                    if (canMove(j * Sprite.SCALED_SIZE, (i + 1) * Sprite.SCALED_SIZE)) {
                        int ts = i * BombermanGame.WIDTH + j;
                        int tt = (i + 1) * BombermanGame.WIDTH + j;
                        graph[ts].add(tt);
                    }

                    if (canMove(j * Sprite.SCALED_SIZE, (i - 1) * Sprite.SCALED_SIZE)) {
                        int ts = i * BombermanGame.WIDTH + j;
                        int tt = (i - 1) * BombermanGame.WIDTH + j;
                        graph[ts].add(tt);
                    }
                }
            }
        }

        if (BombermanGame.getMobs().get(0) instanceof Bomber) {
            Bomber bomer = (Bomber) BombermanGame.getMobs().get(0);
            int bx = bomer.getX() / Sprite.SCALED_SIZE;
            int by = (bomer.getY() + Sprite.SCALED_SIZE / 8) / Sprite.SCALED_SIZE;
            BFS(by * BombermanGame.WIDTH + bx);
            int ad = x / Sprite.SCALED_SIZE + y * BombermanGame.WIDTH / Sprite.SCALED_SIZE;
            if (path[ad] == -1) {
                ArrayList<Integer> _direction = new ArrayList<>();
                if (canMove(x - getSpeed(), y)) _direction.add(1);
                if (canMove(x + getFat() - 1 + getSpeed(), y)) _direction.add(0);
                if (canMove(x, y + Sprite.SCALED_SIZE - 1 + getSpeed())) _direction.add(3);
                if (canMove(x, y - getSpeed())) _direction.add(2);
                if (_direction.size() == 0) {
                    setDirection(-1);
                } else {
                    double index = Math.random() * (_direction.size());
                    setDirection(_direction.get((int) index));
                }
            }
            if (path[ad] == ad + 1) {
                setDirection(0);
            }
            if (path[ad] == ad - 1) {
                setDirection(1);
            }
            if (path[ad] == ad - BombermanGame.WIDTH) {
                setDirection(2);
            }
            if (path[ad] == ad + BombermanGame.WIDTH) {
                setDirection(3);
            }
        } else {
            setDirection(-1);
        }
    }

    public void BFS(int s)
    {
        for (int i = 0; i < MAX; i++)
        {
            visited[i] = false;
            path[i] = -1;
        }
        Queue<Integer> q = new LinkedList<>();
        visited[s] = true;
        q.add(s);
        while (!q.isEmpty())
        {
            int u = q.poll();
            for (int i = 0; i < graph[u].size(); i++)
            {
                int v = graph[u].get(i);
                if (!visited[v])
                {
                    visited[v] = true;
                    q.add(v);
                    path[v] = u;
                }
            }
        }
    }

}
