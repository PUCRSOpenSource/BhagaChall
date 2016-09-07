package Client;

import Shared.GameInterface;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.UUID;

public class GameTUI {

    GameInterface game;
    Scanner scanner;
    UUID userID;

    public GameTUI(GameInterface game) {
        this.game = game;
        this.scanner = new Scanner(System.in);
    }

    public void startGame() throws RemoteException {
        registerUser();
        runMainLoop();
    }

    private void registerUser() throws RemoteException {
        System.out.println("Enter your name:");
        String name = scanner.nextLine();
        userID = game.register(name);
    }

    private void runMainLoop() throws RemoteException {
        boolean shouldRun = true;
        while (shouldRun) {
            findGame();
        }
    }

    private void findGame() throws RemoteException {
        System.out.println(game.hasMatch(userID));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
