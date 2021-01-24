package theSleuth.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import theSleuth.cards.FrighteningBeast;
import theSleuth.cards.MortholDryax;
import theSleuth.cards.OglogMRubbit;
import theSleuth.characters.TheSleuthChar;

import java.util.ArrayList;

public class SecretPatch {
    @SpirePatch(
            clz = CardLibrary.class,
            method = "getCurse",
            paramtypez = {

            }
    )
    public static class ThePatch {
        public static SpireReturn<AbstractCard> Prefix() {
            if (AbstractDungeon.player instanceof TheSleuthChar) {
                if (!((TheSleuthChar) AbstractDungeon.player).seenCurse) {
                    ArrayList<AbstractCard> beastList = new ArrayList<>();
                    beastList.add(new FrighteningBeast());
                    beastList.add(new MortholDryax());
                    beastList.add(new OglogMRubbit());
                    ((TheSleuthChar) AbstractDungeon.player).seenCurse = true;
                    return SpireReturn.Return(beastList.get(AbstractDungeon.cardRandomRng.random(beastList.size() - 1)));
                }
            }
            return SpireReturn.Continue();
        }
    }
}