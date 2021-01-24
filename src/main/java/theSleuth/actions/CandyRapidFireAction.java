package theSleuth.actions;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSleuth.util.CandyRapidFireEffect;
import theSleuth.util.FireShrapnelEffect;

public class CandyRapidFireAction extends AbstractGameAction {

    private CandyRapidFireEffect rrfe;

    public CandyRapidFireAction(AbstractMonster target) {
        this.actionType = ActionType.SPECIAL;

        int bleh = MathUtils.random(8);
        int boogle = MathUtils.random(49);
        int blaaah = MathUtils.random(499);
        int wah = 0;
        if (blaaah == 499) {
            AbstractDungeon.effectList.add(new FireShrapnelEffect(target, 8));
            wah = 9;
        } else if (boogle == 49) {
            AbstractDungeon.effectList.add(new FireShrapnelEffect(target, 7));
            wah = 10;
        } else if (bleh == 0) {
            AbstractDungeon.effectList.add(new FireShrapnelEffect(target, 2));
            wah = 0;
        } else if (bleh == 1) {
            AbstractDungeon.effectList.add(new FireShrapnelEffect(target, 3));
            wah = 1;
        } else if (bleh == 2) {
            AbstractDungeon.effectList.add(new FireShrapnelEffect(target, 4));
            wah = 2;
        } else if (bleh == 3) {
            AbstractDungeon.effectList.add(new FireShrapnelEffect(target, 4));
            wah = 3;
        } else if (bleh == 4) {
            AbstractDungeon.effectList.add(new FireShrapnelEffect(target, 5));
            wah = 4;
        } else if (bleh == 5) {
            AbstractDungeon.effectList.add(new FireShrapnelEffect(target, 6));
            wah = 5;
        } else if (bleh == 6) {
            AbstractDungeon.effectList.add(new FireShrapnelEffect(target, 0));
            wah = 6;
        } else if (bleh == 7) {
            AbstractDungeon.effectList.add(new FireShrapnelEffect(target, 1));
            wah = 7;
        } else if (bleh == 8) {
            AbstractDungeon.effectList.add(new FireShrapnelEffect(target, 2));
            wah = 8;
        }
        rrfe = new CandyRapidFireEffect(target, wah);
        AbstractDungeon.effectList.add(rrfe);
    }


    public void update() {
        if (rrfe.finishedAction)
            this.isDone = true;
    }
}
