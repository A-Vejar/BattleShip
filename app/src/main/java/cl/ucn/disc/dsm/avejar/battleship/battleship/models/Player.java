package cl.ucn.disc.dsm.avejar.battleship.battleship.models;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

import cl.ucn.disc.dsm.avejar.battleship.battleship.enums.ShipType;

public class Player {

    private ShipType shipType;

    @Getter
    private int numShipsArranged = 0;

    @Getter
    private int numShips = 3;

    @Getter
    private List<Ship> ships = new ArrayList<>(numShips);

    private int playerNum;

    /**
     * Ships types and their size of cells
     *
     * CARRIER - 5
     * CRUISER - 3
     * DESTROYER - 2
     */
    public Player(int playerNum) {
        this.playerNum = playerNum;

        Ship ship = new Ship(playerNum, shipType.DESTROYER);
        ships.add(ship);
        ship = new Ship(playerNum, shipType.CRUISER);
        ships.add(ship);
        ship = new Ship(playerNum, shipType.CARRIER);
        ships.add(ship);
    }

    private int getNumShipsToArrange() {
        return numShips - numShipsArranged;
    }

    public boolean canAddCell() {
        return getNumShipsToArrange() > 0;
    }

    /**
     * Add the cell to the game board
     */
    public void addCell(GameBoard gameBoard) {
        Ship ship = ships.get(numShipsArranged);

        ship.addCell(gameBoard);
        if (!ship.canAddCells())
            numShipsArranged++;
    }

    /**
     * Ships attacks
     */
    public boolean canAttack() {
        for (Ship ship : ships)
            if (ship.canAttack())
                return true;
        return false;
    }

    /**
     * Enemy's game board to attack (Tap the cell)
     */
    public void attackCell(GameBoard gameBoard) {
        Ship ship = getNextShipCanAttack();
        ship.attackCell(gameBoard);
    }

    /**
     * Every ship attack by his type
     */
    public Ship getNextShipCanAttack() {
        int index = 0;
        Ship ship;
        do {
            ship = ships.get(index);
            index++;
        } while (!ship.canAttack());
        return ship;
    }

    /**
     * Reset after attack
     */
    public void resetNumsAttacksMade() {
        for (Ship ship : ships)
            ship.setNumAttacksMade(0);
    }

    /**
     * Ships still standing
     */
    public int getNumShipsAlive() {
        int numShipsAlive = 0;
        for (Ship ship : ships)
            if (ship.isAlive())
                numShipsAlive++;
        return numShipsAlive;
    }

    /**
     * Ships still alive
     */
    public boolean isAlive() {
        return getNumShipsAlive() > 0;
    }
}
