package theSleuth.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import theSleuth.cards.FrighteningBeast;
import theSleuth.cards.MortholDryax;
import theSleuth.cards.OglogMRubbit;
import theSleuth.characters.TheSleuthChar;
import theSleuth.util.BlacklistedContentHelper;

public class BanStuffPatch {

    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "initializeCardPools"
    )
    public static class CardPatch {
        public static void Postfix(AbstractDungeon __instance) {
            if (AbstractDungeon.player.chosenClass != TheSleuthChar.Enums.THE_sleuth) {
                AbstractDungeon.curseCardPool.removeCard(MortholDryax.ID);
                AbstractDungeon.curseCardPool.removeCard(OglogMRubbit.ID);
                AbstractDungeon.curseCardPool.removeCard(FrighteningBeast.ID);
            }
        }
    }

    /*
    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "initializeRelicList"
    )
    public static class RelicPatch {

        public static void Prefix(AbstractDungeon __instance) {
            if (AbstractDungeon.player.chosenClass == TheSleuthChar.Enums.THE_sleuth) {
                AbstractDungeon.relicsToRemoveOnStart.addAll(BlacklistedContentHelper.bannedRelics);
            }
        }
    }

     */

    @SpirePatch(
            clz = PotionHelper.class,
            method = "initialize"
    )
    public static class PotionPatch {

        public static void Postfix(AbstractPlayer.PlayerClass chosenClass) {
            if (AbstractDungeon.player.chosenClass == TheSleuthChar.Enums.THE_sleuth) {
                PotionHelper.potions.removeIf(i -> BlacklistedContentHelper.bannedPotions.contains(i));
            }
        }
    }
}