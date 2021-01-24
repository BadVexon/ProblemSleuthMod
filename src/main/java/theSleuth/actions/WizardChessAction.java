




package theSleuth.actions;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSleuth.cards.AbstractSleuthCard;

public class WizardChessAction extends AbstractGameAction {
    private boolean upgraded;

    public WizardChessAction(boolean upgrad) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FASTER;
        upgraded = upgrad;
    }

    public void update() {
        if (AbstractDungeon.player.drawPile.size() + AbstractDungeon.player.discardPile.size() == 0) {
            this.isDone = true;
            return;
        }

        if (AbstractDungeon.player.drawPile.isEmpty()) {
            AbstractDungeon.actionManager.addToTop(new WizardChessAction(this.upgraded));
            AbstractDungeon.actionManager.addToTop(new EmptyDeckShuffleAction());
            this.isDone = true;
            return;
        }

        if (!AbstractDungeon.player.drawPile.isEmpty()) {
            AbstractCard c = AbstractDungeon.player.drawPile.getTopCard();
            c.freeToPlayOnce = true;
            if (c instanceof AbstractSleuthCard) {
                ((AbstractSleuthCard) c).wizarded = true;
            }
            if (upgraded && !(AlwaysRetainField.alwaysRetain.get(c) || c.retain)) {
                AlwaysRetainField.alwaysRetain.set(c, true);
                c.rawDescription = "Retain. NL " + c.rawDescription;
                c.retain = true;
                c.freeToPlayOnce = true;
                if (c instanceof AbstractSleuthCard) {
                    ((AbstractSleuthCard) c).whiskeyd = true;
                }
                c.initializeDescription();
            }
            AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, 1));
        }
        this.isDone = true;
    }
}
