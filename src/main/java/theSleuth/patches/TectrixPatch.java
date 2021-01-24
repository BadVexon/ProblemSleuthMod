package theSleuth.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;
import theSleuth.relics.AffinityCrow;

public class TectrixPatch {
    @SpirePatch(
            clz = AbstractMonster.class,
            method = "calculateDamage"
    )
    public static class ThePatch {
        public static void Postfix(AbstractMonster __instance, int dmg) {
            if (AbstractDungeon.player.hasRelic(AffinityCrow.ID)) {
                if (dmg > 30) {
                    dmg = 30;
                }
                EnemyMoveInfo info = (EnemyMoveInfo) ReflectionHacks.getPrivate(__instance, AbstractMonster.class, "move");
                if (info.isMultiDamage) {
                    if (dmg * info.multiplier > 30) {
                        dmg = 30 / info.multiplier;
                        AffinityCrow.monsterHash.put(__instance, dmg);
                    }
                }
                ReflectionHacks.setPrivate(__instance, AbstractMonster.class, "intentDmg", dmg);
            }
        }
    }
}