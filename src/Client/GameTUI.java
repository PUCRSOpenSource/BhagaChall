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
        runMainLoop();
    }

    private void registerUser() throws RemoteException {
        System.out.println("Enter your name:");
        String name = scanner.nextLine();
        userID = game.register(name);
    }

    private void runMainLoop() throws RemoteException, InterruptedException {
        boolean shouldRun = true;
        findGame();
        while (shouldRun) {
            waitForTurn();
            makePlay();
        }
    }

    private void findGame() throws RemoteException, InterruptedException {
        MatchStatus currentStatus = game.hasMatch(userID);
        System.out.print("Finding your match");
        while (true) {
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
            currentStatus = game.hasMatch(userID);
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
        game.makePlay(userID);
    }
}
