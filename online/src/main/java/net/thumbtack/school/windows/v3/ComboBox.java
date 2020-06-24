package net.thumbtack.school.windows.v3;

import java.util.Objects;

public class ComboBox extends ListBox {
    private Integer selected;

    public ComboBox(Point topLeft, Point bottomRight, boolean active, String[] lines, Integer selected) {
        super(topLeft, bottomRight, active, lines);
        this.selected = selected;
    }

    public ComboBox(int xLeft, int yTop, int width, int height, boolean active, String[] lines, Integer selected) {
        this(new Point(xLeft, yTop), new Point(xLeft + width - 1, yTop + height - 1), active, lines, selected);

    }

    public ComboBox(Point topLeft, Point bottomRight, String[] lines, Integer selected) {
        this(topLeft, bottomRight, true, lines, selected);
    }

    public ComboBox(int xLeft, int yTop, int width, int height, String[] lines, Integer selected) {
        this(new Point(xLeft, yTop), new Point(xLeft + width - 1, yTop + height - 1), true, lines, selected);
    }

    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
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
}
