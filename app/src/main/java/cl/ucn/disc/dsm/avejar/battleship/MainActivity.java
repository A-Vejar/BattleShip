package cl.ucn.disc.dsm.avejar.battleship;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

import cl.ucn.disc.dsm.avejar.battleship.battleship.adapter.GameBoardAdapter;
import cl.ucn.disc.dsm.avejar.battleship.battleship.models.GameBoard;
import cl.ucn.disc.dsm.avejar.battleship.battleship.controllers.Game;

public class MainActivity extends AppCompatActivity {

    private int numCell;

    private TextView tvStage;
    private TextView tvMessage;

    private Button buttonAttack;
    private Button buttonRestart;

    private GridView player;
    private GridView cpu;

    private GameBoardAdapter adapterPlayer;
    private GameBoardAdapter adapterCpu;

    /**
     * Puts the variables to the "Game class" and initializes it
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setFields();
        enableGame();
    }

    private void enableGame() {

        Game game = Game.getGameInstance();

        game.setFields(this, numCell, tvStage, tvMessage,
                buttonAttack, buttonRestart, player, cpu, adapterPlayer, adapterCpu);
        game.initialize();
    }

    private void setFields() {

        numCell = getResources().getInteger(R.integer.num_cells_board_side);
        tvStage = findViewById(R.id.text_view_game_stage);
        tvMessage = findViewById(R.id.text_view_message);
        buttonRestart = findViewById(R.id.button_initialize);
        buttonAttack = findViewById(R.id.button_attack);
        player = findViewById(R.id.gridViewBoard1);
        cpu = findViewById(R.id.gridViewBoard2);

        adapterPlayer = new GameBoardAdapter(this, new ArrayList<GameBoard>());
        adapterCpu = new GameBoardAdapter(this, new ArrayList<GameBoard>());
    }
}
