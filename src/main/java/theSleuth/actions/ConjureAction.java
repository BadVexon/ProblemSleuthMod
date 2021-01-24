package theSleuth.actions;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theSleuth.SleuthMod;
import theSleuth.characters.TheSleuthChar;
import theSleuth.patches.CenterGridCardSelectScreen;
import theSleuth.util.ConjureVFX;

import static theSleuth.SleuthMod.makeCardPath;

public class ConjureAction extends AbstractGameAction {
    private boolean pickCard = false;
    private int damage = -1;
    private int block = -1;
    private AbstractMonster target;
    private AbstractCard funCard;
    private DamageInfo.DamageType damageTypeForTurn;

    public ConjureAction(AbstractMonster targetMonster, DamageInfo.DamageType damageType, AbstractCard q) {
        duration = Settings.ACTION_DUR_XFAST;
        actionType = ActionType.WAIT;
        target = targetMonster;
        funCard = q;
        damageTypeForTurn = damageType;
    }

    @Override
    public void update() {
        funCard.applyPowers();
        funCard.calculateCardDamage(target);
        if (duration == Settings.ACTION_DUR_XFAST) {
            pickCard = true;
            CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            group.addToTop(new ShiftingChoiceCard("Attack", "Attack", makeCardPath("Conjure.png"), "Deal !D! damage.", AbstractCard.CardType.ATTACK, funCard.damage, -1));
            group.addToTop(new ShiftingChoiceCard("Weak", "Weak", makeCardPath("SkillConjure.png"), "Apply 2 Weak.", AbstractCard.CardType.SKILL, -1, -1));
            group.addToTop(new ShiftingChoiceCard("Vulnerable", "Vulnerable", makeCardPath("SkillConjure.png"), "Apply 2 Vulnerable.", AbstractCard.CardType.SKILL, -1, -1));

            CenterGridCardSelectScreen.centerGridSelect = true;
            AbstractDungeon.gridSelectScreen.open(group, 1, "Choose an Action", false);
        } else if (pickCard && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            pickCard = false;
            AbstractCard cardChoice = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            CenterGridCardSelectScreen.centerGridSelect = false;

            if (cardChoice.cardID.contains("Attack")) {
                AbstractDungeon.actionManager.addToTop(new DamageAction(target, new DamageInfo(AbstractDungeon.player, cardChoice.baseDamage, damageTypeForTurn)));
                AbstractDungeon.actionManager.addToTop(new VFXAction(new ConjureVFX(target)));
            }
            if (cardChoice.cardID.contains("Vulnerable")) {
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(target, AbstractDungeon.player, new VulnerablePower(target, 2, false), 2));
            }
            if (cardChoice.cardID.contains("Weak")) {
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(target, AbstractDungeon.player, new WeakPower(target, 2, false), 2));
            }

            isDone = true;
        }
        tickDuration();
    }

    private static class ShiftingChoiceCard extends CustomCard {
        private static final int COST = -2;
        private String baseID;

        ShiftingChoiceCard(String id, String name, String IMG, String description, CardType type, int damageAmt, int blockAmt) {
            super(makeID(id), name, IMG, COST, description, type, TheSleuthChar.Enums.COLOR_GRAY, CardRarity.SPECIAL, CardTarget.NONE);

            baseID = id;

            baseDamage = damageAmt;
            baseBlock = blockAmt;

        }

        private static String makeID(String id) {
            return SleuthMod.makeID("Shifting" + id);
        }


        @Override
        public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

        }

        @Override
        public void upgrade() {

        }
    }
}