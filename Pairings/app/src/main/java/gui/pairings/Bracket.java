package gui.pairings;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Rich on 4/29/16.
 */
public class Bracket {
    private ArrayList<Challenge> challenges;
    private ArrayList<Player> players;

    public Bracket() {
        challenges = new ArrayList<>();
        players = new ArrayList<>();
    }

    public ArrayList<Challenge> getChallenges() {
        return challenges;
    }

    public void addChallenge(Challenge challenge) {
        challenges.add(challenge);
    }

    public void addPlayer(Player p) {
        players.add(p);
        if (players.size() % 2 == 1) {
            Challenge c = new Challenge();
            c.setFirst(p);
            challenges.add(c);
        }
        else {
            challenges.get(challenges.size() - 1).setSecond(p);
        }
    }
}

class Challenge {
    private Player first;
    private Player second;
    private Player victor;

    public void setFirst(Player p) {
        first = p;
    }

    public void setSecond(Player p) {
        second = p;
    }

    public void setVictor(Player p) {
        victor = p;
    }

    public Player getFirst() {
        return first;
    }

    public Player getSecond() {
        return second;
    }

    public Player getVictor() {
        return victor;
    }

}
