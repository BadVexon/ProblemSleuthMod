package theSleuth.actions;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSleuth.util.AltdownVFX;
import theSleuth.util.BeatdownCurvy;
import theSleuth.util.BeatdownLine;
import theSleuth.util.BeatdownVFX;

public class BeatdownAction extends AbstractGameAction {
    private DamageInfo info;
    private DamageInfo copyInfo;
    private static float AMOUNT = 0.5F;

    public BeatdownAction(DamageInfo info) {
        this.info = info;
        this.copyInfo = info;
        this.actionType = ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_FASTER;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FASTER) {
            AbstractMonster targetMonster = null;
            int lowestHealth = 99999;
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!m.isDead && !m.isDying && !m.halfDead) {
                    if (m.currentHealth < lowestHealth) {
                        targetMonster = m;
                        lowestHealth = m.currentHealth;
                    }
                }
            }
            if (targetMonster != null) {
                if (MathUtils.randomBoolean()) {
                    AbstractDungeon.effectList.add(new BeatdownVFX(targetMonster));
                } else {
                    AbstractDungeon.effectList.add(new AltdownVFX(targetMonster));
                }
                for (int r = 0; r < 18; r++) {
                    AbstractDungeon.effectsQueue.add(new BeatdownLine(targetMonster.hb.cX, targetMonster.hb.cY));
                }
                for (int r = 0; r < 5; r++) {
                    AbstractDungeon.effectsQueue.add(new BeatdownCurvy(targetMonster.hb.cX, targetMonster.hb.cY));
                }
                //CardCrawlGame.sound.playA("BLUNT_HEAVY", AMOUNT);
                targetMonster.damage(this.info);
                if (targetMonster.isDying || targetMonster.currentHealth <= 0) {
                    if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                        AbstractDungeon.actionManager.clearPostCombatActions();
                        AMOUNT = 1.0F;
                        this.isDone = true;
                    } else {
                        AbstractDungeon.actionManager.addToTop(new BeatdownAction(copyInfo));
                        AbstractDungeon.actionManager.addToTop(new WaitAction(0.5F));
                        AMOUNT += 0.5F;
                    }
                }
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AMOUNT = 1.0F;
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }

        this.isDone = true;
        this.tickDuration();
    }
}
