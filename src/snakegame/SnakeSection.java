package snakegame;

import java.util.Objects;

/**
 * Змея состоит из частей
 * Этот класс отвечает за части.
 */
public class SnakeSection {
    //Координаты
   private int x, y;

    public SnakeSection(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SnakeSection section = (SnakeSection) o;
        return x == section.x &&
                y == section.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
