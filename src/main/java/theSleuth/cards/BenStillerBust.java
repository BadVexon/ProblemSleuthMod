package theSleuth.cards;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import theSleuth.SleuthMod;
import theSleuth.characters.TheSleuthChar;

import static theSleuth.SleuthMod.makeCardPath;

public class BenStillerBust extends AbstractSleuthCard {

    public static final String ID = SleuthMod.makeID("BenStillerBust");
    public static final String IMG = makeCardPath("BenStillerBust.png");
    public static final AbstractCard.CardColor COLOR = TheSleuthChar.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final int COST = 1;
    private static final int DAMAGE = 4;
    private static final int MILLDAMAGE = 6;
    private static final int DAMAGEUP = 1;
    private boolean inDiscard = false;

    public BenStillerBust() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MILLDAMAGE;
        AlwaysRetainField.alwaysRetain.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    @Override
    public void onMoveToDiscard() {
        if (!inDiscard) {
            for (AbstractMonster q : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!q.isDying && !q.isDead) {
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new ExplosionSmallEffect(q.hb.cX, q.hb.cY), 0.1F));
                }
            }
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(magicNumber, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
            inDiscard = true;
        }
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
    public void triggerOnExhaust() {
        for (AbstractMonster q : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!q.isDying && !q.isDead) {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new ExplosionSmallEffect(q.hb.cX, q.hb.cY), 0.1F));
            }
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(magicNumber, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(DAMAGEUP);
            upgradeMagicNumber(DAMAGEUP);
            initializeDescription();
        }
    }
}
