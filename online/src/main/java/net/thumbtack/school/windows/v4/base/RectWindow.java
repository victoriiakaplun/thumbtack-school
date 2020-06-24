package net.thumbtack.school.windows.v4.base;

import net.thumbtack.school.windows.v4.Desktop;
import net.thumbtack.school.windows.v4.Point;
import net.thumbtack.school.windows.v4.RectButton;

import java.util.Objects;

public abstract class RectWindow extends Window {
    private Point topLeft;
    private Point bottomRight;

    public RectWindow(Point topLeft, Point bottomRight, WindowState state) throws WindowException {
        super(state);
        this.bottomRight = new Point(bottomRight);
        this.topLeft = new Point(topLeft);
    }

    public RectWindow(Point topLeft, Point bottomRight, String state) throws WindowException {
        this(topLeft, bottomRight, WindowState.fromString(state));
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    public void setBottomRight(Point bottomRight) {
        this.bottomRight = bottomRight;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    public int getWidth() {
        return bottomRight.getX() - topLeft.getX() + 1;
    }

    public int getHeight() {
        return bottomRight.getY() - topLeft.getY() + 1;
    }

    @Override
    public void moveTo(int x, int y) {
        bottomRight.setX(x + bottomRight.getX() - topLeft.getX());
        bottomRight.setY(y + bottomRight.getY() - topLeft.getY());
        topLeft.setX(x);
        topLeft.setY(y);
    }

    @Override
    public void moveRel(int dx, int dy) {
        topLeft.moveRel(dx, dy);
        bottomRight.moveRel(dx, dy);
    }

    @Override
    public void resize(double ratio) {
        int newWidth = (int) Math.ceil(getWidth() * ratio);
        int newHeigth = (int) Math.ceil(getHeight() * ratio);
        if (newWidth < 1) {
            newWidth = 1;
        }
        if (newHeigth < 1) {
            newHeigth = 1;
        }
        bottomRight.setX(topLeft.getX() + newWidth - 1);
        bottomRight.setY(topLeft.getY() + newHeigth - 1);
    }

    public boolean isInside(int x, int y) {
        return x <= bottomRight.getX()
                && x >= topLeft.getX()
                && y >= topLeft.getY()
                && y <= bottomRight.getY();
    }

    public boolean isInside(Point point) {
        return point.getX() <= bottomRight.getX()
                && point.getX() >= topLeft.getX()
                && point.getY() >= topLeft.getY()
                && point.getY() <= bottomRight.getY();
    }


    public boolean isIntersects(RectButton rectButton) {
        return rectButton.isInside(topLeft) || rectButton.isInside(bottomRight)
                || rectButton.isInside(topLeft.getX(), bottomRight.getY())
                || rectButton.isInside(topLeft.getY(), bottomRight.getX());
    }

    public boolean isFullyVisibleOnDesktop(Desktop desktop) {
        return topLeft.getX() >= 0 && topLeft.getY() >= 0
                && bottomRight.getX() <= desktop.getWidth()
                && bottomRight.getY() <= desktop.getHeight();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RectWindow that = (RectWindow) o;
        return Objects.equals(topLeft, that.topLeft) &&
                Objects.equals(bottomRight, that.bottomRight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topLeft, bottomRight);
    }
}
