import java.rmi.Naming;
import java.util.UUID;

public class Client {
    public static void main(String[] args) {
        try {
            GameInterface game = (GameInterface) Naming.lookup("//localhost/Game");
            UUID id = game.register("Daer");
            System.out.println(id);
        } catch (Exception e) {
            System.out.println("Client failed.");
            e.printStackTrace();
        }
    }
}

