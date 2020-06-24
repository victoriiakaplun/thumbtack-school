package net.thumbtack.school.windows.v1;

public class WindowFactory {
    public static int counterRectButton;
    public static int counterRoundButton;

    public static RectButton createRectButton(Point leftTop, Point rightBottom, boolean active) {
        RectButton rectButton = new RectButton(leftTop, rightBottom, active);
        counterRectButton++;
        return rectButton;

    }

    public static RoundButton createRoundButton(Point center, int radius, boolean active) {
        counterRoundButton++;
        RoundButton roundButton = new RoundButton(center, radius, active);
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
