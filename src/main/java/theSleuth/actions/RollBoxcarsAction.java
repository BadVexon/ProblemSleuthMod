package theSleuth.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import theSleuth.cards.ClubClubClub;
import theSleuth.cards.DiamondArmor;
import theSleuth.cards.HeartStab;
import theSleuth.cards.SpadeDig;
import theSleuth.patches.CenterGridCardSelectScreen;


public class RollBoxcarsAction extends AbstractGameAction {
    private boolean upgrade;

    private CardGroup possCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

    public RollBoxcarsAction(boolean upgraded) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FASTER;
        upgrade = upgraded;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FASTER) {
            for (AbstractCard c : CardLibrary.getAllCards()) {
                if (c instanceof HeartStab || c instanceof DiamondArmor || c instanceof ClubClubClub || c instanceof SpadeDig) {
                    AbstractCard q = c.makeCopy();
                    if (upgrade) {
                        q.upgrade();
                    }
                    possCards.addToTop(q);
                }
            }
            CenterGridCardSelectScreen.centerGridSelect = true;
            AbstractDungeon.gridSelectScreen.open(possCards, 1, "Choose a card to add to your hand.", false, false, false, false);
            this.tickDuration();
            return;
        }

        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            CenterGridCardSelectScreen.centerGridSelect = false;
            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);

            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c));
        }
        this.isDone = true;

        AbstractDungeon.gridSelectScreen.selectedCards.clear();
        AbstractDungeon.player.hand.refreshHandLayout();
    }
}