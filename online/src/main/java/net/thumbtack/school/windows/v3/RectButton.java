package net.thumbtack.school.windows.v3;

import net.thumbtack.school.windows.v3.base.RectWindow;

import java.util.Objects;

public class RectButton extends RectWindow {
    private String text;

    public RectButton(Point topLeft, Point bottomRight, boolean active, String text) {
       super(topLeft,bottomRight,active);
        this.text = text;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RectButton)) return false;
        if (!super.equals(o)) return false;
        RectButton that = (RectButton) o;
        return Objects.equals(getText(), that.getText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }
}
