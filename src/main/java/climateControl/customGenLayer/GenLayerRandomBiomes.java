package climateControl.customGenLayer;

import climateControl.api.BiomeRandomizer;

import climateControl.api.ClimateControlSettings;
import climateControl.genLayerPack.GenLayerPack;
import climateControl.utils.IntRandomizer;
import climateControl.utils.Zeno410Logger;
import java.util.logging.Logger;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
/**
 *
 * @author MasterCaver modified by Zeno410
 */
public class GenLayerRandomBiomes extends GenLayerPack {


    public static Logger logger = new Zeno410Logger("RandomBiomes").logger();

    private BiomeRandomizer biomeRandomizer;

    private IntRandomizer randomCallback;

    public GenLayerRandomBiomes(long par1, GenLayer par3GenLayer, ClimateControlSettings settings){
        super(par1);
        this.parent = par3GenLayer;

        biomeRandomizer = new BiomeRandomizer(settings.biomeSettings());
        randomCallback = new IntRandomizer() {
            public int nextInt(int maximum) {
                return GenLayerRandomBiomes.this.nextInt(maximum);
            }
        };
    }

    public int nextInt(int maximum) {
        return super.nextInt(maximum);
    }


    /**
     * Returns a list of integer values generated by this layer. These may be interpreted as temperatures, rainfall
     * amounts, or biomeList[] indices based on the particular GenLayer subclass.
     */
    public int[] getInts(int par1, int par2, int par3, int par4)
    {
        int[] var5 = this.parent.getInts(par1, par2, par3, par4);
        int[] var6 = IntCache.getIntCache(par3 * par4);

        for (int var7 = 0; var7 < par4; var7++)
        {
            for (int var8 = 0; var8 < par3; var8++)
            {
                this.initChunkSeed((long)(var8 + par1), (long)(var7 + par2));
                int var9 = var5[var8 + var7 * par3];
                int var10 = (var9 & 3840) >> 8;
                var9 &= -3841;

                // Random biome distribution
                if (isOceanic(var9)){
                    var6[var8 + var7 * par3] = var9;
                } else if (var9 == BiomeGenBase.mushroomIsland.biomeID){
                    var6[var8 + var7 * par3] = var9;
                }else if (var9 >= 1 && var9 <= 4) {
                    var6[var8 + var7 * par3] = biomeRandomizer.global.choose(this.randomCallback).biomeID;
                }else{
                    var6[var8 + var7 * par3] = BiomeGenBase.mushroomIsland.biomeID;
                }
            }
        }

        return var6;
    }
}