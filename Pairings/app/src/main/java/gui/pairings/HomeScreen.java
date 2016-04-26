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
    protected void moveToTournamentSetup(View view) {
        // Move to the tournament setup screen.
        Intent intent = new Intent(this, TournamentType.class);
        startActivity(intent);
    }
}
