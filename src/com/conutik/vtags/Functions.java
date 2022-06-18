package com.conutik.vtags;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Functions {
    static boolean blockBetweenPlayers(Player p, Player ps) {
            Location loc = p.getEyeLocation().clone();
            Location loc1 = ps.getEyeLocation().clone();
            World world = loc.getWorld();
            double distance = loc.distance(loc1);
            Vector p1 = loc.toVector();
            Vector p2 = loc1.toVector();
            Vector vector = p2.clone().subtract(p1).normalize().multiply(1);
            double length = 0;
            for (; length < distance; p1.add(vector)) {
                Block b = world.getBlockAt(p1.getBlockX(), p1.getBlockY(), p1.getBlockZ());
                if (b.getType().isOccluding()) return true;
                length += 1;
            }
            return false;
    }

    static boolean sameWorld(Player p, Player ps) {
        return p.getWorld().getUID().equals(ps.getWorld().getUID());
    }
}
