package gui.pairings;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TournamentScreen extends AppCompatActivity {

    private ViewPager pager;
    private static Tournament tournament;
    private static ArrayList<Player> players;
    private ToggleButton bracketToggle;
    private ToggleButton playerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament_screen);

        players = (ArrayList<Player>) getIntent().getSerializableExtra("players");
        String type = getIntent().getStringExtra("tournamenttype");
        String name = getIntent().getStringExtra("tournamentname");
        tournament = new Tournament(name, type);
        if (players != null) {
            for (Player p : players) {
                tournament.addPlayer(p);
            }
        }
        bracketToggle = (ToggleButton) findViewById(R.id.bracketButton);
        playerToggle = (ToggleButton) findViewById(R.id.playersButton);
        pager = (ViewPager) findViewById(R.id.bracketPager);
        displayBracket(null);
    }

    public void displayBracket(View v) {
        pager.setAdapter(new BracketAdapter(getSupportFragmentManager()));
        bracketToggle.setChecked(true);
        playerToggle.setChecked(false);
    }

    public void displayPlayers(View v) {
        pager.setAdapter(new PlayerAdapter(getSupportFragmentManager()));
        bracketToggle.setChecked(false);
        playerToggle.setChecked(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tournament_screen, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_save :
                saveTournament();
                break;
            case R.id.action_share :
                shareTournament();
                break;
            case R.id.action_back :
                saveTournament();
                returnToMain();
                break;
            case R.id.action_settings :
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveTournament() {
        Toast.makeText(TournamentScreen.this, "Tournament Saved!", Toast.LENGTH_SHORT).show();
    }

    public void shareTournament() {
        final Dialog prompt = new Dialog(this);
        prompt.requestWindowFeature(Window.FEATURE_NO_TITLE);
        prompt.setContentView(R.layout.share_dialog);
        //prompt.setTitle("Share " + (tournament.getName() != null ? tournament.getName() : "Tournament"));
        prompt.show();
    }

    public void returnToMain() {
        Intent i = new Intent(this, HomeScreen.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    public void shareFacebook(View v) {
        Toast.makeText(TournamentScreen.this, "Would launch Facebook!", Toast.LENGTH_SHORT).show();
    }

    public void shareTwitter(View v) {
        Toast.makeText(TournamentScreen.this, "Would launch Twitter!", Toast.LENGTH_SHORT).show();
    }

    public void shareGmail(View v) {
        Toast.makeText(TournamentScreen.this, "Would launch Gmail!", Toast.LENGTH_SHORT).show();
    }


    class BracketAdapter extends FragmentStatePagerAdapter {

        private int count;

        public BracketAdapter(FragmentManager manager) {
            super(manager);
            count = tournament.numSeries();
        }

        @Override
        public Fragment getItem(int position) {
            BracketFragment bracket = new BracketFragment();
            Bundle args = new Bundle();
            args.putInt(BracketFragment.ARG_OBJECT, position);
            bracket.setArguments(args);
            return bracket;
        }

        @Override
        public int getCount() {
            return count;
        }
    }

    public static class BracketFragment extends Fragment {
        public static final String ARG_OBJECT = "bracket";
        private Bracket bracket;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.bracket_layout, container, false);
            int pos = getArguments().getInt(ARG_OBJECT);
            bracket = tournament.getBracket(pos);
            ListView l = (ListView) rootView.findViewById(R.id.bracketListView);
            l.setAdapter(new ChallengeListAdapter(getActivity(), bracket.getChallenges()));

            return rootView;
        }


        class ChallengeListAdapter extends BaseAdapter {
            private ArrayList<Challenge> challenges;
            private final Context context;

            public ChallengeListAdapter(Context context, ArrayList<Challenge> challenges) {
                this.context = context;
                this.challenges = challenges;
            }

            @Override
            public int getCount() {
                return challenges.size();
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                LayoutInflater inflater = LayoutInflater.from(context);
                View bracketView = inflater.inflate(R.layout.challenge, viewGroup, false);
                final Challenge c = challenges.get(i);
                int width = (int) getResources().getDimension(R.dimen.button_width);
                int height = (int) getResources().getDimension(R.dimen.button_height);
                if (c.getFirst() != null) {
                    Button first = new Button(context);
                    first.setText(c.getFirst().getName());
                    first.setLayoutParams(new FrameLayout.LayoutParams(width, height));
                    first.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            launchResultDialog(c);
                        }
                    });
                    ((FrameLayout) bracketView.findViewById(R.id.firstPlayer)).addView(first);
                }
                if (c.getSecond() != null) {
                    Button second = new Button(context);
                    second.setText(c.getSecond().getName());
                    second.setLayoutParams(new FrameLayout.LayoutParams(width, height));
                    second.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            launchResultDialog(c);
                        }
                    });
                    ((FrameLayout) bracketView.findViewById(R.id.secondPlayer)).addView(second);
                }
                if (c.getVictor() != null) {
                    Button victor = new Button(context);
                    victor.setText(c.getVictor().getName());
                    victor.setLayoutParams(new FrameLayout.LayoutParams(width, height));
                    ((FrameLayout) bracketView.findViewById(R.id.victor)).addView(victor);
                }

                return bracketView;
            }

            public void launchResultDialog(final Challenge c) {
                final Dialog d = new Dialog(context);
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setContentView(R.layout.result_dialog);
                final NumberPicker firstNum = (NumberPicker) d.findViewById(R.id.firstNumber);
                if (c.getFirst() != null) {
                    TextView first = (TextView) d.findViewById(R.id.firstName);
                    first.setText(c.getFirst().getName());
                    firstNum.setMaxValue(10);
                    firstNum.setMinValue(0);
                    firstNum.setValue(c.getFirst().getGameWins());
                }
                else {
                    firstNum.setVisibility(View.GONE);
                }
                final NumberPicker secondNum = (NumberPicker) d.findViewById(R.id.secondNumber);
                if (c.getSecond() != null) {
                    TextView second = (TextView) d.findViewById(R.id.secondName);
                    second.setText(c.getSecond().getName());
                    secondNum.setMaxValue(10);
                    secondNum.setMinValue(0);
                    secondNum.setValue(c.getSecond().getGameWins());
                }
                else {
                    secondNum.setVisibility(View.GONE);
                }
                Button b = (Button) d.findViewById(R.id.submitResults);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (c.getFirst() != null && c.getSecond() != null) {
                            int res = firstNum.getValue() - secondNum.getValue();
                            if (res != 0) {
                                c.setVictor(res > 0 ? c.getFirst() : c.getSecond());
                            }
                        }
                        d.cancel();
                    }
                });
                d.show();
            }
        }
    }

    class PlayerAdapter extends FragmentStatePagerAdapter {

        public PlayerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new PlayerFragment();
        }

        @Override
        public int getCount() {
            return 1;
        }
    }

    public static class PlayerFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.players_layout, container, false);
            TableLayout table = (TableLayout) rootView.findViewById(R.id.players_table);
            for (Player p : players) {
                TableRow row = new TableRow(getActivity());
                row.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT));

                TextView name = new TextView(getActivity());
                name.setText(p.getName());
                row.addView(name);
                TextView record = new TextView(getActivity());
                record.setText(p.getGameWins() + " - " + p.getGameLosses());
                row.addView(record);
                TextView place = new TextView(getActivity());
                place.setText(String.valueOf(p.getPlace()));
                row.addView(place);

                table.addView(row);
            }
            return rootView;
        }
    }



}
