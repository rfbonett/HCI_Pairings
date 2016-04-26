package cs420.pairings_organizer;

import java.util.ArrayList;

/**
 * This class stores the data on each player (or team) involved in a given tournament.
 * This information includes the player's name, record, place, and previous opponents.
 * You can manipulate all of these variables from within the application.
 */
public class Player {

    private int gameWins, gamesPlayed, matchWins, matchesPlayed;
    private long place;
    private String name;
    private ArrayList<Player> opponents;

    // Default constructor.
    public Player() {
        gameWins = 0;
        gamesPlayed = 0;
        matchWins = 0;
        matchesPlayed = 0;
        opponents = new ArrayList<Player>();
        place = 0;
        name = "playerName";
    }

    // Constructor with name and place.
    public Player(String nam, long plac) {
        gameWins = 0;
        gamesPlayed = 0;
        matchWins = 0;
        matchesPlayed = 0;
        opponents = new ArrayList<Player>();
        place = plac;
        name = nam;
    }

    // Add an opponent that this player played against.
    public void addOpponent(Player p) {
        opponents.add(p);
    }

    // Get the list of opponents this player has played against.
    public ArrayList<Player> getOpponents() {
        return opponents;
    }

    // Get this player's name.
    public String getName() {
        return name;
    }

    // Set this player's name.
    public void setName(String nam) {
        name = nam;
    }

    // Increase this player's number of wins.
    public void addGameWin(int wins) {
        gameWins += wins;
    }

    // Increase this player's number of games played.
    public void addGamePlayed(int games) {
        gamesPlayed += games;
    }

    // Increase this player's number of match wins.
    public void addMatchWin() {
        matchWins++;
    }

    // Increase this player's number of match losses.
    public void addMatchPlayed() {
        matchesPlayed++;
    }

    // Return the number of match wins this player has.
    public int getMatchWins() {
        return matchWins;
    }

    // Return the number of match losses this player has.
    public int getMatchLosses() {
        return matchesPlayed - matchWins;
    }

    // Return the number of matches this player has played.
    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    // Return this player's number of game wins.
    public int getGameWins() {
        return gameWins;
    }

    // Return this player's number of game losses.
    public int getGameLosses() {
        return gamesPlayed - gameWins;
    }

    // Return this player's number of games played.
    public int getGamesPlayed() {
        return gamesPlayed;
    }

    // Return this player's current standing.
    public void setPlace(long p) {
        place = p;
    }

    // Return this player's current standing.
    public long getPlace() {
        return place;
    }

    // Return the percentage of games won by this player's opponents.
    public double oppGameWinPercentage() {
        double oppWins = 0;
        double oppLosses = 0;

        // Loop through this player's opponents and add up their wins and losses.
        if(opponents.size() > 0) {
            for(Player opp : opponents) {
                oppWins += opp.getGameWins();
                oppLosses += opp.getGameLosses();
            }
            return (oppWins / (oppWins + oppLosses)) * 100;
        }

        // If this player hasn't had any opponents, return 0.
        return 0;
    }

    // Return the percentage of matches won by this player's opponents.
    public double oppMatchWinPercentage() {
        double oppWins = 0;
        double oppLosses = 0;

        // Loop through this player's opponents and add up their wins and losses.
        if(opponents.size() > 0) {
            for(Player opp : opponents) {
                oppWins += opp.getMatchWins();
                oppLosses += opp.getMatchLosses();
            }
            return (oppWins / (oppWins + oppLosses)) * 100;
        }

        // If this player hasn't had any opponents, return 0.
        return 0;
    }

    // Set how Player object is viewed as a String.
    @Override
    public String toString() {
        return name;
    }
}