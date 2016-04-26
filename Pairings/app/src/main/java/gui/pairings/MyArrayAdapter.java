package gui.pairings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import gui.pairings.R;

/**
 * Created by Charlie on 4/26/2016.
 */
public class MyArrayAdapter extends ArrayAdapter<Player> {
    private ArrayList<Player> data;
    private int layoutResourceId;
    private Context context;

    public MyArrayAdapter(Context context, int layoutResourceId, ArrayList<Player> list) {
        super(context, layoutResourceId, list);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = list;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        PlayerViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new PlayerViewHolder();
            holder.player_text = (TextView) row.findViewById(R.id.txtitem);
            holder.del_button = (Button) row.findViewById(R.id.del_player_button);
            holder.seed = (TextView) row.findViewById(R.id.seed_text);
            row.setTag(holder);
        }
        else{
            holder = (PlayerViewHolder) row.getTag();
        }
        holder.del_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.remove(position);
                notifyDataSetChanged();
            }
        });
        return row;
    }

    public class PlayerViewHolder {
        TextView seed;
        TextView player_text;
        Button del_button;
    }
}
