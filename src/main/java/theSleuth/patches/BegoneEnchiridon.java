package theSleuth.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.events.city.CursedTome;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import javassist.CtBehavior;

import java.util.ArrayList;

public class BegoneEnchiridon {
    @SpirePatch(
            clz = CursedTome.class,
            method = "randomBook"
    )
    public static class AppplyPowerActionPatch {
        @SpireInsertPatch
                (
                        locator = Locator.class,
                        localvars = {
                                "possibleBooks"
                        }
                )
        public static void Insert(CursedTome __instance, ArrayList<AbstractRelic> possibleBooks) {
            possibleBooks.remove(possibleBooks.get(1));
        }

        private static class Locator extends SpireInsertLocator {
            private Locator() {
            }

            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "size");// 47
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);// 48
            }
        }
    }
}