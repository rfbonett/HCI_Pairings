package gui.pairings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class HomeScreen extends Activity {

    private Button loadTournament;
    private String tournamentToLoad;

    private ListView filesList;
    private TournamentAdapter adapter;
    private ArrayList<TournamentHolder> tournamentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        // Store the files list.
        filesList = (ListView) findViewById(R.id.files_list);

        // Store the information for the tournament adapter.
        tournamentList = new ArrayList<>();
        adapter = new TournamentAdapter(this);
        filesList.setAdapter(adapter);

        // Set the behavior of the files list when it's empty.
        filesList.setEmptyView(findViewById(R.id.empty));

        // Create the dummy tournaments.
        createDummyTournaments();

        loadTournament = (Button) findViewById(R.id.load_tournament_button);
        loadTournament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check to see if there are any files in the directory so we don't hit errors
                String path = Environment.getExternalStorageDirectory().getPath();
                final ArrayList<String> files = getFiles(path);
                // Hide load button, appear ListView of files
                if (files != null) {
                    filesList.setEnabled(true);
                    loadTournament(files);
                }
            }
        });
    }

    private void createDummyTournaments() {
        // Create a data structure for storing the dummy tournaments.
        ArrayList<Tournament> dummies = new ArrayList<Tournament>();

        // Create some tournament objects.
        Tournament swiss = new Tournament("Tournament 1", "Swiss");
        Tournament robin = new Tournament("Tournament 2", "Round Robin");
        Tournament single = new Tournament("Tournament 3", "Single Elim");
        Tournament dubble = new Tournament("Tournament 4", "Double Elim");

        // Add them to the dummies data structure.
        dummies.add(swiss);
        dummies.add(robin);
        dummies.add(single);
        dummies.add(dubble);

        // Add them to the list.
        for(Tournament t : dummies) {
            TournamentHolder h = new TournamentHolder();
            h.tournament = t;
            h.color = getResources().getColor(android.R.color.white);
            tournamentList.add(h);
        }

        // Update the visual.
        adapter.notifyDataSetChanged();
    }

    /*
     * Moves to the tournament setup screen when the "New Tournament"
     * button is clicked.
     */
    public void moveToTournamentSetup(View view) {
        // Move to the tournament setup screen.
        Intent intent = new Intent(this, TournamentType.class);
        startActivity(intent);
    }

    /*
     * Gets autosave info
     * Moves to the tournament screen
     */
    public void moveToTournamentScreen(View view) {
        Intent intent = new Intent(this, TournamentScreen.class);
        intent.putExtra("tournamentName", tournamentToLoad);
        startActivity(intent);
    }
/*
 * Opens a list of saved tournaments when the "Load Tournament"
 * button is clicked.
 */
    public void loadTournament(ArrayList<String> files) {
        // Open a list of saved files for perusal
        filesList = (ListView) findViewById(R.id.files_list);
        filesList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, files));
        filesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // TODO: Read data from file and transition to tournament screen
            }
        });
    }

    /*
     * Helper method to retrieve list of files
     */
    public ArrayList<String> getFiles(String path) {
        ArrayList<String> myFiles = new ArrayList<String>();
        File f = new File(path);
        f.mkdirs();
        File[] files = f.listFiles();
        if (files == null) {
            return null;
        }
        if (files.length == 0) {
            return null;
        } else {
            for (int i = 0; i < files.length; i++) {
                myFiles.add(files[i].getName());
            }
        }
        return myFiles;
    }

    /**
     * Holds a bunch of TournamentHolders.
     */
    class TournamentAdapter extends BaseAdapter {

        private Activity context;
        private LayoutInflater inflater = null;

        public TournamentAdapter(Activity context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return tournamentList.size();
        }

        @Override
        public Object getItem(int position) {
            return tournamentList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View row;
            final TournamentHolder holder;

            // Set up the inflater.
            LayoutInflater li = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = li.inflate(R.layout.tournament_holder, null);
            holder = tournamentList.get(position);

            // Tournament name label
            holder.name = (TextView) row.findViewById(R.id.tournament_name);
            holder.name.setTextColor(holder.color);
            // Tournament type label
            holder.type = (TextView) row.findViewById(R.id.tournament_type);
            holder.type.setTextColor(holder.color);
            // Delete button
            holder.delete = (Button) row.findViewById(R.id.tournament_delete_button);

            // Provide user-inputted rows to fields in order to become visible.
            holder.name.setText(holder.tournament.getName());
            holder.type.setText(holder.tournament.getType());

            // Set the row's tag.
            row.setTag(holder);

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Delete the entry.
                    tournamentList.remove(holder);
                    // Update the visual.
                    notifyDataSetChanged();
                    //TODO: Delete the tournament file (for the future)
                }
            });

            // Create a listener for the TextViews.
            View.OnClickListener textListener = new View.OnClickListener() {
                @Override
                public void onClick(View tv) {
                    // Store the type of tournament that was selected.
                    tournamentToLoad = ((TextView)tv).getText().toString();

                    // Set the color of the other tournament names.
                    for(TournamentHolder th : tournamentList) {
                            th.color = getResources().getColor(android.R.color.white);
                    }

                    // Set the color of the clicked tournament name.
                    holder.color = getResources().getColor(R.color.colorAccent);

                    // Update the visual.
                    adapter.notifyDataSetChanged();

                    Log.v("HomeScreen", "Tournament to load:  " + tournamentToLoad);

                    // Enable the button that moves you to the next screen.
                    loadTournament.setEnabled(true);
                }
            };

            // Assign the listener to the TextViews.
            holder.name.setOnClickListener(textListener);

            return row;
        }
    }


    /**
     * PlayerHolder serves as a storage class for rows in the ListView.
     */
    class TournamentHolder {
        public TextView name;
        public TextView type;
        public Button delete;
        public int color;
        public Tournament tournament;
    }
}
