package theSleuth.cards;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import theSleuth.SleuthMod;
import theSleuth.actions.UpgradeInCombatAction;
import theSleuth.characters.TheSleuthChar;
import theSleuth.util.OpportunityCurvy;

import static theSleuth.SleuthMod.makeCardPath;

public class OpportunityCost extends AbstractSleuthCard {

    public static final String ID = SleuthMod.makeID("OpportunityCost");
    public static final String IMG = makeCardPath("OpportunityCost.png");
    public static final AbstractCard.CardColor COLOR = TheSleuthChar.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final int COST = 0;

    public OpportunityCost() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.purgeOnUse = true;
        FleetingField.fleeting.set(this, true);
        this.tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new UpgradeInCombatAction());
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new UpgradeShineEffect(Settings.WIDTH / 2, Settings.HEIGHT / 2)));
        if (!upgraded && AbstractDungeon.player instanceof TheSleuthChar) {
            ((TheSleuthChar) AbstractDungeon.player).loseImagination(1);
            ((TheSleuthChar) AbstractDungeon.player).losePulchritude(1);
            ((TheSleuthChar) AbstractDungeon.player).loseVim(1);
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
