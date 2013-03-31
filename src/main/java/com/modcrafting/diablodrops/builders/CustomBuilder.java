package com.modcrafting.diablodrops.builders;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.modcrafting.diablodrops.DiabloDrops;

public class CustomBuilder
{
    DiabloDrops plugin;

    public CustomBuilder(DiabloDrops instance)
    {
        plugin = instance;
    }

    /**
     * Clears and then populates the plugin's custom items list
     */
    public void build()
    {
        FileConfiguration fc = new YamlConfiguration();
        File f = new File(plugin.getDataFolder(), "custom.yml");
        if (f.exists())
        {
            try
            {
                fc.load(f);
            }
            catch (Exception e)
            {
                if (plugin.getDebug())
                    plugin.log.warning(e.getMessage());
            }
        }
        for (String name : fc.getKeys(false))
        {
            ConfigurationSection cs = fc.getConfigurationSection(name);
            Material mat = Material.matchMaterial(cs.getString("Material"));
            ChatColor color = ChatColor.valueOf(cs.getString("Color")
                    .toUpperCase());
            List<String> lore = cs.getStringList("Lore");
            ItemStack tool = new ItemStack(mat);
            List<String> list = new ArrayList<String>();
            for (String s : lore)
            {
                list.add(ChatColor.translateAlternateColorCodes(
                        "&".toCharArray()[0], s));
            }
            ConfigurationSection cs1 = cs
                    .getConfigurationSection("Enchantments");
            if (cs1 != null)
            {
                for (String ench : cs1.getKeys(false))
                {
                    Enchantment encha = Enchantment.getByName(ench
                            .toUpperCase());
                    if (encha == null)
                        continue;
                    tool.addUnsafeEnchantment(encha, cs1.getInt(ench));
                }
            }
            ItemMeta meta = tool.getItemMeta();
            meta.setDisplayName(color + name);
            meta.setLore(list);
            tool.setItemMeta(meta);
            plugin.custom.add(tool);
        }
    }
}
