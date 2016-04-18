package rfbonett.bracketorganizer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class BracketActivity extends AppCompatActivity {

    private RelativeLayout background;
    private Bracket bracket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bracket);

        background = (RelativeLayout) findViewById(R.id.background);
        background.setOnTouchListener(new View.OnTouchListener() {
            private float pressedX;
            private float pressedY;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        pressedX = motionEvent.getRawX();
                        pressedY = motionEvent.getRawY();

                        break;

                    case MotionEvent.ACTION_MOVE:
                        for (int i = 0; i < background.getChildCount(); i++) {
                            View v = background.getChildAt(i);
                            v.setX(v.getX() + (motionEvent.getRawX() - pressedX));
                            v.setY(v.getY() + (motionEvent.getRawY() - pressedY));
                        }
                        pressedX = motionEvent.getRawX();
                        pressedY = motionEvent.getRawY();
                    default:
                        break;
                }
                return false;
            }
        });

        bracket = new Bracket(background, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bracket_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.add_player :
                final Button newPlayer = new Button(this);
                newPlayer.setOnTouchListener(new View.OnTouchListener() {
                    private float x;
                    private float y;
                    private float pressedX;
                    private float pressedY;
                    private boolean moving;

                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        switch (motionEvent.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                moving = false;

                                x = newPlayer.getX();
                                y = newPlayer.getY();
                                pressedX = motionEvent.getRawX();
                                pressedY = motionEvent.getRawY();

                                break;

                            case MotionEvent.ACTION_MOVE:
                                moving = true;
                                newPlayer.setX(x + (motionEvent.getRawX() - pressedX));
                                newPlayer.setY(y + (motionEvent.getRawY() - pressedY));
                                break;
                            case MotionEvent.ACTION_UP:
                                if (!moving) {
                                    AlertDialog.Builder textInput = new AlertDialog.Builder(BracketActivity.this);
                                    textInput.setTitle("Set Name");
                                    final EditText input = new EditText(BracketActivity.this);
                                    textInput.setView(input);
                                    textInput.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            newPlayer.setText(input.getText());
                                        }
                                    });
                                    textInput.show();
                                }
                            default:
                                break;
                        }
                        return false;
                    }
                });
                newPlayer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder textInput = new AlertDialog.Builder(BracketActivity.this);
                        textInput.setTitle("Set Name");
                        final EditText input = new EditText(BracketActivity.this);
                        textInput.setView(input);
                        textInput.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                newPlayer.setText(input.getText());
                            }
                        });
                        textInput.show();
                    }
                });
                newPlayer.setBackgroundResource(R.drawable.player_button_selector);
                background.addView(newPlayer, background.getChildCount());
                bracket.addPlayer(newPlayer);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
