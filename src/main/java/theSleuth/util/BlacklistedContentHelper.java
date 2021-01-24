package theSleuth.util;

import com.megacrit.cardcrawl.cards.colorless.Apotheosis;
import com.megacrit.cardcrawl.potions.PowerPotion;
import com.megacrit.cardcrawl.relics.DeadBranch;

import java.util.ArrayList;

public class    BlacklistedContentHelper {

    public static ArrayList<String> bannedCards = new ArrayList<>();
    public static ArrayList<String> bannedRelics = new ArrayList<>();
    public static ArrayList<String> bannedPotions = new ArrayList<>();
    // Events handled somewhere else.

    static {
        // Banned cards

        // Banned relics

        // Banned potions
        bannedPotions.add(PowerPotion.POTION_ID);
    }
}