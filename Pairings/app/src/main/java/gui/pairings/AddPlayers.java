package gui.pairings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class AddPlayers extends AppCompatActivity {

    private ArrayList<Player> selectionList;
    private EditText player_edit;
    private ListView player_viewer;
    private MyArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_players);
        selectionList = new ArrayList<>();
        player_edit = (EditText) findViewById(R.id.player_name_edit);
        player_viewer = (ListView) findViewById(R.id.pselect_listView);
        adapter = new MyArrayAdapter(this, R.layout.player_view, selectionList);
        player_viewer.setAdapter(adapter);
    }

    // This defines the action that the Add button will take.
    // The player list will be updated every time a player is added.
    public void addPlayer(View view) {
        String new_player = player_edit.getText().toString();
        Player player = new Player();
        player.setName(new_player);
        selectionList.add(player);
        adapter.notifyDataSetChanged();
    }

    // This defines the action that the Begin Tournament button will take.
    public void createTourney(View view) {
        Intent intent = new Intent(this, TournamentScreen.class); // This will be changed to the following class name
        intent.putExtra("gui.pairings.Player Selections", selectionList);
        startActivity(intent);
    }

    // Randomly shuffles the selected players if there is the desire for random seeds.
    public void playerShuffle(View view) {
        Collections.shuffle(selectionList, new Random());
        adapter.notifyDataSetChanged();
    }
}
