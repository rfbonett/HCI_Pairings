package gui.pairings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;


public class AddPlayers extends Activity {
    private ArrayList<PlayerHolder> selectionList;
    private EditText player_edit;
    private ListView player_viewer;
    private PlayerAdapter adapter;
    private String tournamentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_players);
        selectionList = new ArrayList<>();
        player_edit = (EditText) findViewById(R.id.player_name_edit);
        player_viewer = (ListView) findViewById(R.id.pselect_listView);
        adapter = new PlayerAdapter(this, selectionList);
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
        // Create a new player object.
        String new_player = player_edit.getText().toString();
        if(new_player.equals("")) {
            new_player = "player" + (selectionList.size()+1);
        }
        Player player = new Player(new_player, selectionList.size()+1);

        // Add it to the list.
        PlayerHolder h = new PlayerHolder();
        h.player = player;
        selectionList.add(h);

        // Update the visual.
        adapter.notifyDataSetChanged();

        // Reset the text field.
        player_edit.setText("");
    }

    // This defines the action that the Begin Tournament button will take.
    public void createTourney(View view) {
        Intent intent = new Intent(this, TournamentScreen.class);
        intent.putExtra("Player Selections", selectionList);
        intent.putExtra("tournamentType", tournamentType);
        startActivity(intent);
    }

    // Randomly shuffles the selected players if there is the desire for random seeds.
    // TODO: Fix this. Also, make it update the seed numbers.
    public void playerShuffle(View view) {
        //adapter.clear();
        Collections.shuffle(selectionList);
        adapter.notifyDataSetChanged();
    }
}

/**
 * Holds a bunch of PlayerHolders.
 */
class PlayerAdapter extends BaseAdapter {

    private Activity context;
    private ArrayList<PlayerHolder> rows;
    private LayoutInflater inflater = null;

    public PlayerAdapter(Activity context, ArrayList<PlayerHolder> rows) {
        this.context = context;
        this.rows = rows;
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

        // If the view does not previously exist, initialize and add it to the listview
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

            // The player object being shown
            //holder.player = rows.get(position).player;

            // Provide user-inputted rows to fields in order to become visible.
            holder.name.setText(rows.get(position).player.getName());
            holder.seed.setText(Long.toString(rows.get(position).player.getPlace()));

            // Set the row's tag.
            row.setTag(holder);
        }
        else{
            holder = (PlayerHolder)row.getTag();
        }

        // Define delete button activity.
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
    Player player;
    TextView seed;
    TextView name;
    Button delete;
}


