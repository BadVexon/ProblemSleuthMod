package theSleuth.potion;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import theSleuth.SleuthMod;
import theSleuth.powers.SilencedBuffsPower;
import theSleuth.powers.SilencedDebuffsPower;

public class Depotion
        extends AbstractPotion {
    public static final String POTION_ID = SleuthMod.makeID("Depotion");

    public Depotion() {
        super("Demotion Potion", POTION_ID, AbstractPotion.PotionRarity.UNCOMMON, AbstractPotion.PotionSize.M, AbstractPotion.PotionColor.BLUE);


        this.description = "Enemy cannot gain Strength above 0 or debuff you.";


        this.isThrown = true;
        this.targetRequired = true;


        this.tips.add(new PowerTip(this.name, this.description));
    }


    public void use(AbstractCreature target) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, AbstractDungeon.player, new SilencedBuffsPower(target), 1));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, AbstractDungeon.player, new SilencedDebuffsPower(target), 1));
    }


    public AbstractPotion makeCopy() {
        return new Depotion();
    }


    public int getPotency(int potency) {
        return 0;
    }
}
