package Server;

import Shared.Direction;
import Shared.Team;

import java.util.ArrayList;

public class Square {

    private ArrayList<Direction> directions;
    private String occupant = "_";

    public Square() {
        directions = new ArrayList<>();
    }

    public ArrayList<Direction> getDirections() {
        return directions;
    }

    public void addDirection(Direction direction) {
        directions.add(direction);
    }

    public void printDirections() {
        for (Direction d : directions) {
            System.out.print(d + " ");
        }
        System.out.println();
    }

    public String getOccupant() {
        return occupant;
    }

    public void setOccupant(String occupant) {
        this.occupant = occupant;
    }
}
