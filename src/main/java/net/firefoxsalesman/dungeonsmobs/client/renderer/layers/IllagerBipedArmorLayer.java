package net.firefoxsalesman.dungeonsmobs.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.firefoxsalesman.dungeonsmobs.client.models.illager.IllagerBipedModel;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.IllagerArmsUtil;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IllagerBipedArmorLayer<T extends AbstractIllager, M extends IllagerBipedModel<T>, A extends HumanoidModel<T>> extends HumanoidArmorLayer<T, M, A> {

    private final A armorModel;
    private final IllagerBipedModel<T> crossedArmsArmorModel;

    public IllagerBipedArmorLayer(RenderLayerParent<T, M> parent, A p_i50936_2_, A armorModel, ModelManager manager, IllagerBipedModel<T> crossedArmsArmorModel) {
	super(parent, p_i50936_2_, armorModel, manager);
        this.armorModel = armorModel;
        this.crossedArmsArmorModel = crossedArmsArmorModel;
    }

    public void render(PoseStack p_225628_1_, MultiBufferSource p_225628_2_, int p_225628_3_, T p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        super.render(p_225628_1_, p_225628_2_, p_225628_3_, p_225628_4_, p_225628_5_, p_225628_6_, p_225628_7_, p_225628_8_, p_225628_9_, p_225628_10_);
        this.renderArmorPiece(p_225628_1_, p_225628_2_, p_225628_4_, EquipmentSlot.CHEST, p_225628_3_, crossedArmsArmorModel);
    }

    private void renderArmorPiece(PoseStack stack, MultiBufferSource pBuffer, T p_241739_3_, EquipmentSlot slot, int p_241739_5_, IllagerBipedModel<T> crossedArmsModel) {
        ItemStack itemstack = p_241739_3_.getItemBySlot(slot);
        if (itemstack.getItem() instanceof ArmorItem) {
            ArmorItem armoritem = (ArmorItem) itemstack.getItem();
            if (armoritem.getType().getSlot() == slot) {
                this.getParentModel().copyPropertiesTo(crossedArmsModel);
                ResourceLocation crossedTexture = this.getArmorResource(p_241739_3_, itemstack, slot, "crossed");
                boolean armsCanBeCrossed = IllagerArmsUtil.resourceExists(crossedTexture);
                this.setPartVisibilityCrossedArms(crossedArmsModel, slot, armsCanBeCrossed);
                if (!armsCanBeCrossed) return;
                boolean flag1 = itemstack.hasFoil();
                if (armoritem instanceof net.minecraft.world.item.DyeableLeatherItem) {
                    int i = ((net.minecraft.world.item.DyeableLeatherItem) armoritem).getColor(itemstack);
                    float f = (float) (i >> 16 & 255) / 255.0F;
                    float f1 = (float) (i >> 8 & 255) / 255.0F;
                    float f2 = (float) (i & 255) / 255.0F;
                    this.renderModel(stack, pBuffer, p_241739_5_, flag1, crossedArmsModel, f, f1, f2, crossedTexture);
                    this.renderModel(stack, pBuffer, p_241739_5_, flag1, crossedArmsModel, 1.0F, 1.0F, 1.0F, this.getArmorResource(p_241739_3_, itemstack, slot, "crossed_overlay"));
                } else {
                    this.renderModel(stack, pBuffer, p_241739_5_, flag1, crossedArmsModel, 1.0F, 1.0F, 1.0F, crossedTexture);
                }
            }
        }
    }

    private void renderModel(PoseStack p_241738_1_, MultiBufferSource p_241738_2_, int p_241738_3_, boolean p_241738_5_, IllagerBipedModel<T> p_241738_6_, float p_241738_8_, float p_241738_9_, float p_241738_10_, ResourceLocation armorResource) {
        VertexConsumer ivertexbuilder = ItemRenderer.getArmorFoilBuffer(p_241738_2_, RenderType.armorCutoutNoCull(armorResource), false, p_241738_5_);
        p_241738_6_.renderToBuffer(p_241738_1_, ivertexbuilder, p_241738_3_, OverlayTexture.NO_OVERLAY, p_241738_8_, p_241738_9_, p_241738_10_, 1.0F);
    }

    @Override
    protected void setPartVisibility(A entityModel, EquipmentSlot p_188359_2_) {
        super.setPartVisibility(entityModel, p_188359_2_);
        if (p_188359_2_ == EquipmentSlot.CHEST) {
//            if (this.getParentModel().arms.visible) {
//                entityModel.rightArm.visible = false;
//                entityModel.leftArm.visible = false;
//            } else {
            entityModel.rightArm.visible = true;
            entityModel.leftArm.visible = true;
//            }
        }
    }

    private void setPartVisibilityCrossedArms(IllagerBipedModel<T> illagerEntityModel, EquipmentSlot p_188359_2_, boolean armsCanBeCrossed) {
        illagerEntityModel.setAllVisible(false);
        illagerEntityModel.jacket.visible = false;
        illagerEntityModel.arms.visible = false;
        if (p_188359_2_ == EquipmentSlot.CHEST) {
            illagerEntityModel.jacket.visible = true;
            if (this.getParentModel().arms.visible && armsCanBeCrossed) {
                illagerEntityModel.arms.visible = true;
                illagerEntityModel.rightArm.visible = false;
                illagerEntityModel.leftArm.visible = false;
            } else {
                illagerEntityModel.arms.visible = false;
                illagerEntityModel.rightArm.visible = false;
                illagerEntityModel.leftArm.visible = false;
            }
        }
    }
}
