package theSleuth.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.HashMap;


public class ExhaustFromDiscardChoiceAction extends AbstractGameAction {

    private HashMap<AbstractCard, Boolean> canExhaust = new HashMap<>();

    public ExhaustFromDiscardChoiceAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FASTER;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FASTER) {
            canExhaust.clear();

            for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
                canExhaust.put(c, false);
            }

            if (canExhaust.isEmpty()) {
                this.isDone = true;
                return;
            } else if (canExhaust.size() == 1) {
                for (AbstractCard c : canExhaust.keySet()) {
                    int index = AbstractDungeon.player.discardPile.group.indexOf(c);
                    if (index != -1) {
                        AbstractDungeon.player.discardPile.moveToExhaustPile(c);
                    }
                    this.isDone = true;
                    return;
                }
            } else {
                CardGroup options = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                options.group.addAll(canExhaust.keySet());
                AbstractDungeon.gridSelectScreen.open(options, 1, "Choose a card to Exhaust", false, false, false, false);
                this.tickDuration();
                return;
            }
        }

        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);

            int index = -1;
            index = AbstractDungeon.player.discardPile.group.indexOf(c);
            if (index != -1) {
                AbstractDungeon.player.discardPile.moveToExhaustPile(c);
            }
        }
        this.isDone = true;

        AbstractDungeon.gridSelectScreen.selectedCards.clear();
        AbstractDungeon.player.hand.refreshHandLayout();
    }
}