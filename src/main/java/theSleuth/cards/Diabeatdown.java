package theSleuth.cards;

import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSleuth.SleuthMod;
import theSleuth.actions.BeatdownAction;
import theSleuth.characters.TheSleuthChar;

import static theSleuth.SleuthMod.makeCardPath;


public class Diabeatdown extends AbstractSleuthCard {

    public static final String ID = SleuthMod.makeID("Diabeatdown");
    public static final String IMG = makeCardPath("Diabeatdown.png");
    public static final AbstractCard.CardColor COLOR = TheSleuthChar.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final int COST = 2;
    private static final int DAMAGE = 24;
    private static final int DAMAGE_UP = 6;
    private static final int VIM_AMT = 15;

    public Diabeatdown() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        vim = VIM_AMT;
        this.tags.add(CardTags.HEALING);
        this.setBackgroundTexture("theSleuthResources/images/512/bg_attack_sleuth_gray_vim.png", "theSleuthResources/images/1024/bg_attack_sleuth_gray_vim.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
    }

    @Override
    public void realUse(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.8F));
        AbstractDungeon.actionManager.addToBottom(new BeatdownAction(new DamageInfo(p, damage, damageTypeForTurn)));
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
