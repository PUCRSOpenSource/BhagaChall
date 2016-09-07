package Client;

import Shared.GameInterface;
import java.rmi.RemoteException;

public class GameTUI {

    GameInterface game;

    public GameTUI(GameInterface game) {
        this.game = game;
    }

    public void startGame() throws RemoteException {
        System.out.println(game.register("Daer"));
    }
}
