package github.poscard8.wood_enjoyer.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Axis;
import github.poscard8.wood_enjoyer.WoodEnjoyer;
import github.poscard8.wood_enjoyer.client.ModModelLayers;
import github.poscard8.wood_enjoyer.client.model.ModdedBoatModel;
import github.poscard8.wood_enjoyer.client.model.ModdedChestBoatModel;
import github.poscard8.wood_enjoyer.common.entity.ModdedBoat;
import github.poscard8.wood_enjoyer.common.entity.ModdedChestBoat;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.WaterPatchModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Quaternionf;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ModdedBoatRenderer extends BoatRenderer {

    private final Map<Pair<ModdedBoat.Type, Boolean>, Pair<ResourceLocation, ListModel<ModdedBoat>>> boatResources;

    public ModdedBoatRenderer(EntityRendererProvider.Context ctx, boolean hasChest) {
        super(ctx, hasChest);
        this.boatResources = Map.of(
                Pair.of(ModdedBoat.Type.WALNUT, false), Pair.of(getTextureLocation(ModdedBoat.Type.WALNUT), this.createBoatModel(ctx, false)),
                Pair.of(ModdedBoat.Type.WALNUT, true), Pair.of(getTextureLocation(ModdedBoat.Type.WALNUT), this.createBoatModel(ctx, true)),
                Pair.of(ModdedBoat.Type.CHESTNUT, false), Pair.of(getTextureLocation(ModdedBoat.Type.CHESTNUT), this.createBoatModel(ctx, false)),
                Pair.of(ModdedBoat.Type.CHESTNUT, true), Pair.of(getTextureLocation(ModdedBoat.Type.CHESTNUT), this.createBoatModel(ctx, true))
        );
    }

    private static ResourceLocation getTextureLocation(ModdedBoat.Type type) {
        return new ResourceLocation(WoodEnjoyer.ID, "textures/entity/boat/" + type + ".png");
    }

    private ListModel<ModdedBoat> createBoatModel(EntityRendererProvider.Context ctx, boolean hasChest) {
        ModelPart modelpart = hasChest ? ctx.bakeLayer(ModModelLayers.MODDED_CHEST_BOAT) : ctx.bakeLayer(ModModelLayers.MODDED_BOAT);
        return hasChest ? new ModdedChestBoatModel<>(modelpart) : new ModdedBoatModel<>(modelpart);
    }

    public void render(Boat boat, float p_113930_, float tilt, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.0F, 0.375F, 0.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - p_113930_));
        float f = (float) boat.getHurtTime() - tilt;
        float f1 = boat.getDamage() - tilt;

        if (f1 < 0.0F) {
            f1 = 0.0F;
        }
        if (f > 0.0F) {
            poseStack.mulPose(Axis.XP.rotationDegrees(Mth.sin(f) * f * f1 / 10.0F * (float) boat.getHurtDir()));
        }

        float f2 = boat.getBubbleAngle(tilt);
        if (!Mth.equal(f2, 0.0F)) {
            poseStack.mulPose((new Quaternionf()).setAngleAxis(boat.getBubbleAngle(tilt) * ((float) Math.PI / 180F), 1.0F, 0.0F, 1.0F));
        }

        Pair<ResourceLocation, ListModel<ModdedBoat>> pair = this.getModelWithLocation((ModdedBoat) boat);
        ResourceLocation resourcelocation = pair.getFirst();
        ListModel<ModdedBoat> boatModel = pair.getSecond();
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
        boatModel.setupAnim((ModdedBoat) boat, tilt, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer vertexconsumer = bufferSource.getBuffer(boatModel.renderType(resourcelocation));
        boatModel.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        if (!boat.isUnderWater()) {
            VertexConsumer vertexconsumer1 = bufferSource.getBuffer(RenderType.waterMask());
            if (boatModel instanceof WaterPatchModel waterpatchmodel) {
                waterpatchmodel.waterPatch().render(poseStack, vertexconsumer1, packedLight, OverlayTexture.NO_OVERLAY);
            }
        }

        poseStack.popPose();
    }

    public Pair<ResourceLocation, ListModel<ModdedBoat>> getModelWithLocation(ModdedBoat boat) {
        return this.boatResources.get(Pair.of(boat.getModdedVariant(), boat instanceof ModdedChestBoat));
    }


}
