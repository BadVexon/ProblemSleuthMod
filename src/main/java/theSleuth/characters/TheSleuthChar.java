package theSleuth.characters;

import basemod.abstracts.CustomPlayer;
import basemod.abstracts.CustomSavable;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSleuth.SleuthMod;
import theSleuth.actions.CheckupAction;
import theSleuth.cards.*;
import theSleuth.relics.JammedPrinter;
import theSleuth.relics.Typewriter;
import theSleuth.util.GainStatCurvy;
import theSleuth.util.GainStatLine;

import java.util.ArrayList;
import java.util.List;

import static theSleuth.SleuthMod.*;
import static theSleuth.characters.TheSleuthChar.Enums.COLOR_GRAY;

public class TheSleuthChar extends CustomPlayer implements CustomSavable<ArrayList<Integer>> {
    public static final Logger logger = LogManager.getLogger(SleuthMod.class.getName());
    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 73;
    public static final int MAX_HP = 73;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final String[] orbTextures = {
            "theSleuthResources/images/char/sleuthCharacter/orb/alt_layer1.png",
            "theSleuthResources/images/char/sleuthCharacter/orb/alt_layer2.png",
            "theSleuthResources/images/char/sleuthCharacter/orb/alt_layer3.png",
            "theSleuthResources/images/char/sleuthCharacter/orb/alt_layer4.png",
            "theSleuthResources/images/char/sleuthCharacter/orb/alt_layer5.png",
            "theSleuthResources/images/char/sleuthCharacter/orb/alt_layer6.png",
            "theSleuthResources/images/char/sleuthCharacter/orb/alt_layer1d.png",
            "theSleuthResources/images/char/sleuthCharacter/orb/layer2d.png",
            "theSleuthResources/images/char/sleuthCharacter/orb/layer3d.png",
            "theSleuthResources/images/char/sleuthCharacter/orb/layer4d.png",
            "theSleuthResources/images/char/sleuthCharacter/orb/layer5d.png",};
    private static final String ID = makeID("sleuthCharacter");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    private int maxLines = 36;
    private int stride = 360 / maxLines;
    private float offset = MathUtils.random(-180.0F, 180.0F);

    public int playerPulch = 0;
    public int playerVim = 0;
    public int playerImagine = 0;
    public int tempImagine = 0;
    public int questOfSpirit;
    public boolean seenQuest;
    public boolean seenCurse = false;

    @Override
    public List<CutscenePanel> getCutscenePanels() {
        List<CutscenePanel> panels = new ArrayList<>();
        panels.add(new CutscenePanel(SleuthMod.makeCardPath("comic_1.png")));
        panels.add(new CutscenePanel(SleuthMod.makeCardPath("comic_2.png")));
        panels.add(new CutscenePanel(SleuthMod.makeCardPath("comic_3.png")));
        return panels;
    }


    public void loseHighestStat() {
        if (oglogCheck()) {
            if (playerPulch == playerVim && playerVim == (playerImagine + tempImagine)) {
                int i = AbstractDungeon.cardRandomRng.random(2);
                if (i == 0) {
                    losePulchritude(1);
                } else if (i == 1) {
                    loseVim(1);
                } else if (i == 2) {
                    loseImagination(1);
                }
            }
            if (playerPulch == playerVim && playerPulch > (playerImagine + tempImagine)) {
                int i = AbstractDungeon.cardRandomRng.random(1);
                if (i == 0) {
                    losePulchritude(1);
                } else {
                    loseVim(1);
                }
            } else if (playerPulch == (playerImagine + tempImagine) && playerPulch > playerVim) {
                int i = AbstractDungeon.cardRandomRng.random(1);
                if (i == 0) {
                    losePulchritude(1);
                } else {
                    loseImagination(1);
                }
            } else if (playerVim == (playerImagine + tempImagine) && playerVim > playerPulch) {
                int i = AbstractDungeon.cardRandomRng.random(1);
                if (i == 0) {
                    loseImagination(1);
                } else {
                    loseVim(1);
                }
            }
            if ((playerPulch > playerVim) && playerPulch > (playerImagine + tempImagine)) {
                losePulchritude(1);
            } else if ((playerVim > playerPulch) && playerVim > (playerImagine + tempImagine)) {
                loseVim(1);
            } else if (((playerImagine + tempImagine) > playerPulch) && ((playerImagine + tempImagine) > playerVim)) {
                loseImagination(1);
            }
            AbstractDungeon.actionManager.addToTop(new CheckupAction());
        }
    }

