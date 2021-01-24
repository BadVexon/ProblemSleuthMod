package theSleuth.actions;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSleuth.cards.AbstractSleuthCard;

import java.util.ArrayList;
import java.util.Iterator;

public class WhiskeyRetainAction2 extends AbstractGameAction {
    private AbstractPlayer p;
    private ArrayList<AbstractCard> cannotUpgrade = new ArrayList<>();

    public WhiskeyRetainAction2() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            for (AbstractCard q : AbstractDungeon.player.hand.group) {
                if (AlwaysRetainField.alwaysRetain.get(q) || q.retain) {
                    this.cannotUpgrade.add(q);
                }
            }

            if (this.cannotUpgrade.size() == this.p.hand.group.size()) {
                this.isDone = true;
                return;
            }

            if (this.p.hand.group.size() - this.cannotUpgrade.size() == 1) {
                for (AbstractCard c : this.p.hand.group) {
                    AlwaysRetainField.alwaysRetain.set(c, true);
                    c.rawDescription = c.rawDescription + " NL Retain.";
                    c.retain = true;
                    if (c instanceof AbstractSleuthCard) {
                        ((AbstractSleuthCard) c).whiskeyd = true;
                    }
                    c.initializeDescription();
                    c.superFlash();
                }
            }

            this.p.hand.group.removeAll(this.cannotUpgrade);
            if (this.p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open("give Retain.", 1, false, false, false, false);
                this.tickDuration();
                return;
            }

            if (this.p.hand.group.size() == 1) {
                AlwaysRetainField.alwaysRetain.set(this.p.hand.getTopCard(), true);
                this.p.hand.getTopCard().rawDescription =  "Retain. NL " + this.p.hand.getTopCard().rawDescription;
                this.p.hand.getTopCard().retain = true;
                if (this.p.hand.getTopCard() instanceof AbstractSleuthCard) {
                    ((AbstractSleuthCard) this.p.hand.getTopCard()).whiskeyd = true;
                }
                this.p.hand.getTopCard().initializeDescription();
                this.p.hand.getTopCard().superFlash();
                this.returnCards();
                this.isDone = true;
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                AlwaysRetainField.alwaysRetain.set(c, true);
                c.rawDescription = "Retain. NL " + c.rawDescription;
                c.retain = true;
                if (c instanceof AbstractSleuthCard) {
                    ((AbstractSleuthCard) c).whiskeyd = true;
                }
                c.initializeDescription();
                this.p.hand.addToTop(c);
                c.superFlash();
            }

            this.returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }

        this.tickDuration();
    }

    private void returnCards() {
        for (AbstractCard c : this.cannotUpgrade) {
            this.p.hand.addToTop(c);
        }

        this.p.hand.refreshHandLayout();
        this.p.hand.glowCheck();
    }
}
