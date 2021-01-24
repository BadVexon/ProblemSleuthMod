package theSleuth.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DropOffAction extends AbstractGameAction {
    private float startingDuration;
    private int magic;

    public DropOffAction(int magicNum) {
        this.target = AbstractDungeon.player;
        this.actionType = ActionType.WAIT;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = Settings.ACTION_DUR_FAST;
        this.magic = magicNum;
    }

    public void update() {
        if (this.duration == this.startingDuration) {
            int tmp = AbstractDungeon.player.hand.size();
            AbstractDungeon.actionManager.addToBottom(new DiscardAction(AbstractDungeon.player, AbstractDungeon.player, tmp, true));
            for (int i = 0; i < tmp; i++) {
                AbstractDungeon.actionManager.addToBottom(new AddTemporaryHPAction(AbstractDungeon.player, AbstractDungeon.player, magic));
            }

            this.isDone = true;
        }

    }
}
