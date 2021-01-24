package theSleuth.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSleuth.SleuthMod;

import static theSleuth.SleuthMod.makeCardPath;


public class OglogMRubbit extends AbstractSleuthCard {

    public static final String ID = SleuthMod.makeID("OglogMRubbit");
    public static final String IMG = makeCardPath("OglogMRubbit.png");
    public static final AbstractCard.CardColor COLOR = AbstractCard.CardColor.CURSE;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.CURSE;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.NONE;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.CURSE;
    private static final int COST = -2;

    public OglogMRubbit() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public AbstractCard makeCopy() {
        return new OglogMRubbit();
    }

    @Override
    public void upgrade() {
    }
}