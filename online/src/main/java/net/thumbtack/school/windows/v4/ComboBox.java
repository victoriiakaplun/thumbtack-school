package net.thumbtack.school.windows.v4;

import net.thumbtack.school.windows.v4.base.WindowErrorCode;
import net.thumbtack.school.windows.v4.base.WindowException;
import net.thumbtack.school.windows.v4.base.WindowState;

import java.util.Objects;

import static net.thumbtack.school.windows.v4.base.WindowState.fromString;

public class ComboBox extends ListBox {
    private Integer selected;

    public ComboBox(Point topLeft, Point bottomRight, WindowState state, String[] lines, Integer selected) throws WindowException {
        super(topLeft, bottomRight, state, lines);
        setSelected(selected);
    }

    public ComboBox(Point topLeft, Point bottomRight, String state, String[] lines, Integer selected) throws WindowException {
        this(topLeft, bottomRight, fromString(state), lines, selected);
    }

    public ComboBox(int xLeft, int yTop, int width, int height, WindowState state, String[] lines, Integer selected) throws WindowException {
        this(new Point(xLeft, yTop), new Point(xLeft + width - 1, yTop + height - 1), state, lines, selected);
    }

    public ComboBox(int xLeft, int yTop, int width, int height, String state, String[] lines, Integer selected) throws WindowException {
        this(new Point(xLeft, yTop), new Point(xLeft + width - 1, yTop + height - 1), fromString(state), lines, selected);
    }

    public ComboBox(Point topLeft, Point bottomRight, String[] lines, Integer selected) throws WindowException {
        this(topLeft, bottomRight, WindowState.ACTIVE, lines, selected);
    }

    public ComboBox(int xLeft, int yTop, int width, int height, String[] lines, Integer selected) throws WindowException {
        this(new Point(xLeft, yTop), new Point(xLeft + width - 1, yTop + height - 1), WindowState.ACTIVE, lines, selected);
    }

    public Integer getSelected() {
        return selected;
    }

    @Override
    public void setLines(String[] lines) {
        super.setLines(lines);
        try {
            setSelected(null);
        } catch (WindowException e) {
            e.printStackTrace();
        }
    }

    public void setSelected(Integer selected) throws WindowException {
        if (getLines() == null && selected != null) {
            throw new WindowException(WindowErrorCode.EMPTY_ARRAY);
        }
        if (getLines() != null && selected != null && (selected >= getLines().length || selected < 0)) {
            throw new WindowException(WindowErrorCode.WRONG_INDEX);
        }
        this.selected = selected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ComboBox comboBox = (ComboBox) o;
        return Objects.equals(selected, comboBox.selected);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), selected);
    }

    public boolean isIntersects(ComboBox comboBox) {
        return comboBox.isInside(getTopLeft()) || comboBox.isInside(getBottomRight())
                || comboBox.isInside(getTopLeft().getX(), getTopLeft().getY())
                || comboBox.isInside(getBottomRight().getY(), getBottomRight().getX());
    }

    public boolean isInside(ComboBox comboBox) {
        return (comboBox.getBottomRight().getX() <= getBottomRight().getX()
                && comboBox.getBottomRight().getX() >= getTopLeft().getX()
                && comboBox.getBottomRight().getY() >= getTopLeft().getY()
                && comboBox.getBottomRight().getY() <= getBottomRight().getY()
        );
    }
}

