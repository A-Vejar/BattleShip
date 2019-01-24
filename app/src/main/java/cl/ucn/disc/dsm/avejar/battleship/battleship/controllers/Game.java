package cl.ucn.disc.dsm.avejar.battleship.battleship.controllers;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.Random;

import cl.ucn.disc.dsm.avejar.battleship.battleship.ShipLocation;
import cl.ucn.disc.dsm.avejar.battleship.battleship.adapter.GameBoardAdapter;
import cl.ucn.disc.dsm.avejar.battleship.battleship.enums.Stage;
import cl.ucn.disc.dsm.avejar.battleship.battleship.enums.Status;
import cl.ucn.disc.dsm.avejar.battleship.battleship.models.GameBoard;
import cl.ucn.disc.dsm.avejar.battleship.battleship.models.Player;
import cl.ucn.disc.dsm.avejar.battleship.battleship.models.Ship;

public class Game {

    private static Game gameInstance;

    private Context context;
    private int numCells;

    private Stage stage;
    private Status status;

    private TextView tvStage;
    private TextView tvMessage;

    private Button attackBtn;
    private Button resetBtn;

    private GridView gridPlayer;
    private GridView gridCpu;

    private GameBoardAdapter adapterPlayer;
    private GameBoardAdapter adapterCpu;

    private Player player;
    private Player cpu;

    private Game() {
    }

    /**
     * Singleton
     */
    public static Game getGameInstance() {
        if (gameInstance == null)
            gameInstance = new Game();
        return gameInstance;
    }

    /**
     * Set this method to MainActivity
     */
    public void setFields(Context context, int numCells,
                          TextView tvStage, TextView tvMessage,
                          Button attackBtn, Button resetBtn,
                          GridView gridPlayer, GridView gridCpu,
                          GameBoardAdapter adapterPlayer, GameBoardAdapter adapterCpu) {

        this.context = context;
        this.numCells = numCells;
        this.tvStage = tvStage;
        this.tvMessage = tvMessage;
        this.attackBtn = attackBtn;
        this.resetBtn = resetBtn;
        this.gridPlayer = gridPlayer;
        this.gridCpu = gridCpu;
        this.adapterPlayer = adapterPlayer;
        this.adapterCpu = adapterCpu;
    }

    /**
     * Re-start the game by clearing the boards and letting the own cpu arrange
     */
    public void initialize() {
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialize();
            }
        });
        disableClicks();

        adapterPlayer.clear();
        adapterCpu.clear();
        adapterPlayer.addCells(gridPlayer, 1, getNumCellsBoardArea());
        adapterCpu.addCells(gridCpu, 2, getNumCellsBoardArea());

        player = new Player(1);
        cpu = new Player(2);

        cpuBegin();
    }

    private void cpuBegin() {

        SetLocation.setShip(cpu, adapterCpu, numCells); // Zach's code
        enableGameBegin();
    }

    private void enableGameBegin() {

        gameStageMessage(Stage.BEGIN);
        playerBegin();
    }

    private void playerBegin() {

        gridPlayer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                GameBoard gameBoard = (GameBoard) parent.getAdapter().getItem(position);
                player.addCell(gameBoard);
                adapterPlayer.notifyDataSetChanged();

                if (player.canAddCell())
                    setMessage(player.getShips().get(player.getNumShipsArranged()).getNumCellsToAdd() +
                            " Remain cells to add from your ship [" +
                            (player.getNumShipsArranged() + 1) + "]");
                else {

                    gridPlayer.setOnItemClickListener(null);

                    if (checkBegin())
                        enableGameBattle();
                    else
                        setMessage("Error in the arrangement, click Restart to start over");
                }
            }
        });
    }

    private boolean checkBegin() {

        ShipLocation shipArr = new ShipLocation();
        GameBoardAdapter gameBoardAdapter = adapterPlayer;

        int c = 0;
        for (int i = 0; i < gameBoardAdapter.getCount(); i++) {

            GameBoard gameBoard = adapterPlayer.getItem(i);
            if (gameBoard.getStatus() == status.FULL) {

                c = c + 1;
                if (c == 10) {

                    if (((shipArr.checkArrangeLH(gameBoardAdapter)) ||
                            (shipArr.checkArrangeLV(gameBoardAdapter)))) {

                        if (((shipArr.checkArrangeMH(gameBoardAdapter)) ||
                                (shipArr.checkArrangeMV(gameBoardAdapter)))) {

                            if (((shipArr.checkArrangeSH(gameBoardAdapter)) ||
                                    (shipArr.checkArrangeSV(gameBoardAdapter)))) {

                                return true;
                            }
                        }
                    }

                }
            }
        }
        return false;
    }

    private void enableGameBattle() {

        gameStageMessage(Stage.BATTLE_PHASE);
        enableGameAttack();
    }


    private void enableGameAttack() {
        attackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gameStageMessage(Stage.ATTACK_PHASE);
                attackBtn.setOnClickListener(null);
                playerAttack();
            }
        });
    }

    private void playerAttack() {

        gridCpu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                GameBoard gameBoard = (GameBoard) parent.getAdapter().getItem(position);
                player.attackCell(gameBoard);
                adapterCpu.notifyDataSetChanged();

                if (!cpu.isAlive()) {

                    disableClicks();
                    setMessage("¡¡ YOU WON !! [Press Restart button to start a new game]");

                } else if (player.canAttack()) {

                    Ship ship = player.getNextShipCanAttack();
                    setMessage(ship.getNumAttacksLeft() + " Remain attacks from ship [" +
                            (player.getShips().indexOf(ship) + 1) + "]");

                } else {

                    gridCpu.setOnItemClickListener(null);
                    player.resetNumsAttacksMade();
                    cpuAttack();
                }
            }
        });
    }

    private void cpuAttack() {

        Random random = new Random();

        while (cpu.canAttack()) {

            GameBoard gameBoard;

            do {
                gameBoard = adapterPlayer.getItem(random.nextInt(getNumCellsBoardArea()));

            }while (gameBoard.getStatus() == status.HIT ||
                    gameBoard.getStatus() == status.MISS);

            cpu.attackCell(gameBoard);
            adapterPlayer.notifyDataSetChanged();

            if (!player.isAlive()) {
                disableClicks();
                setMessage("¡¡ YOU LOST !! [Press Restart button to start a new game]");
                break;
            }
        }

        if (player.isAlive()) {

            cpu.resetNumsAttacksMade();
            enableGameBattle();
        }
    }

    private void gameStageMessage(Stage stage) {

        this.stage = stage;
        String msg = "Stage Phase: " + stage;
        tvStage.setText(msg);
        describeGameStage();
    }

    private void describeGameStage() {

        String msg;

        if (stage == Stage.BEGIN)
            msg = "Set your " +
                    player.getNumShips() + " ships on the game board.";

        else if (stage == Stage.BATTLE_PHASE)
            msg = "Click to attack";

        else
            msg = "Tap the enemy's cell on the game board to attack | " +
                    cpu.getNumShipsAlive() + " Ships standing";

        setMessage(msg);
    }

    private void setMessage(String msg) {

        tvMessage.setText("Instructions\n" + msg);
    }

    private void disableClicks() {

        attackBtn.setOnClickListener(null);
        gridPlayer.setOnItemClickListener(null);
        gridCpu.setOnItemClickListener(null);
    }

    private int getNumCellsBoardArea() {
        return (int) Math.pow(numCells, 2);
    }
}
