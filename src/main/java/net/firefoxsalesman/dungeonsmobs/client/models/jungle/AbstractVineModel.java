package net.firefoxsalesman.dungeonsmobs.client.models.jungle;

import net.firefoxsalesman.dungeonsmobs.entity.jungle.AbstractVineEntity;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.molang.MolangParser;
import software.bernie.geckolib.model.GeoModel;

public abstract class AbstractVineModel extends GeoModel<AbstractVineEntity> {

    @Override
    public void setCustomAnimations(AbstractVineEntity entity, long uniqueID, AnimationState<AbstractVineEntity> customPredicate) {
        super.setCustomAnimations(entity, uniqueID, customPredicate);

        CoreGeoBone everything = this.getAnimationProcessor().getBone("everything");

        everything.setHidden(entity.tickCount <= entity.getAnimationTransitionTime());

        for (int i = 1; i < 26; i++) {
            CoreGeoBone part = this.getAnimationProcessor().getBone("part" + i);
            int partsToShow = 26 - entity.getLengthInSegments();
            if (part != null) {
                part.setHidden(i < partsToShow);
            }
        }

    }

    @Override
    public void applyMolangQueries(AbstractVineEntity animatable, double currentTick) {
        super.applyMolangQueries(animatable, currentTick);

        AbstractVineEntity vine = (AbstractVineEntity) animatable;
        MolangParser.INSTANCE.setValue("query.vine_length", vine::getLengthInSegments);
    }
}
