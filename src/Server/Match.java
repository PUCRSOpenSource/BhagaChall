package Server;

import Shared.MatchStatus;

import java.util.UUID;

public class Match {
    Player tiger;
    Player goat;
    Player currentPlayer;

    Board board = new Board();

    public boolean isOpen() {
        return tiger == null || goat == null;
    }

    public boolean isClosed() {
        return tiger != null && goat != null;
    }

    public MatchStatus playerStatus(UUID playerID) {
        if (tiger != null && tiger.check(playerID)) return MatchStatus.TIGER;
        if (goat != null && goat.check(playerID)) return MatchStatus.GOAT;
        return null;
    }

    public void add(Player player) {
        if (goat == null) {
            goat = player;
        } else {
            tiger = player;
            currentPlayer = tiger;
        }
    }

    public boolean hasPlayer(UUID userID) {
        return tiger.check(userID) || goat.check(userID);
    }

    public void play() {
        currentPlayer.play();
        currentPlayer = currentPlayer.equals(tiger) ? goat : tiger;
    }

    public boolean isTurn(UUID userID) {
        Player player = player(userID);
        return currentPlayer.equals(player);
    }

    private Player player(UUID userID) {
        if (tiger.check(userID)) return tiger;
        if (goat.check(userID)) return goat;
        return null;
    }

    public String getEncodedBoard() {
        return board.getEncodedBoard();
    }
}
