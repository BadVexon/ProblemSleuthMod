package theSleuth.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import theSleuth.cards.AbstractSleuthCard;

public class TemporaryFlopAction extends AbstractGameAction {
    private AbstractSleuthCard cardToModify;
    private int wisdom;
    private int power;
    private int courage;

    public TemporaryFlopAction(AbstractSleuthCard c, int imagin, int vim, int pulch) {
        wisdom = imagin;
        power = vim;
        courage = pulch;
        cardToModify = c;
    }

    public void update() {
        cardToModify.imagin = wisdom;
        cardToModify.vim = power;
        cardToModify.pulch = courage;
        this.isDone = true;
    }
}
