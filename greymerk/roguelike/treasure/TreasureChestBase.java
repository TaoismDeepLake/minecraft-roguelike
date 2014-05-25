package greymerk.roguelike.treasure;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.treasure.loot.CustomLoot;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.WeightedRandomLoot;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityChest;
import net.minecraft.src.World;

public abstract class TreasureChestBase implements ITreasureChest{

	protected World world;
	protected Random rand;
	protected int posX;
	protected int posY;
	protected int posZ;
	protected TileEntityChest chest;

	
	public TreasureChestBase(){
	}
		

	public ITreasureChest generate(World world, Random rand, int posX, int posY, int posZ, int level, boolean trapped) {
		this.world = world;
		this.rand = rand;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;

		int type = trapped ? Block.chestTrapped.blockID : Block.chest.blockID;
		
		
		if(!WorldGenPrimitive.setBlock(world, posX, posY, posZ, type)){
			return null;
		}
		
		chest = (TileEntityChest) world.getBlockTileEntity(posX, posY, posZ);
		
		try{
			
			int amount = RogueConfig.getBoolean(RogueConfig.GENEROUS) ? 16 : 12;
			
			for (int i = 0; i < amount; i++) {
								
				ItemStack item;
				
				item = Loot.getLoot(Loot.JUNK, rand, level);
				
				chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), item);
			}
			
			fillChest(chest, level);
			
		} catch(NullPointerException e){
			return null;
		}
		
		return this;
	}
	
	@Override
	public ITreasureChest generate(World world, Random rand, int posX, int posY, int posZ) {
		return generate(world, rand, posX, posY, posZ, Catacomb.getLevel(posY), false);
	}
	
	public boolean setInventorySlot(ItemStack item, int slot){
		try{
			chest.setInventorySlotContents(slot, item);
			return true;
		} catch(NullPointerException e){
			return false;
		}
	}
	
	public int getInventorySize(){
		
		if(chest == null){
			return 1;
		}
		
		try{
			return chest.getSizeInventory();
		} catch(NullPointerException e){
			return 1;
		}
	}
	
	protected abstract void fillChest(TileEntityChest chest, int level);
	

	

}