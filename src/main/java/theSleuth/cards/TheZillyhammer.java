package theSleuth.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.VerticalImpactEffect;
import theSleuth.SleuthMod;
import theSleuth.characters.TheSleuthChar;

import static theSleuth.SleuthMod.makeCardPath;

public class TheZillyhammer extends AbstractSleuthCard {

    public static final String ID = SleuthMod.makeID("TheZillyhammer");
    public static final String IMG = makeCardPath("TheZillyhammer.png");
    public static final AbstractCard.CardColor COLOR = TheSleuthChar.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final int COST = 2;
    private static final int DAMAGE = 30;
    private static final int DAMAGE_UP = 10;
    private static final int IMAGINE_LOSS = 5;
    private static final int IMAGINATION = 20;

    public TheZillyhammer() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        imagin = IMAGINATION;
        this.baseDamage = DAMAGE;
        this.isMultiDamage = true;
        baseMagicNumber = magicNumber = IMAGINE_LOSS;
        this.tags.add(CardTags.HEALING);
        this.setBackgroundTexture("theSleuthResources/images/512/bg_attack_sleuth_gray_imagination.png", "theSleuthResources/images/1024/bg_attack_sleuth_gray_imagination.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
    }

    @Override
    public void realUse(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!monster.isDead && !monster.isDying) {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new VerticalImpactEffect(monster.hb.cX + monster.hb.width / 4.0F, monster.hb.cY - monster.hb.height / 4.0F)));
            }
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        if (AbstractDungeon.player instanceof TheSleuthChar) {
            ((TheSleuthChar) AbstractDungeon.player).loseImagination(magicNumber);

        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(DAMAGE_UP);
            upgradeMagicNumber(-1);
            initializeDescription();
        }
    }
}
