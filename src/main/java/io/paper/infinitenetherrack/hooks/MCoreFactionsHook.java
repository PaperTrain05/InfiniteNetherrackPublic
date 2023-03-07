package io.paper.infinitenetherrack.hooks;

import com.massivecraft.factions.*;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class MCoreFactionsHook {
    public static Faction getFactionByLocation(Location location) {
        FLocation fLocation = new FLocation(location);
        return Board.getInstance().getFactionAt(fLocation);
    }

    public static boolean isWilderness(Location location) {
        return MCoreFactionsHook.getFactionByLocation(location).isWilderness();
    }

    public static boolean isSystemFaction(Location location) {
        if (MCoreFactionsHook.isWilderness(location)) {
            return false;
        }
        return MCoreFactionsHook.getFactionByLocation(location).isWarZone() || MCoreFactionsHook.getFactionByLocation(location).isSafeZone();
    }

    public static boolean isOwn(Location location, Player player) {
        Faction faction = MCoreFactionsHook.getFactionByLocation(location);
        FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);
        return faction.getFPlayers().contains(fPlayer);
    }
}
