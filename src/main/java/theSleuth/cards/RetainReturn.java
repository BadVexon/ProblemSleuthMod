package theSleuth.cards;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSleuth.SleuthMod;
import theSleuth.actions.SpecificNonChosenDiscardPileToHandAction;
import theSleuth.characters.TheSleuthChar;
import theSleuth.powers.DiscardAllRetainCardsAtEndOfTurn;

import static theSleuth.SleuthMod.makeCardPath;

public class RetainReturn extends AbstractSleuthCard {

    public static final String ID = SleuthMod.makeID("RetainReturn");
    public static final String IMG = makeCardPath("RetainReturn.png");
    public static final AbstractCard.CardColor COLOR = TheSleuthChar.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.NONE;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;
    private static final int PULCHRITUDE_AMT = 9;

    public RetainReturn() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.pulch = PULCHRITUDE_AMT;
        this.tags.add(CardTags.HEALING);
        this.setBackgroundTexture("theSleuthResources/images/512/bg_skill_sleuth_gray_pulch.png", "theSleuthResources/images/1024/bg_skill_sleuth_gray_pulch.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
    }

    @Override
    public void realUse(AbstractPlayer p, AbstractMonster m) {
        for (AbstractCard d : AbstractDungeon.player.discardPile.group) {
            if (AlwaysRetainField.alwaysRetain.get(d)) {
                AbstractDungeon.actionManager.addToBottom(new SpecificNonChosenDiscardPileToHandAction(d));
            }
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DiscardAllRetainCardsAtEndOfTurn(p, p, 1)));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}
