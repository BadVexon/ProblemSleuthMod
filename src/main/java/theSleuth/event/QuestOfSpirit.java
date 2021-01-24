package theSleuth.event;


import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import theSleuth.SleuthMod;
import theSleuth.characters.TheSleuthChar;
import theSleuth.relics.SaltedMelon;
import theSleuth.relics.Tectrixcalibur;

public class QuestOfSpirit extends AbstractImageEvent {


    public static final String ID = SleuthMod.makeID("QuestOfSpirit");
    public static final String IMG = SleuthMod.makeCardPath("Kingdom.png");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private int screenNum = 0;
    private int choiceNum = 0;

    public QuestOfSpirit() {
        super(NAME, DESCRIPTIONS[0], IMG);
        this.noCardsInRewards = true;

        if (AbstractDungeon.ascensionLevel >= 15) {
            imageEventText.setDialogOption(OPTIONS[6]);
        } else {
            imageEventText.setDialogOption(OPTIONS[0]);
        }
        imageEventText.setDialogOption(OPTIONS[1]);
        imageEventText.setDialogOption(OPTIONS[2]);
        imageEventText.setDialogOption(OPTIONS[3]);
        imageEventText.setDialogOption(OPTIONS[4]);
    }

    @Override
    protected void buttonEffect(int i) {
        switch (screenNum) {
            case 0:
                switch (i) {
                    case 0:
                        if (AbstractDungeon.player instanceof TheSleuthChar) {
                            if (AbstractDungeon.ascensionLevel >= 15) {
                                ((TheSleuthChar) AbstractDungeon.player).playerImagine = Math.max(3, ((TheSleuthChar) AbstractDungeon.player).playerImagine);
                                ((TheSleuthChar) AbstractDungeon.player).playerVim = Math.max(3, ((TheSleuthChar) AbstractDungeon.player).playerVim);
                                ((TheSleuthChar) AbstractDungeon.player).playerPulch = Math.max(3, ((TheSleuthChar) AbstractDungeon.player).playerPulch);
                            } else {
                                ((TheSleuthChar) AbstractDungeon.player).playerImagine = Math.max(4, ((TheSleuthChar) AbstractDungeon.player).playerImagine);
                                ((TheSleuthChar) AbstractDungeon.player).playerVim = Math.max(4, ((TheSleuthChar) AbstractDungeon.player).playerVim);
                                ((TheSleuthChar) AbstractDungeon.player).playerPulch = Math.max(4, ((TheSleuthChar) AbstractDungeon.player).playerPulch);
                            }
                            ((TheSleuthChar) AbstractDungeon.player).seenQuest = true;
                            ((TheSleuthChar) AbstractDungeon.player).questOfSpirit = 0;
                        }
                        this.imageEventText.loadImage(SleuthMod.makeCardPath("Elf.png"));
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[5]);
                        this.imageEventText.clearRemainingOptions();
                        choiceNum = 0;
                        screenNum = 1;
                        break;
                    case 1:
                        if (AbstractDungeon.player instanceof TheSleuthChar) {
                            ((TheSleuthChar) AbstractDungeon.player).seenQuest = true;
                            ((TheSleuthChar) AbstractDungeon.player).questOfSpirit = 1;
                        }
                        if (AbstractDungeon.actNum > 1)
                        {
                            if (AbstractDungeon.ascensionLevel < 15) {
                                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, new SaltedMelon());
                            }
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, new SaltedMelon());
                        }
                        this.imageEventText.loadImage(SleuthMod.makeCardPath("Hogs.png"));
                        if (AbstractDungeon.ascensionLevel >= 15) {
                            this.imageEventText.updateBodyText(DESCRIPTIONS[6]);
                        } else {
                            this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        }
                        this.imageEventText.updateDialogOption(0, OPTIONS[5]);
                        this.imageEventText.clearRemainingOptions();
                        choiceNum = 1;
                        screenNum = 1;
                        break;
                    case 2:
                        if (AbstractDungeon.player instanceof TheSleuthChar) {
                            ((TheSleuthChar) AbstractDungeon.player).seenQuest = true;
                            ((TheSleuthChar) AbstractDungeon.player).questOfSpirit = 2;
                        }
                        if (AbstractDungeon.actNum > 2)
                        {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, new Tectrixcalibur());
                        }
                        this.imageEventText.loadImage(SleuthMod.makeCardPath("King.png"));
                        if (AbstractDungeon.ascensionLevel >= 15) {
                            this.imageEventText.updateBodyText(DESCRIPTIONS[7]);
                        } else {
                            this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        }
                        this.imageEventText.updateDialogOption(0, OPTIONS[5]);
                        this.imageEventText.clearRemainingOptions();
                        choiceNum = 2;
                        screenNum = 1;
                        break;
                    case 3:
                        if (AbstractDungeon.player instanceof TheSleuthChar) {
                            ((TheSleuthChar) AbstractDungeon.player).seenQuest = true;
                            ((TheSleuthChar) AbstractDungeon.player).questOfSpirit = 3;
                        }
                        this.imageEventText.loadImage(SleuthMod.makeCardPath("Clown.png"));
                        this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[5]);
                        this.imageEventText.clearRemainingOptions();
                        choiceNum = 3;
                        screenNum = 1;
                        break;
                    case 4:
                        if (AbstractDungeon.player instanceof TheSleuthChar) {
                            ((TheSleuthChar) AbstractDungeon.player).seenQuest = true;
                        }
                        this.imageEventText.loadImage(SleuthMod.makeCardPath("castle.png"));
                        this.imageEventText.updateBodyText(DESCRIPTIONS[5]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[5]);
                        this.imageEventText.clearRemainingOptions();
                        choiceNum = 4;
                        screenNum = 1;
                        break;
                }
                break;
            case 1:
                if (i == 0) {
                    openMap();
                }
                break;
        }
    }

}
