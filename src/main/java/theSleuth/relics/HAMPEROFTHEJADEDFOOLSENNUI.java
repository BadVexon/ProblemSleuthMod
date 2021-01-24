package theSleuth.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ThornsPower;
import theSleuth.SleuthMod;
import theSleuth.util.TextureLoader;

import static theSleuth.SleuthMod.makeRelicOutlinePath;
import static theSleuth.SleuthMod.makeRelicPath;

public class HAMPEROFTHEJADEDFOOLSENNUI extends CustomRelic {


    public static final String ID = SleuthMod.makeID("HAMPEROFTHEJADEDFOOLSENNUI");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("hamper.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("hamper.png"));

    public HAMPEROFTHEJADEDFOOLSENNUI() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.FLAT);
    }

    public void atBattleStart() {
        this.flash();
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ThornsPower(AbstractDungeon.player, 10), 10));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
