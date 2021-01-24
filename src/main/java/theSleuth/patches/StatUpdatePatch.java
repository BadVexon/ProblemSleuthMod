package theSleuth.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import theSleuth.characters.TheSleuthChar;

import static theSleuth.SleuthMod.newHitbox;

@SpirePatch(
        clz = TopPanel.class,
        method = "update"
)
public class StatUpdatePatch {
    public static void Prefix(TopPanel __instance) {
        if (AbstractDungeon.player instanceof TheSleuthChar) {
            newHitbox.dragUpdate();
            newHitbox.update();
        }
    }
}
