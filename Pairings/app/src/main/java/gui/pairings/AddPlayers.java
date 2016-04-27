package gui.pairings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;


public class AddPlayers extends Activity {

    private EditText player_edit;
    private ListView player_viewer;
    private PlayerAdapter adapter;
    private String tournamentType, tournamentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_players);

        player_edit = (EditText) findViewById(R.id.player_name_edit);
        player_viewer = (ListView) findViewById(R.id.pselect_listView);

        // Set up the adapter.
        adapter = new PlayerAdapter(this);
        player_viewer.setAdapter(adapter);

        // Get the information passed from the TournamentScreen activity.
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tournamentType = extras.getString("tournamentType");
            tournamentName = extras.getString("tournamentName");
        }
    }

    // This defines the action that the Add button will take.
    // The player list will be updated every time a player is added.
    public void addPlayer(View view) {
        // Create a new player object.
        String new_player = player_edit.getText().toString();
        if(new_player.equals("")) {
            new_player = "player" + (adapter.getCount()+1);
        }
        Player player = new Player(new_player, adapter.getCount()+1);

        // Add the player to the list.
        adapter.addPlayer(player);

        // Reset the text field.
        player_edit.setText("");
    }

    public void playerShuffle(View view) {
        adapter.shuffle();
    }

    // This defines the action that the Begin Tournament button will take.
    public void createTourney(View view) {
        if(adapter.getCount() >= 2) {
            Intent intent = new Intent(this, TournamentScreen.class);
            // Make an array of players.
            ArrayList<Player> players = new ArrayList<Player>();
            for(PlayerHolder h : adapter.getRows()) {
                players.add(h.player);
            }
            intent.putExtra("players", players);
            intent.putExtra("tournamentType", tournamentType);
            intent.putExtra("tournamentName", tournamentName);
            startActivity(intent);
        }
        else{
            Context context = getApplicationContext();
            CharSequence text = "Please add at least 2 players.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }
}

/**
 * Holds a bunch of PlayerHolders.
 */
class PlayerAdapter extends BaseAdapter {

    private Activity context;
    public ArrayList<PlayerHolder> rows;
    private LayoutInflater inflater = null;

    public PlayerAdapter(Activity context) {
        this.context = context;
        this.rows = new ArrayList<>();
    }

    public void addPlayer(Player player) {
        // Add it to the list.
        PlayerHolder holder = new PlayerHolder();
        holder.player = player;
        rows.add(holder);

        // Update the visual.
        notifyDataSetChanged();
    }

    public ArrayList<PlayerHolder> getRows() {
        return rows;
    }

    // Randomly shuffles the selected players if there is the desire for random seeds.
    // TODO: Fix this. Also, make it update the seed numbers.
    public void shuffle() {
        Collections.shuffle(rows);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return rows.size();
    }

    @Override
    public Object getItem(int position) {
        return rows.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        PlayerHolder holder;

        // If the view is not currently displayed, set it up and add it to the ListView
        if (row == null) {
            // Set up the inflater.
            LayoutInflater li = (LayoutInflater)context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = li.inflate(R.layout.player_view, null);

            // Associate the container class with the xml file items.
            holder = new PlayerHolder();

            // Player name label
            holder.name = (TextView) row.findViewById(R.id.txtitem);
            holder.name.setTextSize(24);
            holder.name.setPadding(0, 12, 0, 0);

            // Delete button
            holder.delete = (Button) row.findViewById(R.id.del_player_button);
            holder.delete.setBackgroundResource(R.color.holo_purple_dark);
            holder.delete.setPadding(20, 0, 20, 0);

            // Seed label
            holder.seed = (TextView) row.findViewById(R.id.seed_text);
            holder.seed.setPadding(0, 20, 25, 5);
            holder.seed.setTextSize(20);

            // Provide user-inputted rows to fields in order to become visible.
            holder.name.setText(rows.get(position).player.getName());
            holder.seed.setText(Long.toString(rows.get(position).player.getPlace())+".");

            // Set the row's tag.
            row.setTag(holder);
        }
        else{
            holder = (PlayerHolder)row.getTag();
        }

        // Define delete button activity. This is here so it is always updated
        // to the button's current position.
        //TODO: Fix this. It should delete the clicked entry.
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log which position was clicked.
                Log.v("AddPlayers", "position clicked was: " + position);
                // Delete the entry.
                rows.remove(position);
                // Update the visual.
                notifyDataSetChanged();
            }
        });

        return row;
    }
}


/**
 * PlayerHolder serves as a storage class for rows in the ListView.
 */
class PlayerHolder {
    public Player player;
    public TextView seed;
    public TextView name;
    public Button delete;
}


