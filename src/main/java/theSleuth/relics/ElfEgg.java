package theSleuth.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSleuth.SleuthMod;
import theSleuth.util.TextureLoader;

import static theSleuth.SleuthMod.makeRelicOutlinePath;
import static theSleuth.SleuthMod.makeRelicPath;

public class ElfEgg extends CustomRelic {

    public static final String ID = SleuthMod.makeID("ElfEgg");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("elf_egg.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("elf_egg.png"));

    public ElfEgg() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.FLAT);
    }

    @Override
    public void onEnterRestRoom() {
        AbstractDungeon.player.increaseMaxHp(3, true);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
