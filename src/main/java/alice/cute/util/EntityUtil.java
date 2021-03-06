package alice.cute.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityWolf;

public class EntityUtil
{
	public static Minecraft mc = Minecraft.getMinecraft();
	
	public static List<Class<?>> getPassives() 
	{
        return new ArrayList(Arrays.asList(EntityPigZombie.class, EntitySquid.class, EntityIronGolem.class, EntityWolf.class, EntityEnderman.class, EntityChicken.class, EntityCow.class, EntitySheep.class, EntityRabbit.class, EntityPig.class, EntityBat.class, EntityHorse.class, EntitySnowman.class));
    }

    public static List<Class<?>> getHostiles() 
    {
        return new ArrayList(Arrays.asList(EntitySpider.class, EntitySkeleton.class, EntityZombie.class, EntityBlaze.class, EntityCreeper.class, EntityCaveSpider.class, EntityBlaze.class, EntityGhast.class, EntityWitch.class, EntitySlime.class, EntityWither.class));
    }

    public static List<Class<?>> getVehicles() 
    {

        return new ArrayList(Arrays.asList(EntityBoat.class, EntityMinecart.class));
    }

    public static boolean isPassive(Entity entity) 
    {
        if (entity instanceof EntityWolf && ((EntityWolf) entity).isAngry())
            return false;

        if (entity instanceof EntityAgeable || 
        	entity instanceof EntityAmbientCreature || 
        	entity instanceof EntitySquid)
            return true;

        return entity instanceof EntityIronGolem && ((EntityIronGolem) entity).isPlayerCreated();
    }

    public static boolean isVehicle(Entity entity) 
    {
        return entity instanceof EntityBoat || entity instanceof EntityMinecart;
    }

    public static boolean isHostileMob(Entity entity) 
    {
        return (entity.isCreatureType(EnumCreatureType.MONSTER, false) && !EntityUtil.isNeutralMob(entity)) || 
        		entity instanceof EntitySpider;
    }

    public static boolean isNeutralMob(Entity entity) 
    {
    	return  entity instanceof EntityPigZombie || 
    			entity instanceof EntityEnderman ||
    			entity instanceof EntityWolf || 
    			entity instanceof EntityIronGolem;
    }

    public static boolean isCurrentPlayer(Entity entity) 
    {
    	if(entity == null)
    		return false;
    	return entity.getName() == mc.thePlayer.getName();
    }
    
    public static boolean isLiving(Entity entity) 
    {
        return entity instanceof EntityLivingBase;
    }
}
