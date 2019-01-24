package cl.ucn.disc.dsm.avejar.battleship.battleship;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import cl.ucn.disc.dsm.avejar.battleship.MainActivity;
import cl.ucn.disc.dsm.avejar.battleship.R;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        button = findViewById(R.id.button2);
        button.setOnClickListener(this);
    }

    private void accessButton() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        accessButton();
    }
}
