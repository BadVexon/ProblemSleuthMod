package theSleuth.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import theSleuth.cards.AbstractSleuthCard;

public class TemporaryBoostAction extends AbstractGameAction {
    private AbstractSleuthCard cardToModify;

    public TemporaryBoostAction(AbstractSleuthCard c) {
        cardToModify = c;
    }

    public void update() {
        cardToModify.imagin = 0;
        cardToModify.vim = 0;
        cardToModify.pulch = 0;
        this.isDone = true;
    }
}
