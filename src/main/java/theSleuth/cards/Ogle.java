package theSleuth.cards;

import basemod.ReflectionHacks;
import com.badlogic.gdx.math.RandomXS128;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;
import theSleuth.SleuthMod;
import theSleuth.actions.PatternShiftAction;
import theSleuth.characters.TheSleuthChar;
import theSleuth.util.LeerVFX;
import theSleuth.util.PatternShiftPreviewEffect;

import static theSleuth.SleuthMod.makeCardPath;


public class Ogle extends AbstractSleuthCard {

    public static final String ID = SleuthMod.makeID("Ogle");
    public static final String IMG = makeCardPath("Ogle.png");
    public static final AbstractCard.CardColor COLOR = TheSleuthChar.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final int COST = 1;
    private static final int DAMAGE = 7;
    private static final int IMAGIN_AMT = 5;
    public AbstractMonster newTarget;
    public boolean intentRevert = false;
    public EnemyMoveInfo move;

    public Ogle() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        exhaust = true;
        baseDamage = DAMAGE;
        imagin = IMAGIN_AMT;
        this.tags.add(CardTags.HEALING);
        this.setBackgroundTexture("theSleuthResources/images/512/bg_attack_sleuth_gray_imagination.png", "theSleuthResources/images/1024/bg_attack_sleuth_gray_imagination.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
    }

    @Override
    public void realUse(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new LeerVFX(p.hb.cX, p.hb.cY, m.hb.cX, m.hb.cY, p.flipHorizontal)));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        AbstractDungeon.actionManager.addToBottom(new PatternShiftAction(p, m));
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        super.calculateCardDamage(m);
        if (AbstractDungeon.player instanceof TheSleuthChar) {
            if ((((TheSleuthChar) AbstractDungeon.player).playerImagine + ((TheSleuthChar) AbstractDungeon.player).tempImagine) >= this.imagin) {
                if (m != null) {
                    if (this.newTarget == null) {
                        this.newTarget = m;
                        this.move = (EnemyMoveInfo) ReflectionHacks.getPrivate(m, AbstractMonster.class, "move");
                        int counter = AbstractDungeon.aiRng.counter;
                        long seed0 = (Long) ReflectionHacks.getPrivate(AbstractDungeon.aiRng.random, RandomXS128.class, "seed0");
                        long seed1 = (Long) ReflectionHacks.getPrivate(AbstractDungeon.aiRng.random, RandomXS128.class, "seed1");
                        theSleuth.actions.PatternShiftAction.previewNextIntent(this.newTarget);
                        AbstractDungeon.aiRng.counter = counter;
                        AbstractDungeon.aiRng.random.setState(seed0, seed1);
                    }
                }
                this.intentRevert = false;
            }
        } else {
            if (m != null) {
                if (this.newTarget == null) {
                    this.newTarget = m;
                    this.move = (EnemyMoveInfo) ReflectionHacks.getPrivate(m, AbstractMonster.class, "move");
                    int counter = AbstractDungeon.aiRng.counter;
                    long seed0 = (Long) ReflectionHacks.getPrivate(AbstractDungeon.aiRng.random, RandomXS128.class, "seed0");
                    long seed1 = (Long) ReflectionHacks.getPrivate(AbstractDungeon.aiRng.random, RandomXS128.class, "seed1");
                    theSleuth.actions.PatternShiftAction.previewNextIntent(this.newTarget);
                    AbstractDungeon.aiRng.counter = counter;
                    AbstractDungeon.aiRng.random.setState(seed0, seed1);
                }
            }
            this.intentRevert = false;
        }
    }

    @Override
    public void update() {
        super.update();
        if (AbstractDungeon.player instanceof TheSleuthChar) {
            if ((((TheSleuthChar) AbstractDungeon.player).playerImagine + ((TheSleuthChar) AbstractDungeon.player).tempImagine) >= this.imagin) {
                if (this.newTarget != null && !this.intentRevert) {
                    AbstractDungeon.effectsQueue.add(new PatternShiftPreviewEffect(this.newTarget.intentHb.cX, this.newTarget.intentHb.cY, 0.75F, 1.75F));
                }

                if (this.newTarget != null && this.intentRevert) {
                    CardCrawlGame.sound.stop("EVENT_SHINING");
                    this.newTarget.moveHistory.remove(this.newTarget.moveHistory.size() - 1);
                    if (this.newTarget.moveHistory.size() > 0) {
                        this.newTarget.moveHistory.remove(this.newTarget.moveHistory.size() - 1);
                    }

                    theSleuth.actions.PatternShiftAction.restorePreviewedSpecialCases(this.newTarget);
                    this.newTarget.setMove(this.move.nextMove, this.move.intent, this.move.baseDamage, this.move.multiplier, this.move.isMultiDamage);
                    this.newTarget.createIntent();
                    this.newTarget = null;
                }
                this.intentRevert = true;
            }
        } else {
            if (this.newTarget != null && !this.intentRevert) {
                AbstractDungeon.effectsQueue.add(new PatternShiftPreviewEffect(this.newTarget.intentHb.cX, this.newTarget.intentHb.cY, 0.75F, 1.75F));
            }

            if (this.newTarget != null && this.intentRevert) {
                CardCrawlGame.sound.stop("EVENT_SHINING");
                this.newTarget.moveHistory.remove(this.newTarget.moveHistory.size() - 1);
                if (this.newTarget.moveHistory.size() > 0) {
                    this.newTarget.moveHistory.remove(this.newTarget.moveHistory.size() - 1);
                }

                theSleuth.actions.PatternShiftAction.restorePreviewedSpecialCases(this.newTarget);
                this.newTarget.setMove(this.move.nextMove, this.move.intent, this.move.baseDamage, this.move.multiplier, this.move.isMultiDamage);
                this.newTarget.createIntent();
                this.newTarget = null;
            }
            this.intentRevert = true;
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            upgradeDamage(1);
            initializeDescription();
            AlwaysRetainField.alwaysRetain.set(this, true);
            retain = true;
        }
    }
}
