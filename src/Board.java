import java.util.ArrayList;

public class Board {

    private final int SIZE = 5;
    Square[][] matrix = new Square[5][5];
    private char currentGoat = 'A';
    private int numberOfGoatsEaten = 0;

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

    public boolean moveTiger(int tiger, Direction direction) {
        int[] position = find("" + tiger);
        if (position[0] == -1) return false;
        int x = position[0];
        int y = position[1];
        if (!matrix[x][y].getDirections().contains(direction)) {
            return false;
        }
        Square nextSquare = squareAt(x, y, direction, 1);
        if (nextSquare == null) return false;
        if (!isOccupied(nextSquare)) {
            matrix[x][y].setOccupant("_");
            nextSquare.setOccupant("" + tiger);
            return true;
        }

        if (nextSquare.getOccupant().equals("1") ||
                nextSquare.getOccupant().equals("2") ||
                nextSquare.getOccupant().equals("3") ||
                nextSquare.getOccupant().equals("4")) {
            return false;
        }
        Square nextNextSquare = squareAt(x, y, direction, 2);
        if (nextNextSquare == null) return false;
        if (isOccupied(nextNextSquare)) {
            return false;
        }
        matrix[x][y].setOccupant("_");
        nextNextSquare.setOccupant("" + tiger);
        nextSquare.setOccupant("_");
        numberOfGoatsEaten++;
        return true;
    }

    private boolean isOccupied(Square square) {
        return !square.getOccupant().equals("_");
    }

    private int[] find(String id) {
        int[] position = new int[2];
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                if (matrix[x][y].getOccupant().equals(id)) {
                    position[0] = x;
                    position[1] = y;
                    return position;
                }
            }
        }
        position[0] = -1;
        position[1] = -1;
        return position;
    }

    private Square squareAt(int originX, int originY, Direction direction, int hops) {
        try {
            switch (direction) {
                case NORTH:
                    return matrix[originX][originY + hops];
                case NORTHEAST:
                    return matrix[originX + hops][originY + hops];
                case EAST:
                    return matrix[originX + hops][originY];
                case SOUTHEAST:
                    return matrix[originX + hops][originY - hops];
                case SOUTH:
                    return matrix[originX][originY - hops];
                case SOUTHWEST:
                    return matrix[originX - hops][originY - hops];
                case WEST:
                    return matrix[originX - hops][originY];
                default:
                    return matrix[originX - hops][originY + hops];
            }
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public boolean moveGoat(String goat, Direction direction) {
        int[] position = find("" + goat);
        if (position[0] == -1) return false;
        int x = position[0];
        int y = position[1];
        if (!matrix[x][y].getDirections().contains(direction)) {
            return false;
        }
        Square nextSquare = squareAt(x, y, direction, 1);
        if (!isOccupied(nextSquare)) {
            matrix[x][y].setOccupant("_");
            nextSquare.setOccupant("" + goat);
            return true;
        }

        return false;
    }

    public int getNumberOfGoatsEaten() {
        return numberOfGoatsEaten;
    }

    public boolean tigersCanMove() {
        int[] tiger1 = find("1");
        int[] tiger2 = find("2");
        int[] tiger3 = find("3");
        int[] tiger4 = find("4");
        return tigerCanMove(tiger1[0], tiger1[1]) ||
                tigerCanMove(tiger2[0], tiger2[1]) ||
                tigerCanMove(tiger3[0], tiger3[1]) ||
                tigerCanMove(tiger4[0], tiger4[1]);

    }

    private boolean tigerCanMove(int x, int y) {
        Square tigerSquare = matrix[x][y];
        ArrayList<Direction> directions = tigerSquare.getDirections();

        String[] goats = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T"};

        for (Direction direction : directions) {
            if (squareAt(x, y, direction, 1).getOccupant().equals("_")) return true;

            for (String goat : goats) {
                if (squareAt(x, y, direction, 1).getOccupant().equals(goat) &&
                        squareAt(x, y, direction, 2).getOccupant().equals("_")) return true;
            }

        }
        return false;
    }
}
