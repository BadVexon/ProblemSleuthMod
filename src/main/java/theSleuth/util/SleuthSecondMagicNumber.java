package theSleuth.util;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theSleuth.cards.AbstractSleuthCard;

public class SleuthSecondMagicNumber extends DynamicVariable {

    @Override
    public String key() {
        return "sleuthSecondMagic";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractSleuthCard) card).isSleuthSecondMagicNumberModified;
    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractSleuthCard) card).sleuthSecondMagicNumber;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractSleuthCard) card).sleuthBaseSecondMagicNumber;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractSleuthCard) card).upgradedSleuthSecondMagicNumber;
    }
}