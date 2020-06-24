package net.thumbtack.school.windows.v4;

import net.thumbtack.school.windows.v4.base.WindowException;
import net.thumbtack.school.windows.v4.base.WindowState;

public class WindowFactory {
    public static int counterRectButton;
    public static int counterRoundButton;

    public static RectButton createRectButton(Point leftTop, Point rightBottom, WindowState state, String text) throws WindowException {
        RectButton rectButton = new RectButton(leftTop, rightBottom, state,text);
        counterRectButton++;
        return rectButton;
    }

    public static RoundButton createRoundButton(Point center, int radius, WindowState state, String text) throws WindowException {
        counterRoundButton++;
        RoundButton roundButton = new RoundButton(center, radius, state, text);
        return roundButton;
    }

    public static int getRectButtonCount() {
        return counterRectButton;
    }

    public static int getRoundButtonCount() {
        return counterRoundButton;
    }

    public static int getWindowCount() {
        return counterRectButton + counterRoundButton;
    }

    public static void reset() {
        counterRoundButton = 0;
        counterRectButton = 0;
    }

}
