import java.util.UUID;

public class Player {
    private String name;
    private UUID id;

    public Player(String name, UUID id) {
        this.name = name;
        this.id = id;
    }

    public boolean check(UUID id) {
        return this.id.equals(id);
    }

    public void play() {
        System.out.println("Player " + name + " played!!");
    }

    @Override
    public String toString() {
        return "Name: " + name + ", ID: " + id + "\n";
    }
}
