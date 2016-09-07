package Server;

import Shared.MatchStatus;

import java.util.UUID;

public class Match {
    Player tiger;
    Player goat;

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
        }
    }
}
