package github.poscard8.wood_enjoyer.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import github.poscard8.wood_enjoyer.common.entity.ModdedBoat;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.WaterPatchModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Set;

@OnlyIn(Dist.CLIENT)
public class ModdedBoatModel<T extends ModdedBoat> extends ListModel<T> implements WaterPatchModel {

    protected ModelPart base;
    protected ModelPart sides;
    protected ModelPart leftPaddle;
    protected ModelPart rightPaddle;
    protected ModelPart waterPatch;

    public ModdedBoatModel(ModelPart root) {
        this.base = root.getChild("base");
        this.sides = root.getChild("sides");
        this.leftPaddle = root.getChild("left_paddle");
        this.rightPaddle = root.getChild("right_paddle");
        this.waterPatch = root.getChild("water_patch");
    }

    public static LayerDefinition createMainLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("base", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -13.0F, -9.0F, 10.0F, 3.0F, 18.0F)
                .texOffs(0, 22).addBox(5.0F, -13.0F, -7.0F, 6.0F, 3.0F, 14.0F)
                .texOffs(100, 0).addBox(11.0F, -13.0F, -4.0F, 6.0F, 3.0F, 8.0F)
                .texOffs(0, 8).addBox(17.0F, -13.0F, -2.0F, 4.0F, 3.0F, 4.0F)
                .texOffs(0, 22).addBox(-11.0F, -13.0F, -7.0F, 6.0F, 3.0F, 14.0F)
                .texOffs(100, 0).addBox(-17.0F, -13.0F, -4.0F, 6.0F, 3.0F, 8.0F)
                .texOffs(0, 0).addBox(-21.0F, -13.0F, -2.0F, 4.0F, 3.0F, 4.0F), PartPose.offset(0.0F, 16.0F, 0.0F));

        PartDefinition sides = partdefinition.addOrReplaceChild("sides", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

        sides.addOrReplaceChild("southeast", CubeListBuilder.create().texOffs(0, 42).addBox(-23.0F, -15.001F, -4.0F, 24.0F, 6.0F, 3.0F), PartPose.offsetAndRotation(0.0F, -4.0F, -8.0F, 0.0F, 0.4363F, 0.0F));
        sides.addOrReplaceChild("southwest", CubeListBuilder.create().texOffs(0, 52).addBox(-23.0F, -15.0F, 1.0F, 24.0F, 6.0F, 3.0F), PartPose.offsetAndRotation(0.0F, -4.0F, 8.0F, 0.0F, -0.4363F, 0.0F));
        sides.addOrReplaceChild("northeast", CubeListBuilder.create().texOffs(0, 42).addBox(-1.0F, -15.0F, -4.0F, 24.0F, 6.0F, 3.0F), PartPose.offsetAndRotation(0.0F, -4.0F, -8.0F, 0.0F, -0.4363F, 0.0F));
        sides.addOrReplaceChild("northwest", CubeListBuilder.create().texOffs(0, 52).addBox(-1.0F, -15.001F, 1.0F, 24.0F, 6.0F, 3.0F), PartPose.offsetAndRotation(0.0F, -4.0F, 8.0F, 0.0F, 0.4363F, 0.0F));

        partdefinition.addOrReplaceChild("left_paddle", CubeListBuilder.create().texOffs(62, 0).addBox(-1.0F, 0.0F, -5.0F, 2.0F, 2.0F, 18.0F).addBox(-1.001F, -3.0F, 8.0F, 1.0F, 6.0F, 7.0F), PartPose.offsetAndRotation(3.0F, -5.0F, 9.0F, 0.0F, 0.0F, 0.19634955F));
        partdefinition.addOrReplaceChild("right_paddle", CubeListBuilder.create().texOffs(62, 20).addBox(-1.0F, 0.0F, -5.0F, 2.0F, 2.0F, 18.0F).addBox(0.001F, -3.0F, 8.0F, 1.0F, 6.0F, 7.0F), PartPose.offsetAndRotation(3.0F, -5.0F, -9.0F, 0.0F, (float) Math.PI, 0.19634955F));

        partdefinition.addOrReplaceChild("water_patch", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -10.0F, -3.0F, 10.0F, 18.0F, 3.0F)
                .texOffs(-2, -2).addBox(5.0F, -7.5F, -3.0F, 6.0F, 13.0F, 3.0F)
                .texOffs(-2, -2).addBox(11.0F, -4.5F, -3.0F, 6.0F, 7.0F, 3.0F)
                .texOffs(0, 0).addBox(17.0F, -2.5F, -3.0F, 4.0F, 3.0F, 3.0F)
                .texOffs(-2, -2).addBox(-11.0F, -7.5F, -3.0F, 6.0F, 13.0F, 3.0F)
                .texOffs(-2, -2).addBox(-17.0F, -4.5F, -3.0F, 6.0F, 7.0F, 3.0F)
                .texOffs(0, 0).addBox(-21.0F, -2.5F, -3.0F, 4.0F, 3.0F, 3.0F), PartPose.offsetAndRotation(0.0F, -3.0F, 1.0F, (float) Math.PI / 2.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(T boat, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        animatePaddle(boat, 0, leftPaddle, limbSwing);
        animatePaddle(boat, 1, rightPaddle, limbSwing);
    }

    static void animatePaddle(ModdedBoat boat, int direction, ModelPart part, float limbSwing) {
        float f = boat.getRowingTime(direction, limbSwing);
        part.xRot = Mth.clampedLerp((-(float) Math.PI / 3F), -0.2617994F, (Mth.sin(-f) + 1.0F) / 2.0F);
        part.yRot = Mth.clampedLerp((-(float) Math.PI / 4F), ((float) Math.PI / 4F), (Mth.sin(-f + 1.0F) + 1.0F) / 2.0F);
        if (direction == 1) {
            part.yRot = (float) Math.PI - part.yRot;
        }

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        base.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        sides.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        leftPaddle.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        rightPaddle.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public Iterable<ModelPart> parts() {
        return Set.of(base, sides, leftPaddle, rightPaddle, waterPatch);
    }

    @Override
    public ModelPart waterPatch() {
        return waterPatch;
    }
}