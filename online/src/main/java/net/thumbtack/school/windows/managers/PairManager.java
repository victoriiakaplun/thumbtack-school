package net.thumbtack.school.windows.managers;

import net.thumbtack.school.windows.v4.Desktop;
import net.thumbtack.school.windows.v4.base.Window;
import net.thumbtack.school.windows.v4.base.WindowErrorCode;
import net.thumbtack.school.windows.v4.base.WindowException;

public class PairManager<T extends Window, U extends Window> {
    private T firstWindow;
    private U secondWindow;

    public PairManager(T firstWindow, U secondWindow) throws WindowException {
        if (firstWindow == null || secondWindow == null) {
            throw new WindowException(WindowErrorCode.NULL_WINDOW);
        }
        this.firstWindow = firstWindow;
        this.secondWindow = secondWindow;
    }

    public T getFirstWindow() {
        return firstWindow;
    }

    public void setFirstWindow(T firstWindow) throws WindowException {
        if (firstWindow == null) {
            throw new WindowException(WindowErrorCode.NULL_WINDOW);
        }
        this.firstWindow = firstWindow;
    }

    public U getSecondWindow() {
        return secondWindow;
    }

    public void setSecondWindow(U secondWindow) throws WindowException {
        if (secondWindow == null) {
            throw new WindowException(WindowErrorCode.NULL_WINDOW);
        }
        this.secondWindow = secondWindow;
    }

    public boolean allWindowsFullyVisibleOnDesktop(PairManager OtherPairManager, Desktop desktop) {
        return this.getFirstWindow().isFullyVisibleOnDesktop(desktop)
                && this.getSecondWindow().isFullyVisibleOnDesktop(desktop)
                && OtherPairManager.getFirstWindow().isFullyVisibleOnDesktop(desktop)
                && OtherPairManager.getSecondWindow().isFullyVisibleOnDesktop(desktop);
    }

    public static boolean allWindowsFullyVisibleOnDesktop(PairManager firstPairManager, PairManager secondPairManager, Desktop desktop) {
        return firstPairManager.getFirstWindow().isFullyVisibleOnDesktop(desktop)
                && firstPairManager.getSecondWindow().isFullyVisibleOnDesktop(desktop)
                && secondPairManager.getFirstWindow().isFullyVisibleOnDesktop(desktop)
                && secondPairManager.getSecondWindow().isFullyVisibleOnDesktop(desktop);
    }
}
