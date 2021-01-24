package theSleuth.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theSleuth.SleuthMod;
import theSleuth.characters.TheSleuthChar;

import static theSleuth.SleuthMod.makeCardPath;


public class WeighIn extends AbstractSleuthCard {

    public static final String ID = SleuthMod.makeID("WeighIn");
    public static final String IMG = makeCardPath("WeighIn.png");
    public static final AbstractCard.CardColor COLOR = TheSleuthChar.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final int COST = 1;

    public WeighIn() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.isInnate = true;
        this.exhaust = true;
        baseMagicNumber = magicNumber = 2;
        sleuthBaseSecondMagicNumber = sleuthSecondMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster q : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!q.isDying && !q.isDead) {
                addToBot(new ApplyPowerAction(q, p, new WeakPower(q, magicNumber, false), magicNumber));
                addToBot(new ApplyPowerAction(q, p, new VulnerablePower(q, sleuthSecondMagicNumber, false), sleuthSecondMagicNumber));
            }
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeSleuthSecondMagicNumber(1);
            initializeDescription();
        }
    }
}