package theSleuth.util;

import com.megacrit.cardcrawl.core.AbstractCreature;

public interface SingleTargetRelic {
    void onTargetChosen(AbstractCreature c);
}