package net.thumbtack.school.windows.v3;

import net.thumbtack.school.windows.v3.base.RectWindow;

import java.util.Arrays;

import static net.thumbtack.school.base.StringOperations.reverse;

public class ListBox extends RectWindow {
    private String[] lines;

    public ListBox(Point topLeft, Point bottomRight, boolean active, String[] lines) {
        super(topLeft, bottomRight, active);
        setLines(lines);
    }

    public ListBox(int xLeft, int yTop, int width, int height, boolean active, String[] lines) {
        this(new Point(xLeft, yTop), new Point(xLeft + width - 1, yTop + height - 1), active, lines);
    }

    public ListBox(Point topLeft, Point bottomRight, String[] lines) {
        this(topLeft, bottomRight, true, lines);
    }

    public ListBox(int xLeft, int yTop, int width, int height, String[] lines) {
        this(new Point(xLeft, yTop), new Point(xLeft + width - 1, yTop + height - 1), true, lines);
    }

    public String[] getLines() {
        return lines;
    }

    public void setLines(String[] lines) {
        if (lines == null) {
            this.lines = null;
        } else {
            this.lines = new String[lines.length];
            System.arraycopy(lines, 0, this.lines, 0, lines.length);
        }
    }

    public String[] getLinesSlice(int from, int to) {
        if (lines == null) {
            return null;
        }
        if (to > lines.length) {
            to = lines.length;
        }
        String[] linesSlice = new String[to - from];
        int i = 0;
        for (int pos = from; pos < to; pos++) {
            linesSlice[i] = lines[pos];
            i++;
        }
        return linesSlice;
    }

    public String getLine(int index) {
        if (index >= lines.length || lines[index] == null) {
            return null;
        }
        return lines[index];
    }

    public void setLine(int index, String line) {
        if (lines[index] != null) {
            lines[index] = new String(line);
        }
    }

    public Integer findLine(String line) {
        if (lines != null) {
            for (Integer pos = 0; pos < lines.length; pos++) {
                if (lines[pos].equals(line)) {
                    return pos;
                }
            }
        }
        return null;
    }

    public void reverseLineOrder() {
        String tmp;
        if (lines != null) {
            for (int i = 0; i < lines.length / 2; i++) {
                tmp = lines[i];
                lines[i] = lines[lines.length - i - 1];
                lines[lines.length - i - 1] = tmp;
            }
        }
    }

    public void reverseLines() {
        if (lines != null) {
            for (int pos = 0; pos < lines.length; pos++) {
                setLine(pos, reverse(lines[pos]));
            }
        }
    }

    public void duplicateLines() {
        if (lines != null) {
            String[] tmp = new String[lines.length * 2];
            int index = 0;
            for (int pos = 0; pos < tmp.length; pos += 2) {
                tmp[pos] = lines[index];
                tmp[pos + 1] = lines[index];
                index++;
            }
            setLines(tmp);
        }

    }

    public void removeOddLines() {
        if (lines != null && lines.length != 1) {
            String[] tmp = new String[lines.length / 2 + lines.length % 2];
            int index = 0;
            for (int pos = 0; pos < lines.length; pos += 2) {
                tmp[index] = lines[pos];
                index++;
            }
            setLines(tmp);
        }
    }

    public boolean isSortedDescendant() {
        if (lines == null) {
            return true;
        }
        for (int pos = 1; pos < lines.length; pos++) {
            if (lines[pos].compareTo(lines[pos - 1]) >= 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isIntersects(ListBox listBox) {
        return listBox.isInside(getTopLeft()) || listBox.isInside(getBottomRight())
                || listBox.isInside(getTopLeft().getX(), getBottomRight().getY())
                || listBox.isInside(getTopLeft().getY(), getBottomRight().getX());
    }

    public boolean isInside(ListBox listBox) {
        return (listBox.getBottomRight().getX() <= getBottomRight().getX()
                && listBox.getBottomRight().getX() >= getTopLeft().getX()
                && listBox.getBottomRight().getY() >= getTopLeft().getY()
                && listBox.getBottomRight().getY() <= getBottomRight().getY()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListBox)) return false;
        if (!super.equals(o)) return false;
        ListBox listBox = (ListBox) o;
        return Arrays.equals(getLines(), listBox.getLines());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Arrays.hashCode(getLines());
        return result;
    }
}
