package Client;

import Shared.GameInterface;

import java.rmi.Naming;

public class Client {
    public static void main(String[] args) {
        try {
            GameInterface game = (GameInterface) Naming.lookup("//localhost/Server.Game");
            new GameTUI(game).startGame();
        } catch (Exception e) {
            System.out.println("Client failed.");
            e.printStackTrace();
        }
    }
}

