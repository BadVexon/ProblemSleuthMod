package theSleuth.cards;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theSleuth.SleuthMod;
import theSleuth.characters.TheSleuthChar;
import theSleuth.util.TreatyCurvies;

import static theSleuth.SleuthMod.makeCardPath;


public class UnDeEscalate extends AbstractSleuthCard {

    public static final String ID = SleuthMod.makeID("UnDeEscalate");
    public static final String IMG = makeCardPath("UnDeEscalate.png");
    public static final AbstractCard.CardColor COLOR = TheSleuthChar.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ALL;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final int COST = 0;
    private static final int PULCHRITUDE_AMT = 10;
    private static final int STACK_LOSS = -1;

    public UnDeEscalate() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.pulch = PULCHRITUDE_AMT;
        baseMagicNumber = magicNumber = STACK_LOSS;
        this.tags.add(CardTags.HEALING);
        AlwaysRetainField.alwaysRetain.set(this, true);
        this.setBackgroundTexture("theSleuthResources/images/512/bg_skill_sleuth_gray_pulch.png", "theSleuthResources/images/1024/bg_skill_sleuth_gray_pulch.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
    }

    @Override
    public void realUse(AbstractPlayer p, AbstractMonster m) {
        for (AbstractPower q : p.powers) {
            if (q.type == AbstractPower.PowerType.DEBUFF) {
                if (q.amount < 0 && q.canGoNegative) {
                    AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                        @Override
                        public void update() {
                            q.stackPower(1);
                            isDone = true;
                        }
                    });
                } else {
                    if (q.amount == 1) {
                        addToBot(new RemoveSpecificPowerAction(p, p, q.ID));
                    } else {
                        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, q.ID, 1));
                    }
                }
            }
        }
        for (int i = 0; i < 10; i++) {
            AbstractDungeon.effectList.add(new TreatyCurvies(p.hb.cX, p.hb.cY));
        }
        if (!upgraded) {
            for (AbstractMonster f : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!f.isDying && !f.isDead) {
                    for (AbstractPower q : f.powers) {
                        if (q.type == AbstractPower.PowerType.DEBUFF) {
                            if (q.amount < 0 && q.canGoNegative) {
                                AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                                    @Override
                                    public void update() {
                                        q.stackPower(1);
                                        isDone = true;
                                    }
                                });
                            } else {
                                if (q.amount == 1) {
                                    addToBot(new RemoveSpecificPowerAction(f, p, q.ID));
                                } else {
                                    AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(f, f, q.ID, 1));
                                }
                            }
                        }
                    }
                    for (int i = 0; i < 10; i++) {
                        AbstractDungeon.effectList.add(new TreatyCurvies(f.hb.cX, f.hb.cY));
                    }
                }
            }
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
