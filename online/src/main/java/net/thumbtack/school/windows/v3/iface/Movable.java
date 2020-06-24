package net.thumbtack.school.windows.v3.iface;

import net.thumbtack.school.windows.v3.Point;

public interface Movable {
    default void moveTo(Point point) {
        this.moveTo(point.getX(), point.getY());
    }

    void moveTo(int x, int y);

    void moveRel(int x, int y);
}
