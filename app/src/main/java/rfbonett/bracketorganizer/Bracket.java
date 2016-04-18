package rfbonett.bracketorganizer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.Image;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by Rich on 2/27/16.
 */
public class Bracket {
    private ArrayList<Button> players;
    private ArrayList<Button> brackets;
    private RelativeLayout background;
    private Context context;
    private ImageView bracket;

    public Bracket(RelativeLayout background, Context context) {
        this.background = background;
        this.context = context;
        players = new ArrayList<Button>();
        brackets = new ArrayList<Button>();
    }

    public void addPlayer(Button player) {
        players.add(player);
        updateBracketBitmap();
    }

    private void updateBracketBitmap() {

    }

/*
    private void updateBracket() {
        if (brackets.size() < players.size() / 2) {
            final Button bracket = new Button(context);
            bracket.setOnTouchListener(new View.OnTouchListener() {
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

                            x = bracket.getX();
                            y = bracket.getY();
                            pressedX = motionEvent.getRawX();
                            pressedY = motionEvent.getRawY();

                            break;

                        case MotionEvent.ACTION_MOVE:
                            moving = true;
                            bracket.setX(x + (motionEvent.getRawX() - pressedX));
                            bracket.setY(y + (motionEvent.getRawY() - pressedY));
                            break;
                        case MotionEvent.ACTION_UP:
                            if (!moving) {

                            }
                        default:
                            break;
                    }
                    return false;
                }
            });
            background.addView(bracket);
        }
    } */


}
