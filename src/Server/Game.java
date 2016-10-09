package Server;

import Shared.GameInterface;
import Shared.MatchStatus;

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
    public boolean isMyTurn(UUID userID) throws RemoteException {
        return lobby.isTurn(userID);
    }

    @Override
    public void makePlay(UUID userID) throws RemoteException {
        lobby.makePlay(userID);
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
    public boolean putGoat(UUID userID, int x, int y) {
        return lobby.putGoat(userID, x, y);
    }
}

