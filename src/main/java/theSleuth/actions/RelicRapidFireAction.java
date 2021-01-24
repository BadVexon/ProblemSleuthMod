package theSleuth.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theSleuth.util.RelicRapidFireEffect;

public class RelicRapidFireAction extends AbstractGameAction {

    private RelicRapidFireEffect rrfe;

    public RelicRapidFireAction(AbstractMonster target, AbstractRelic relic, int time) {
        this.actionType = ActionType.SPECIAL;

        rrfe = new RelicRapidFireEffect(relic, target, time);
        AbstractDungeon.effectList.add(rrfe);
    }

    public RelicRapidFireAction(AbstractMonster target, AbstractRelic relic) {
        this.actionType = ActionType.SPECIAL;

        rrfe = new RelicRapidFireEffect(relic, target);
        AbstractDungeon.effectList.add(rrfe);
    }

    public void update() {
        if (rrfe.finishedAction)
            this.isDone = true;
    }
}
