import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            System.out.println("RMI registry ready.");
        } catch (RemoteException e) {
            System.out.println("RMI registry already running.");
        }
        try {
            Naming.rebind("Game", new Game());
            System.out.println("PidServer is ready.");
        } catch (Exception e) {
            System.out.println("PidServer failed:");
            e.printStackTrace();
        }
    }
}
