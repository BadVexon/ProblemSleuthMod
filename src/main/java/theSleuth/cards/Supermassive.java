package theSleuth.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.HeartBuffEffect;
import theSleuth.SleuthMod;
import theSleuth.characters.TheSleuthChar;
import theSleuth.powers.ReflectDamagePower;
import theSleuth.util.SparkCurvyEffect;

import static theSleuth.SleuthMod.makeCardPath;


public class Supermassive extends AbstractSleuthCard {

    public static final String ID = SleuthMod.makeID("Supermassive");
    public static final String IMG = makeCardPath("Supermassive.png");
    public static final AbstractCard.CardColor COLOR = TheSleuthChar.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final int COST = 3;
    private static final int UPGRADE_COST = 2;
    private static final int VIM_AMT = 5;

    public Supermassive() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        exhaust = true;
        vim = VIM_AMT;
        this.tags.add(CardTags.HEALING);
        this.setBackgroundTexture("theSleuthResources/images/512/bg_skill_sleuth_gray_vim.png", "theSleuthResources/images/1024/bg_skill_sleuth_gray_vim.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
    }

    @Override
    public void realUse(AbstractPlayer p, AbstractMonster m) {
        for (int r = 0; r < 10; r++) {
            AbstractDungeon.effectList.add(new SparkCurvyEffect(p.hb.cX, p.hb.cY, Color.YELLOW, false));
        }
        AbstractDungeon.effectList.add(new HeartBuffEffect(p.hb.cX, p.hb.cY));
        for (int r = 0; r < 10; r++) {
            AbstractDungeon.effectList.add(new SparkCurvyEffect(p.hb.cX, p.hb.cY, Color.YELLOW, false));
        }
        if (AbstractDungeon.player instanceof TheSleuthChar) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, ((TheSleuthChar) AbstractDungeon.player).playerVim));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ReflectDamagePower(p, 1), 1));
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            initializeDescription();
        }
    }
}