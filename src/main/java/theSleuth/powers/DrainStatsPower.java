package theSleuth.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theSleuth.SleuthMod;
import theSleuth.characters.TheSleuthChar;
import theSleuth.util.TextureLoader;

import static theSleuth.SleuthMod.makePowerPath;

public class DrainStatsPower extends AbstractPower {
    public AbstractCreature source;

    public static final String POWER_ID = SleuthMod.makeID("DrainStatsPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("drainstat_80.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("drainstat_30.png"));

    private int pulch;
    private int vim;
    private int imagination;
    private int tempImagination;

    public DrainStatsPower(final AbstractCreature owner) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = -1;

        type = PowerType.DEBUFF;
        isTurnBased = false;
        canGoNegative = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void onInitialApplication() {
        if (AbstractDungeon.player instanceof TheSleuthChar && TheSleuthChar.oglogCheck()) {
            pulch = ((TheSleuthChar) AbstractDungeon.player).playerPulch;
            vim = ((TheSleuthChar) AbstractDungeon.player).playerVim;
            imagination = ((TheSleuthChar) AbstractDungeon.player).playerImagine;

            ((TheSleuthChar) AbstractDungeon.player).playerPulch = 0;
            ((TheSleuthChar) AbstractDungeon.player).playerVim = 0;
            ((TheSleuthChar) AbstractDungeon.player).playerImagine = 0;

        }
    }

    @Override
    public void onRemove() {
        if (AbstractDungeon.player instanceof TheSleuthChar && TheSleuthChar.oglogCheck()) {
            ((TheSleuthChar) AbstractDungeon.player).playerPulch = pulch;
            ((TheSleuthChar) AbstractDungeon.player).playerVim = vim;
            ((TheSleuthChar) AbstractDungeon.player).playerImagine = imagination;

        }
    }

    @Override
    public void onVictory() {
        if (AbstractDungeon.player instanceof TheSleuthChar && TheSleuthChar.oglogCheck()) {
            ((TheSleuthChar) AbstractDungeon.player).playerPulch = pulch;
            ((TheSleuthChar) AbstractDungeon.player).playerVim = vim;
            ((TheSleuthChar) AbstractDungeon.player).playerImagine = imagination;
        }
    }

    @Override
    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, this.ID));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}