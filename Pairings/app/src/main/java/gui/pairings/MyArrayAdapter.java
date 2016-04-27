package gui.pairings;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;


public class MyArrayAdapter extends ArrayAdapter<Player> {
    private ArrayList<Player> data;
    private int layoutResourceId;
    private Context context;

    // Constructor for special adapter.
    public MyArrayAdapter(Context context, int layoutResourceId, ArrayList<Player> list) {
        super(context, layoutResourceId, list);

        // Initialize variables for global use.
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = list;
    }

    // Setup method call to determine what each individual layout will be comprised of in the
    // listview.
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        PlayerViewHolder holder;

        // If the view does not previously exist, initialize and add it to the listview
        if (row == null) {
            // Inflate the layout for it to appear on screen.
            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(layoutResourceId, parent, false);

            // Associate the container class with the xml file items.
            holder = new PlayerViewHolder();
            // Player name label
            holder.player_text = (TextView) row.findViewById(R.id.txtitem);
            holder.player_text.setTextSize(24);
            holder.player_text.setPadding(0, 12, 0, 0);
            // Delete button
            holder.del_button = (Button) row.findViewById(R.id.del_player_button);
            holder.del_button.setBackgroundResource(R.color.holo_purple_dark);
            holder.del_button.setPadding(20, 0, 20, 0);
            // Seed label
            holder.seed = (TextView) row.findViewById(R.id.seed_text);
            holder.seed.setPadding(0, 20, 25, 5);
            holder.seed.setTextSize(20);
            // The player object being shown
            holder.player = data.get(position);

            // Provide user-inputted data to fields in order to become visible.
            holder.player_text.setText(data.get(position).getName());
            holder.seed.setText(Long.toString(data.get(position).getPlace()));

            row.setTag(holder);
        }
        else{
            holder = (PlayerViewHolder) row.getTag();
        }
        // Define delete button activity.
        holder.del_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log which position was clicked.
                Log.v("AddPlayers", "position clicked was: " + position);

                // Delete the entry.
                data.remove(position);

                // Update the visual.
                notifyDataSetChanged();
            }
        });
        return row;
    }

    // Simple storage class for each individual row in the listview for the adapter to act on.
    public class PlayerViewHolder {
        Player player;
        TextView seed;
        TextView player_text;
        Button del_button;
    }
}
