package theSleuth.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSleuth.SleuthMod;
import theSleuth.util.TextureLoader;

import static theSleuth.SleuthMod.makeRelicOutlinePath;
import static theSleuth.SleuthMod.makeRelicPath;

public class SaltedMelon extends CustomRelic {

    public static final String ID = SleuthMod.makeID("SaltedMelon");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("salted_melon.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("salted_melon.png"));

    public SaltedMelon() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.FLAT);
    }

    public void onEnterRestRoom() {
        AbstractDungeon.player.increaseMaxHp(1, true);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
