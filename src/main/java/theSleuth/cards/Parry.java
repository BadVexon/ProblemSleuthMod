package theSleuth.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import theSleuth.SleuthMod;
import theSleuth.characters.TheSleuthChar;
import theSleuth.powers.AutoParry;

import static theSleuth.SleuthMod.makeCardPath;


public class Parry extends AbstractSleuthCard {

    public static final String ID = SleuthMod.makeID("Parry");
    public static final String IMG = makeCardPath("Parry.png");
    public static final AbstractCard.CardColor COLOR = TheSleuthChar.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.POWER;
    private static final int COST = 1;
    private static final int VIM_AMT = 5;
    private static final int ARTIFACT_AMT = 1;
    private static final int UPGRADE_PLUS_ARTIFACT = 1;
    private static final int THORNS = 2;
    private static final int UPGRADE_PLUS_THORN = 1;

    public Parry() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        vim = VIM_AMT;
        baseMagicNumber = magicNumber = ARTIFACT_AMT;
        sleuthBaseSecondMagicNumber = sleuthSecondMagicNumber = THORNS;
        this.tags.add(CardTags.HEALING);
        this.setBackgroundTexture("theSleuthResources/images/512/bg_power_sleuth_gray_vim.png", "theSleuthResources/images/1024/bg_power_sleuth_gray_vim.png");
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
    }

    @Override
    public void realUse(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ArtifactPower(p, magicNumber), magicNumber));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new ThornsPower(p, sleuthSecondMagicNumber), sleuthSecondMagicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new AutoParry(p, p, 1), 1));
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_ARTIFACT);
            upgradeSleuthSecondMagicNumber(UPGRADE_PLUS_THORN);
            initializeDescription();
        }
    }
}