package theSleuth.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.GoldenSlashEffect;
import theSleuth.SleuthMod;
import theSleuth.characters.TheSleuthChar;

import static theSleuth.SleuthMod.makeCardPath;


public class LeverageHeight extends AbstractSleuthCard {

    public static final String ID = SleuthMod.makeID("LeverageHeight");
    public static final String IMG = makeCardPath("LeverageHeight.png");
    public static final AbstractCard.CardColor COLOR = TheSleuthChar.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;
    private static final int COST = 1;
    private static final int DAMAGE = 12;
    private static final int DAMAGE_UP = 3;
    private static final int DISCARDS = 1;
    private static final int DISCARDS_UP = 1;

    public LeverageHeight() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        pulch = 12;
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = DISCARDS;
        this.tags.add(CardTags.HEALING);
        this.setBackgroundTexture("theSleuthResources/images/512/bg_attack_sleuth_gray_pulch.png", "theSleuthResources/images/1024/bg_attack_sleuth_gray_pulch.png");
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
    }

    @Override
    public void realUse(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new GoldenSlashEffect(m.hb.cX, m.hb.cY, true)));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        AbstractDungeon.actionManager.addToBottom(new DiscardAction(p, p, magicNumber, false));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, magicNumber));
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(DAMAGE_UP);
            upgradeMagicNumber(DISCARDS_UP);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}