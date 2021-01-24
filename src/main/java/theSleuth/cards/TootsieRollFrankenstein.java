package theSleuth.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSleuth.SleuthMod;
import theSleuth.characters.TheSleuthChar;
import theSleuth.powers.TootsieRollFrankensteinPower;
import theSleuth.util.GambitStuff;

import static theSleuth.SleuthMod.makeCardPath;

public class TootsieRollFrankenstein extends AbstractSleuthCard {

    public static final String ID = SleuthMod.makeID("TootsieRollFrankenstein");
    public static final String IMG = makeCardPath("Tootsiestein.png");
    public static final AbstractCard.CardColor COLOR = TheSleuthChar.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.POWER;
    private static final int COST = 1;
    private static final int IMAGIN_AMT = 15;
    private static final int VIM_AMT = 15;

    public TootsieRollFrankenstein() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        imagin = IMAGIN_AMT;
        vim = VIM_AMT;
        baseMagicNumber = magicNumber = 1;
        this.tags.add(CardTags.HEALING);
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
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new TootsieRollFrankensteinPower(p, p, magicNumber), magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
            initializeDescription();
        }
    }
}
