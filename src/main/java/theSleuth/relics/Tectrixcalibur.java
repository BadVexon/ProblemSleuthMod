package theSleuth.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theSleuth.SleuthMod;
import theSleuth.util.TextureLoader;

import static theSleuth.SleuthMod.makeRelicOutlinePath;
import static theSleuth.SleuthMod.makeRelicPath;

public class Tectrixcalibur extends CustomRelic {

    public static final String ID = SleuthMod.makeID("Tectrixcalibur");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("tectrixcalibur.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("tectrixcalibur.png"));

    public Tectrixcalibur() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public void atBattleStart() {
        this.flash();
        if (AbstractDungeon.ascensionLevel < 15) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 2), 2));
        } else {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 1), 1));
        }
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, 1), 1));
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    public String getUpdatedDescription() {
        if (AbstractDungeon.ascensionLevel >= 15) {
            return DESCRIPTIONS[1];
        }
        return DESCRIPTIONS[0];
    }
}
