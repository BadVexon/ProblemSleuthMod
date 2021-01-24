package theSleuth.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSleuth.SleuthMod;
import theSleuth.actions.DMKHatAction;
import theSleuth.util.SingleTargetRelic;
import theSleuth.util.TextureLoader;

import static theSleuth.SleuthMod.makeRelicOutlinePath;
import static theSleuth.SleuthMod.makeRelicPath;

public class JesterOfEncumbrance extends CustomRelic  {

    public static final String ID = SleuthMod.makeID("JesterOfEncumbrance");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("encumbrance_jester.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("encumbrance_jester.png"));

    public JesterOfEncumbrance() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    @Override
    public void atBattleStartPreDraw() {
        AbstractDungeon.actionManager.addToBottom(new DMKHatAction());
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
