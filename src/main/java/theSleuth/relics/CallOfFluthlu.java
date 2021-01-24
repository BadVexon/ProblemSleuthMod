package theSleuth.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSleuth.SleuthMod;
import theSleuth.util.TextureLoader;

import static theSleuth.SleuthMod.makeRelicOutlinePath;
import static theSleuth.SleuthMod.makeRelicPath;

public class CallOfFluthlu extends CustomRelic {

    public static final String ID = SleuthMod.makeID("CallOfFluthlu");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("call_of_fluthlu.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("call_of_fluthlu.png"));

    public CallOfFluthlu() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.CLINK);
    }

    @Override
    public void atTurnStartPostDraw() {
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Dazed()));
    }

    @Override
    public int getPrice() {
        return AbstractDungeon.cardRandomRng.random(70, 100);
    }


    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
