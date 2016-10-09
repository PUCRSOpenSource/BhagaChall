package Client;

import Shared.GameInterface;
import Shared.MatchStatus;
import Shared.Team;

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
        boolean shouldRun = true;
        while (shouldRun) {
            waitForTurn();
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

    private void waitForTurn() throws RemoteException, InterruptedException {
        while (!game.isMyTurn(userID)) {
            Thread.sleep(1000);
        }
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
        }
        moveGoat();
    }

    private void putGoat() throws RemoteException {
        System.out.println("Input the coordinates you want to put your goat using format xy");
        int input = scanner.nextInt();
        int x = input / 10;
        int y = input % 10;
        game.putGoat(userID, x, y);
    }

    private void moveGoat() {

    }

    private void moveTiger() {

    }

}
