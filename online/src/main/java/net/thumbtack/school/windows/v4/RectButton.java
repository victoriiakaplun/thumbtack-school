package net.thumbtack.school.windows.v4;

import net.thumbtack.school.windows.v4.base.RectWindow;
import net.thumbtack.school.windows.v4.base.WindowException;
import net.thumbtack.school.windows.v4.base.WindowState;

import java.util.Objects;

import static net.thumbtack.school.windows.v4.base.WindowState.fromString;

public class RectButton extends RectWindow {
    private String text;

    public RectButton(Point topLeft, Point bottomRight, WindowState state, String text) throws WindowException {
        super(topLeft, bottomRight, state);
        this.text = text;
    }

    public RectButton(int xLeft, int yTop, int width, int height, WindowState state, String text) throws WindowException {
        this(new Point(xLeft, yTop), new Point(xLeft + width - 1, yTop + height - 1), state, text);

    }
    public RectButton(Point topLeft, Point bottomRight, String state, String text) throws WindowException{
        this(topLeft,bottomRight,fromString(state), text);
    }


    public RectButton(int xLeft, int yTop, int width, int height, String state, String text) throws WindowException {
        this(new Point(xLeft, yTop), new Point(xLeft + width - 1, yTop + height - 1), fromString(state), text);
    }

    public RectButton(Point topLeft, Point bottomRight, String text) throws WindowException {
        this(topLeft, bottomRight, WindowState.ACTIVE, text);
    }

    public RectButton(int xLeft, int yTop, int width, int height, String text) throws WindowException {
        this(new Point(xLeft, yTop), new Point(xLeft + width - 1, yTop + height - 1), WindowState.ACTIVE, text);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isInside(RectButton rectButton) {
        return rectButton.getBottomRight().getX() <= getBottomRight().getX()
                && rectButton.getBottomRight().getX() >= getTopLeft().getX()
                && rectButton.getBottomRight().getY() >=getTopLeft().getY()
                && rectButton.getBottomRight().getY() <= getBottomRight().getY();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RectButton that = (RectButton) o;
        return Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), text);
    }

    @Override
    public String toString() {
        return getTopLeft().toString() + " " + getBottomRight().toString() + " " + getState() + " " + text;
    }
}
