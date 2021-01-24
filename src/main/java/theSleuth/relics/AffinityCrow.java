package theSleuth.relics;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;
import theSleuth.SleuthMod;
import theSleuth.util.TextureLoader;

import java.util.ArrayList;
import java.util.HashMap;

import static theSleuth.SleuthMod.makeRelicOutlinePath;
import static theSleuth.SleuthMod.makeRelicPath;

public class AffinityCrow extends CustomRelic {

    public static final String ID = SleuthMod.makeID("AffinityCrow");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("affinity_crow.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("affinity_crow.png"));

    public AffinityCrow() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.HEAVY);
    }

    public static HashMap<AbstractMonster, Integer> monsterHash = new HashMap<>();

    public static ArrayList<AbstractMonster> monsterHashTwo = new ArrayList<>();

    @Override
    public void atTurnStart() {
        for (AbstractMonster q : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!q.isDying && !q.isDead && !q.halfDead) {
                EnemyMoveInfo thing = (EnemyMoveInfo) ReflectionHacks.getPrivate(q, AbstractMonster.class, "move");
                for (DamageInfo d : q.damage) {
                    if (d.base == thing.baseDamage) {
                        if (thing.baseDamage > 30) {
                            d.base = 30;
                            thing.baseDamage = 30;
                        }
                        if (thing.isMultiDamage) {
                            if (thing.multiplier * thing.baseDamage > 30) {
                                d.base = (int) 30 / thing.multiplier;
                                thing.baseDamage = (int) 30 / thing.multiplier;
                            }
                        }
                    }
                }
                if (thing.isMultiDamage) {
                    monsterHashTwo.add(q);
                }
                q.createIntent();
            }
        }
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
