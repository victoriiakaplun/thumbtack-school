package net.thumbtack.school.windows.v4;

import net.thumbtack.school.windows.v4.base.Window;
import net.thumbtack.school.windows.v4.base.WindowErrorCode;
import net.thumbtack.school.windows.v4.base.WindowException;
import net.thumbtack.school.windows.v4.base.WindowState;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestRectButton {

    @Test
    public void testRectButton1() throws WindowException {
        Point topLeft = new Point(10, 20);
        Point bottomRight = new Point(30, 40);
        RectButton rectButton = new RectButton(topLeft, bottomRight, WindowState.INACTIVE, "OK");
        assertEquals(10, rectButton.getTopLeft().getX());
        assertEquals(20, rectButton.getTopLeft().getY());
        assertEquals(30, rectButton.getBottomRight().getX());
        assertEquals(40, rectButton.getBottomRight().getY());
        assertEquals(21, rectButton.getWidth());
        assertEquals(21, rectButton.getHeight());
        assertEquals(WindowState.INACTIVE, rectButton.getState());
        assertEquals("OK", rectButton.getText());
    }

    @Test
    public void testRectButton2() throws WindowException {
        Point topLeft = new Point(10, 20);
        Point bottomRight = new Point(30, 40);
        RectButton rectButton = new RectButton(topLeft, bottomRight, "INACTIVE", "OK");
        assertEquals(10, rectButton.getTopLeft().getX());
        assertEquals(20, rectButton.getTopLeft().getY());
        assertEquals(30, rectButton.getBottomRight().getX());
        assertEquals(40, rectButton.getBottomRight().getY());
        assertEquals(21, rectButton.getWidth());
        assertEquals(21, rectButton.getHeight());
        assertEquals(WindowState.INACTIVE, rectButton.getState());
        assertEquals("OK", rectButton.getText());
    }

    @Test
    public void testRectButton3() throws WindowException {
        RectButton rectButton = new RectButton(10, 20, 30, 40, WindowState.INACTIVE, "OK");
        assertEquals(10, rectButton.getTopLeft().getX());
        assertEquals(20, rectButton.getTopLeft().getY());
        assertEquals(39, rectButton.getBottomRight().getX());
        assertEquals(59, rectButton.getBottomRight().getY());
        assertEquals(30, rectButton.getWidth());
        assertEquals(40, rectButton.getHeight());
        assertEquals(WindowState.INACTIVE, rectButton.getState());
        assertEquals("OK", rectButton.getText());
    }

    @Test
    public void testRectButton4() throws WindowException {
        RectButton rectButton = new RectButton(10, 20, 30, 40, "INACTIVE", "OK");
        assertEquals(10, rectButton.getTopLeft().getX());
        assertEquals(20, rectButton.getTopLeft().getY());
        assertEquals(39, rectButton.getBottomRight().getX());
        assertEquals(59, rectButton.getBottomRight().getY());
        assertEquals(30, rectButton.getWidth());
        assertEquals(40, rectButton.getHeight());
        assertEquals(WindowState.INACTIVE, rectButton.getState());
        assertEquals("OK", rectButton.getText());
    }

    @Test
    public void testRectButton5() throws WindowException {
        Point topLeft = new Point(10, 20);
        Point bottomRight = new Point(30, 40);
        RectButton rectButton = new RectButton(topLeft, bottomRight, "OK");
        assertEquals(10, rectButton.getTopLeft().getX());
        assertEquals(20, rectButton.getTopLeft().getY());
        assertEquals(30, rectButton.getBottomRight().getX());
        assertEquals(40, rectButton.getBottomRight().getY());
        assertEquals(21, rectButton.getWidth());
        assertEquals(21, rectButton.getHeight());
        assertEquals(WindowState.ACTIVE, rectButton.getState());
        assertEquals("OK", rectButton.getText());
    }

    @Test
    public void testRectButton6() throws WindowException {
        RectButton rectButton = new RectButton(10, 20, 30, 40, "OK");
        assertEquals(10, rectButton.getTopLeft().getX());
        assertEquals(20, rectButton.getTopLeft().getY());
        assertEquals(39, rectButton.getBottomRight().getX());
        assertEquals(59, rectButton.getBottomRight().getY());
        assertEquals(30, rectButton.getWidth());
        assertEquals(40, rectButton.getHeight());
        assertEquals(WindowState.ACTIVE, rectButton.getState());
        assertEquals("OK", rectButton.getText());
    }

    @Test
    public void testState() throws WindowException {
        Window window = new RectButton(10, 20, 30, 40, "OK");
        assertEquals(WindowState.ACTIVE, window.getState());
        window.setState(WindowState.INACTIVE);
        assertEquals(WindowState.INACTIVE, window.getState());
        window.setState(WindowState.DESTROYED);
        assertEquals(WindowState.DESTROYED, window.getState());
    }

    @SuppressWarnings("unused")
    @Test
    public void testWrongState() {
        boolean failed = false;
        try {
            Window window = new RectButton(10, 20, 30, 40, (WindowState) null, "OK");
            failed = true;
        } catch (WindowException ex) {
            assertEquals(WindowErrorCode.WRONG_STATE, ex.getWindowErrorCode());
        }
        try {
            Window window = new RectButton(10, 20, 30, 40, (String) null, "OK");
            failed = true;
        } catch (WindowException ex) {
            assertEquals(WindowErrorCode.WRONG_STATE, ex.getWindowErrorCode());
        }
        try {
            Window window = new RectButton(10, 20, 30, 40, "ENABLED", "OK");
            failed = true;
        } catch (WindowException ex) {
            assertEquals(WindowErrorCode.WRONG_STATE, ex.getWindowErrorCode());
        }
        try {
            Window window = new RectButton(10, 20, 30, 40, "OK");
            window.setState(WindowState.DESTROYED);
            window.setState((WindowState.ACTIVE));
            failed = true;
        } catch (WindowException ex) {
            assertEquals(WindowErrorCode.WRONG_STATE, ex.getWindowErrorCode());
        }
        try {
            Window window = new RectButton(10, 20, 30, 40, WindowState.DESTROYED, "OK");
            failed = true;
        } catch (WindowException ex) {
            assertEquals(WindowErrorCode.WRONG_STATE, ex.getWindowErrorCode());
        }
        try {
            Window window = new RectButton(10, 20, 30, 40, "DESTROYED", "OK");
            failed = true;
        } catch (WindowException ex) {
            assertEquals(WindowErrorCode.WRONG_STATE, ex.getWindowErrorCode());
        }
        if (failed) {
            fail();
        }
    }

    @Test
    public void testSetRectButton() throws WindowException {
        RectButton rectButton = new RectButton(10, 20, 30, 40, "OK");
        rectButton.setTopLeft(new Point(0, 0));
        rectButton.setBottomRight(new Point(200, 100));
        rectButton.setState(WindowState.INACTIVE);
        assertEquals(0, rectButton.getTopLeft().getX());
        assertEquals(0, rectButton.getTopLeft().getY());
        assertEquals(200, rectButton.getBottomRight().getX());
        assertEquals(100, rectButton.getBottomRight().getY());
        assertEquals(201, rectButton.getWidth());
        assertEquals(101, rectButton.getHeight());
        assertEquals(WindowState.INACTIVE, rectButton.getState());
        assertEquals("OK", rectButton.getText());
    }

    @Test
    public void testMoveToRectButton1() throws WindowException {
        RectButton rectButton = new RectButton(10, 20, 30, 40, "OK");
        rectButton.moveTo(100, 50);
        assertEquals(100, rectButton.getTopLeft().getX());
        assertEquals(50, rectButton.getTopLeft().getY());
        assertEquals(129, rectButton.getBottomRight().getX());
        assertEquals(89, rectButton.getBottomRight().getY());
        assertEquals(30, rectButton.getWidth());
        assertEquals(40, rectButton.getHeight());
    }

    @Test
    public void testMoveToRectButton2() throws WindowException {
        RectButton rectButton = new RectButton(10, 20, 30, 40, "OK");
        rectButton.moveTo(new Point(100, 50));
        assertEquals(100, rectButton.getTopLeft().getX());
        assertEquals(50, rectButton.getTopLeft().getY());
        assertEquals(129, rectButton.getBottomRight().getX());
        assertEquals(89, rectButton.getBottomRight().getY());
        assertEquals(30, rectButton.getWidth());
        assertEquals(40, rectButton.getHeight());
    }

    @Test
    public void testMoveRelRectButton() throws WindowException {
        RectButton rectButton = new RectButton(10, 20, 30, 40, "OK");
        rectButton.moveRel(100, 50);
        assertEquals(110, rectButton.getTopLeft().getX());
        assertEquals(70, rectButton.getTopLeft().getY());
        assertEquals(139, rectButton.getBottomRight().getX());
        assertEquals(109, rectButton.getBottomRight().getY());
        assertEquals(30, rectButton.getWidth());
        assertEquals(40, rectButton.getHeight());
    }

    @Test
    public void testResizeRectButton() throws WindowException {
        RectButton rectButton1 = new RectButton(10, 20, 30, 40, "OK");
        rectButton1.resize(2);
        assertEquals(10, rectButton1.getTopLeft().getX());
        assertEquals(20, rectButton1.getTopLeft().getY());
        assertEquals(69, rectButton1.getBottomRight().getX());
        assertEquals(99, rectButton1.getBottomRight().getY());
        assertEquals(60, rectButton1.getWidth());
        assertEquals(80, rectButton1.getHeight());
        RectButton rectButton2 = new RectButton(10, 20, 30, 40, "OK");
        rectButton2.resize(0.5);
        assertEquals(10, rectButton2.getTopLeft().getX());
        assertEquals(20, rectButton2.getTopLeft().getY());
        assertEquals(24, rectButton2.getBottomRight().getX());
        assertEquals(39, rectButton2.getBottomRight().getY());
        assertEquals(15, rectButton2.getWidth());
        assertEquals(20, rectButton2.getHeight());
        RectButton rectButton3 = new RectButton(10, 20, 4, 4, "OK");
        rectButton3.resize(0.5);
        assertEquals(10, rectButton3.getTopLeft().getX());
        assertEquals(20, rectButton3.getTopLeft().getY());
        assertEquals(11, rectButton3.getBottomRight().getX());
        assertEquals(21, rectButton3.getBottomRight().getY());
        assertEquals(2, rectButton3.getWidth());
        assertEquals(2, rectButton3.getHeight());
        RectButton rectButton4 = new RectButton(10, 20, 30, 40, "OK");
        rectButton4.resize(0.01);
        assertEquals(10, rectButton4.getTopLeft().getX());
        assertEquals(20, rectButton4.getTopLeft().getY());
        assertEquals(10, rectButton4.getBottomRight().getX());
        assertEquals(20, rectButton4.getBottomRight().getY());
        assertEquals(1, rectButton4.getWidth());
        assertEquals(1, rectButton4.getHeight());
    }

    @Test
    public void testIsPointInsideRectButton1() throws WindowException {
        RectButton rectButton = new RectButton(10, 20, 30, 40, "OK");
        assertTrue(rectButton.isInside(20, 30));
        assertTrue(rectButton.isInside(10, 30));
        assertTrue(rectButton.isInside(30, 30));
        assertTrue(rectButton.isInside(10, 59));
        assertFalse(rectButton.isInside(10, 60));
        assertFalse(rectButton.isInside(0, 0));
        assertFalse(rectButton.isInside(10, 100));
        assertFalse(rectButton.isInside(-10, 20));
        assertFalse(rectButton.isInside(10, -20));
    }

    @Test
    public void testIsPointInsideRectButton2() throws WindowException {
        RectButton rectButton = new RectButton(10, 20, 30, 40, "OK");
        assertTrue(rectButton.isInside(new Point(20, 30)));
        assertTrue(rectButton.isInside(new Point(10, 30)));
        assertTrue(rectButton.isInside(new Point(30, 30)));
        assertTrue(rectButton.isInside(new Point(10, 59)));
        assertFalse(rectButton.isInside(new Point(10, 60)));
        assertFalse(rectButton.isInside(new Point(0, 0)));
        assertFalse(rectButton.isInside(new Point(10, 100)));
        assertFalse(rectButton.isInside(new Point(-10, 20)));
        assertFalse(rectButton.isInside(new Point(10, -20)));
    }

    @Test
    public void testIsIntersectsRectButton() throws WindowException {
        RectButton rectButton = new RectButton(10, 20, 30, 40, "OK");
        assertTrue(rectButton.isIntersects(new RectButton(15, 25, 25, 35, "OK")));
        assertTrue(rectButton.isIntersects(new RectButton(-10, 20, 30, 40, "OK")));
        assertTrue(rectButton.isIntersects(new RectButton(10, 20, 50, 40, "OK")));
        assertTrue(rectButton.isIntersects(new RectButton(-10, 20, 50, 40, "OK")));
        assertTrue(rectButton.isIntersects(new RectButton(10, -20, 30, 41, "OK")));
        assertFalse(rectButton.isIntersects(new RectButton(10, -20, 30, 40, "OK")));
        assertTrue(rectButton.isIntersects(new RectButton(10, 20, 30, 60, "OK")));
        assertTrue(rectButton.isIntersects(new RectButton(-10, -20, 50, 60, "OK")));
        assertTrue(rectButton.isIntersects(new RectButton(0, 10, 20, 30, "OK")));
        assertTrue(rectButton.isIntersects(new RectButton(20, 30, 40, 50, "OK")));
        assertFalse(rectButton.isIntersects(new RectButton(-40, 20, -30, 40, "OK")));
        assertFalse(rectButton.isIntersects(new RectButton(110, 120, 30, 40, "OK")));
        assertFalse(rectButton.isIntersects(new RectButton(10, 120, 30, 40, "OK")));
        assertFalse(rectButton.isIntersects(new RectButton(10, -40, 30, 20, "OK")));
        assertTrue(rectButton.isIntersects(new RectButton(15, 15, 25, 50, "OK")));
    }

    @Test
    public void testIsRectButtonInsideRectButton() throws WindowException {
        RectButton rectButton = new RectButton(10, 20, 30, 40, "OK");
        assertTrue(rectButton.isInside(new RectButton(15, 25, 25, 35, "OK")));
        assertFalse(rectButton.isInside(new RectButton(-40, 20, 30, 40, "OK")));
        assertFalse(rectButton.isInside(new RectButton(110, 120, 130, 140, "OK")));
        assertFalse(rectButton.isInside(new RectButton(10, 120, 30, 40, "OK")));
        assertFalse(rectButton.isInside(new RectButton(10, -40, 30, 20, "OK")));
    }

    @Test
    public void testIsFullyVisibleOnDesktop() throws WindowException {
        Desktop desktop = new Desktop();
        assertTrue(new RectButton(15, 25, 25, 35, "OK").isFullyVisibleOnDesktop(desktop));
        assertTrue(new RectButton(0, 0, 639, 479, "OK").isFullyVisibleOnDesktop(desktop));
        assertFalse(new RectButton(200, 200, 640, 480, "OK").isFullyVisibleOnDesktop(desktop));
        assertFalse(new RectButton(-200, -200, 640, 480, "OK").isFullyVisibleOnDesktop(desktop));
        assertFalse(new RectButton(-1200, 700, 1199, 480, "OK").isFullyVisibleOnDesktop(desktop));
        assertFalse(new RectButton(200, -200, 100, 100, "OK").isFullyVisibleOnDesktop(desktop));
        assertTrue(new RectButton(200, 300, 100, 100, "OK").isFullyVisibleOnDesktop(desktop));
        assertFalse(new RectButton(200, 700, 100, 100, "OK").isFullyVisibleOnDesktop(desktop));
    }

    @Test
    public void testRectButtonsAreEqual() throws WindowException {
        RectButton rectButton1 = new RectButton(10, 20, 30, 40, "OK");
        RectButton rectButton2 = new RectButton(10, 20, 30, 40, "OK");
        RectButton rectButton3 = new RectButton(20, 30, 40, 50, "OK");
        RectButton rectButton4 = new RectButton(20, 30, 40, 50, "Cancel");
        assertNotEquals(rectButton1, null);
        assertEquals(rectButton1, rectButton1);
        assertNotEquals(rectButton1, null);
        assertEquals(rectButton1, rectButton2);
        assertNotEquals(rectButton1, rectButton3);
        assertNotEquals(rectButton1, rectButton4);
    }

    @Test
    public void testRectButtonSetText() throws WindowException {
        RectButton rectButton = new RectButton(10, 20, 30, 40, "OK");
        rectButton.setText("Cancel");
        assertEquals("Cancel", rectButton.getText());
    }
}
