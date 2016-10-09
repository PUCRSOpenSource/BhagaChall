package Server;

import Shared.Direction;
import Shared.MatchStatus;
import Shared.TurnStatus;

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
            currentPlayer = goat;
        }
    }

    public boolean hasPlayer(UUID userID) {
        return tiger.check(userID) || goat.check(userID);
    }

    public TurnStatus isTurn(UUID userID) {
        Player player = player(userID);
        if (currentPlayer.equals(player)) {
            return TurnStatus.TRUE;
        }
        return TurnStatus.FALSE;
    }

    private Player player(UUID userID) {
        if (tiger.check(userID)) return tiger;
        if (goat.check(userID)) return goat;
        return null;
    }

    public String getEncodedBoard() {
        return board.getEncodedBoard();
    }

    public boolean hasGoatLeft() {
        return board.hasGoatLeft();
    }

    public boolean putGoat(UUID userID, int x, int y) {
        if (!isGoat(userID)) return false;
        if (board.isOccupied(x, y)) return false;
        board.putGoat(x, y);
        currentPlayer = tiger;
        return true;
    }


    private boolean isGoat(UUID userID) {
        Player player = player(userID);
        if (player != null) {
            return player.equals(goat);
        }
        return false;
    }

    private boolean isTiger(UUID userID) {
        Player player = player(userID);
        if (player != null) {
            return player.equals(tiger);
        }
        return false;
    }

    public boolean moveTiger(UUID userID, int tiger, Direction direction) {
        if (!isTiger(userID)) return false;
        boolean success = board.moveTiger(tiger, direction);
        if (success) {
            currentPlayer = goat;
        }
        return success;
    }

    public boolean moveGoat(UUID userID, String goat, Direction direction) {
        if (!isGoat(userID)) return false;
        boolean success = board.moveGoat(goat, direction);
        if (success) {
            currentPlayer = tiger;
        }
        return success;
    }
}
