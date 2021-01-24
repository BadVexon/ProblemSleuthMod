package theSleuth.cards;

import basemod.abstracts.CustomCard;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.commons.lang3.StringUtils;
import theSleuth.characters.TheSleuthChar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public abstract class AbstractSleuthCard extends CustomCard {

    static Color GowBoy = new Color(0.5F, 0.15F, 1.0F, 1.0F);

    public int pulch;
    public int vim;
    public int imagin;
    public boolean whiskeyd = false;
    public int sleuthSecondMagicNumber;
    public int sleuthBaseSecondMagicNumber;
    public boolean upgradedSleuthSecondMagicNumber;
    public boolean isSleuthSecondMagicNumberModified;
    public boolean wizarded;

    public AbstractSleuthCard(final String id, final String name, final String img, final int cost, final String rawDescription, final CardType type, final CardColor color, final CardRarity rarity, final CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
        isCostModified = false;
        isCostModifiedForTurn = false;
        isDamageModified = false;
        isBlockModified = false;
        isMagicNumberModified = false;
        isSleuthSecondMagicNumberModified = false;
        wizarded = false;
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if (AbstractDungeon.player instanceof TheSleuthChar && (this.pulch > 0 || this.vim > 0 || this.imagin > 0)) {
            if (AbstractDungeon.player.limbo.contains(this) && ((((TheSleuthChar) AbstractDungeon.player).playerPulch < this.pulch) || (((TheSleuthChar) AbstractDungeon.player).playerVim < this.vim) || ((((TheSleuthChar) AbstractDungeon.player).playerImagine + ((TheSleuthChar) AbstractDungeon.player).tempImagine) < this.imagin))) {
                this.target = CardTarget.SELF;
                this.purgeOnUse = true;
                glowColor = GowBoy;
                selfRetain = true;
                if (this instanceof Pepperminthryl) {
                    this.rawDescription = colorPepperminthryl(this.rawDescription);
                } else {
                    this.rawDescription = colorEveryWordInDescription(this.rawDescription);
                }
                initializeDescription();
            } else if ((AbstractDungeon.player.hand.contains(this) || AbstractDungeon.player.discardPile.contains(this) || AbstractDungeon.player.drawPile.contains(this)) && ((((TheSleuthChar) AbstractDungeon.player).playerPulch < this.pulch) || (((TheSleuthChar) AbstractDungeon.player).playerVim < this.vim) || ((((TheSleuthChar) AbstractDungeon.player).playerImagine + ((TheSleuthChar) AbstractDungeon.player).tempImagine) < this.imagin))) {
                this.freeToPlayOnce = true;
                this.target = CardTarget.SELF;
                this.purgeOnUse = true;
                glowColor = GowBoy;
                selfRetain = true;
                if (this instanceof Pepperminthryl) {
                    this.rawDescription = colorPepperminthryl(this.rawDescription);
                } else {
                    this.rawDescription = colorEveryWordInDescription(this.rawDescription);
                }
                initializeDescription();
            } else {
                this.freeToPlayOnce = wizarded;
                AbstractCard megaCard = this.makeCopy();
                if (this.upgraded) {
                    megaCard.upgrade();
                }
                glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR;
                this.target = megaCard.target;
                this.purgeOnUse = megaCard.purgeOnUse;
                selfRetain = (megaCard.selfRetain || whiskeyd);
                this.rawDescription = megaCard.rawDescription;
                if (whiskeyd) {
                    this.rawDescription = "Retain. NL " + megaCard.rawDescription;
                }
                initializeDescription();
            }
        }
    }

    public void realUse(AbstractPlayer p, AbstractMonster m) {
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.player instanceof TheSleuthChar) {
            boolean vimOK = (this.vim == 0 || ((TheSleuthChar) AbstractDungeon.player).playerVim >= this.vim);
            boolean imaginOK = (this.imagin == 0 || (((TheSleuthChar) AbstractDungeon.player).playerImagine + ((TheSleuthChar) AbstractDungeon.player).tempImagine) >= this.imagin);
            boolean pulchOK = (this.pulch == 0 || ((TheSleuthChar) AbstractDungeon.player).playerPulch >= this.pulch);
            if (vimOK && imaginOK && pulchOK) {
                this.realUse(p, m);
            } else {
                if (!vimOK) {
                    ((TheSleuthChar) AbstractDungeon.player).gainVim(1);
                }
                if (!imaginOK) {
                    ((TheSleuthChar) AbstractDungeon.player).gainImagination(1);
                }
                if (!pulchOK) {
                    ((TheSleuthChar) AbstractDungeon.player).gainPulchritude(1);
                }
            }
        } else {
            this.realUse(p, m);
        }
    }

    private static String colorPepperminthryl(String description) {
        List<String> parsedDescription = new ArrayList<>(Arrays.asList(description.split(" ")));
        ListIterator<String> it = parsedDescription.listIterator();
        while (it.hasNext()) {
            String s = it.next();
            if (!s.startsWith("!") && !s.equals("NL") && !s.equals("ALWAYS") && !s.equals("costs") && !s.equals("1.")) {
                it.set("[#9a9b9c]" + s + "[]");
            }
        }
        return StringUtils.join(parsedDescription, " ");
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tips = new ArrayList<>();
        if (this.pulch > 0) {
            tips.add(new TooltipInfo("Pulchritude", "Charismatic statistic. If you don't have enough #yPulchritude for a card, it will cost #b0, #yRetain, #yPurge, and give you #b1 point of #yPulchritude permanently instead of activating the card's effects."));
        }
        if (this.vim > 0) {
            tips.add(new TooltipInfo("Vim", "Stamina statistic. If you don't have enough #yVim for a card, it will cost #b0, #yRetain, #yPurge, and give you #b1 point of #yVim permanently instead of activating the card's effects."));
        }
        if (this.imagin > 0) {
            tips.add(new TooltipInfo("Imagination", "Imaginary statistic. If you don't have enough #yImagination for a card, it will cost #b0, #yRetain, #yPurge, and give you #b1 point of #yImagination permanently instead of activating the card's effects."));
        }
        return tips;
    }

    private static String colorEveryWordInDescription(String description) {
        List<String> parsedDescription = new ArrayList<>(Arrays.asList(description.split(" ")));
        ListIterator<String> it = parsedDescription.listIterator();
        while (it.hasNext()) {
            String s = it.next();
            if (!s.startsWith("!") && !s.equals("NL")) {
                it.set("[#9a9b9c]" + s + "[]");
            }
        }
        return StringUtils.join(parsedDescription, " ");
    }

    public void displayUpgrades() {
        super.displayUpgrades();
        if (upgradedSleuthSecondMagicNumber) {
            sleuthSecondMagicNumber = sleuthBaseSecondMagicNumber;
            isSleuthSecondMagicNumberModified = true;
        }

    }

    void upgradeSleuthSecondMagicNumber(int amount) {
        sleuthBaseSecondMagicNumber += amount;
        sleuthSecondMagicNumber = sleuthBaseSecondMagicNumber;
        upgradedSleuthSecondMagicNumber = true;
    }
}