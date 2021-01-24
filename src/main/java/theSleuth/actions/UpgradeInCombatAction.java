package theSleuth.actions;

import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSleuth.SleuthMod;

import java.util.ArrayList;

public class UpgradeInCombatAction
        extends AbstractGameAction {
    private ArrayList<AbstractCard> cannotUpgrade = new ArrayList<>();
    public static String ID = SleuthMod.makeID("UpgradeInCombatAction");

    public UpgradeInCombatAction() {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;

        if (this.duration == Settings.ACTION_DUR_FAST) {
            for (AbstractCard c : p.hand.group) {
                AbstractCard masterC = StSLib.getMasterDeckEquivalent(c);
                if ((masterC == null) || (!masterC.canUpgrade())) {
                    this.cannotUpgrade.add(c);
                }
            }
            if (this.cannotUpgrade.size() == p.hand.group.size()) {
                this.isDone = true;
                return;
            }
            if (p.hand.group.size() - this.cannotUpgrade.size() == 1) {
                for (AbstractCard c : p.hand.group) {
                    AbstractCard masterC = StSLib.getMasterDeckEquivalent(c);
                    if (masterC != null && masterC.canUpgrade()) {
                        masterC.upgrade();
                        if (c.canUpgrade()) {
                            c.upgrade();
                        }
                        c.superFlash();

                        this.isDone = true;
                        return;
                    }
                }
            }
            p.hand.group.removeAll(this.cannotUpgrade);
            AbstractDungeon.handCardSelectScreen.open("upgrade permanently.", 1, false, false, false, true);
            this.tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                if (c.canUpgrade()) {
                    c.upgrade();
                }
                p.hand.addToTop(c);
                c.superFlash();
                AbstractCard cardToSmith = StSLib.getMasterDeckEquivalent(c);
                if (cardToSmith != null && cardToSmith.canUpgrade()) {
                    p.masterDeck.getSpecificCard(cardToSmith).upgrade();
                }
            }
            for (AbstractCard c : this.cannotUpgrade) {
                p.hand.addToTop(c);
            }
            p.hand.refreshHandLayout();
        }

        AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
        AbstractDungeon.player.hand.glowCheck();
        this.isDone = true;
    }
}