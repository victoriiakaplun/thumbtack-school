package net.thumbtack.school.windows.managers;

import net.thumbtack.school.windows.v4.Desktop;
import net.thumbtack.school.windows.v4.base.Window;
import net.thumbtack.school.windows.v4.base.WindowErrorCode;
import net.thumbtack.school.windows.v4.base.WindowException;
import net.thumbtack.school.windows.v4.cursors.Cursor;

public class ArrayManager<T extends Window> {
    private T[] windows;

    public ArrayManager(T[] windows) throws WindowException {
        for (T elem : windows) {
            if (elem == null) {
                throw new WindowException(WindowErrorCode.NULL_WINDOW);
            }
        }
        this.windows = windows;

    }

    public T[] getWindows() {
        return windows;
    }

    public void setWindows(T[] windows) throws WindowException {
        for (T elem : windows) {
            if (elem == null) {
                throw new WindowException(WindowErrorCode.NULL_WINDOW);
            }
        }
        this.windows = windows;
    }

    public T getWindow(int i) {
        return windows[i];
    }

    public void setWindow(T window, int i) throws WindowException {
        if (window == null) {
            throw new WindowException(WindowErrorCode.NULL_WINDOW);
        }
        if (i >= windows.length || i < 0) {
            throw new WindowException(WindowErrorCode.WRONG_INDEX);
        }
        windows[i] = window;
    }

    public boolean isSameSize(ArrayManager otherWindows) {
        return windows.length == otherWindows.getWindows().length;
    }

    public boolean allWindowsFullyVisibleOnDesktop(Desktop desktop) {
        for (int i = 0; i < windows.length; i++) {
            if (!this.getWindow(i).isFullyVisibleOnDesktop(desktop)) {
                return false;
            }
        }
        return true;
    }

    public boolean anyWindowFullyVisibleOnDesktop(Desktop desktop) {
        for (int i = 0; i < windows.length; i++) {
            if (this.getWindow(i).isFullyVisibleOnDesktop(desktop)) {
                return true;
            }
        }
        return false;
    }

    public Window getFirstWindowFromCursor(Cursor cursor) {
        for (T elem : windows) {
            if (elem.isInside(cursor.getX(), cursor.getY())) {
                return elem;
            }
        }
        return null;
    }
}