    public void gainVim(int amt) {
        if (oglogCheck()) {
            playerVim += amt;
            for (int i = 0; i < maxLines; i++) {
                AbstractDungeon.effectList.add(new GainStatLine(newHitbox.hb.cX, newHitbox.hb.cY + (20 * Settings.scale), new Color(0.596F, 0.250F, 0.250F, 1.0F), ((stride * i) + MathUtils.random(-stride, stride) + offset)));
                if (i % 2 == 0) {
                    AbstractDungeon.effectList.add(new GainStatCurvy(newHitbox.hb.cX, newHitbox.hb.cY + (20 * Settings.scale), new Color(0.596F, 0.250F, 0.250F, 1.0F)));
                }
            }
            AbstractDungeon.actionManager.addToTop(new CheckupAction());
        }
    }

    public void gainImagination(int amt) {
        if (oglogCheck()) {
            playerImagine += amt;
            for (int i = 0; i < maxLines; i++) {
                AbstractDungeon.effectList.add(new GainStatLine(newHitbox.hb.cX, newHitbox.hb.cY + (20 * Settings.scale), new Color(0.274F, 0.258F, 0.580F, 1.0F), ((stride * i) + MathUtils.random(-stride, stride) + offset)));
                if (i % 2 == 0) {
                    AbstractDungeon.effectList.add(new GainStatCurvy(newHitbox.hb.cX, newHitbox.hb.cY + (20 * Settings.scale), new Color(0.274F, 0.258F, 0.580F, 1.0F)));
                }
            }
            AbstractDungeon.actionManager.addToTop(new CheckupAction());
        }
    }

    public void gainPulchritude(int amt) {
        if (oglogCheck()) {
            playerPulch += amt;
            for (int i = 0; i < maxLines; i++) {
                AbstractDungeon.effectList.add(new GainStatLine(newHitbox.hb.cX, newHitbox.hb.cY + (20 * Settings.scale), new Color(0.247F, 0.545F, 0.239F, 1.0F), ((stride * i) + MathUtils.random(-stride, stride) + offset)));
                if (i % 2 == 0) {
                    AbstractDungeon.effectList.add(new GainStatCurvy(newHitbox.hb.cX, newHitbox.hb.cY + (20 * Settings.scale), new Color(0.247F, 0.545F, 0.239F, 1.0F)));
                }
            }
            AbstractDungeon.actionManager.addToTop(new CheckupAction());
        }
    }

    public void gainTempImagination(int amt) {
        if (oglogCheck()) {
            tempImagine += amt;
            fortuneCheck(amt);
            for (int i = 0; i < maxLines; i++) {
                AbstractDungeon.effectList.add(new GainStatLine(newHitbox.hb.cX, newHitbox.hb.cY + (20 * Settings.scale), new Color(0.501F, 0.368F, 0.054F, 1.0F), ((stride * i) + MathUtils.random(-stride, stride) + offset)));
                if (i % 2 == 0) {
                    AbstractDungeon.effectList.add(new GainStatCurvy(newHitbox.hb.cX, newHitbox.hb.cY + (20 * Settings.scale), new Color(0.501F, 0.368F, 0.054F, 1.0F)));
                }
            }
            AbstractDungeon.actionManager.addToTop(new CheckupAction());
        }
    }

