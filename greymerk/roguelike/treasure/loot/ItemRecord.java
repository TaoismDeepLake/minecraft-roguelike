package greymerk.roguelike.treasure.loot;

import java.util.Random;

import net.minecraft.src.ItemStack;

public class ItemRecord extends ItemBase{

	public ItemRecord(int weight) {
		super(weight);
	}

	@Override
	public ItemStack getLootItem(Random rand, int level) {
		return Record.getRandomRecord(rand);
	}
	
	

}