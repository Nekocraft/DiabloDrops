package com.modcrafting.diablodrops.items;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.modcrafting.diablodrops.DiabloDrops;

public class Socket extends Drop
{
    public enum SkullType
    {
        CREEPER(4), PLAYER(3), SKELETON(0), WITHER(1), ZOMBIE(2);
        public int type;

        private SkullType(final int i)
        {
            type = i;
        }

        public short getData()
        {
            return (short) type;
        }

    }

    private static ChatColor color()
    {
        return DiabloDrops.getInstance().getDropAPI().colorPicker();
    }

    public Socket(final Material mat)
    {
        super(mat, color(), "镶嵌物", ChatColor.GOLD
                + "将其放置于火炉底部", ChatColor.GOLD
                + "并将可镶嵌的物品放置于顶部", ChatColor.GOLD
                + "以此添加附魔效果.");
        SkullType type = null;
        int numType = DiabloDrops.getInstance().getSingleRandom()
                .nextInt(SkullType.values().length);
        for (SkullType skullType : SkullType.values())
        {
            if (skullType.getData() == numType)
            {
                type = skullType;
                break;
            }
        }
        if (type == null)
        {
            switch (DiabloDrops.getInstance().getSingleRandom().nextInt(5))
            {
                case 1:
                    type = SkullType.WITHER;
                    break;
                case 2:
                    type = SkullType.ZOMBIE;
                    break;
                case 3:
                    type = SkullType.PLAYER;
                    break;
                case 4:
                    type = SkullType.CREEPER;
                    break;
                default:
                    type = SkullType.SKELETON;
                    break;
            }
        }
        this.setDurability(type.getData());
        ItemMeta meta;
        if (hasItemMeta())
            meta = getItemMeta();
        else
            meta = Bukkit.getItemFactory().getItemMeta(mat);
        if (mat.equals(Material.SKULL_ITEM))
        {
            SkullMeta sk = (SkullMeta) meta;
            if (type.equals(SkullType.PLAYER))
            {
                if (Bukkit.getServer().getOfflinePlayers().length > 0)
                {
                    sk.setOwner(Bukkit.getServer().getOfflinePlayers()[DiabloDrops
                            .getInstance()
                            .getSingleRandom()
                            .nextInt(
                                    Bukkit.getServer().getOfflinePlayers().length)]
                            .getName());
                }
                else
                {
                    if (DiabloDrops.getInstance().getSingleRandom()
                            .nextBoolean())
                    {
                        sk.setOwner("deathmarin");
                    }
                    else
                    {
                        sk.setOwner("ToppleTheNun");
                    }
                }
            }
        }
        setItemMeta(meta);
    }
}