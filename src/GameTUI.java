import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.UUID;

public class GameTUI {

    GameInterface game;
    Scanner scanner;
    UUID userID;
    Team team;

    public GameTUI(GameInterface game) {
        this.game = game;
        this.scanner = new Scanner(System.in);
    }

    public void startGame() throws RemoteException, InterruptedException {
        registerUser();
        findGame();
        runMainLoop();
    }

    private void registerUser() throws RemoteException {
        System.out.println("Enter your name:");
        String name = scanner.nextLine();
        userID = game.register(name);
    }

    private void runMainLoop() throws RemoteException, InterruptedException {
        while (true) {
            TurnStatus status = waitForTurn();

            if (status == TurnStatus.WINNER) {
                System.out.println("You win");
                return;
            }

            if (status == TurnStatus.LOSER) {
                System.out.println("You lose");
                return;
            }
            makePlay();
        }
    }

    private void findGame() throws RemoteException, InterruptedException {
        MatchStatus currentStatus;
        System.out.print("Finding your match");
        while (true) {
            currentStatus = game.hasMatch(userID);
            switch (currentStatus) {
                case TIGER:
                    team = Team.TIGER;
                    return;
                case GOAT:
                    team = Team.GOAT;
                    return;
                case NOT_FOUND:
                    break;
                case ERROR:
                    break;
                case TIMEOUT:
                    break;
            }
            System.out.print(".");
            Thread.sleep(1000);
        }
    }

    private TurnStatus waitForTurn() throws RemoteException, InterruptedException {
        System.out.println();
        System.out.println("Enemy player is making their move.");
        TurnStatus status = TurnStatus.FALSE;
        while (status != TurnStatus.TRUE) {
            if (status == TurnStatus.WINNER) {
                return status;
            }
            if (status == TurnStatus.LOSER) {
                return status;
            }
            if (status == TurnStatus.ERROR) {
                return status;
            }
            status = game.isMyTurn(userID);
            Thread.sleep(1000);
        }
        return status;
    }

    private void makePlay() throws RemoteException {
        System.out.println("");
        System.out.println("You are playing for the " + team);
        System.out.println("Make your move");
        System.out.println(game.getBoard(userID));
        if (team == Team.GOAT) {
            makeGoatPlay();
        } else {
            moveTiger();
        }
    }

    private void makeGoatPlay() throws RemoteException {
        if (game.hasGoatLeft(userID)) {
            putGoat();
        } else {
            moveGoat();
        }
    }

    private void putGoat() throws RemoteException {
        boolean success = false;
        while (!success) {
            System.out.println("Input the coordinates you want to put your goat using format xy");
            int input = scanner.nextInt();
            scanner.nextLine();
            int x = input / 10;
            int y = input % 10;
            success = game.putGoat(userID, x, y);
            if (!success) {
                System.out.println("input invalid");
            }
        }
    }

    private void moveGoat() throws RemoteException {
        boolean success = false;
        while (!success) {
            System.out.println("Input the direction you want to move your goat using format goatdirection");
            System.out.println("Directions are: ");
            System.out.println("0 = EAST; 1 = SOUTHEAST; 2 = SOUTH; 3 = SOUTHWEST; 4 = WEST; 5 = NORTHWEST; 6 = NORTH; 7 = NORTHEAST");

            String input = scanner.nextLine();
            char[] values = input.toCharArray();

            Direction direction = direction(Character.getNumericValue(values[1]));

            success = game.moveGoat(userID, String.valueOf(values[0]), direction);

            if (!success) {
                System.out.println("input invalid");
            }
        }
    }

    private void moveTiger() throws RemoteException {
        boolean success = false;
        while (!success) {
            System.out.println("Input the direction you want to move your tiger using format tigerdirection");
            System.out.println("Directions are: ");
            System.out.println("0 = EAST; 1 = SOUTHEAST; 2 = SOUTH; 3 = SOUTHWEST; 4 = WEST; 5 = NORTHWEST; 6 = NORTH; 7 = NORTHEAST");

            int input = scanner.nextInt();
            scanner.nextLine();

            int tiger = input / 10;
            Direction direction = direction(input % 10);

            success = game.moveTiger(userID, tiger, direction);
            if (!success) {
                System.out.println("input invalid");
            }
        }

    }

    private Direction direction(int input) {
        switch (input) {
            case 0:
                return Direction.EAST;
            case 1:
                return Direction.SOUTHEAST;
            case 2:
                return Direction.SOUTH;
            case 3:
                return Direction.SOUTHWEST;
            case 4:
                return Direction.WEST;
            case 5:
                return Direction.NORTHWEST;
            case 6:
                return Direction.NORTH;
            default:
                return Direction.NORTHEAST;
        }
    }

}
