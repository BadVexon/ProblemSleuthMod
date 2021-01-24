package theSleuth.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseTempHpPower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theSleuth.SleuthMod;
import theSleuth.characters.TheSleuthChar;
import theSleuth.util.TextureLoader;

import static theSleuth.SleuthMod.makePowerPath;

public class AutoParry extends AbstractPower implements OnLoseTempHpPower {
    public AbstractCreature source;

    public static final String POWER_ID = SleuthMod.makeID("AutoParry");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("auto_parry_80.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("auto_parry_30.png"));

    public AutoParry(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public int onLoseHp(int damageAmount) {
        if (damageAmount > 0) {
            if (AbstractDungeon.player instanceof TheSleuthChar) {
                ((TheSleuthChar) AbstractDungeon.player).gainVim(1);
            }
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
        }
        return damageAmount;
    }

    @Override
    public int onLoseTempHp(DamageInfo info, int damageAmount) {
        if (damageAmount > 0 && !(TempHPField.tempHp.get(AbstractDungeon.player) < damageAmount)) {
            this.flash();
            if (AbstractDungeon.player instanceof TheSleuthChar) {
                ((TheSleuthChar) AbstractDungeon.player).gainVim(1);
            }
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
        }
        return damageAmount;
    }

    @Override
    public void onVictory() {
        if (AbstractDungeon.player instanceof TheSleuthChar) {
            ((TheSleuthChar) AbstractDungeon.player).gainVim(this.amount);
        }
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0];
        } else if (amount > 1) {
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        }
    }
}