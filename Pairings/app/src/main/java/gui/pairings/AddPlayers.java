package gui.pairings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Collections;


public class AddPlayers extends Activity {
    private ArrayList<Player> selectionList;
    private EditText player_edit;
    private ListView player_viewer;
    private MyArrayAdapter adapter;
    private String tournamentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_players);
        selectionList = new ArrayList<>();
        player_edit = (EditText) findViewById(R.id.player_name_edit);
        player_viewer = (ListView) findViewById(R.id.pselect_listView);
        adapter = new MyArrayAdapter(this, R.layout.player_view, selectionList);
        player_viewer.setAdapter(adapter);

        // Get the information passed from the TournamentScreen activity.
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tournamentType = extras.getString("tournamentType");
        }
    }

    // This defines the action that the Add button will take.
    // The player list will be updated every time a player is added.
    public void addPlayer(View view) {
        // Add a new player to the list
        String new_player = player_edit.getText().toString();
        Player player = new Player(new_player, selectionList.size()+1);
        selectionList.add(player);
        adapter.notifyDataSetChanged();

        // Reset the text field.
        player_edit.setText("");
    }

    // This defines the action that the Begin Tournament button will take.
    public void createTourney(View view) {
        Intent intent = new Intent(this, TournamentScreen.class); // This will be changed to the following class name
        intent.putExtra("Player Selections", selectionList);
        intent.putExtra("tournamentType", tournamentType);
        startActivity(intent);
    }

    // Randomly shuffles the selected players if there is the desire for random seeds.
    public void playerShuffle(View view) {
        adapter.clear();
        Collections.shuffle(selectionList);
        adapter.notifyDataSetChanged();
    }
}
