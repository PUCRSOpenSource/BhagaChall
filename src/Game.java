import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

public class Game extends UnicastRemoteObject implements GameInterface {

    private Lobby lobby;

    protected Game() throws RemoteException {
        lobby = new Lobby();
    }

    @Override
    public UUID register(String name) throws RemoteException {
        return lobby.register(name);
    }

    @Override
    public MatchStatus hasMatch(UUID userID) throws RemoteException {
        return lobby.hasMatch(userID);
    }

    @Override
    public TurnStatus isMyTurn(UUID userID) throws RemoteException {
        return lobby.isTurn(userID);
    }

    @Override
    public String getBoard(UUID userID) throws RemoteException {
        return lobby.getBoard(userID);
    }

    @Override
    public boolean hasGoatLeft(UUID userID) throws RemoteException {
        return lobby.hasGoatLeft(userID);
    }

    @Override
    public boolean putGoat(UUID userID, int x, int y)  throws RemoteException  {
        return lobby.putGoat(userID, x, y);
    }

    @Override
    public boolean moveTiger(UUID userID, int tiger, Direction direction) throws RemoteException {
        return lobby.moveTiger(userID, tiger, direction);
    }

    @Override
    public boolean moveGoat(UUID userID, String goat, Direction direction) throws RemoteException {
        return lobby.moveGoat(userID, goat, direction);
    }
}

