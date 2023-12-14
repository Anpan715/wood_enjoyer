package github.poscard8.wood_enjoyer.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import github.poscard8.wood_enjoyer.common.blockentity.StumpBlockEntity;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class StumpRenderer implements BlockEntityRenderer<StumpBlockEntity> {

    private final ItemRenderer itemRenderer;

    public StumpRenderer(BlockEntityRendererProvider.Context ctx) {
        this.itemRenderer = ctx.getItemRenderer();
    }

    @Override
    public void render(StumpBlockEntity stump, float p_112308_, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {

        int light = LevelRenderer.getLightColor(Objects.requireNonNull(stump.getLevel()), stump.getBlockState(), stump.getBlockPos());
        ItemStack stack = stump.getItem();

        switch (stump.getLogCount()) {
            default -> {
            }
            case 1 -> {

                poseInit(poseStack, stump);

                itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, light, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, stump.getLevel(), 0);
                poseStack.popPose();
            }
            case 2 -> {

                poseInit(poseStack, stump);

                poseStack.translate(-0.125F, 0, 0.2F);
                itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, light, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, stump.getLevel(), 1);

                poseStack.mulPose(Axis.YP.rotationDegrees(180));
                poseStack.translate(-0.25F, 0, 0.4F);
                itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, light, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, stump.getLevel(), 2);

                poseStack.mulPose(Axis.YP.rotationDegrees(90));
                poseStack.translate(0, 0, 0.25F);
                itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, light, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, stump.getLevel(), 3);

                poseStack.mulPose(Axis.YP.rotationDegrees(180));
                poseStack.translate(-0.4F, 0, 0.25F);
                itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, light, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, stump.getLevel(), 4);

                poseStack.popPose();
            }
            case 3 -> {

                poseInit(poseStack, stump);

                poseStack.translate(-0.125F, 0, 0.2F);
                itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, light, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, stump.getLevel(), 5);

                poseStack.mulPose(Axis.YP.rotationDegrees(180));
                poseStack.translate(-0.325F, 0, 0.4F);
                itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, light, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, stump.getLevel(), 6);

                poseStack.mulPose(Axis.YP.rotationDegrees(90));
                poseStack.translate(0, 0, 0.4F);
                itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, light, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, stump.getLevel(), 7);

                poseStack.mulPose(Axis.YP.rotationDegrees(180));
                poseStack.translate(-0.4F, 0, 0.325F);
                itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, light, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, stump.getLevel(), 8);

                poseStack.popPose();
            }
            case 4 -> {

                poseInit(poseStack, stump);

                poseStack.translate(-0.2F, 0, 0.2F);
                itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, light, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, stump.getLevel(), 9);

                poseStack.mulPose(Axis.YP.rotationDegrees(90));
                poseStack.translate(0, 0, 0.4);
                itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, light, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, stump.getLevel(), 10);

                poseStack.mulPose(Axis.YP.rotationDegrees(90));
                poseStack.translate(0, 0, 0.4);
                itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, light, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, stump.getLevel(), 11);

                poseStack.mulPose(Axis.YP.rotationDegrees(90));
                poseStack.translate(0, 0, 0.4);
                itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, light, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, stump.getLevel(), 12);

                poseStack.popPose();
            }
        }

    }

    private static void poseInit(PoseStack poseStack, StumpBlockEntity stump) {
        poseStack.pushPose();
        poseStack.translate(0.5, 0.6875F, 0.5);
        poseStack.scale(1.25F, 1.25F, 1.25F);
        poseStack.mulPose(Axis.YP.rotation(stump.getRotation()));
    }
}
