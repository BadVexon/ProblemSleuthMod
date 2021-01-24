package theSleuth.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSleuth.SleuthMod;
import theSleuth.actions.CandyRapidFireAction;
import theSleuth.actions.FlashAtkImgEffectButBetter;
import theSleuth.characters.TheSleuthChar;

import static theSleuth.SleuthMod.makeCardPath;

public class CandyMech extends AbstractSleuthCard {

    public static final String ID = SleuthMod.makeID("CandyMech");
    public static final String IMG = makeCardPath("CandyMech.png");
    public static final AbstractCard.CardColor COLOR = TheSleuthChar.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final int COST = 2;
    private static final int DAMAGE = 16;
    private static final int BLOCK = 16;
    private static final int IMAGINE_LOSS = 3;
    private static final int UPGRADE_LOSS = -1;
    private static final int IMAGINATION = 15;
    private static final int UPGRADE_IMAGINATION = 10;

    public CandyMech() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        imagin = IMAGINATION;
        this.baseBlock = BLOCK;
        this.baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = IMAGINE_LOSS;
        sleuthBaseSecondMagicNumber = sleuthSecondMagicNumber = imagin;
        this.tags.add(CardTags.HEALING);
        this.setBackgroundTexture("theSleuthResources/images/512/bg_attack_sleuth_gray_imagination.png", "theSleuthResources/images/1024/bg_attack_sleuth_gray_imagination.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
    }

    @Override
    public void realUse(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        AbstractDungeon.actionManager.addToBottom(new CandyRapidFireAction(m));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new FlashAtkImgEffectButBetter(m.hb.cX, m.hb.cY, AbstractGameAction.AttackEffect.BLUNT_HEAVY)));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        if (AbstractDungeon.player instanceof TheSleuthChar) {
            ((TheSleuthChar) AbstractDungeon.player).loseImagination(magicNumber);
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_LOSS);
            imagin = UPGRADE_IMAGINATION;
            upgradeSleuthSecondMagicNumber(-5);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
