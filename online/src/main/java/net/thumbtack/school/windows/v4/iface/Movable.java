package net.thumbtack.school.windows.v4.iface;

import net.thumbtack.school.windows.v4.Point;

public interface Movable {
    default void moveTo(Point point) {
        this.moveTo(point.getX(), point.getY());
    }

    void moveTo(int x, int y);

    void moveRel(int x, int y);
}
