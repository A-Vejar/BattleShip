package cl.ucn.disc.dsm.avejar.battleship.battleship.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cl.ucn.disc.dsm.avejar.battleship.battleship.adapter.GameBoardAdapter;
import cl.ucn.disc.dsm.avejar.battleship.battleship.enums.Status;
import cl.ucn.disc.dsm.avejar.battleship.battleship.models.Player;

public class SetLocation {

    private static Random random = new Random();

    private static GameBoardAdapter gameBoardAdapter;
    private static Player player;
    private static Status status;

    private static int x;
    private static int y;
    private static int col;

    // Constructor
    private SetLocation() {
        x = 0;
        y = 0;
        col = 1;
    }

    private static int getPositionXY() {

        return x * col + y;
    }

    public static int getPositionXY(int x, int y) {

        return x * col + y;
    }

    /**
     * Initialized a random empty cell
     */
    private static void getEmptyCell() {

        do {
            x = random.nextInt(col);
            y = random.nextInt(col);
        }while (gameBoardAdapter.getItem(getPositionXY()).getStatus() == status.FULL);
    }

    /**
     * Tries to validated the north
     */
    private static boolean northValid(int size) {

        if (y - size < 0)
            return false;

        int source = getPositionXY(x, y);

        for (int i = 0; i < size; i++)
            if (gameBoardAdapter.getItem(source - i).getStatus() == status.FULL)
                return false;

        return true;
    }

    /**
     * Set the placement
     */
    private static void setNorth(int size) {

        for (int i = 0; i < size; i++)
            player.addCell(gameBoardAdapter.getItem(getPositionXY(x, y - i)));
    }

    /**
     * Tries to validated the south
     */
    private static boolean southValid(int size) {

        if (y + size > col)
            return false;

        for (int i = 0; i < size; i++)
            if (gameBoardAdapter.getItem(getPositionXY(x, y + i)).getStatus() == status.FULL)
                return false;

        return true;
    }

    /**
     * Set the placement
     */
    private static void setSouth(int size) {

        for (int i = 0; i < size; i++)
            player.addCell(gameBoardAdapter.getItem(getPositionXY(x, y + i)));
    }

    /**
     * Tries to validated the east
     */
    private static boolean eastValid(int size) {

        if (x + size > col)
            return false;

        for (int i = 0; i < size; i++)
            if (gameBoardAdapter.getItem(getPositionXY(x + i, y)).getStatus() == status.FULL)
                return false;

        return true;
    }

    /**
     * Set the placement
     */
    private static void setEast(int size) {

        for (int i = 0; i < size; i++)
            player.addCell(gameBoardAdapter.getItem(getPositionXY(x + i, y)));
    }

    /**
     * Tries to validated the west
     */
    private static boolean westValid(int size) {

        if (x - size < 0)
            return false;

        for (int i = 0; i < size; i++) {
            int nextX = x - i;
            if (nextX < 0 || gameBoardAdapter.getItem(getPositionXY(nextX, y)).getStatus() == status.FULL)
                return false;
        }
        return true;
    }

    /**
     * Set the placement
     */
    private static void setWest(int size) {

        for (int i = 0; i < size; i++)
            player.addCell(gameBoardAdapter.getItem(getPositionXY(x - i, y)));
    }

    //performs random sampling
    private static void setPlacement(int size) {

        boolean notPlaced = true;

        while (notPlaced) {

            List<Integer> aux = new ArrayList<>(4);

            // Cardinal points
            aux.add(0);
            aux.add(1);
            aux.add(2);
            aux.add(3);

            getEmptyCell();

            while (!aux.isEmpty()) {

                int i = random.nextInt(aux.size());

                switch (aux.get(i)) {

                    case 0:
                        if (northValid(size)) {

                            setNorth(size);
                            notPlaced = false;
                        }else {
                            aux.remove(i);
                        }
                        break;

                    case 1:
                        if (eastValid(size)) {
                            setEast(size);
                            notPlaced = false;
                        }else {
                            aux.remove(i);
                        }
                        break;

                    case 2:
                        if (southValid(size)) {
                            setSouth(size);
                            notPlaced = false;
                        }else {
                            aux.remove(i);
                        }
                        break;

                    case 3:
                        if (westValid(size)) {
                            setWest(size);
                            notPlaced = false;
                        }else {
                            aux.remove(i);
                        }
                        break;
                }
            }
        }
    }

    /**
     * Put the ships in available cells
     */
    public static void setShip(Player newPlayer, GameBoardAdapter gameBoardAdapter, int dim) {
        player = newPlayer;

        SetLocation.gameBoardAdapter = gameBoardAdapter;
        col = dim;

        setPlacement(2);
        setPlacement(3);
        setPlacement(5);

        SetLocation.gameBoardAdapter.notifyDataSetChanged();
    }
}
