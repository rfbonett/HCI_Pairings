package gui.pairings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class TournamentType extends Activity {

    private Button swiss, roundRobin, doubleElim, singleElim, addPlayers;
    private EditText titleField;
    private ArrayList<Button> buttons;
    private String tournamentType, tournamentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament_type);

        // Store the UI elements we need to interact with.
        swiss = (Button)findViewById(R.id.swiss_button);
        roundRobin = (Button)findViewById(R.id.round_robin_button);
        singleElim = (Button)findViewById(R.id.single_elim_button);
        doubleElim = (Button)findViewById(R.id.double_elim_button);
        addPlayers = (Button)findViewById(R.id.add_players_button);
        titleField = (EditText)findViewById(R.id.name_field);

        // Put the buttons in an array so we can easily iterate over them.
        buttons = new ArrayList<Button>();
        buttons.add(swiss);
        buttons.add(roundRobin);
        buttons.add(singleElim);
        buttons.add(doubleElim);
    }

    /*
     * Change the color of the clicked button to a darker color, and change
     * the color of all the other buttons back to normal.
     */
    protected void chooseType(View btn) {
        // Set the color of the pressed button to stand out and set the color of
        // the other buttons back to normal.
        for(Button b : buttons) {
            if(b.getId() == btn.getId()) {
                btn.setBackgroundResource(android.R.color.holo_orange_dark);
            }
            else {
                b.setBackgroundResource(R.color.holo_purple);
            }
        }
        // Store the type of tournament that was selected.
        tournamentType = ((Button)btn).getText().toString();
        // Enable the button that moves you to the next screen.
        addPlayers.setEnabled(true);
    }

    /*
     * Move to the next screen and send it the tournament type and name.
     */
    protected void nextScreen(View view) {
        // Grab the tournament name.
        tournamentName = titleField.getText().toString();
        if(tournamentName.equals("")) {
            tournamentName = "myTournament";
        }
        // Move to the next screen and send it the collected data.
        Intent intent = new Intent(this, AddPlayers.class);
        intent.putExtra("tournamentType", tournamentType);
        intent.putExtra("tournamentName", tournamentName);
        startActivity(intent);
    }
}
