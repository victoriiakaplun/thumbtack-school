package net.thumbtack.school.windows.v2;

import java.util.Arrays;
import java.util.Objects;

import static net.thumbtack.school.base.StringOperations.reverse;

public class ListBox {
    private Point topLeft;
    private Point bottomRight;
    private boolean active;
    private String[] lines;

    public ListBox(Point topLeft, Point bottomRight, boolean active, String[] lines) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.active = active;
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

    public Point getBottomRight() {
        return bottomRight;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public boolean isActive() {
        return active;
    }

    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    public void setBottomRight(Point bottomRight) {
        this.bottomRight = bottomRight;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getWidth() {
        return bottomRight.getX() - topLeft.getX() + 1;
    }

    public int getHeight() {
        return bottomRight.getY() - topLeft.getY() + 1;
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
            linesSlice[i] = new String(lines[pos]);
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
            this.lines[index] = new String(line);
        }
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
                setLine(pos, reverse(lines[pos]));
            }
        }
    }

    public void duplicateLines() {
        if (lines != null) {
            String[] tmp = new String[lines.length * 2];
            int index = 0;
            for (int pos = 0; pos < tmp.length; pos += 2) {
                tmp[pos] = new String(lines[index]);
                tmp[pos + 1] = new String(lines[index]);
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
                tmp[index] = new String(lines[pos]);
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

    public void moveTo(int x, int y) {
        bottomRight.setX(x + bottomRight.getX() - topLeft.getX());
        bottomRight.setY(y + bottomRight.getY() - topLeft.getY());
        topLeft.setX(x);
        topLeft.setY(y);
    }

    public void moveTo(Point point) {
        this.moveTo(point.getX(), point.getY());
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
        return isInside(point.getX(), point.getY());
    }

    public boolean isIntersects(ListBox listBox) {
        return listBox.isInside(topLeft) || listBox.isInside(bottomRight)
                || listBox.isInside(topLeft.getX(), bottomRight.getY())
                || listBox.isInside(topLeft.getY(), bottomRight.getX());
    }

    public boolean isInside(ListBox listBox) {
        return (listBox.getBottomRight().getX() <= bottomRight.getX()
                && listBox.getBottomRight().getX() >= topLeft.getX()
                && listBox.getBottomRight().getY() >= topLeft.getY()
                && listBox.getBottomRight().getY() <= bottomRight.getY()
        );
    }

    public boolean isFullyVisibleOnDesktop(Desktop desktop) {
        return (topLeft.getX() >= 0
                && topLeft.getY() >= 0
                && bottomRight.getX() <= desktop.getWidth()
                && bottomRight.getY() <= desktop.getHeight()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListBox listBox = (ListBox) o;
        return active == listBox.active &&
                Objects.equals(topLeft, listBox.topLeft) &&
                Objects.equals(bottomRight, listBox.bottomRight) &&
                Arrays.equals(lines, listBox.lines);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(topLeft, bottomRight, active);
        result = 31 * result + Arrays.hashCode(lines);
        return result;
    }
}
