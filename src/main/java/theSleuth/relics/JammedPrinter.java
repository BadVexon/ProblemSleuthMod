package theSleuth.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSleuth.SleuthMod;
import theSleuth.util.TextureLoader;

import static theSleuth.SleuthMod.makeRelicOutlinePath;
import static theSleuth.SleuthMod.makeRelicPath;

public class JammedPrinter extends CustomRelic {

    public static final String ID = SleuthMod.makeID("JammedPrinter");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("jammed_printer.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("jammed_printer.png"));

    public JammedPrinter() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.CLINK);
    }

    @Override
    public int getPrice() {
        return AbstractDungeon.cardRandomRng.random(70, 100);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
