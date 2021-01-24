package theSleuth.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


public class PlayAndExhaustFromDiscardAction extends AbstractGameAction {

    public PlayAndExhaustFromDiscardAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FASTER;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FASTER) {
            if (AbstractDungeon.player.discardPile.isEmpty()) {
                this.isDone = true;
                return;
            } else if (AbstractDungeon.player.discardPile.size() == 1) {
                AbstractCard card = AbstractDungeon.player.discardPile.getTopCard();
                AbstractDungeon.player.discardPile.group.remove(card);
                AbstractDungeon.getCurrRoom().souls.remove(card);
                card.freeToPlayOnce = true;
                card.exhaust = true;
                AbstractDungeon.player.limbo.group.add(card);
                card.current_y = -200.0F * Settings.scale;
                card.target_x = (float) Settings.WIDTH / 2.0F + 200.0F * Settings.scale;
                card.target_y = (float) Settings.HEIGHT / 2.0F;
                card.targetAngle = 0.0F;
                card.lighten(false);
                card.drawScale = 0.12F;
                card.targetDrawScale = 0.75F;
                if (!card.canUse(AbstractDungeon.player, (AbstractMonster) this.target)) {
                    AbstractDungeon.actionManager.addToTop(new UnlimboAction(card));
                    AbstractDungeon.actionManager.addToTop(new DiscardSpecificCardAction(card, AbstractDungeon.player.limbo));
                    AbstractDungeon.actionManager.addToTop(new WaitAction(0.4F));
                } else {
                    card.applyPowers();
                    card.freeToPlayOnce = true;
                    card.exhaust = true;
                    AbstractDungeon.actionManager.addToTop(new QueueCardAction(card, AbstractDungeon.getRandomMonster()));
                    AbstractDungeon.actionManager.addToTop(new UnlimboAction(card));
                }
                if (!Settings.FAST_MODE) {
                    AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
                } else {
                    AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
                }
                this.isDone = true;
            } else {
                CardGroup options = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                options.group.addAll(AbstractDungeon.player.discardPile.group);
                AbstractDungeon.gridSelectScreen.open(options, 1, "Choose a card to Play and Exhaust", false, false, false, false);
                this.tickDuration();
                return;
            }
        }

        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                AbstractDungeon.player.discardPile.group.remove(card);
                AbstractDungeon.getCurrRoom().souls.remove(card);
                card.freeToPlayOnce = true;
                card.exhaust = true;
                AbstractDungeon.player.limbo.group.add(card);
                card.current_y = -200.0F * Settings.scale;
                card.target_x = (float) Settings.WIDTH / 2.0F + 200.0F * Settings.scale;
                card.target_y = (float) Settings.HEIGHT / 2.0F;
                card.targetAngle = 0.0F;
                card.lighten(false);
                card.drawScale = 0.12F;
                card.targetDrawScale = 0.75F;
                if (!card.canUse(AbstractDungeon.player, (AbstractMonster) this.target)) {
                    AbstractDungeon.actionManager.addToTop(new UnlimboAction(card));
                    AbstractDungeon.actionManager.addToTop(new DiscardSpecificCardAction(card, AbstractDungeon.player.limbo));
                    AbstractDungeon.actionManager.addToTop(new WaitAction(0.4F));
                } else {
                    card.applyPowers();
                    card.exhaust = true;
                    card.freeToPlayOnce = true;
                    AbstractDungeon.actionManager.addToTop(new QueueCardAction(card, AbstractDungeon.getRandomMonster()));
                    AbstractDungeon.actionManager.addToTop(new UnlimboAction(card));
                }
                if (!Settings.FAST_MODE) {
                    AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
                } else {
                    AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
                }
            }
        this.isDone = true;

        AbstractDungeon.gridSelectScreen.selectedCards.clear();
        AbstractDungeon.player.hand.refreshHandLayout();
        AbstractDungeon.player.hand.glowCheck();
    }
}