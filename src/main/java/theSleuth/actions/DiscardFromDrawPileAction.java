




package theSleuth.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DiscardFromDrawPileAction extends AbstractGameAction {
    private AbstractPlayer p;
    private int amount;

    public DiscardFromDrawPileAction(int amt) {
        this.p = AbstractDungeon.player;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FASTER;
        this.amount = amt;
    }

    public void update() {
        if (AbstractDungeon.getCurrRoom().isBattleEnding()) {
            this.isDone = true;
        } else {
            if (this.duration == Settings.ACTION_DUR_FASTER) {
                if (this.p.drawPile.isEmpty()) {
                    this.isDone = true;
                    return;
                }

                if (this.p.drawPile.size() <= amount) {
                    for (int i = 0; i < this.p.drawPile.size(); i++) {
                        AbstractCard tmp = this.p.drawPile.getTopCard();
                        AbstractDungeon.actionManager.addToTop(new DiscardSpecificCardAction(tmp, AbstractDungeon.player.drawPile));
                    }
                }

                if (this.p.drawPile.group.size() > amount) {
                    AbstractDungeon.gridSelectScreen.open(this.p.drawPile, amount, "Choose a card to discard.", false, false, false, false);
                    this.tickDuration();
                    return;
                }
            }

            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {

                for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                    AbstractDungeon.actionManager.addToTop(new DiscardSpecificCardAction(c, AbstractDungeon.player.drawPile));
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }

            this.tickDuration();
        }
    }
}
