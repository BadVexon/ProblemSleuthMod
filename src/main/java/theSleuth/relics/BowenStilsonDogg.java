package theSleuth.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSleuth.SleuthMod;
import theSleuth.actions.DiscardFromDrawPileAction;
import theSleuth.util.TextureLoader;

import static theSleuth.SleuthMod.makeRelicOutlinePath;
import static theSleuth.SleuthMod.makeRelicPath;

public class BowenStilsonDogg extends CustomRelic {

    public static final String ID = SleuthMod.makeID("BowenStilsonDogg");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("bowen_stilson_dogg.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("bowen_stilson_dogg.png"));

    public BowenStilsonDogg() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.CLINK);
    }

    @Override
    public void atBattleStartPreDraw() {
        AbstractDungeon.actionManager.addToTop(new DiscardFromDrawPileAction(1));
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
