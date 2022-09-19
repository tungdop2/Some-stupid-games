package main.java.maze;

public enum DIR {
    N(1, 0, -1), S(2, 0, 1), E(4, 1, 0), W(8, -1, 0);
    int bit;
    int dx;
    int dy;
    DIR opposite;

    // use the static initializer to resolve forward references
    static {
        N.opposite = S;
        S.opposite = N;
        E.opposite = W;
        W.opposite = E;
    }

    private DIR(int bit, int dx, int dy) {
        this.bit = bit;
        this.dx = dx;
        this.dy = dy;
    }
};