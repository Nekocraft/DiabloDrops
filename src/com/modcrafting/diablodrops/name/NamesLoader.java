package com.modcrafting.diablodrops.name;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;

import com.modcrafting.diablodrops.DiabloDrops;

public class NamesLoader
{
    File dataFolder;
    DiabloDrops plugin;

    public NamesLoader(DiabloDrops instance)
    {
        plugin = instance;
        dataFolder = instance.getDataFolder();
    }

    /**
     * Takes values from a file and adds them to list
     * 
     * @param l
     *            List of strings to add values
     * @param name
     *            Name of the file to take values from
     */
    public void loadFile(List<String> l, String name)
    {
        try
        {
            BufferedReader list = new BufferedReader(new FileReader(new File(
                    dataFolder, name)));
            String p;
            while ((p = list.readLine()) != null)
            {
                if (!p.contains("#") && p.length() > 0)
                {
                    l.add(p);
                }
            }
            list.close();
        }
        catch (Exception e)
        {
            if (plugin.debug)
                plugin.log.warning(e.getMessage());
        }
    }

    /**
     * Creates a file with given name
     * 
     * @param name
     *            Name of the file to write
     */
    public void writeDefault(String name)
    {
        File actual = new File(dataFolder, name);
        if (!actual.exists())
        {
            try
            {
                InputStream input = this.getClass().getResourceAsStream(
                        "/" + name);
                FileOutputStream output = new FileOutputStream(actual);
                byte[] buf = new byte[8192];
                int length = 0;
                while ((length = input.read(buf)) > 0)
                {
                    output.write(buf, 0, length);
                }
                output.close();
                input.close();
            }
            catch (Exception e)
            {
                if (plugin.debug)
                    plugin.log.warning(e.getMessage());
            }
        }
    }

    public void loadFile(HashMap<Material, List<String>> hm, File f)
    {
        Material m = Material.getMaterial(f.getName().replace(".txt", "")
                .toUpperCase());
        List<String> l = new ArrayList<String>();
        try
        {
            BufferedReader list = new BufferedReader(new FileReader(f));
            String p;
            while ((p = list.readLine()) != null)
            {
                if (!p.contains("#") && p.length() > 0)
                {
                    l.add(p);
                }
            }

            if (m != null)
            {
                hm.put(m, l);
            }
            else
            {
                hm.put(Material.AIR, l);
            }

            list.close();
        }
        catch (Exception e)
        {
            if (plugin.debug)
                plugin.log.warning(e.getMessage());
        }
    }
}
