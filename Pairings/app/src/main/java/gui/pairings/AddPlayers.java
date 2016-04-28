package gui.pairings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
    private ArrayList<PlayerHolder> selectionList;
    private EditText player_edit;
    private ListView player_viewer;
    private PlayerAdapter adapter;
    private String tournamentType, tournamentName;
    private int playerCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_players);
        selectionList = new ArrayList<>();
        player_edit = (EditText) findViewById(R.id.player_name_edit);
        player_viewer = (ListView) findViewById(R.id.pselect_listView);
        adapter = new PlayerAdapter(this);
        player_viewer.setAdapter(adapter);

        // Set the behavior of the players list when it's empty.
        player_viewer.setEmptyView(findViewById(R.id.empty));

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
        if (new_player.equals("")) {
            new_player = "player" + (++playerCounter);
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
        if (selectionList.size() >= 2) {
            Intent intent = new Intent(this, TournamentScreen.class);
            // Make an array of players.
            ArrayList<Player> players = new ArrayList<Player>();
            for (PlayerHolder h : selectionList) {
                players.add(h.player);
            }
            // Pass the needed information to the next screen.
            intent.putExtra("players", players);
            intent.putExtra("tournamentType", tournamentType);
            intent.putExtra("tournamentName", tournamentName);
            startActivity(intent);
        } else {
            // Show a warning that you need at least 2 players.
            Context context = getApplicationContext();
            CharSequence text = "Please add at least 2 players.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    // Update the seed numbers.
    private void updateSeedNumbers() {
        // Update the seed numbers.
        for(int i=0; i < selectionList.size(); i++) {
            selectionList.get(i).player.setPlace(i+1);
        }
    }

    // Randomly shuffles the selected players if there is the desire for random seeds.
    public void playerShuffle(View view) {
        Collections.shuffle(selectionList);
        updateSeedNumbers();
        adapter.notifyDataSetChanged();
    }


    /**
     * Holds a bunch of PlayerHolders.
     */
    class PlayerAdapter extends BaseAdapter {

        private Activity context;
        private LayoutInflater inflater = null;

        public PlayerAdapter(Activity context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return selectionList.size();
        }

        @Override
        public Object getItem(int position) {
            return selectionList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View row;
            final PlayerHolder holder;

            // Set up the inflater.
            LayoutInflater li = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = li.inflate(R.layout.player_view, null);
            holder = selectionList.get(position);

            // Player name label
            holder.name = (TextView) row.findViewById(R.id.txtitem);
            holder.name.setTextSize(24);
            holder.name.setPadding(0, 12, 0, 0);

            // Delete button
            holder.delete = (Button) row.findViewById(R.id.del_player_button);
            holder.delete.setBackgroundResource(R.color.colorPrimaryDark);
            holder.delete.setPadding(20, 0, 20, 0);

            // Seed label
            holder.seed = (TextView) row.findViewById(R.id.seed_text);
            holder.seed.setPadding(0, 20, 25, 5);
            holder.seed.setTextSize(20);

            // Provide user-inputted rows to fields in order to become visible.
            holder.name.setText(holder.player.getName());
            holder.seed.setText(Long.toString(holder.player.getPlace()) + ".");

            // Set the row's tag.
            row.setTag(holder);

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Delete the entry.
                    selectionList.remove(holder);
                    // Update the seeds.
                    updateSeedNumbers();
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
}

