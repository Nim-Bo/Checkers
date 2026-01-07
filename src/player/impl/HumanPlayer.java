package player.impl;

import model.Board;
import model.Movement;
import player.contracts.Player;
import ui.Console;

import java.util.List;

public class HumanPlayer implements Player {
    private int color;
    private Console console;

    public HumanPlayer(int color, Console console) {
        this.color = color;
        this.console = console;
    }

}
