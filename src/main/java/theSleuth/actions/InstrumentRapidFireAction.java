package theSleuth.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theSleuth.util.InstrumentRapidFireEffect;

public class InstrumentRapidFireAction extends AbstractGameAction {

    private InstrumentRapidFireEffect rrfe;

    public InstrumentRapidFireAction(AbstractMonster target) {
        this.actionType = ActionType.SPECIAL;

        rrfe = new InstrumentRapidFireEffect(target, 15);
        AbstractDungeon.effectList.add(rrfe);
    }

    public InstrumentRapidFireAction(AbstractMonster target, int time) {
        this.actionType = ActionType.SPECIAL;

        rrfe = new InstrumentRapidFireEffect(target, time);
        AbstractDungeon.effectList.add(rrfe);
    }

    public void update() {
        if (rrfe.finishedAction)
            this.isDone = true;
    }
}
