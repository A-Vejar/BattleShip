package cl.ucn.disc.dsm.avejar.battleship.battleship;

import cl.ucn.disc.dsm.avejar.battleship.battleship.adapter.GameBoardAdapter;
import cl.ucn.disc.dsm.avejar.battleship.battleship.enums.Status;
import cl.ucn.disc.dsm.avejar.battleship.battleship.models.GameBoard;

public class ShipLocation {

    private Status status;

    GameBoard checkL1;
    GameBoard checkL2;
    GameBoard checkL3;
    GameBoard checkL4;
    GameBoard checkL5;

    GameBoard checkM1;
    GameBoard checkM2;
    GameBoard checkM3;


    /**
     * Checks for a valid large horizontal arrangement
     */
    public boolean checkArrangeLH(GameBoardAdapter board) {

        int boardSize = (int) Math.sqrt(board.getCount());

        for (int i = 0; i < board.getCount() - 4; i++) {
            GameBoard gameBoard = board.getItem(i);

            if (gameBoard.getStatus() == status.FULL) {

                if (((i + 1) % boardSize == boardSize - 1) ||
                        ((i + 2) % boardSize == boardSize - 1) ||
                        ((i + 3) % boardSize == boardSize - 1)) {

                } else {
                    GameBoard gameBoard2 = board.getItem(i + 1);

                    if (gameBoard2.getStatus() == status.FULL) {
                        GameBoard gameBoard3 = board.getItem(i + 2);

                        if (gameBoard3.getStatus() == status.FULL) {
                            GameBoard gameBoard4 = board.getItem(i + 3);

                            if (gameBoard4.getStatus() == status.FULL) {
                                GameBoard gameBoard5 = board.getItem(i + 4);

                                if (gameBoard5.getStatus() == status.FULL) {

                                    checkL1 = board.getItem(i);
                                    checkL2 = board.getItem(i + 1);
                                    checkL3 = board.getItem(i + 2);
                                    checkL4 = board.getItem(i + 3);
                                    checkL5 = board.getItem(i + 4);
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks for a valid large vertical arrangement
     */
    public boolean checkArrangeLV(GameBoardAdapter board) {

        int boardSize = (int) Math.sqrt(board.getCount());

        for (int i = 0; i < (board.getCount() - (boardSize * 4)); i++) {
            GameBoard gameBoard = board.getItem(i);

            if (gameBoard.getStatus() == status.FULL) {
                GameBoard gameBoard2 = board.getItem(i + boardSize);

                if (gameBoard2.getStatus() == status.FULL) {
                    GameBoard gameBoard3 = board.getItem(i + boardSize * 2);

                    if (gameBoard3.getStatus() == status.FULL) {
                        GameBoard gameBoard4 = board.getItem(i + boardSize * 3);

                        if (gameBoard4.getStatus() == status.FULL) {
                            GameBoard gameBoard5 = board.getItem(i + boardSize * 4);

                            if (gameBoard5.getStatus() == status.FULL) {

                                checkL1 = board.getItem(i);
                                checkL2 = board.getItem(i + boardSize);
                                checkL3 = board.getItem(i + boardSize * 2);
                                checkL4 = board.getItem(i + boardSize * 3);
                                checkL5 = board.getItem(i + boardSize * 4);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks for a valid medium horizontal arrangement
     */
    public boolean checkArrangeMH(GameBoardAdapter board) {

        int boardSize = (int) Math.sqrt(board.getCount());

        for (int i = 0; i < board.getCount() - 2; i++) {
            GameBoard gameBoard = board.getItem(i);

            if (gameBoard.getStatus() == status.FULL) {

                if ((board.getItem(i) != checkL1) &&
                        (board.getItem(i) != checkL2) &&
                        (board.getItem(i) != checkL3) &&
                        (board.getItem(i) != checkL4) &&
                        (board.getItem(i) != checkL5)) {

                    if (((i + 1) % boardSize == boardSize - 1) ||
                            ((i + 2) % boardSize == boardSize - 1)) {

                    } else {
                        GameBoard gameBoard2 = board.getItem(i + 1);

                        if (gameBoard2.getStatus() == status.FULL) {
                            GameBoard gameBoard3 = board.getItem(i + 2);

                            if (gameBoard3.getStatus() == status.FULL) {
                                if ((gameBoard3 != checkL1) &&
                                        (gameBoard3 != checkL2) &&
                                        (gameBoard3 != checkL3) &&
                                        (gameBoard3 != checkL4) &&
                                        (gameBoard3 != checkL5)) {

                                    checkM1 = board.getItem(i);
                                    checkM2 = board.getItem(i + 1);
                                    checkM3 = board.getItem(i + 2);
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks for a valid medium vertical arrangement
     */
    public boolean checkArrangeMV(GameBoardAdapter board) {

        int boardSize = (int) Math.sqrt(board.getCount());

        for (int i = 0; i < (board.getCount() - (boardSize * 2)); i++) {
            GameBoard gameBoard = board.getItem(i);

            if (gameBoard.getStatus() == status.FULL) {

                if ((board.getItem(i) != checkL1) &&
                        (board.getItem(i) != checkL2) &&
                        (board.getItem(i) != checkL3) &&
                        (board.getItem(i) != checkL4) &&
                        (board.getItem(i) != checkL5)) {

                    GameBoard gameBoard2 = board.getItem(i + boardSize);

                    if (gameBoard2.getStatus() == status.FULL) {
                        GameBoard gameBoard3 = board.getItem(i + boardSize * 2);

                        if (gameBoard3.getStatus() == status.FULL) {

                            if ((gameBoard3 != checkL1) &&
                                    (gameBoard3 != checkL2) &&
                                    (gameBoard3 != checkL3) &&
                                    (gameBoard3 != checkL4) &&
                                    (gameBoard3 != checkL5)) {

                                checkM1 = board.getItem(i);
                                checkM2 = board.getItem(i + boardSize);
                                checkM3 = board.getItem(i + boardSize * 2);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks for a valid small horizontal arrangement
     */
    public boolean checkArrangeSH(GameBoardAdapter board) {

        int boardSize = (int) Math.sqrt(board.getCount());

        for (int i = 0; i < board.getCount(); i++) {
            GameBoard gameBoard = board.getItem(i);

            if (gameBoard.getStatus() == status.FULL) {
                if ((board.getItem(i) != checkM1) &&
                        (board.getItem(i) != checkM2) &&
                        (board.getItem(i) != checkM3)
                        && ((board.getItem(i) != checkL1) &&
                        (board.getItem(i) != checkL2) &&
                        (board.getItem(i) != checkL3) &&
                        (board.getItem(i) != checkL4) &&
                        (board.getItem(i) != checkL5))) {

                    if ((i + 1) % boardSize == boardSize - 1) {

                    } else {
                        GameBoard gameBoard2 = board.getItem(i + 1);

                        if (gameBoard2.getStatus() == status.FULL) {
                            if ((gameBoard2 != checkM1) &&
                                    (gameBoard2 != checkM2) &&
                                    (gameBoard2 != checkM3) &&
                                    (gameBoard2 != checkL1) &&
                                    (gameBoard2 != checkL2) &&
                                    (gameBoard2 != checkL3) &&
                                    (gameBoard2 != checkL4) &&
                                    (gameBoard2 != checkL5)) {

                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks for a valid small vertical arrangement
     */
    public boolean checkArrangeSV(GameBoardAdapter board) {

        int boardSize = (int) Math.sqrt(board.getCount());

        for (int i = 0; i < (board.getCount() - boardSize); i++) {
            GameBoard gameBoard = board.getItem(i);

            if (gameBoard.getStatus() == status.FULL) {

                if ((board.getItem(i) != checkM1) &&
                        (board.getItem(i) != checkM2) &&
                        (board.getItem(i) != checkM3)
                        && ((board.getItem(i) != checkL1) &&
                        (board.getItem(i) != checkL2) &&
                        (board.getItem(i) != checkL3) &&
                        (board.getItem(i) != checkL4) &&
                        (board.getItem(i) != checkL5))) {

                    GameBoard gameBoard2 = board.getItem(i + boardSize);

                    if (gameBoard2.getStatus() == status.FULL) {

                        if ((gameBoard2 != checkM1) &&
                                (gameBoard2 != checkM2) &&
                                (gameBoard2 != checkM3) &&
                                (gameBoard2 != checkL1) &&
                                (gameBoard2 != checkL2) &&
                                (gameBoard2 != checkL3) &&
                                (gameBoard2 != checkL4) &&
                                (gameBoard2 != checkL5)) {

                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
