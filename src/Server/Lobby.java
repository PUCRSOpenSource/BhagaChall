package Server;

import java.util.ArrayList;
import java.util.UUID;

public class Lobby {
    private ArrayList<Player> players;

    public Lobby() {
        players = new ArrayList<>();
    }

    public UUID register(String username) {
        UUID id = UUID.randomUUID();
        players.add(new Player(username, id));
        printPlayers();
        return id;
    }

    public void printPlayers() {
        System.out.println("Printing players:");
        for (Player player : players) {
            System.out.println(player);
        }
    }

}