    public void loseVim(int amt) {
        if (oglogCheck()) {
            for (int i = 0; i < amt; i++) {
                if (playerVim > 0) {
                    playerVim--;
                }
            }
            for (int i = 0; i < maxLines; i++) {
                AbstractDungeon.effectList.add(new GainStatLine(newHitbox.hb.cX, newHitbox.hb.cY + (20 * Settings.scale), Color.GRAY.cpy(), ((stride * i) + MathUtils.random(-stride, stride) + offset)));
                if (i % 2 == 0) {
                    AbstractDungeon.effectList.add(new GainStatCurvy(newHitbox.hb.cX, newHitbox.hb.cY + (20 * Settings.scale), Color.GRAY.cpy()));
                }
            }

            AbstractDungeon.actionManager.addToTop(new CheckupAction());
        }
    }

    public void losePulchritude(int amt) {
        if (oglogCheck()) {
            for (int i = 0; i < amt; i++) {
                if (playerPulch > 0) {
                    playerPulch--;
                }
            }
            for (int i = 0; i < maxLines; i++) {
                AbstractDungeon.effectList.add(new GainStatLine(newHitbox.hb.cX, newHitbox.hb.cY + (20 * Settings.scale), Color.GRAY.cpy(), ((stride * i) + MathUtils.random(-stride, stride) + offset)));
                if (i % 2 == 0) {
                    AbstractDungeon.effectList.add(new GainStatCurvy(newHitbox.hb.cX, newHitbox.hb.cY + (20 * Settings.scale), Color.GRAY.cpy()));
                }
            }
            AbstractDungeon.actionManager.addToTop(new CheckupAction());
        }

    }

    public void loseImagination(int amt) {
        if (oglogCheck()) {
            for (int i = 0; i < amt; i++) {
                if (tempImagine > 0) {
                    tempImagine--;
                    fortuneCheck(-1);
                } else if (playerImagine > 0) {
                    playerImagine--;
                }
            }
            for (int i = 0; i < maxLines; i++) {
                AbstractDungeon.effectList.add(new GainStatLine(newHitbox.hb.cX, newHitbox.hb.cY + (20 * Settings.scale), Color.GRAY.cpy(), ((stride * i) + MathUtils.random(-stride, stride) + offset)));
                if (i % 2 == 0) {
                    AbstractDungeon.effectList.add(new GainStatCurvy(newHitbox.hb.cX, newHitbox.hb.cY + (20 * Settings.scale), Color.GRAY.cpy()));
                }
            }
            AbstractDungeon.actionManager.addToTop(new CheckupAction());
        }
    }

