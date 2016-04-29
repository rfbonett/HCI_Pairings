package gui.pairings;

/**
 * Class that stores tournament information. Created for the purpose of showing dummy
 * tournaments on the home screen, but could be expanded later to be actually functional.
 */
public class Tournament {

    private String name, type;

    // Constructor that takes the tournament name and type as a parameter.
    public Tournament(String name, String type) {
        this.name = name;
        this.type = type;
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
}
