package greymerk.roguelike.dungeon.rooms;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class DungeonsSlime extends DungeonBase {
	World world;
	Random rand;
	int originX;
	int originY;
	int originZ;

	IBlockFactory fillBlocks;
	MetaBlock liquid;
	
	public DungeonsSlime() {
	}

	public boolean generate(World inWorld, Random inRandom, LevelSettings settings, Cardinal[] entrances, Coord origin) {

		ITheme theme = settings.getTheme();
		
		world = inWorld;
		rand = inRandom;
		originX = origin.getX();
		originY = origin.getY();
		originZ = origin.getZ();

		liquid = new MetaBlock(Dungeon.getLevel(originY) == 4 ? Blocks.lava : Blocks.flowing_water);

		fillBlocks = theme.getPrimaryWall();
		
		MetaBlock air = new MetaBlock(Blocks.air);
		
		// fill air
		WorldGenPrimitive.fillRectSolid(world, rand, originX - 6, originY, originZ - 6, originX + 6, originY + 3, originZ + 6, air);
		
		// shell
		WorldGenPrimitive.fillRectHollow(world, rand, originX - 7, originY - 2, originZ - 7, originX + 7, originY + 4, originZ + 7, fillBlocks, false, true);
		
		// floor
		WorldGenPrimitive.fillRectSolid(world, rand, originX - 7, originY - 2, originZ - 7, originX + 7, originY - 1, originZ + 7, fillBlocks);
		
		pool(originX - 4, originZ - 4);
		pool(originX - 4, originZ + 4);
		pool(originX + 4, originZ - 4);
		pool(originX + 4, originZ + 4);
		
		// centre top lip
		WorldGenPrimitive.fillRectSolid(world, rand, originX - 1, originY + 3, originZ - 2, originX + 1, originY + 3, originZ - 2, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, rand, originX - 1, originY + 3, originZ + 2, originX + 1, originY + 3, originZ + 2, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, rand, originX - 2, originY + 3, originZ - 1, originX - 2, originY + 3, originZ + 1, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, rand, originX + 2, originY + 3, originZ - 1, originX + 2, originY + 3, originZ + 1, fillBlocks);
		
		if(Dungeon.getLevel(originY) == 4){
			Spawner.generate(world, rand, settings, new Coord(originX, originY + 5, originZ), Spawner.LAVASLIME);
		} else {
			WorldGenPrimitive.randomVines(world, rand, originX - 7, originY + 2, originZ - 7, originX + 7, originY + 5, originZ + 7);			
		}
		
		return true;
	}
	
	private void pool(int inX, int inZ){
		// water pool
		WorldGenPrimitive.fillRectSolid(world, rand, inX - 1, originY - 1, inZ - 1, inX + 1, originY - 1, inZ + 1, liquid);
		
		// pillars
		WorldGenPrimitive.fillRectSolid(world, rand, inX - 2, originY - 1, inZ - 2, inX - 2, originY + 3, inZ - 2, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, rand, inX - 2, originY - 1, inZ + 2, inX - 2, originY + 3, inZ + 2, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, rand, inX + 2, originY - 1, inZ - 2, inX + 2, originY + 3, inZ - 2, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, rand, inX + 2, originY - 1, inZ + 2, inX + 2, originY + 3, inZ + 2, fillBlocks);
		
		// base lip
		WorldGenPrimitive.fillRectSolid(world, rand, inX - 1, originY - 1, inZ - 2, inX + 1, originY - 1, inZ - 2, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, rand, inX - 1, originY - 1, inZ + 2, inX + 1, originY - 1, inZ + 2, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, rand, inX - 2, originY - 1, inZ - 1, inX - 2, originY - 1, inZ + 1, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, rand, inX + 2, originY - 1, inZ - 1, inX + 2, originY - 1, inZ + 1, fillBlocks);
		
		// top lip
		WorldGenPrimitive.fillRectSolid(world, rand, inX - 1, originY + 3, inZ - 2, inX + 1, originY + 3, inZ - 2, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, rand, inX - 1, originY + 3, inZ + 2, inX + 1, originY + 3, inZ + 2, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, rand, inX - 2, originY + 3, inZ - 1, inX - 2, originY + 3, inZ + 1, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, rand, inX + 2, originY + 3, inZ - 1, inX + 2, originY + 3, inZ + 1, fillBlocks);

		// carve ceiling air
		WorldGenPrimitive.fillRectSolid(world, rand, inX - 1, originY + 1, inZ - 1, inX + 1, originY + 6, inZ + 1, new MetaBlock(Blocks.air));
		
		// roof
		WorldGenPrimitive.fillRectSolid(world, rand, inX - 2, originY + 7, inZ - 2, inX + 2, originY + 8, inZ + 2, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, rand, inX - 1, originY + 8, inZ - 1, inX + 1, originY + 8, inZ + 1, liquid);
		
		
	}
	
	public boolean isValidDungeonLocation(World world, int originX, int originY, int originZ) {
		return false;
	}
	
	public int getSize(){
		return 8;
	}
}
