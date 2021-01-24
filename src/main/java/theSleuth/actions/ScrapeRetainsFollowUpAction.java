




package theSleuth.actions;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class ScrapeRetainsFollowUpAction extends AbstractGameAction {
    public ScrapeRetainsFollowUpAction() {
        this.duration = Settings.ACTION_DUR_FASTER;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FASTER) {
            Iterator var1 = ScrapeRetainsAction.scrapedCards.iterator();

            while (var1.hasNext()) {
                AbstractCard c = (AbstractCard) var1.next();
                if (AlwaysRetainField.alwaysRetain.get(c)) {
                    AbstractDungeon.player.hand.moveToDiscardPile(c);
                    c.triggerOnManualDiscard();
                    GameActionManager.incrementDiscard(false);
                }
            }

            ScrapeRetainsAction.scrapedCards.clear();
        }

        this.tickDuration();
    }
}
