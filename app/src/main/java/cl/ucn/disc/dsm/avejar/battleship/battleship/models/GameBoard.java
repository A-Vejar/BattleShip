package cl.ucn.disc.dsm.avejar.battleship.battleship.models;

import cl.ucn.disc.dsm.avejar.battleship.battleship.enums.Status;
import lombok.Getter;
import lombok.Setter;

public class GameBoard {

    @Getter
    @Setter
    private int playerNum;

    @Getter
    @Setter
    private Status status;

    public GameBoard(int playerNum, Status status) {
        this.playerNum = playerNum;
        this.status = status;
    }
}
