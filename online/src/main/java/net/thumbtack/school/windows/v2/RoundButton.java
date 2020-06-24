package net.thumbtack.school.windows.v2;

import java.util.Objects;

public class RoundButton {
    private Point center;
    private int radius;
    private boolean active;
    private String text;

    public RoundButton(Point center, int radius, boolean active, String text) {
        this.center = new Point(center);
        this.radius = radius;
        this.active = active;
        this.text = new String(text);
    }

    public RoundButton(int xCenter, int yCenter, int radius, boolean active, String text) {
        this(new Point(xCenter, yCenter), radius, active, text);
    }

    public RoundButton(Point center, int radius, String text) {
        this(center, radius, true, text);
    }

    public RoundButton(int xCenter, int yCenter, int radius, String text) {
        this(xCenter, yCenter, radius, true, text);
    }

    public Point getCenter() {
        return center;
    }

    public int getRadius() {
        return radius;
    }

    public String getText() {
        return text;
    }

    public boolean isActive() {
        return active;
    }

    public void moveTo(int x, int y) {
        center.setX(x);
        center.setY(y);
    }

    public void moveTo(Point point) {
        this.moveTo(point.getX(), point.getY());
    }

    public void setCenter(int x, int y) {
        center.setX(x);
        center.setY(y);
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setText(String text) {
        this.text = new String(text);
    }

    public void moveRel(int dx, int dy) {
        center.moveRel(dx, dy);
    }

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
        RoundButton that = (RoundButton) o;
        return radius == that.radius &&
                active == that.active &&
                Objects.equals(center, that.center) &&
                Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(center, radius, active, text);
    }
}
