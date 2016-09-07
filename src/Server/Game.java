package Server;

import Shared.GameInterface;

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
}

