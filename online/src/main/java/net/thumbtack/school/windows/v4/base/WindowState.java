package net.thumbtack.school.windows.v4.base;

public enum WindowState {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    DESTROYED("DESTROYED");

    private String stateString;

    WindowState(String stateString) {
        this.stateString = stateString;
    }

    public String getStateString() {
        return stateString;
    }

    public static WindowState fromString(String stateString) throws WindowException {
        if (stateString == null || (!stateString.equals("ACTIVE") && !stateString.equals("INACTIVE") && !stateString.equals("DESTROYED"))) {
            throw new WindowException(WindowErrorCode.WRONG_STATE);
        }
        return WindowState.valueOf(stateString);
    }
}
