package theSleuth.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSleuth.SleuthMod;
import theSleuth.characters.TheSleuthChar;

import static theSleuth.SleuthMod.makeCardPath;


public class WeirdPuzzleShit extends AbstractSleuthCard {

    public static final String ID = SleuthMod.makeID("WeirdPuzzleShit");
    public static final String IMG = makeCardPath("WeirdPuzzleShit.png");
    public static final AbstractCard.CardColor COLOR = TheSleuthChar.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final int COST = 1;
    private static final int PULCHRITUDE_AMT = 16;

    public WeirdPuzzleShit() {
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
        AbstractCard doTheJig = new MoustacheFire();
        if (this.upgraded) {
            doTheJig.upgrade();
        }
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(doTheJig));
    }

    @Override
    public void update() {
        super.update();
        if (AbstractDungeon.player instanceof TheSleuthChar) {
            if ((((TheSleuthChar)AbstractDungeon.player).playerPulch >= this.pulch) && (AbstractDungeon.player.hand.contains(this) || AbstractDungeon.player.drawPile.contains(this) || AbstractDungeon.player.discardPile.contains(this))) {
                this.costForTurn = ((AbstractDungeon.player.drawPile.size() / 2) + this.cost);
            }
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
