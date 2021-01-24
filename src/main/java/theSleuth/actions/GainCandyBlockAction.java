package theSleuth.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSleuth.util.FlashAtkCandyEffect;

public class GainCandyBlockAction extends AbstractGameAction {
    private static final float DUR = 0.25F;

    public GainCandyBlockAction(AbstractCreature target, AbstractCreature source, int amount) {
        this.setValues(target, source, amount);// 13
        this.actionType = ActionType.BLOCK;// 14
        this.duration = 0.25F;// 15
        this.startDuration = 0.25F;// 16
    }// 17

    public GainCandyBlockAction(AbstractCreature target, AbstractCreature source, int amount, boolean superFast) {
        this.setValues(target, source, amount);// 20
        this.actionType = ActionType.BLOCK;// 21
        this.duration = 0.1F;// 22
        this.startDuration = 0.1F;// 23
    }// 24

    public void update() {
        if (!this.target.isDying && !this.target.isDead && this.duration == this.startDuration) {// 28 29
            AbstractDungeon.effectList.add(new FlashAtkCandyEffect(target.hb.cX, target.hb.cY));
            this.target.addBlock(this.amount);// 31
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                c.applyPowers();// 35
            }
        }

        this.tickDuration();// 40
    }// 41
}