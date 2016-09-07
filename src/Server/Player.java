package Server;

import java.util.UUID;

public class Player {
    private String name;
    private UUID id;

    public Player(String name, UUID id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\n" + "ID: " + id + "\n";
    }
}
