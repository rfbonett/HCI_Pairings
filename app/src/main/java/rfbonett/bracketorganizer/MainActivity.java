package rfbonett.bracketorganizer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private Spinner tournamentTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tournamentTypes = (Spinner) findViewById(R.id.tournamentTypes);
    }


    public void createBracket(View v) {
        startActivity(new Intent(this, BracketActivity.class));
    }

}
