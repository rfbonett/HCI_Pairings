package gui.pairings;

import java.util.ArrayList;

/**
 * Class that stores tournament information. Created for the purpose of showing dummy
 * tournaments on the home screen, but could be expanded later to be actually functional.
 */
public class Tournament {

    private String name, type;
    private ArrayList<Bracket> series;
    private ArrayList<Player> players;

    // Constructor that takes the tournament name and type as a parameter.
    public Tournament(String name, String type) {
        this.name = name;
        this.type = type;
        players = new ArrayList<>();
        series = new ArrayList<>();
        series.add(new Bracket());
    }

    // Set the name of this tournament.
    public void setName(String name) {
        this.name = name;
    }

    // Get the name of this tournament.
    public String getName() {
        return name;
    }

    // get the type of this tournament.
    public String getType() {
        return type;
    }

    public int numSeries() {
        return 3;
    }

    public Bracket getBracket(int position) {
        return series.get(0);
    }

    public void addPlayer(Player p) {
        players.add(p);
        series.get(0).addPlayer(p);
    }


}
