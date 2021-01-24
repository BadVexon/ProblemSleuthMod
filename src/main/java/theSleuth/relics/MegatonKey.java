package theSleuth.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSleuth.SleuthMod;
import theSleuth.util.TextureLoader;

import static theSleuth.SleuthMod.makeRelicOutlinePath;
import static theSleuth.SleuthMod.makeRelicPath;

public class MegatonKey extends CustomRelic {

    public static final String ID = SleuthMod.makeID("MegatonKey");

    private static boolean loseRelic = false;

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("megaton_key.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("megaton_key.png"));

    public MegatonKey() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.HEAVY);
    }

    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;
        loseRelic = true;

    }

    public static void relicBullshit() {
        if (loseRelic) {
            String id1 = AbstractDungeon.player.relics.get(0).relicId;
            String id2 = AbstractDungeon.player.relics.get(1).relicId;
            AbstractDungeon.player.loseRelic(id1);
            AbstractDungeon.player.loseRelic(id2);
            loseRelic = false;
        }
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.relics.size() > 1;
    }

    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
