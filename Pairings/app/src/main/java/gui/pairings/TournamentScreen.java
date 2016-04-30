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
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TournamentScreen extends AppCompatActivity {

    private ViewPager bracketPager;
    private static Tournament tournament;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament_screen);

        ArrayList<Player> players = (ArrayList<Player>) getIntent().getSerializableExtra("players");
        String type = getIntent().getStringExtra("tournamenttype");
        String name = getIntent().getStringExtra("tournamentname");
        tournament = new Tournament(name, type);
        if (players != null) {
            for (Player p : players) {
                tournament.addPlayer(p);
            }
        }
        bracketPager = (ViewPager) findViewById(R.id.bracketPager);
        bracketPager.setAdapter(new BracketAdapter(getSupportFragmentManager()));
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
                return bracketView;
            }

            public void launchResultDialog(Challenge c) {
                final Dialog d = new Dialog(context);
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setContentView(R.layout.result_dialog);
                TextView first = (TextView) d.findViewById(R.id.firstName);
                first.setText(c.getFirst().getName());
                TextView second = (TextView) d.findViewById(R.id.secondName);
                second.setText(c.getSecond().getName());
                d.show();
            }
        }
    }



}
