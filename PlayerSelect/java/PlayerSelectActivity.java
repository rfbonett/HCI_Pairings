package cs420.pairings_organizer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PlayerSelectActivity extends AppCompatActivity {
    private ArrayList<Player> selectionList;
    private EditText player_edit;
    private ListView player_viewer;
    private MyArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_select);
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
        Intent intent = new Intent(this, filler.class); // This will be changed to the following class name
        intent.putExtra("Player Selections", selectionList);
        startActivity(intent);
    }

    // Randomly shuffles the selected players if there is the desire for random seeds.
    public void playerShuffle(View view) {
        Collections.shuffle(selectionList, new Random());
        adapter.notifyDataSetChanged();
    }

    /*
    private class MyArrayAdapter extends ArrayAdapter<Player> {
        private int layout;

        public MyArrayAdapter(Context context, int resource, List objects) {
            super(context, resource, objects);
            layout = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder mainView = null;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder newView = new ViewHolder();
                newView.seed = (TextView) convertView.findViewById(R.id.seed_text);
                newView.player_text = (TextView) convertView.findViewById(R.id.txtitem);
                newView.del_button = (Button) convertView.findViewById(R.id.del_player_button);
                convertView.setTag(newView);
            }
            else {
                mainView = (ViewHolder) convertView.getTag();
            }
            return convertView;
        }
    }

    public class ViewHolder {
        TextView seed;
        TextView player_text;
        Button del_button;
    } */
}
