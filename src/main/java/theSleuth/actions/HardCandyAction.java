




package theSleuth.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSleuth.cards.AbstractSleuthCard;

import java.util.Iterator;

public class HardCandyAction extends AbstractGameAction {
    private AbstractPlayer p;

    public HardCandyAction(int amount) {
        this.p = AbstractDungeon.player;
        this.setValues(this.p, AbstractDungeon.player, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update() {

        CardGroup imaginDiscard = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c instanceof AbstractSleuthCard) {
                if (((AbstractSleuthCard) c).imagin > 0) {
                    imaginDiscard.group.add(c);
                }
            }
        }
        if (this.p.hand.size() >= 10 || imaginDiscard.size() == 0) {
            this.isDone = true;
        } else if (imaginDiscard.size() == 1) {
            AbstractCard card = imaginDiscard.group.get(0);
            if (this.p.hand.size() < 10) {
                this.p.hand.addToHand(card);
                this.p.discardPile.removeCard(card);
            }

            card.lighten(false);
            this.p.hand.refreshHandLayout();
            this.isDone = true;
        } else if (this.duration == 0.5F) {
            AbstractDungeon.gridSelectScreen.open(imaginDiscard, this.amount, "Choose an Imagination card to return to your hand.", false);
            this.tickDuration();
        } else {
            if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
                Iterator var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

                AbstractCard c;
                while (var1.hasNext()) {
                    c = (AbstractCard) var1.next();
                    if (this.p.hand.size() < 10) {
                        this.p.hand.addToHand(c);
                        this.p.discardPile.removeCard(c);
                    }

                    c.lighten(false);
                    c.unhover();
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                this.p.hand.refreshHandLayout();

                for (var1 = imaginDiscard.group.iterator(); var1.hasNext(); c.target_y = 0.0F) {
                    c = (AbstractCard) var1.next();
                    c.unhover();
                    c.target_x = (float) CardGroup.DISCARD_PILE_X;
                }

                this.isDone = true;
            }

            this.tickDuration();
        }
    }
}
