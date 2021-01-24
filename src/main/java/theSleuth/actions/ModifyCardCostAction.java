package theSleuth.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class ModifyCardCostAction extends AbstractGameAction {
    AbstractCard q;
    int f;

    public ModifyCardCostAction(AbstractCard card, int amount) {
        q = card;
        f = amount;
    }

    public void update() {
        q.updateCost(f);

        this.isDone = true;
    }
}
