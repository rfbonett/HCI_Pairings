package gui.pairings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
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
}
