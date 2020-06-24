package net.thumbtack.school.windows.v3.base;

import net.thumbtack.school.windows.v3.Desktop;
import net.thumbtack.school.windows.v3.Point;
import net.thumbtack.school.windows.v3.iface.Movable;
import net.thumbtack.school.windows.v3.iface.Resizable;

public abstract class Window implements Movable, Resizable {
    private boolean active;

    public Window(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean b) {
        active = b;
    }

    public abstract void moveTo(int x, int y);

    public abstract void moveRel(int dx, int dy);

    public abstract boolean equals(Object o);

    public abstract int hashCode();

    public abstract boolean isInside(int x, int y);

    public abstract boolean isInside(Point point);

    public abstract boolean isFullyVisibleOnDesktop(Desktop desktop);
}
