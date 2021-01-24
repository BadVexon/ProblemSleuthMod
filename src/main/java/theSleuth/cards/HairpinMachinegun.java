package theSleuth.cards;

import com.badlogic.gdx.math.MathUtils;
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
import theSleuth.actions.FlashAtkImgEffectButBetter;
import theSleuth.actions.RelicRapidFireAction;
import theSleuth.characters.TheSleuthChar;

import static theSleuth.SleuthMod.makeCardPath;


public class HairpinMachinegun extends AbstractSleuthCard {

    public static final String ID = SleuthMod.makeID("HairpinMachinegun");
    public static final String IMG = makeCardPath("Machinegun.png");
    public static final AbstractCard.CardColor COLOR = TheSleuthChar.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final int COST = 1;
    private static final int DAMAGE = 8;
    private static final int UPGRADE_PLUS_DMG = 2;
    private static final int BLOCK = 7;
    private static final int UPGRADE_PLUS_BLOCK = 2;
    private boolean curForm = true;

    public HairpinMachinegun() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if (!this.curForm) {
            this.name = "Hairpin";
            this.type = CardType.SKILL;
            this.target = CardTarget.SELF;
            this.rawDescription = "Gain !B! Block twice. NL Switches form whenever played.";
            if (whiskeyd)
            {
                this.rawDescription = this.rawDescription + " NL Retain.";
            }
            loadCardImage(makeCardPath("Hairpin.png"));
        } else {
            this.name = "Machinegun";
            this.type = CardType.ATTACK;
            this.target = CardTarget.ENEMY;
            this.rawDescription = "Deal !D! damage twice. NL Switches form whenever played.";
            if (whiskeyd)
            {
                this.rawDescription = this.rawDescription + " NL Retain.";
            }
            loadCardImage(makeCardPath("Machinegun.png"));
        }
        this.initializeDescription();
    }

    @Override
    public void update() {
        super.update();
        if (AbstractDungeon.player != null && (AbstractDungeon.player.drawPile.contains(this) || AbstractDungeon.player.hand.contains(this) || AbstractDungeon.player.discardPile.contains(this))) {
            this.applyPowers();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!this.curForm) {
            for (int i = 0; i < 2; i++)
            {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
            }
            this.curForm = true;
        } else {
            for (int i = 0; i < 2; i++) {
                if (!AbstractDungeon.player.relics.isEmpty()) {
                    AbstractDungeon.actionManager.addToBottom(new RelicRapidFireAction(m, AbstractDungeon.player.relics.get(MathUtils.random(AbstractDungeon.player.relics.size() - 1))));
                }
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new FlashAtkImgEffectButBetter(m.hb.cX, m.hb.cY, AbstractGameAction.AttackEffect.BLUNT_LIGHT)));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
            }
            this.curForm = false;
        }
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