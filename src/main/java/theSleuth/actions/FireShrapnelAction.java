package theSleuth.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSleuth.util.FireShrapnelEffect;

public class FireShrapnelAction extends AbstractGameAction {

    private FireShrapnelEffect rrfe;

    public FireShrapnelAction(AbstractMonster target) {
        this.actionType = ActionType.SPECIAL;

        rrfe = new FireShrapnelEffect(target);
        AbstractDungeon.effectList.add(rrfe);
    }

    public void update() {
        if (rrfe.finishedAction)
            this.isDone = true;
    }
}
