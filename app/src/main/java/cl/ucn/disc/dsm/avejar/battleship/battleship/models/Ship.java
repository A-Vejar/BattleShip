package cl.ucn.disc.dsm.avejar.battleship.battleship.models;

import java.util.ArrayList;
import java.util.List;

import cl.ucn.disc.dsm.avejar.battleship.battleship.enums.ShipType;
import cl.ucn.disc.dsm.avejar.battleship.battleship.enums.Status;
import lombok.Getter;
import lombok.Setter;

public class Ship {

    private List<GameBoard> gameBoards;
    private ShipType shipType;
    private Status status;

    @Setter
    private int numAttacksMade = 0;
    @Getter
    private int numCells;

    private int numCellsAdded = 0;
    private int playerNum;
    private int numAttacksAllowed;

    /**
     * Creates a type of ship
     */
    public Ship(int playerNum, ShipType shipType) {

        this.playerNum = playerNum;
        this.shipType = shipType;

        setShipTypeDependentFields();

        gameBoards = new ArrayList<>(numCells);
    }

    private void setShipTypeDependentFields() {

        if (shipType == ShipType.DESTROYER) {
            numCells = 2;
            numAttacksAllowed = 1;

        } else if (shipType == ShipType.CRUISER) {
            numCells = 3;
            numAttacksAllowed = 2;

        } else {
            numCells = 5;
            numAttacksAllowed = 3;
        }
    }

    /**
     * Remaining cells to arrange
     */
    public int getNumCellsToAdd() {
        return numCells - numCellsAdded;
    }

    /**
     * Ask if its is done arrange
     */
    public boolean canAddCells() {
        return getNumCellsToAdd() > 0;
    }

    /**
     * Add the cell to the game board
     */
    public void addCell(GameBoard gameBoard) {
        gameBoard.setStatus(status.FULL);
        gameBoards.add(gameBoard);
        numCellsAdded++;
    }

    /**
     * Remaining attacks from a ship
     */
    public int getNumAttacksLeft() {
        return numAttacksAllowed - numAttacksMade;
    }

    /**
     * Ask if its still can attack the ship
     */
    public boolean canAttack() {
        return isAlive() && getNumAttacksLeft() > 0;
    }

    /**
     * Change the cell on the game board by tapping
     */
    public void attackCell(GameBoard gameBoard) {

        if (gameBoard.getStatus() == status.EMPTY)
            gameBoard.setStatus(status.MISS);

        if (gameBoard.getStatus() == status.FULL)
            gameBoard.setStatus(status.HIT);

        numAttacksMade++;
    }

    /**
     * Cells that hasn't been hit
     */
    public boolean isAlive() {

        for (GameBoard gameBoard : gameBoards)
            if (gameBoard.getStatus() != status.HIT)
                return true;

        return false;
    }
}
