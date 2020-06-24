package net.thumbtack.school.windows.v4.base;

import net.thumbtack.school.windows.v4.Desktop;
import net.thumbtack.school.windows.v4.Point;

import java.util.Objects;

public abstract class RoundWindow extends Window {
    private Point center;
    private int radius;
    private String text;

    public RoundWindow(Point center, int radius, WindowState state, String text) throws WindowException {
        super(state);
        this.center = new Point(center);
        this.radius = radius;
        this.text = text;
    }

    public RoundWindow(Point center, int radius, String state, String text) throws WindowException {
        this(center, radius, WindowState.fromString(state), text);
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(int x, int y) {
        center.setX(x);
        center.setY(y);
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public void moveTo(int x, int y) {
        center.moveTo(x, y);
    }

    @Override
    public void moveRel(int dx, int dy) {
        center.moveRel(dx, dy);
    }

    @Override
    public void resize(double ratio) {
        this.setRadius((int) Math.ceil(this.radius * ratio));
    }

    public boolean isInside(int x, int y) {
        return (x - center.getX()) * (x - center.getX()) + (y - center.getY()) * (y - center.getY()) <= radius * radius;
    }

    public boolean isInside(Point point) {
        return isInside(point.getX(), point.getY());
    }

    public boolean isFullyVisibleOnDesktop(Desktop desktop) {
        return getCenter().getX() - getRadius() >= 0
                && getCenter().getY() - getRadius() >= 0
                && getCenter().getX() + getRadius() <= desktop.getWidth() - 1
                && getCenter().getY() + getRadius() <= desktop.getHeight() - 1
                ;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoundWindow that = (RoundWindow) o;
        return radius == that.radius &&
                Objects.equals(center, that.center) &&
                Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(center, radius, text);
    }
}
