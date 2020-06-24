package net.thumbtack.school.windows.v4.base;

import net.thumbtack.school.windows.v4.Desktop;
import net.thumbtack.school.windows.v4.Point;
import net.thumbtack.school.windows.v4.iface.Movable;
import net.thumbtack.school.windows.v4.iface.Resizable;

import java.io.Serializable;

import static net.thumbtack.school.windows.v4.base.WindowState.fromString;

public abstract class Window implements Movable, Resizable, Serializable {
    private WindowState state;

    public Window(WindowState state) throws WindowException {
        if (state == WindowState.DESTROYED || state == null) {
            throw new WindowException(WindowErrorCode.WRONG_STATE);
        }
        this.state = state;
    }

    public Window(String state) throws WindowException{
        this(fromString(state));
    }

    public WindowState getState() {
        return state;
    }

    public void setState(WindowState state) throws WindowException {
        if (this.state == WindowState.DESTROYED || this.state == null) {
            throw new WindowException(WindowErrorCode.WRONG_STATE);
        }
        this.state = state;
    }

    public void moveTo(Point point) {
        this.moveTo(point.getX(), point.getY());
    }

    public abstract void moveTo(int x, int y);

    public abstract void moveRel(int dx, int dy);

    public abstract boolean equals(Object o);

    public abstract int hashCode();

    public abstract boolean isInside(int x, int y);

    public abstract boolean isInside(Point point);

    public abstract boolean isFullyVisibleOnDesktop(Desktop desktop);
}
