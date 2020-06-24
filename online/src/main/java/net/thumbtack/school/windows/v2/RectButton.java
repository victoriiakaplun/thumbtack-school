package net.thumbtack.school.windows.v2;

import java.util.Objects;

public class RectButton {
    private Point topLeft;
    private Point bottomRight;
    boolean active;
    private String text;

    public RectButton(Point topLeft, Point bottomRight, boolean active, String text) {
        this.topLeft = new Point(topLeft);
        this.bottomRight = new Point(bottomRight);
        this.active = active;
        this.text = new String(text);
    }

    public RectButton(int xLeft, int yTop, int width, int height, boolean active, String text) {
        this(new Point(xLeft, yTop), new Point(xLeft + width - 1, yTop + height - 1), active, text);

    }

    public RectButton(Point topLeft, Point bottomRight, String text) {
        this(topLeft, bottomRight, true, text);
    }

    public RectButton(int xLeft, int yTop, int width, int height, String text) {
        this(new Point(xLeft, yTop), new Point(xLeft + width - 1, yTop + height - 1), true, text);

    }

    public Point getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    public void setBottomRight(Point bottomRight) {
        this.bottomRight = bottomRight;
    }

    public boolean isActive() {
        return active;
    }

    public int getWidth() {
        return bottomRight.getX() - topLeft.getX() + 1;
    }

    public int getHeight() {
        return bottomRight.getY() - topLeft.getY() + 1;
    }

    public void moveTo(int x, int y) {
        bottomRight.setX(x + bottomRight.getX() - topLeft.getX());
        bottomRight.setY(y + bottomRight.getY() - topLeft.getY());
        topLeft.setX(x);
        topLeft.setY(y);

    }

    public void moveTo(Point point) {
        bottomRight.setX(point.getX() + bottomRight.getX() - topLeft.getX());
        bottomRight.setY(point.getY() + bottomRight.getY() - topLeft.getY());
        topLeft.setX(point.getX());
        topLeft.setY(point.getY());
    }

    public void moveRel(int dx, int dy) {
        topLeft.moveRel(dx, dy);
        bottomRight.moveRel(dx, dy);

    }

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
        return (x <= bottomRight.getX()
                && x >= topLeft.getX()
                && y >= topLeft.getY()
                && y <= bottomRight.getY()
        );
    }

    public boolean isInside(Point point) {
        return (point.getX() <= bottomRight.getX()
                && point.getX() >= topLeft.getX()
                && point.getY() >= topLeft.getY()
                && point.getY() <= bottomRight.getY()
        );

    }

    public boolean isIntersects(RectButton rectButton) {
        return rectButton.isInside(topLeft) || rectButton.isInside(bottomRight)
                || rectButton.isInside(topLeft.getX(), bottomRight.getY())
                || rectButton.isInside(topLeft.getY(), bottomRight.getX());
    }

    public boolean isInside(RectButton rectButton) {
        return (rectButton.getBottomRight().getX() <= bottomRight.getX()
                && rectButton.getBottomRight().getX() >= topLeft.getX()
                && rectButton.getBottomRight().getY() >= topLeft.getY()
                && rectButton.getBottomRight().getY() <= bottomRight.getY()
        );
    }

    public boolean isFullyVisibleOnDesktop(Desktop desktop) {
        return (topLeft.getX() >= 0 && topLeft.getY() >= 0
                && bottomRight.getX() <= desktop.getWidth()
                && bottomRight.getY() <= desktop.getHeight());
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = new String(text);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RectButton that = (RectButton) o;
        return active == that.active &&
                Objects.equals(topLeft, that.topLeft) &&
                Objects.equals(bottomRight, that.bottomRight) &&
                Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topLeft, bottomRight, active, text);
    }

}
