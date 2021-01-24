//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package theSleuth.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theSleuth.SleuthMod;
import theSleuth.util.TextureLoader;

import static theSleuth.SleuthMod.makePowerPath;

public class SilencedBuffsPower extends AbstractPower implements OnReceivePowerPower {
    public static final String POWER_ID = SleuthMod.makeID("SilencedBuffsPower");
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private static final PowerStrings powerStrings;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("NoBuffs_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("NoBuffs_32.png"));

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public SilencedBuffsPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.canGoNegative = false;

        this.type = PowerType.DEBUFF;
        this.isTurnBased = true;
        this.updateDescription();
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
    }

    public void updateDescription() {
        if (this.owner != null && !this.owner.isPlayer) {
            this.description = FontHelper.colorString(this.owner.name, "y") + DESCRIPTIONS[0];
        }
    }

    public void onInitialApplication() {
        if (this.owner.hasPower(StrengthPower.POWER_ID)) {
            if (this.owner.getPower(StrengthPower.POWER_ID).amount > 0) {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, StrengthPower.POWER_ID));
            }
        }
    }

    public boolean onReceivePower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.ID.equals(StrengthPower.POWER_ID) && source == this.owner) {
            if (this.owner.hasPower(StrengthPower.POWER_ID)) {
                if (this.owner.getPower(StrengthPower.POWER_ID).amount >= 0) {
                    this.flash();
                    return false;
                }
                return true;
            }
        } else {
            return true;
        }
        return true;
    }
}
