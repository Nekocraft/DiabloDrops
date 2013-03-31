package com.modcrafting.diablodrops;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class Settings
{
    private final boolean colorBlindCompat;
    private final ChatColor[] colorList;
    private final double custom;
    private final double lore;
    private final double socket;
    private final double socketed;
    private final int minSockets;
    private final int maxSockets;
    private final double standard;
    private final double tome;

    public Settings(FileConfiguration fc)
    {
        socket = fc.getDouble("SocketItem.Chance", 5.0);
        socketed = fc.getDouble("SockettedItem.Chance", 20.0);
        minSockets = fc.getInt("SockettedItem.MinimumSockets", 1);
        maxSockets = fc.getInt("SockettedItem.MaximumSockets", 5);
        tome = fc.getDouble("IdentifyTome.Chance", 2.0);
        standard = fc.getDouble("Percentages.ChancePerSpawn", 2.0);
        lore = fc.getDouble("Lore.Chance", 2.0);
        custom = fc.getDouble("Custom.Chance", 2.0);
        colorBlindCompat = fc.getBoolean("Display.ColorBlind", false);
        colorList = setupSocketColors(fc);
    }

    public int getCustomChance()
    {
        return (int) (custom * 100);
    }

    public int getLoreChance()
    {
        return (int) (lore * 100);
    }

    /**
     * @return the maxSockets
     */
    public int getMaxSockets()
    {
        return maxSockets;
    }

    /**
     * @return the minSockets
     */
    public int getMinSockets()
    {
        return minSockets;
    }

    public int getSocketChance()
    {
        return (int) (socket * 100);
    }

    public ChatColor[] getSocketColors()
    {
        return colorList;
    }

    public int getSocketedChance()
    {
        return (int) (socketed * 100);
    }

    public int getStandardChance()
    {
        return (int) (standard * 100);
    }

    public int getTomeChance()
    {
        return (int) (tome * 100);
    }

    public boolean isColorBlindCompat()
    {
        return colorBlindCompat;
    }

    private ChatColor[] setupSocketColors(FileConfiguration fc)
    {
        List<String> colorStringList = fc.getStringList("SocketItem.Colors");
        if (colorStringList == null)
            colorStringList = Arrays.asList(new String[] { "GREEN", "BLUE",
                    "RED" });
        ChatColor[] colorList = new ChatColor[colorStringList.size()];
        for (int i = 0; i < colorStringList.size(); i++)
        {
            String string = colorStringList.get(i);
            ChatColor cc = null;
            try
            {
                cc = ChatColor.valueOf(string.toUpperCase());
            }
            catch (Exception e)
            {
                continue;
            }
            if (cc != null)
                colorList[i] = cc;
        }
        return colorList;
    }

}
