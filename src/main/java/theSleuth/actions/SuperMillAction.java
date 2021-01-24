




package theSleuth.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

public class SuperMillAction extends AbstractGameAction {
    private int q;

    public SuperMillAction(int position) {
        q = position;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FASTER;
    }

    public void update() {
        if (!AbstractDungeon.player.drawPile.isEmpty()) {
            if (q == 4) {
                AbstractDungeon.actionManager.addToTop(new VFXAction(new ShowCardBrieflyEffect(AbstractDungeon.player.drawPile.getTopCard().makeStatEquivalentCopy(), (float) Settings.WIDTH * 0.2F, (float) Settings.HEIGHT / 2.0F)));
            } else if (q == 3) {
                AbstractDungeon.actionManager.addToTop(new VFXAction(new ShowCardBrieflyEffect(AbstractDungeon.player.drawPile.getTopCard().makeStatEquivalentCopy(), (float) Settings.WIDTH * 0.35F, (float) Settings.HEIGHT / 2.0F)));
            } else if (q == 2) {
                AbstractDungeon.actionManager.addToTop(new VFXAction(new ShowCardBrieflyEffect(AbstractDungeon.player.drawPile.getTopCard().makeStatEquivalentCopy(), (float) Settings.WIDTH * 0.5F, (float) Settings.HEIGHT / 2.0F)));
            } else if (q == 1) {
                AbstractDungeon.actionManager.addToTop(new VFXAction(new ShowCardBrieflyEffect(AbstractDungeon.player.drawPile.getTopCard().makeStatEquivalentCopy(), (float) Settings.WIDTH * 0.65F, (float) Settings.HEIGHT / 2.0F)));
            } else if (q == 0) {
                AbstractDungeon.actionManager.addToTop(new VFXAction(new ShowCardBrieflyEffect(AbstractDungeon.player.drawPile.getTopCard().makeStatEquivalentCopy(), (float) Settings.WIDTH * 0.8F, (float) Settings.HEIGHT / 2.0F)));
            }
            AbstractDungeon.actionManager.addToTop(new DiscardSpecificCardAction(AbstractDungeon.player.drawPile.getTopCard(), AbstractDungeon.player.drawPile));
        }

        this.isDone = true;
    }
}
