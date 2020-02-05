package de.swerik.luna.game_state;

public enum State {
    PLAY(1),
    MAIN_MENU(2),
    LOADING(3);

    private byte stateNumber;

    State(int stateNumber) {
        this.stateNumber = (byte) stateNumber;
    }
}
