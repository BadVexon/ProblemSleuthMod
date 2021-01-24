package theSleuth.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSleuth.SleuthMod;
import theSleuth.characters.TheSleuthChar;
import theSleuth.util.ClubVFX;
import theSleuth.util.MidnightCurvy;
import theSleuth.util.MidnightLine;

import static theSleuth.SleuthMod.makeCardPath;

public class ClubClubClub extends AbstractSleuthCard {

    public static final String ID = SleuthMod.makeID("ClubClubClub");
    public static final String IMG = makeCardPath("ClubClubClub.png");
    public static final AbstractCard.CardColor COLOR = TheSleuthChar.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ALL_ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final int COST = 1;
    private static final int DAMAGE = 6;
    private static final int DAMAGE_UP = 2;

    public ClubClubClub() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.isMultiDamage = true;
        this.setBackgroundTexture("theSleuthResources/images/512/bg_attack_sleuth_gray_antistat.png", "theSleuthResources/images/1024/bg_attack_sleuth_gray_antistat.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < 2; i++) {
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            for (AbstractMonster q : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!q.isDead && !q.isDying) {
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new ClubVFX(q)));
                    for (int r = 0; r < 18; r++) {
                        AbstractDungeon.effectList.add(new MidnightLine(q.hb.cX, q.hb.cY, Color.GRAY.cpy()));
                    }
                    for (int r = 0; r < 5; r++) {
                        AbstractDungeon.effectList.add(new MidnightCurvy(q.hb.cX, q.hb.cY, Color.GRAY.cpy(), false));
                    }
                }
            }
        }
        if (AbstractDungeon.player instanceof TheSleuthChar) {
            ((TheSleuthChar) AbstractDungeon.player).loseHighestStat();
        }
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
