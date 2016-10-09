package Server;

import Shared.Direction;

import java.util.ArrayList;

public class Square {

    private ArrayList<Direction> directions;

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
}
