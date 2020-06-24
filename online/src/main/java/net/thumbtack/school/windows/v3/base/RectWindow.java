package net.thumbtack.school.windows.v3.base;

import net.thumbtack.school.windows.v3.Desktop;
import net.thumbtack.school.windows.v3.Point;
import net.thumbtack.school.windows.v3.RectButton;

import java.util.Objects;

public abstract class RectWindow extends Window {
    private Point topLeft;
    private Point bottomRight;

    public RectWindow(Point topLeft, Point bottomRight, boolean active) {
        super(active);
        this.topLeft = new Point(topLeft);
        this.bottomRight = new Point(bottomRight);
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    public void setBottomRight(Point bottomRight) {
        this.bottomRight = bottomRight;
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
        this.topLeft.moveRel(dx, dy);
        this.bottomRight.moveRel(dx, dy);
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
        return this.isInside(point.getX(), point.getY());
    }

    public boolean isIntersects(RectButton rectButton) {
        return rectButton.isInside(topLeft) || rectButton.isInside(bottomRight)
                || rectButton.isInside(topLeft.getX(), bottomRight.getY())
                || rectButton.isInside(topLeft.getY(), bottomRight.getX());
    }

    public boolean isInside(RectButton rectButton) {
        return rectButton.getBottomRight().getX() <= bottomRight.getX()
                && rectButton.getBottomRight().getX() >= topLeft.getX()
                && rectButton.getBottomRight().getY() >= topLeft.getY()
                && rectButton.getBottomRight().getY() <= bottomRight.getY();
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
