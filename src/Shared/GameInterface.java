package Shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

public interface GameInterface extends Remote {
    UUID register(String name) throws RemoteException;
    MatchStatus hasMatch(UUID userID) throws RemoteException;
}
