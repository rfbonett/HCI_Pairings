package gui.pairings;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class HomeScreen extends Activity {

    private Button newTournament, loadTournament, loadAutosave;
    private ListView filesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);


        newTournament = (Button)findViewById(R.id.new_tournament_button);
        loadAutosave = (Button) findViewById(R.id.load_autosave_button);
        loadTournament = (Button)findViewById(R.id.load_tournament_button);        loadTournament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = Environment.getExternalStorageDirectory().getPath();
                final ArrayList<String> files = getFiles(path);
                if (files != null) {
                    loadTournament.setVisibility(View.GONE);
                    ObjectAnimator anim = ObjectAnimator.ofFloat(newTournament, "translationY", -400);
                    anim.setDuration(500);
                    anim.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            filesList = (ListView) findViewById(R.id.files_list);
                            filesList.setVisibility(View.VISIBLE);
                            loadTournament(files);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    ObjectAnimator anim2 = ObjectAnimator.ofFloat(loadAutosave, "translationY", 400);
                    anim2.setDuration(500);
                    anim2.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {

                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    anim.start();
                    anim2.start();
                }
                else {
                    noFilesMessgage();
                }
            }
        });
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
     * Moves to the tournament screen
     */
    public void moveToTournamentScreen(View view) {
        Intent intent = new Intent(this, TournamentScreen.class);
        // We would attach some extras here in a working implementation.
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

    private void noFilesMessgage() {
        String msg = "No files to display.";
        Toast t = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        t.show();
    }

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
}
