package Shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

public interface GameInterface extends Remote {
    UUID register(String name) throws RemoteException;

    MatchStatus hasMatch(UUID userID) throws RemoteException;

    boolean isMyTurn(UUID userID) throws RemoteException;

    String getBoard(UUID userID) throws RemoteException;

    boolean hasGoatLeft(UUID userID) throws RemoteException;

    boolean putGoat(UUID userID, int x, int y) throws RemoteException;

    boolean moveTiger(UUID userID, int tiger, Direction direction) throws RemoteException;

    boolean moveGoat(UUID userID, String goat, Direction direction) throws RemoteException;
}
