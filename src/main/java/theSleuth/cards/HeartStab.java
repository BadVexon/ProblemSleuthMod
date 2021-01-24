package theSleuth.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theSleuth.SleuthMod;
import theSleuth.actions.FlashAtkImgEffectButBetter;
import theSleuth.characters.TheSleuthChar;
import theSleuth.util.HeartVFX;
import theSleuth.util.MidnightCurvy;
import theSleuth.util.MidnightLine;

import static theSleuth.SleuthMod.makeCardPath;


public class HeartStab extends AbstractSleuthCard {

    public static final String ID = SleuthMod.makeID("HeartStab");
    public static final String IMG = makeCardPath("HeartStab.png");
    public static final AbstractCard.CardColor COLOR = TheSleuthChar.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final int COST = 1;
    private static final int DAMAGE = 10;
    private static final int DAMAGE_UP = 4;
    private static final int AMT = 2;

    public HeartStab() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = AMT;
        this.setBackgroundTexture("theSleuthResources/images/512/bg_attack_sleuth_gray_antistat.png", "theSleuthResources/images/1024/bg_attack_sleuth_gray_antistat.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < 18; i++) {
            AbstractDungeon.effectsQueue.add(new MidnightLine(m.hb.cX, m.hb.cY, Color.GRAY.cpy()));
        }
        for (int i = 0; i < 5; i++) {
            AbstractDungeon.effectsQueue.add(new MidnightCurvy(m.hb.cX, m.hb.cY, Color.GRAY.cpy(), false));
        }
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new HeartVFX(m)));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new FlashAtkImgEffectButBetter(m.hb.cX, m.hb.cY, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL)));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, magicNumber, false), magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, magicNumber, false), magicNumber));
        if (AbstractDungeon.player instanceof TheSleuthChar) {
            ((TheSleuthChar) AbstractDungeon.player).loseHighestStat();
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(DAMAGE_UP);
            initializeDescription();
        }
    }
}
