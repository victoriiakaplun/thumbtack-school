package net.thumbtack.school.windows.v3;

import net.thumbtack.school.windows.v3.base.RoundWindow;

import java.util.Objects;

public class RoundButton extends RoundWindow {

    public RoundButton(Point center, int radius, boolean active, String text) {
        super(center, active, radius, text);
    }

    public RoundButton(int xCenter, int yCenter, int radius, boolean active, String text) {
        this(new Point(xCenter, yCenter), radius, active, text);
    }

    public RoundButton(Point center, int radius, String text) {
        this(center, radius, true, text);
    }

    public RoundButton(int xCenter, int yCenter, int radius, String text) {
        this(xCenter, yCenter, radius, true, text);
    }


}
