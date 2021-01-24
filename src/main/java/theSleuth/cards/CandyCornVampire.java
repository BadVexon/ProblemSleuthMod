package theSleuth.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.MalleablePower;
import theSleuth.SleuthMod;
import theSleuth.characters.TheSleuthChar;
import theSleuth.util.GambitStuff;

import static theSleuth.SleuthMod.makeCardPath;


public class CandyCornVampire extends AbstractSleuthCard {

    public static final String ID = SleuthMod.makeID("CandyCornVampire");
    public static final String IMG = makeCardPath("CandyCornVampire.png");
    public static final AbstractCard.CardColor COLOR = TheSleuthChar.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.POWER;
    private static final int COST = 1;
    private static final int PULCH_AMT = 15;
    private static final int IMAGINATION_AMT = 15;

    public CandyCornVampire() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        pulch = PULCH_AMT;
        imagin = IMAGINATION_AMT;
        this.tags.add(CardTags.HEALING);
        baseMagicNumber = magicNumber = 3;
        sleuthBaseSecondMagicNumber = sleuthSecondMagicNumber = 2;
        this.setBackgroundTexture("theSleuthResources/images/512/bg_power_sleuth_gray_multistat.png", "theSleuthResources/images/1024/bg_power_sleuth_gray_multistat.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
    }

    @Override
    public void realUse(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < 20; i++) {
            AbstractDungeon.effectList.add(new GambitStuff(p.hb.cX, p.hb.cY));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, sleuthSecondMagicNumber), sleuthSecondMagicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MalleablePower(p, magicNumber), magicNumber));
        // AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NoMoreMalleable(p), 1));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(3);
            upgradeSleuthSecondMagicNumber(1);
            initializeDescription();
        }
    }
}
