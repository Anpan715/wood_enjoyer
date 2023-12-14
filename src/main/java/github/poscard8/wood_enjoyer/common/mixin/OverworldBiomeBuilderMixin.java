package github.poscard8.wood_enjoyer.common.mixin;

import com.mojang.datafixers.util.Pair;
import github.poscard8.wood_enjoyer.common.worldgen.ModBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@SuppressWarnings("ALL")
@Mixin(OverworldBiomeBuilder.class)
public abstract class OverworldBiomeBuilderMixin {

    @Shadow
    abstract void addSurfaceBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, Climate.Parameter temperature,
                                  Climate.Parameter humidity, Climate.Parameter continentalness, Climate.Parameter erosion,
                                  Climate.Parameter weirdness, float offset, ResourceKey<Biome> biomeKey);

    @Inject(method = "addMidSlice", at = @At("HEAD"))
    private void addMixedForest(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, Climate.Parameter weirdness, CallbackInfo ci) {
        this.addSurfaceBiome(consumer, Climate.Parameter.span(-0.15F, 0.55F), Climate.Parameter.span(0, 1), Climate.Parameter.span(0.1F, 0.7F),
                Climate.Parameter.span(-0.8F, 1), weirdness, 0, ModBiomes.MIXED_FOREST);
    }


}