    public TheSleuthChar(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures, "theSleuthResources/images/char/sleuthCharacter/orb/alt_vfx.png", new float[]{-20.0f, 20.0f, -40.0f, 40.0f, 0.0f}, new SpriterAnimation("theSleuthResources/images/char/sleuthCharacter/sleuthchar.scml"));
        this.initializeClass(null, THE_sleuth_SHOULDER_1, THE_sleuth_SHOULDER_2, THE_sleuth_CORPSE, this.getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN));
        this.dialogX = this.drawX + 0.0F * Settings.scale;
        this.dialogY = this.drawY + 120.0F * Settings.scale;
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                STARTING_HP, MAX_HP, 0, STARTING_GOLD, CARD_DRAW, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();

        logger.info("Begin loading starter Deck Strings");

        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(KeyHandgun.ID);
        retVal.add(TakeInventory.ID);
        retVal.add(Diplomacy.ID);

        return retVal;
    }

    @Override
    public void draw(int numCards) {
        if (AbstractDungeon.player.hasRelic(JammedPrinter.ID)) {
            for (int i = 0; i < numCards; ++i) {
                if (!this.drawPile.isEmpty()) {
                    boolean is2Top = false;
                    AbstractCard c;
                    if (this.drawPile.size() >= 2) {
                        c = this.drawPile.getNCardFromTop(2);
                        is2Top = true;
                    } else {
                        c = this.drawPile.getTopCard();
                    }
                    c.current_x = CardGroup.DRAW_PILE_X;
                    c.current_y = CardGroup.DRAW_PILE_Y;
                    c.setAngle(0.0F, true);
                    c.lighten(false);
                    c.drawScale = 0.12F;
                    c.targetDrawScale = 0.75F;
                    if (this.hasPower("Confusion") && c.cost >= 0) {
                        int newCost = AbstractDungeon.cardRandomRng.random(3);
                        if (c.cost != newCost) {
                            c.cost = newCost;
                            c.costForTurn = c.cost;
                            c.isCostModified = true;
                        }
                    }

                    c.triggerWhenDrawn();
                    this.hand.addToHand(c);
                    if (is2Top) {
                        drawPile.group.remove(drawPile.group.size() - 2);
                    } else {
                        this.drawPile.removeTopCard();
                    }
                    if (this.hasPower("Corruption") && c.type == AbstractCard.CardType.SKILL) {
                        c.setCostForTurn(-9);
                    }

                    for (AbstractRelic r : this.relics) {
                        r.onCardDraw(c);
                    }
                }
            }
        } else {
            super.draw(numCards);
        }
    }

    public void fortuneCheck(int amt) {
        for (AbstractCard q : AbstractDungeon.player.drawPile.group) {
            if (q instanceof Fortune) {
                q.updateCost(-amt);
            }
        }
        for (AbstractCard q : AbstractDungeon.player.hand.group) {
            if (q instanceof Fortune) {
                q.updateCost(-amt);
            }
        }
        for (AbstractCard q : AbstractDungeon.player.discardPile.group) {
            if (q instanceof Fortune) {
                q.updateCost(-amt);
            }
        }
    }

    public static boolean oglogCheck() {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof OglogMRubbit) {
                c.superFlash();
                return false;
            }
        }
        return true;
    }


    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();

        retVal.add(Typewriter.ID);

        return retVal;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("ATTACK_DAGGER_1", 1.25f);
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_DAGGER_1";
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 0;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return COLOR_GRAY;
    }

    @Override
    public Color getCardTrailColor() {
        return SleuthMod.sleuth_GRAY;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new TakeInventory();
    }

    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new TheSleuthChar(name, chosenClass);
    }

    @Override
    public Color getCardRenderColor() {
        return SleuthMod.sleuth_GRAY;
    }

    @Override
    public Color getSlashAttackColor() {
        return SleuthMod.sleuth_GRAY;
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY};
    }

    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    @Override
    public String getVampireText() {
        return TEXT[2];
    }

    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass THE_sleuth;
        @SpireEnum(name = "sleuth_GRAY_COLOR")
        public static AbstractCard.CardColor COLOR_GRAY;
        @SpireEnum(name = "sleuth_GRAY_COLOR")
        @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }

    @Override
    public ArrayList<Integer> onSave() {
        if (AbstractDungeon.player instanceof TheSleuthChar) {
            ArrayList<Integer> bigList = new ArrayList<>();
            bigList.add(((TheSleuthChar) AbstractDungeon.player).playerPulch);
            bigList.add(((TheSleuthChar) AbstractDungeon.player).playerVim);
            bigList.add(((TheSleuthChar) AbstractDungeon.player).playerImagine);
            bigList.add(((TheSleuthChar) AbstractDungeon.player).questOfSpirit);
            if (((TheSleuthChar) AbstractDungeon.player).seenQuest) {
                bigList.add(1);
            } else {
                bigList.add(0);
            }
            if (((TheSleuthChar) AbstractDungeon.player).seenCurse) {
                bigList.add(1);
            } else {
                bigList.add(0);
            }
            System.out.println(bigList.toString());
            return bigList;
        }
        return null;
    }

    @Override
    public void onLoad(ArrayList<Integer> herbert) {
        if (AbstractDungeon.player instanceof TheSleuthChar) {
            ((TheSleuthChar) AbstractDungeon.player).playerPulch = herbert.get(0);
            ((TheSleuthChar) AbstractDungeon.player).playerVim = herbert.get(1);
            ((TheSleuthChar) AbstractDungeon.player).playerImagine = herbert.get(2);
            ((TheSleuthChar) AbstractDungeon.player).questOfSpirit = herbert.get(3);
            ((TheSleuthChar) AbstractDungeon.player).seenQuest = herbert.get(4) == 1;
            ((TheSleuthChar) AbstractDungeon.player).seenQuest = herbert.get(5) == 1;
            System.out.println(herbert.toString());
        }
    }

}
