package theSleuth.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import theSleuth.SleuthMod;
import theSleuth.characters.TheSleuthChar;
import theSleuth.util.StatHelper;

@SpirePatch(
        clz = TopPanel.class,
        method = "render",
        paramtypes = {"com.badlogic.gdx.graphics.g2d.SpriteBatch"}
)
public class StatRenderPatch {
    public static void Prefix(TopPanel __instance, SpriteBatch sb) {
        if (AbstractDungeon.player instanceof TheSleuthChar) {
            if (((TheSleuthChar) AbstractDungeon.player).tempImagine > 0) {
                StatHelper.renderGenericTip(SleuthMod.newHitbox.hb.x, SleuthMod.newHitbox.hb.y + (SleuthMod.newHitbox.hb.height / 1.33F), "Stat Count", "#yPulchritude: #b" + ((TheSleuthChar) AbstractDungeon.player).playerPulch + " NL #yVim: #b" + ((TheSleuthChar) AbstractDungeon.player).playerVim + " NL #yImagination: #b" + ((TheSleuthChar) AbstractDungeon.player).playerImagine + " + #y" + ((TheSleuthChar) AbstractDungeon.player).tempImagine);
            } else {
                StatHelper.renderGenericTip(SleuthMod.newHitbox.hb.x, SleuthMod.newHitbox.hb.y + (SleuthMod.newHitbox.hb.height / 1.33F), "Stat Count", "#yPulchritude: #b" + ((TheSleuthChar) AbstractDungeon.player).playerPulch + " NL #yVim: #b" + ((TheSleuthChar) AbstractDungeon.player).playerVim + " NL #yImagination: #b" + ((TheSleuthChar) AbstractDungeon.player).playerImagine);
            }
            StatHelper.render(sb);
        }
    }
}
