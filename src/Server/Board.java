package Server;

import Shared.Direction;

public class Board {

    private final int SIZE = 5;
    Square[][] matrix = new Square[5][5];
    char currentGoat = 'A';

    public Board() {
        initializeBoard();
        createBoard();
    }

    private void initializeBoard() {
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                matrix[x][y] = new Square();
            }
        }
    }

    private void createBoard() {
        addDirectDirections();
        addDiagonalDirections();
        positionTigers();
    }

    private void addDirectDirections() {
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                addDirectDirections(matrix[x][y], x, y);
            }
        }
    }

    private void addDirectDirections(Square square, int x, int y) {
        if (insideBoard(x + 1, y)) square.addDirection(Direction.EAST);
        if (insideBoard(x - 1, y)) square.addDirection(Direction.WEST);
        if (insideBoard(x, y + 1)) square.addDirection(Direction.NORTH);
        if (insideBoard(x, y - 1)) square.addDirection(Direction.SOUTH);
    }

    private boolean insideBoard(int x, int y) {
        return x >= 0 && x <= 4 && y >= 0 && y <= 4;
    }

    private void addDiagonalDirections() {
        addDiagonalDirections(matrix[1][1], 1, 1);
        addDiagonalDirections(matrix[3][1], 3, 1);
        addDiagonalDirections(matrix[1][3], 1, 3);
        addDiagonalDirections(matrix[3][3], 3, 3);
    }

    private void addDiagonalDirections(Square square, int x, int y) {
        addAllDiagonals(square);
        Square northwest = matrix[x - 1][y + 1];
        Square northeast = matrix[x + 1][y + 1];
        Square southwest = matrix[x - 1][y - 1];
        Square southeast = matrix[x + 1][y - 1];
        northwest.addDirection(Direction.SOUTHEAST);
        northeast.addDirection(Direction.SOUTHWEST);
        southwest.addDirection(Direction.NORTHEAST);
        southeast.addDirection(Direction.NORTHWEST);
    }

    private void addAllDiagonals(Square square) {
        square.addDirection(Direction.NORTHEAST);
        square.addDirection(Direction.NORTHWEST);
        square.addDirection(Direction.SOUTHEAST);
        square.addDirection(Direction.SOUTHWEST);
    }

    private void positionTigers() {
        matrix[0][4].setOccupant("1");
        matrix[4][4].setOccupant("2");
        matrix[4][0].setOccupant("3");
        matrix[0][0].setOccupant("4");
    }

    public void debugBoard() {
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                System.out.println("When you are on position x = " + x + " y = " + y);
                System.out.println("You can move the following directions:");
                matrix[x][y].printDirections();
            }
        }
    }

    public String getEncodedBoard() {
        String encodedBoard = "";
        for (int y = SIZE - 1; y >= 0; y--) {
            for (int x = 0; x < SIZE; x++) {
                encodedBoard += matrix[x][y].getOccupant() + " ";
            }
            encodedBoard += "\n";
        }
        return encodedBoard;
    }

    public boolean hasGoatLeft() {
        return currentGoat <= 'T';
    }

    public boolean isOccupied(int x, int y) {
        if (!insideBoard(x, y)) return true;
        return !matrix[x][y].getOccupant().equals("_");
    }

    public void putGoat(int x, int y) {
        matrix[x][y].setOccupant(String.valueOf(currentGoat));
        currentGoat++;
    }
}
