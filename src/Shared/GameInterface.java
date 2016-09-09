package Shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

public interface GameInterface extends Remote {
    UUID register(String name) throws RemoteException;
    MatchStatus hasMatch(UUID userID) throws RemoteException;
    boolean isMyTurn(UUID userID) throws RemoteException;
    void makePlay(UUID userID) throws  RemoteException;
}
