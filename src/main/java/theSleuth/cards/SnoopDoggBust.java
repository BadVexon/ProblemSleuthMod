package theSleuth.cards;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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

public class SnoopDoggBust extends AbstractSleuthCard {

    public static final String ID = SleuthMod.makeID("SnoopDoggBust");
    public static final String IMG = makeCardPath("SnoopDoggBust.png");
    public static final AbstractCard.CardColor COLOR = TheSleuthChar.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.NONE;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final int COST = 1;
    private static final int WEAKNUM = 1;
    private static final int WEAKUP = 1;
    private boolean inDiscard = false;

    public SnoopDoggBust() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = WEAKNUM;
        AlwaysRetainField.alwaysRetain.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void update() {
        super.update();
        if (CardCrawlGame.isInARun()) {
            if (!AbstractDungeon.player.discardPile.contains(this)) {
                inDiscard = false;
            }
        }
    }


    @Override
    public void onMoveToDiscard() {
        if (!inDiscard) {
            for (AbstractMonster q : AbstractDungeon.getCurrRoom().monsters.monsters) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(q, AbstractDungeon.player, new WeakPower(q, magicNumber, false), magicNumber));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(q, AbstractDungeon.player, new VulnerablePower(q, 1, false), 1));
            }
            inDiscard = true;
        }
    }

    @Override
    public void triggerOnExhaust() {
        for (AbstractMonster q : AbstractDungeon.getCurrRoom().monsters.monsters) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(q, AbstractDungeon.player, new WeakPower(q, magicNumber, false), magicNumber));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(q, AbstractDungeon.player, new VulnerablePower(q, 1, false), 1));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(WEAKUP);
            initializeDescription();
        }
    }
}
