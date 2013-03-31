package com.modcrafting.diablodrops.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.diablodrops.items.IdentifyTome;
import com.modcrafting.diablodrops.items.Socket;
import com.modcrafting.diablodrops.items.SockettedItem;
import com.modcrafting.diablodrops.sets.ArmorSet;
import com.modcrafting.diablodrops.tier.Tier;

public class DiabloDropCommand implements CommandExecutor{
    private DiabloDrops plugin;

    public DiabloDropCommand(final DiabloDrops plugin){
        this.plugin = plugin;
    }

    public String combineSplit(final int startIndex, final String[] string){
        StringBuilder builder = new StringBuilder();
        if(string.length >= 1){
        	for (int i = startIndex; i < string.length; i++){
                builder.append(string[i]);
                builder.append(" ");
            }
            if (builder.length() > 1){
                builder.deleteCharAt(builder.length() - 1);
                return builder.toString();
            }
        }
        return "";
    }
    
    @Override
    public boolean onCommand(final CommandSender sender, final Command command,
            final String commandLabel, final String[] args){
        if (!sender.hasPermission(command.getPermission())||!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "你不能运行这个指令.");
            return true;
        }
        Player player = (Player) sender;
        if(args.length<1){
            ItemStack ci = plugin.getDropAPI().getItem();
            while (ci == null)
                ci = plugin.getDropAPI().getItem();
            player.getInventory().addItem(ci);
            player.sendMessage(ChatColor.GREEN + "你已经获得一个 更多掉落 生成的物品.");
            return true;
        }
        if(args[0].equalsIgnoreCase("tome")||args[0].equalsIgnoreCase("book")||args[0].equalsIgnoreCase("鉴定卷轴")){
        	player.getInventory().addItem(new IdentifyTome());
        	player.sendMessage(ChatColor.GREEN + "你已获得一个鉴定卷轴");
        }else if (args[0].equalsIgnoreCase("socket")||args[0].equalsIgnoreCase("socketitem")||args[0].equalsIgnoreCase("镶嵌物")){
        	List<String> l = plugin.getConfig().getStringList("SocketItem.Items");
        	player.getInventory().addItem(
        			new Socket(Material.valueOf(l.get(
        					plugin.getSingleRandom().nextInt(
        							l.size())).toUpperCase())));
        	player.sendMessage(ChatColor.GREEN + "你已获得一个镶嵌增强.");       
        }else if (args[0].equalsIgnoreCase("socketted")||args[0].equalsIgnoreCase("socketteditem")||args[0].equalsIgnoreCase("镶嵌物品")){
	    	 player.getInventory().addItem(
	    			 new SockettedItem(plugin.getDropAPI().dropPicker()));
	    	 player.sendMessage(ChatColor.GREEN + "你已获得一个镶嵌过的物品.");
	     }else if (args[0].equalsIgnoreCase("repair")||args[0].equalsIgnoreCase("修复")){
	    	 if (plugin.getDropAPI().canBeItem(player.getItemInHand().getType())){
	    		 player.getItemInHand().setDurability((short) 0);
	    		 player.sendMessage(ChatColor.GREEN+"物品已修复.");
	    		 return true;
	    	 }
	     }else if (args[0].equalsIgnoreCase("reload")&&sender.hasPermission("diablodrops.reload")){
	    	 plugin.getServer().getPluginManager().disablePlugin(plugin);
	    	 plugin.getServer().getPluginManager().enablePlugin(plugin);
	    	 plugin.reloadConfig();
	    	 plugin.getLogger().info("Reloaded");
	    	 sender.sendMessage(ChatColor.GREEN + "更多掉落 重新载入完毕");
	     }else if (args[0].equalsIgnoreCase("debug")||args[0].equalsIgnoreCase("调试")){
	    	 int i = plugin.custom.size();
	    	 plugin.getLogger().info(i + "]: 自定义物品已载入.");
	    	 i = plugin.armorSets.size();
	    	 plugin.getLogger().info(i + "]: 护甲套装已载入.");
	    	 i = plugin.tiers.size();
	    	 plugin.getLogger().info(i + "]: 物品级别已载入.");
	    	 i = plugin.prefix.size();
	    	 plugin.getLogger().info(i + "]: 默认前缀已载入.");
	    	 i = plugin.suffix.size();
	    	 plugin.getLogger().info(i + "]: 默认后缀已载入.");
	    	 i = plugin.hmprefix.size();
	    	 plugin.getLogger().info(i + "]: 自定义前缀已载入.");
	    	 i = plugin.hmsuffix.size();
	    	 plugin.getLogger().info(i + "]: 自定义后缀已载入.");
	    	 i = plugin.defenselore.size();
	    	 plugin.getLogger().info(i + "]: 防御效果已载入.");
	    	 i = plugin.offenselore.size();
	    	 plugin.getLogger().info(i + "]: 进攻效果已载入.");
	    	 i = plugin.worlds.size();
	    	 plugin.getLogger().info(i + "]: 世界白名单已载入.");
	    	 if (args.length > 1&&(args[1].equalsIgnoreCase("detailed")||args[1].equalsIgnoreCase("详细"))){
	    		 StringBuilder sb = new StringBuilder();
	    		 sb.append("\n-----自定义物品-----\n");
	    		 for (ItemStack tool : plugin.custom)
	    			 sb.append(tool.getItemMeta().getDisplayName()+ " ");
	    		 sb.append("\n-----自定义物品-----\n");
                 for (ArmorSet a : plugin.armorSets)
                	 sb.append(a.getName() + " ");
                 sb.append("\n-----物品级别-----\n");
                 for (Tier a : plugin.tiers)
                	 sb.append(a.getName() + " ");
                 sb.append("\n-----默认前缀-----\n");
                 for (String s : plugin.prefix)
                	 sb.append(s + " ");
                 sb.append("\n-----默认后缀-----\n");
                 for (String s : plugin.suffix)
                     sb.append(s + " ");
                 sb.append("\n-----自定义前缀-----\n");
                 for (Material m : plugin.hmprefix.keySet()){
                	 sb.append(m.toString() + "\n");
                	 for (String p : plugin.hmprefix.get(m))
                		 sb.append(p + " ");
                 }
                 sb.append("\n-----自定义后缀-----\n");
                 for (Material m : plugin.hmsuffix.keySet()){
                	 sb.append(m.toString() + "\n");
                	 for (String p : plugin.hmsuffix.get(m))
                		 sb.append(p + " ");
                 }
                 sb.append("\n-----防御效果-----\n");
                 for (String s : plugin.defenselore)
                	 sb.append(s + " ");
                 sb.append("\n-----进攻效果-----\n");
                 for (String s : plugin.offenselore)
                	 sb.append(s + " ");
                 plugin.getLogger().info(sb.toString());
            }
        }else if (args[0].equalsIgnoreCase("custom")||args[0].equalsIgnoreCase("自定义")){
        	if(plugin.custom.size()<1)
        		return true;
        	ItemStack customItem = null;
        	if(args.length>1){
                for (ItemStack is : plugin.custom){
                	String itemname = ChatColor.stripColor(plugin.getItemAPI().getName(is));
                	String titemname = combineSplit(1,args);
                    if (itemname.equalsIgnoreCase(titemname)){
                        customItem = is;
                        break;
                    }
                }
        	}
        	if (customItem == null)
        		customItem = plugin.custom.get(plugin.getSingleRandom().nextInt(plugin.custom.size()));
        	if (customItem != null){
        		player.getInventory().addItem(customItem);
        		player.sendMessage(ChatColor.GREEN+"你已经获得"
        				+ChatColor.stripColor(plugin.getItemAPI().getName(customItem))+"物品.");
        	}
        }else if (args[0].equalsIgnoreCase("give")||args[0].equalsIgnoreCase("给予")){
        	if (args.length < 2){
        		sender.sendMessage(ChatColor.RED + "参数不足.");
        		return true;
        	}
        	List<String> stringList = new ArrayList<String>();
        	for (String s : args){
        		if (s.equals(args[0]))
        			continue;
        		Player p = plugin.getServer().getPlayer(s);
        		ItemStack giveItem = plugin.getDropAPI().getItem();
        		while (giveItem == null)
        			giveItem = plugin.getDropAPI().getItem();
        		stringList.add(p.getName());
        		p.getInventory().addItem(giveItem);
        		p.sendMessage(ChatColor.GREEN + "你已经获得一个 更多掉落 生成的物品.");
        	}
        	sender.sendMessage(ChatColor.GREEN
        			+ "You gave items to "+ ChatColor.WHITE + 
        			stringList.toString().replace("[", "").replace("]", "") + ChatColor.GREEN + ".");
                    return true;
        }else if (args[0].equalsIgnoreCase("modify")||args[1].equalsIgnoreCase("修改")){
        	if ((args.length < 2)|| (player.getItemInHand() == null)
        			|| player.getItemInHand().getType().equals(Material.AIR)){
        		sender.sendMessage(ChatColor.RED + "参数不足.");
        		return true;
        	}
        	ItemStack tool = player.getItemInHand();
        	ItemMeta meta = tool.getItemMeta();
        	if (args[1].equalsIgnoreCase("lore")||args[1].equalsIgnoreCase("效果")){
        		if (args[2].equalsIgnoreCase("clear")||args[2].equalsIgnoreCase("清除")){
        			meta.setLore(null);
        			tool.setItemMeta(meta);
        			player.sendMessage(ChatColor.GREEN+ "清除了这个物品的效果!");
        			return true;
        		}
        		String lore = combineSplit(2, args);
        		lore = ChatColor.translateAlternateColorCodes("&".toCharArray()[0], lore);
        		meta.setLore(Arrays.asList(lore.split(",")));
        		tool.setItemMeta(meta);
        		player.sendMessage(ChatColor.GREEN+ "为此物品设置了效果!");
        		return true;
        	}else if (args[1].equalsIgnoreCase("name")||args[1].equalsIgnoreCase("名称")||args[1].equalsIgnoreCase("名字")){
        		if (args[2].equalsIgnoreCase("clear")||args[2].equalsIgnoreCase("清除")){
        			tool.getItemMeta().setDisplayName(null);
        			player.sendMessage(ChatColor.GREEN+ "清除了这个物品的名称!");
        			return true;
        		}
        		String name = combineSplit(2, args);
        		name = ChatColor.translateAlternateColorCodes("&".toCharArray()[0], name);
        		meta.setDisplayName(name);
        		tool.setItemMeta(meta);
        		player.sendMessage(ChatColor.GREEN+ "为此物品设置了名称!");
        		return true;
        	}else if (args[1].equalsIgnoreCase("enchant")||args[1].equalsIgnoreCase("附魔")){
        		if (args.length < 3){
            		sender.sendMessage(ChatColor.RED + "没有足够的参数.");
            		return true;
            	}
        		if (args[2].equalsIgnoreCase("clear")||args[2].equalsIgnoreCase("清除")){
        			for (Enchantment e : Enchantment.values())
        				tool.getItemMeta().removeEnchant(e);
        			player.sendMessage(ChatColor.GREEN+ "清除了这个物品的附魔效果!");
        			return true;
        		}else if (args.length > 4 && (args[2].equalsIgnoreCase("add")||args[2].equalsIgnoreCase("增加")||args[2].equalsIgnoreCase("添加")) ){
        			int i = 1;
        			try{
        				i = Integer.parseInt(args[4]);
        			}catch (NumberFormatException nfe){}
        			Enchantment ech = Enchantment.getByName(args[3].toUpperCase());
        			if (ech != null){
            			try{
            				int l = Integer.parseInt(args[3]);
            				ech = Enchantment.getById(l);
            			}catch (NumberFormatException nfe){}
        			}
        			if (ech != null){
        				tool.addUnsafeEnchantment(ech, i);
        				player.sendMessage(ChatColor.GREEN+"已添加附魔效果.");
                    }else{
                    	player.sendMessage(ChatColor.RED+ args[3]+" :这种附魔效果并不存在!");
                    }
        			return true;
        		}else if (args[2].equalsIgnoreCase("remove")||args[2].equalsIgnoreCase("移除")||args[2].equalsIgnoreCase("删除")){
        			if(args.length > 3){
        				Map<Enchantment, Integer> hm = new HashMap<Enchantment, Integer>();
        				for (Enchantment e1 : tool.getEnchantments().keySet())
        					if (!e1.getName().equalsIgnoreCase(args[3]))
        						hm.put(e1, tool.getEnchantmentLevel(e1));
        				tool.addUnsafeEnchantments(hm);
        				player.sendMessage(ChatColor.GREEN+"移除附魔效果.");
        				return true;
                    }

    				for (Enchantment e : Enchantment.values())
    					tool.getItemMeta().removeEnchant(e);
    				player.sendMessage(ChatColor.GREEN+"移除附魔效果.");
                    return true;
        		}
        	}else if (args[0].equalsIgnoreCase("tier")){
        		//
                    String name = "";
                    Player p = null;
                    for (String s : args)
                    {
                        if (StringUtils.containsIgnoreCase(s, "p:"))
                        {
                            s = s.replace("p:", "");
                            p = Bukkit.getPlayer(s);
                            continue;
                        }
                        if (!s.equals(args[0]))
                            if (name.equals(""))
                                name = s;
                            else
                                name = name + " " + s;
                    }
                    Tier tier = plugin.getDropAPI().getTier(name);
                    ItemStack customItem = plugin.getDropAPI().getItem(tier);
                    if (customItem != null && p != null)
                    {
                        p.getInventory().addItem(customItem);
                        p.sendMessage(ChatColor.GREEN
                                + "你已经获得一个 更多掉落 生成的物品.");
                        sender.sendMessage(ChatColor.WHITE + p.getName()
                                + ChatColor.GREEN
                                + " has been given a DiabloDrops item.");
                    }
                    else if (customItem != null && p == null
                            && sender instanceof Player)
                    {
                        player.getInventory().addItem(customItem);
                        player.sendMessage(ChatColor.GREEN
                                + "你已经获得一个 更多掉落 生成的物品.");
                    }
                    else
                    {
                        sender.sendMessage(ChatColor.RED
                                + "那不是一个物品级别或者你暂时不能使用这个指令.");
                    }
                    return true;
                }
                if (!(sender instanceof Player))
                {
                    sender.sendMessage("你不能运行这个指令.");
                    return true;
                }
                else
                {
                    ItemStack ci2 = plugin.getDropAPI().getItem();
                    while (ci2 == null)
                    {
                        ci2 = plugin.getDropAPI().getItem();
                    }
                    player.getInventory().addItem(ci2);
                    player.sendMessage(ChatColor.GREEN
                            + "你已经获得一个 更多掉落 生成的物品.");
                    return true;
                }
        }
		return true;
    }
}
