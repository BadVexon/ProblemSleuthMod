package theSleuth.cards;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSleuth.SleuthMod;
import theSleuth.characters.TheSleuthChar;
import theSleuth.powers.NoBuffs;
import theSleuth.util.TreatyCurvies;

import static theSleuth.SleuthMod.makeCardPath;


public class Treaty extends AbstractSleuthCard {

    public static final String ID = SleuthMod.makeID("Treaty");
    public static final String IMG = makeCardPath("Treaty.png");
    public static final AbstractCard.CardColor COLOR = TheSleuthChar.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final int COST = 0;
    private static final int PULCHRITUDE_AMT = 10;
    private static final int TURN_NUM = 1;

    public Treaty() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.pulch = PULCHRITUDE_AMT;
        AlwaysRetainField.alwaysRetain.set(this, true);
        baseMagicNumber = magicNumber = TURN_NUM;
        this.tags.add(CardTags.HEALING);
        this.setBackgroundTexture("theSleuthResources/images/512/bg_skill_sleuth_gray_pulch.png", "theSleuthResources/images/1024/bg_skill_sleuth_gray_pulch.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
    }

    @Override
    public void realUse(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NoBuffs(p, magicNumber), magicNumber));
        for (int i = 0; i < 10; i++) {
            AbstractDungeon.effectList.add(new TreatyCurvies(p.hb.cX, p.hb.cY, false));
        }
        if (upgraded) {
            for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!monster.isDead && !monster.isDying) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new NoBuffs(monster, magicNumber), magicNumber));
                    for (int i = 0; i < 10; i++) {
                        AbstractDungeon.effectList.add(new TreatyCurvies(monster.hb.cX, monster.hb.cY, false));
                    }
                }
            }
        }
        else
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new NoBuffs(m, magicNumber), magicNumber));
            for (int i = 0; i < 10; i++) {
                AbstractDungeon.effectList.add(new TreatyCurvies(m.hb.cX, m.hb.cY));
            }
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.target = CardTarget.ALL;
            initializeDescription();
        }
    }
}
