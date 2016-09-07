import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

public class Game extends UnicastRemoteObject implements GameInterface {

    protected Game() throws RemoteException {

    }

    @Override
    public UUID register(String name) throws RemoteException {
        return UUID.randomUUID();
    }
}

