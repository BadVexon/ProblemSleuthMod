package theSleuth.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSleuth.SleuthMod;
import theSleuth.actions.FlashAtkImgEffectButBetter;
import theSleuth.actions.RelicRapidFireAction;
import theSleuth.characters.TheSleuthChar;

import static theSleuth.SleuthMod.makeCardPath;


public class Diplomacy extends AbstractSleuthCard {

    public static final String ID = SleuthMod.makeID("Diplomacy");
    public static final String IMG = makeCardPath("Diplomacy.png");
    public static final AbstractCard.CardColor COLOR = TheSleuthChar.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.BASIC;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final int COST = 1;
    private static final int DAMAGE = 7;
    private static final int UPGRADE_PLUS_DMG = 2;
    private static final int BLOCK = 7;
    private static final int UPGRADE_PLUS_BLOCK = 2;
    private static final int PULCHRITUDE_AMT = 5;

    public Diplomacy() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
        pulch = PULCHRITUDE_AMT;
        this.tags.add(CardTags.HEALING);
        this.setBackgroundTexture("theSleuthResources/images/512/bg_attack_sleuth_gray_pulch.png", "theSleuthResources/images/1024/bg_attack_sleuth_gray_pulch.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
    }

    @Override
    public void realUse(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        if (AbstractDungeon.player.relics.size() > 0) {
            AbstractDungeon.actionManager.addToBottom(new RelicRapidFireAction(m, AbstractDungeon.player.relics.get(AbstractDungeon.cardRandomRng.random(AbstractDungeon.player.relics.size() - 1))));
        }
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new FlashAtkImgEffectButBetter(m.hb.cX, m.hb.cY, AbstractGameAction.AttackEffect.BLUNT_HEAVY)));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        AbstractDungeon.actionManager.addToBottom(new ExhaustAction(1, false, false, false));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }
}
