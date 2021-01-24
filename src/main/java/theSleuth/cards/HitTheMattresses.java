package theSleuth.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSleuth.SleuthMod;
import theSleuth.characters.TheSleuthChar;
import theSleuth.util.MattressCurvy;
import theSleuth.util.MattressLine;
import theSleuth.util.MattressVFX;

import static theSleuth.SleuthMod.makeCardPath;


public class HitTheMattresses extends AbstractSleuthCard {

    public static final String ID = SleuthMod.makeID("HitTheMattresses");
    public static final String IMG = makeCardPath("HitTheMattresses.png");
    public static final AbstractCard.CardColor COLOR = TheSleuthChar.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final int COST = 2;
    private static final int UPGRADED_STAT = 10;

    public HitTheMattresses() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
    }

    @Override
    public void applyPowers() {
        if (AbstractDungeon.player instanceof TheSleuthChar) {
            this.baseDamage = (((TheSleuthChar) AbstractDungeon.player).playerImagine + ((TheSleuthChar) AbstractDungeon.player).tempImagine + ((TheSleuthChar) AbstractDungeon.player).playerPulch + ((TheSleuthChar) AbstractDungeon.player).playerVim);
        }
        super.applyPowers();
        if (!this.upgraded) {
            this.rawDescription = DESCRIPTION;
        } else {
            this.rawDescription = UPGRADE_DESCRIPTION;
        }

        if (AbstractDungeon.player instanceof TheSleuthChar) {
            if (((TheSleuthChar) AbstractDungeon.player).playerPulch >= this.pulch && ((TheSleuthChar) AbstractDungeon.player).playerVim >= this.vim && (((TheSleuthChar) AbstractDungeon.player).playerImagine + ((TheSleuthChar) AbstractDungeon.player).tempImagine) >= this.imagin)
            {
                this.rawDescription = this.rawDescription + EXTENDED_DESCRIPTION[0];
            }
        }
        this.initializeDescription();
    }

    @Override
    public void realUse(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.effectList.add(new MattressVFX(m));
        for (int r = 0; r < 18; r++) {
            AbstractDungeon.effectsQueue.add(new MattressLine(m.hb.cX, m.hb.cY));
        }
        for (int r = 0; r < 5; r++) {
            AbstractDungeon.effectsQueue.add(new MattressCurvy(m.hb.cX, m.hb.cY));
        }
        float AMOUNT = 2.5F;
        float WAT = this.damage * 0.05F;
        AMOUNT -= WAT;
        // CardCrawlGame.sound.playA("BLUNT_HEAVY", AMOUNT);
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.pulch = UPGRADED_STAT;
            this.vim = UPGRADED_STAT;
            this.imagin = UPGRADED_STAT;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.setBackgroundTexture("theSleuthResources/images/512/bg_attack_sleuth_gray_multistat.png", "theSleuthResources/images/1024/bg_attack_sleuth_gray_multistat.png");
            initializeDescription();
        }
    }
}
