package theSleuth.cards;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSleuth.SleuthMod;
import theSleuth.characters.TheSleuthChar;
import theSleuth.util.BouncelessShrapnelEffect;
import theSleuth.util.FireShrapnelEffect;

import static theSleuth.SleuthMod.makeCardPath;

public class PezUzi extends AbstractSleuthCard {

    public static final String ID = SleuthMod.makeID("PezUzi");
    public static final String IMG = makeCardPath("PezUzi.png");
    public static final AbstractCard.CardColor COLOR = TheSleuthChar.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ALL_ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final int COST = 1;
    private static final int DAMAGE = 8;
    private static final int DAMAGE_UP = 3;
    private static final int IMAGINE_BOON = 1;
    private static final int IMAGINE_BOON_UP = 1;
    private static final int IMAGINATION = 3;

    public PezUzi() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        imagin = IMAGINATION;
        this.baseDamage = DAMAGE;
        this.isMultiDamage = true;
        baseMagicNumber = magicNumber = IMAGINE_BOON;
        this.tags.add(CardTags.HEALING);
        this.setBackgroundTexture("theSleuthResources/images/512/bg_attack_sleuth_gray_imagination.png", "theSleuthResources/images/1024/bg_attack_sleuth_gray_imagination.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
    }

    @Override
    public void realUse(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster q : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!q.isDying && !q.isDead && !q.halfDead) {
                for (int i = 0; i < 15; i++) {
                    int r = MathUtils.random(1);
                    switch (r) {
                        case 0:
                            AbstractDungeon.effectsQueue.add(new FireShrapnelEffect(q));
                        case 1:
                            AbstractDungeon.effectsQueue.add(new BouncelessShrapnelEffect(q));
                    }
                }
            }
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
        if (AbstractDungeon.player instanceof TheSleuthChar) {
            ((TheSleuthChar) AbstractDungeon.player).gainTempImagination(magicNumber);
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(DAMAGE_UP);
            upgradeMagicNumber(IMAGINE_BOON_UP);
            initializeDescription();
        }
    }
}
