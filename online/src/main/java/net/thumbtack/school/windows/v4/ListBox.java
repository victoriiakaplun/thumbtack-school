package net.thumbtack.school.windows.v4;

import net.thumbtack.school.windows.v4.base.RectWindow;
import net.thumbtack.school.windows.v4.base.WindowErrorCode;
import net.thumbtack.school.windows.v4.base.WindowException;
import net.thumbtack.school.windows.v4.base.WindowState;

import static net.thumbtack.school.base.StringOperations.reverse;
import static net.thumbtack.school.windows.v4.base.WindowState.fromString;

public class ListBox extends RectWindow {
    private String[] lines;

    public ListBox(Point topLeft, Point bottomRight, WindowState state, String[] lines) throws WindowException {
        super(topLeft, bottomRight, state);
        if (lines != null) {
            this.lines = new String[lines.length];
            System.arraycopy(lines, 0, this.lines, 0, lines.length);
        }
    }

    public ListBox(Point topLeft, Point bottomRight, String state, String[] lines) throws WindowException {
        this(topLeft, bottomRight, fromString(state), lines);
    }

    public ListBox(int xLeft, int yTop, int width, int height, WindowState state, String[] lines) throws WindowException {
        this(new Point(xLeft, yTop), new Point(xLeft + width - 1, yTop + height - 1), state, lines);
    }

    public ListBox(int xLeft, int yTop, int width, int height, String state, String[] lines) throws WindowException {
        this(new Point(xLeft, yTop), new Point(xLeft + width - 1, yTop + height - 1), fromString(state), lines);
    }

    public ListBox(Point topLeft, Point bottomRight, String[] lines) throws WindowException {
        this(topLeft, bottomRight, WindowState.ACTIVE, lines);
    }

    public ListBox(int xLeft, int yTop, int width, int height, String[] lines) throws WindowException {
        this(new Point(xLeft, yTop), new Point(xLeft + width - 1, yTop + height - 1), WindowState.ACTIVE, lines);
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

    public String[] getLinesSlice(int from, int to) throws WindowException {
        if (lines == null) {
            throw new WindowException(WindowErrorCode.EMPTY_ARRAY);
        }
        if (from < 0 || to > lines.length || from > to - 1) {
            throw new WindowException(WindowErrorCode.WRONG_INDEX);
        }

        String[] linesSlice = new String[to - from];
        int i = 0;
        for (int pos = from; pos < to; pos++) {
            linesSlice[i] = lines[pos];
            i++;
        }
        return linesSlice;
    }

    public String getLine(int index) throws WindowException {
        if (lines == null) {
            throw new WindowException(WindowErrorCode.EMPTY_ARRAY);
        }
        if (index >= lines.length) {
            throw new WindowException(WindowErrorCode.WRONG_INDEX);
        }
        if (lines[index] == null) {
            throw new WindowException(WindowErrorCode.WRONG_INDEX);
        }
        return lines[index];
    }

    public void setLine(int index, String line) throws WindowException {
        if (lines == null) {
            throw new WindowException(WindowErrorCode.EMPTY_ARRAY);
        }
        if (index >= lines.length || index < 0) {
            throw new WindowException(WindowErrorCode.WRONG_INDEX);
        }
        if (lines[index] == null) {
            throw new WindowException(WindowErrorCode.WRONG_INDEX);
        }
        this.lines[index] = line;
    }

    public Integer findLine(String line) {
        if (lines == null) {
            return null;
        }
        for (Integer pos = 0; pos < lines.length; pos++) {
            if (lines[pos].equals(line)) {
                return pos;
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
                this.lines[pos] = reverse(lines[pos]);
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
        return listBox.getBottomRight().getX() <= getBottomRight().getX()
                && listBox.getBottomRight().getX() >= getTopLeft().getX()
                && listBox.getBottomRight().getY() >= getTopLeft().getY()
                && listBox.getBottomRight().getY() <= getBottomRight().getY();
    }
}
