package com.modcrafting.diablodrops.tier;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public class Tier
{
    private final int amt;
    private final int chance;
    private final ChatColor color;
    private final String displayName;
    private final float dropChance;
    private final List<Material> l;
    private final List<String> lore;
    private final int lvl;
    private final String name;

    public Tier(final String name, final ChatColor color, final int amt,
            final int lvl, final int chance, final List<Material> l,
            final List<String> lore, final String displayName,
            final float dropChance)
    {
        this.name = name;
        this.color = color;
        this.amt = amt;
        this.lvl = lvl;
        this.chance = chance;
        this.l = l;
        this.lore = lore;
        if (displayName != null)
            this.displayName = displayName;
        else
            this.displayName = name;
        this.dropChance = dropChance;
    }

    /**
     * Get the amount of the tier
     * 
     * @return amount
     */
    public Integer getAmount()
    {
        return amt;
    }

    /**
     * Get chance of the tier
     * 
     * @return Integer
     */
    public Integer getChance()
    {
        return chance;
    }

    /**
     * Get the color of the tier
     * 
     * @return color
     */
    public ChatColor getColor()
    {
        return color;
    }

    public String getDisplayName()
    {
        if (displayName != null)
            return displayName;
        return name;
    }

    public float getDropChance()
    {
        return dropChance;
    }

    /**
     * Get the levels of the tier
     * 
     * @return levels
     */
    public Integer getLevels()
    {
        return lvl;
    }

    /**
     * Get the lore of the tier
     * 
     * @return lore
     */
    public List<String> getLore()
    {
        return lore;
    }

    /**
     * Gets a list of acceptable materials
     * 
     * @return Material
     */
    public List<Material> getMaterials()
    {
        return l;
    }

    /**
     * Get the name of the tier
     * 
     * @return name
     */
    public String getName()
    {
        return name;
    }
}
