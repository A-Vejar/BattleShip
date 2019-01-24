package cl.ucn.disc.dsm.avejar.battleship.battleship.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

import java.util.List;

import cl.ucn.disc.dsm.avejar.battleship.R;
import cl.ucn.disc.dsm.avejar.battleship.battleship.enums.Status;
import cl.ucn.disc.dsm.avejar.battleship.battleship.models.GameBoard;

public class GameBoardAdapter extends ArrayAdapter<GameBoard> {

    private Status status;
    private LayoutInflater inflater;

    public GameBoardAdapter(Context context, List<GameBoard> objects) {
        super(context, -1, objects);
        inflater = LayoutInflater.from(context);
    }

    /**
     * Color appearance
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        GameBoard gameBoard = getItem(position);
        ViewHolder holder;

        if(convertView == null){

            convertView = inflater.inflate(R.layout.gameboard_cell, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {

            holder = (ViewHolder) convertView.getTag();
        }


        if (gameBoard.getStatus() == status.HIT)
            holder.button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.HitColor));
        else if (gameBoard.getStatus() == status.MISS)
            holder.button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.MissColor));

        else if (gameBoard.getPlayerNum() == 2)
            holder.button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.UnknownColorColor));

        else if (gameBoard.getStatus() == status.EMPTY)
            holder.button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.EmptyColor));
        else
            holder.button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.FullColor));

        return convertView;
    }

    /**
     * Puts the empty cells
     */
    public void addCells(GridView gridView, int playerNum, int numCells) {
        gridView.setAdapter(this);
        for (int i = 0; i < numCells; i++)
            this.add(new GameBoard(playerNum, status.EMPTY));
    }

    /**
     * Inner class
     */
    public static class ViewHolder {

        Button button;

        ViewHolder(View view){

            button = view.findViewById(R.id.cell);
        }
    }
}
