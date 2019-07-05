package me.badbones69.crazyenchantments.api.objects;

import me.badbones69.crazyenchantments.Methods;
import me.badbones69.crazyenchantments.api.CrazyEnchantments;
import me.badbones69.crazyenchantments.api.FileManager.Files;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CEBook {
	
	private CEnchantment enchantment;
	private int amount;
	private int power;
	private boolean glowing;
	private int destroy_rate;
	private int success_rate;
	private CrazyEnchantments ce = CrazyEnchantments.getInstance();
	
	/**
	 *
	 * @param enchantment Enchantment you want.
	 */
	public CEBook(CEnchantment enchantment) {
		this(enchantment, 1, 1);
	}
	
	/**
	 *
	 * @param enchantment Enchantment you want.
	 * @param power Tier of the enchantment.
	 */
	public CEBook(CEnchantment enchantment, Integer power) {
		this(enchantment, power, 1);
	}
	
	/**
	 *
	 * @param enchantment Enchantment you want.
	 * @param power Tier of the enchantment.
	 * @param amount Amount of books you want.
	 */
	public CEBook(CEnchantment enchantment, Integer power, Integer amount) {
		this.enchantment = enchantment;
		this.amount = amount;
		this.power = power;
		this.glowing = Files.CONFIG.getFile().getBoolean("Settings.Enchantment-Book-Glowing");
		int Smax = Files.CONFIG.getFile().getInt("Settings.BlackScroll.SuccessChance.Max");
		int Smin = Files.CONFIG.getFile().getInt("Settings.BlackScroll.SuccessChance.Min");
		int Dmax = Files.CONFIG.getFile().getInt("Settings.BlackScroll.DestroyChance.Max");
		int Dmin = Files.CONFIG.getFile().getInt("Settings.BlackScroll.DestroyChance.Min");
		this.destroy_rate = percentPick(Dmax, Dmin);
		this.success_rate = percentPick(Smax, Smin);
	}
	
	/**
	 *
	 * @param enchantment Enchantment you want.
	 * @param power Tier of the enchantment.
	 * @param amount Amount of books you want.
	 * @param destroy_rate The rate of the destroy rate.
	 * @param success_rate The rate of the success rate.
	 */
	public CEBook(CEnchantment enchantment, Integer power, Integer amount, Integer destroy_rate, Integer success_rate) {
		this.enchantment = enchantment;
		this.amount = amount;
		this.power = power;
		this.glowing = Files.CONFIG.getFile().getBoolean("Settings.Enchantment-Book-Glowing");
		this.destroy_rate = destroy_rate;
		this.success_rate = success_rate;
	}
	
	/**
	 *
	 * @param enchantment Set the enchantment.
	 */
	public void setEnchantment(CEnchantment enchantment) {
		this.enchantment = enchantment;
	}
	
	/**
	 * Get the CEEnchantment.
	 * @return The CEEnchantment.
	 */
	public CEnchantment getEnchantment() {
		return this.enchantment;
	}
	
	/**
	 *
	 * @param toggle Toggle on or off the glowing effect.
	 */
	public void setGlowing(Boolean toggle) {
		this.glowing = toggle;
	}
	
	/**
	 * If the item will be glowing or not.
	 * @return Ture if glowing and false if not.
	 */
	public Boolean getGlowing() {
		return this.glowing;
	}
	
	/**
	 *
	 * @param amount Set the amount of books.
	 */
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	/**
	 * Get the amount of the item.
	 * @return The amount that it will be as an ItemStack.
	 */
	public int getAmount() {
		return this.amount;
	}
	
	/**
	 *
	 * @param power Set the tier of the enchantment.
	 */
	public void setPower(Integer power) {
		this.power = power;
	}
	
	/**
	 * Get the power of the book.
	 * @return The power of the book.
	 */
	public int getPower() {
		return this.power;
	}
	
	/**
	 *
	 * @param destroy_rate Set the destroy rate on the book.
	 */
	public void setDestroyRate(Integer destroy_rate) {
		this.destroy_rate = destroy_rate;
	}
	
	/**
	 * Get the destroy rate on the book.
	 * @return Destroy rate of the book.
	 */
	public int getDestroyRate() {
		return this.destroy_rate;
	}
	
	/**
	 *
	 * @param success_rate Set the success rate on the book.
	 */
	public void setSuccessRate(Integer success_rate) {
		this.success_rate = success_rate;
	}
	
	/**
	 * Get the success rate on the book.
	 * @return The success rate of the book.
	 */
	public int getSuccessRate() {
		return this.success_rate;
	}
	
	/**
	 *
	 * @return Return the book as an ItemStack.
	 */
	public ItemStack buildBook() {
		String item = Files.CONFIG.getFile().getString("Settings.Enchantment-Book-Item");
		String name = enchantment.getBookColor() + enchantment.getCustomName() + " " + ce.convertLevelString(power);
		List<String> lore = new ArrayList<>();
		for(String bookLine : Files.CONFIG.getFile().getStringList("Settings.EnchantmentBookLore")) {
			if(bookLine.contains("%Description%") || bookLine.contains("%description%")) {
				for(String enchantmentLine : enchantment.getInfoDescription()) {
					lore.add(Methods.color(enchantmentLine));
				}
			}else {
				lore.add(Methods.color(bookLine)
				.replaceAll("%Destroy_Rate%", destroy_rate + "").replaceAll("%destroy_rate%", destroy_rate + "")
				.replaceAll("%Success_Rate%", success_rate + "").replaceAll("%success_rate%", success_rate + ""));
			}
		}
		return new ItemBuilder().setMaterial(item).setAmount(amount).setName(name).setLore(lore).setGlowing(glowing).build();
	}
	
	private Integer percentPick(int max, int min) {
		Random i = new Random();
		if(max == min) {
			return max;
		}else {
			return min + i.nextInt(max - min);
		}
	}
	
}