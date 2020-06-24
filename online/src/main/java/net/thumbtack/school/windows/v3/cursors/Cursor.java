package net.thumbtack.school.windows.v3.cursors;

import net.thumbtack.school.windows.v3.Point;
import net.thumbtack.school.windows.v3.iface.Movable;

public class Cursor implements Movable {
    private int x;
    private int y;
    private int cursorForm;

    public Cursor(int x, int y, int cursorForm) {
        this.x = x;
        this.y = y;
        this.cursorForm = cursorForm;
    }

    public Cursor(Point point, int cursorForm) {
        this(point.getX(), point.getY(), cursorForm);
    }

    public int getCursorForm() {
        return cursorForm;
    }

    public void setCursorForm(int cursorForm) {
        this.cursorForm = cursorForm;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void moveRel(int dx, int dy) {
        this.x = x + dx;
        this.y = y + dy;
    }
}
