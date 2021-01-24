package theSleuth;

import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSleuth.cards.*;
import theSleuth.characters.TheSleuthChar;
import theSleuth.event.QuestOfSpirit;
import theSleuth.potion.Depotion;
import theSleuth.relics.*;
import theSleuth.util.DraggableStatDisplay;
import theSleuth.util.SleuthSecondMagicNumber;
import theSleuth.util.TextureLoader;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Properties;

@SpireInitializer
public class SleuthMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber,
        PostBattleSubscriber,
        OnCardUseSubscriber,
        StartGameSubscriber,
        PostUpdateSubscriber,
        OnPlayerDamagedSubscriber {
    public static final Logger logger = LogManager.getLogger(SleuthMod.class.getName());
    public static final String ALL_CHARACTERS = "allCharacters";
    public static final Color sleuth_GRAY = new Color(225.0f, 226.0f, 227.0f, 1);
    public static final String THE_sleuth_SHOULDER_1 = "theSleuthResources/images/char/sleuthCharacter/shoulder.png";
    public static final String THE_sleuth_SHOULDER_2 = "theSleuthResources/images/char/sleuthCharacter/shoulder2.png";
    public static final String THE_sleuth_CORPSE = "theSleuthResources/images/char/sleuthCharacter/corpse.png";
    private static final String BADGE_IMAGE = "theSleuthResources/images/Badge.png";
    private static final String MODNAME = "The Problem Sleuth";
    private static final String AUTHOR = "Vex'd and Spleen";
    private static final String DESCRIPTION = "buncha weird puzzle shit";
    private static final String ATTACK_sleuth_GRAY = "theSleuthResources/images/512/bg_attack_sleuth_gray_statless.png";
    private static final String SKILL_sleuth_GRAY = "theSleuthResources/images/512/bg_skill_sleuth_gray_statless.png";
    private static final String POWER_sleuth_GRAY = "theSleuthResources/images/512/bg_power_sleuth_gray_statless.png";
    private static final String ENERGY_ORB_sleuth_GRAY = "theSleuthResources/images/512/card_sleuth_gray_orb.png";
    private static final String CARD_ENERGY_ORB = "theSleuthResources/images/512/card_small_orb.png";
    private static final String ATTACK_sleuth_GRAY_PORTRAIT = "theSleuthResources/images/1024/bg_attack_sleuth_gray_statless.png";
    private static final String SKILL_sleuth_GRAY_PORTRAIT = "theSleuthResources/images/1024/bg_skill_sleuth_gray_statless.png";
    private static final String POWER_sleuth_GRAY_PORTRAIT = "theSleuthResources/images/1024/bg_power_sleuth_gray_statless.png";
    private static final String ENERGY_ORB_sleuth_GRAY_PORTRAIT = "theSleuthResources/images/1024/card_sleuth_gray_orb.png";
    private static final String THE_sleuth_BUTTON = "theSleuthResources/images/charSelect/sleuthCharacterButton.png";
    private static final String THE_sleuth_PORTRAIT = "theSleuthResources/images/charSelect/sleuthCharacterPortraitBG.png";
    public static Properties sleuthSettings = new Properties();
    public static boolean allCharacters = false;
    private static String modID;
    private static TheSleuthChar TheSleuthObject;
    private static boolean thindone;
    public static Texture imaginOrb;
    public static Texture imaginOrb2;
    public static Texture imaginOrb3;
    public static Texture pulchOrb;
    public static Texture pulchOrb2;
    public static Texture pulchOrb3;
    public static Texture vimOrb;
    public static Texture vimOrb2;
    public static Texture vimOrb3;
    public static Texture largePulchOrb;
    public static Texture largeVimOrb;
    public static Texture largeImaginOrb;
    public static final TextureAtlas UIAtlas = new TextureAtlas();

    public static DraggableStatDisplay newHitbox;

    public SleuthMod() {
        logger.info("Subscribe to BaseMod hooks");

        BaseMod.subscribe(this);

        setModID("theSleuth");

        sleuthSettings.setProperty(ALL_CHARACTERS, "FALSE");
        try {
            SpireConfig config = new SpireConfig("SleuthMod", "sleuthConfig", sleuthSettings);
            config.load();
            allCharacters = config.getBool(ALL_CHARACTERS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("Done subscribing");

        logger.info("Creating the color " + TheSleuthChar.Enums.COLOR_GRAY.toString());

        BaseMod.addColor(TheSleuthChar.Enums.COLOR_GRAY, sleuth_GRAY, sleuth_GRAY, sleuth_GRAY,
                sleuth_GRAY, sleuth_GRAY, sleuth_GRAY, sleuth_GRAY,
                ATTACK_sleuth_GRAY, SKILL_sleuth_GRAY, POWER_sleuth_GRAY, ENERGY_ORB_sleuth_GRAY,
                ATTACK_sleuth_GRAY_PORTRAIT, SKILL_sleuth_GRAY_PORTRAIT, POWER_sleuth_GRAY_PORTRAIT,
                ENERGY_ORB_sleuth_GRAY_PORTRAIT, CARD_ENERGY_ORB);

        logger.info("Done creating the color");

        newHitbox = new DraggableStatDisplay();

    }

    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }

    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }

    public static String makeVFXPath(String resourcePath) {
        return getModID() + "Resources/images/vfx/" + resourcePath;
    }

    public static String getModID() {
        return modID;
    }

    public static void setModID(String ID) {
        modID = ID;
    }


    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= Problem Sleuth mod initializing =========================");
        SleuthMod sleuthmod = new SleuthMod();
        logger.info("========================= Problem Sleuth mod initialized {|:0&&= =========================");
    }

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    @Override
    public void receiveEditCharacters() {
        TheSleuthObject = new TheSleuthChar("The Sleuth", TheSleuthChar.Enums.THE_sleuth);
        BaseMod.addCharacter(TheSleuthObject,
                THE_sleuth_BUTTON, THE_sleuth_PORTRAIT, TheSleuthChar.Enums.THE_sleuth);
    }

    @Override
    public void receivePostInitialize() {
        imaginOrb = TextureLoader.getTexture("theSleuthResources/images/imagin_orb_1.png");
        UIAtlas.addRegion("imaginOrb", imaginOrb, 0, 0, imaginOrb.getWidth(), imaginOrb.getHeight());
        imaginOrb2 = TextureLoader.getTexture("theSleuthResources/images/imagin_orb_2.png");
        UIAtlas.addRegion("imaginOrb2", imaginOrb2, 0, 0, imaginOrb2.getWidth(), imaginOrb2.getHeight());
        imaginOrb3 = TextureLoader.getTexture("theSleuthResources/images/imagin_orb_3.png");
        UIAtlas.addRegion("imaginOrb3", imaginOrb3, 0, 0, imaginOrb3.getWidth(), imaginOrb3.getHeight());

        pulchOrb = TextureLoader.getTexture("theSleuthResources/images/pulch_orb_1.png");
        UIAtlas.addRegion("pulchOrb", pulchOrb, 0, 0, pulchOrb.getWidth(), pulchOrb.getHeight());
        pulchOrb2 = TextureLoader.getTexture("theSleuthResources/images/pulch_orb_2.png");
        UIAtlas.addRegion("pulchOrb2", pulchOrb2, 0, 0, pulchOrb2.getWidth(), pulchOrb2.getHeight());
        pulchOrb3 = TextureLoader.getTexture("theSleuthResources/images/pulch_orb_3.png");
        UIAtlas.addRegion("pulchOrb3", pulchOrb3, 0, 0, pulchOrb3.getWidth(), pulchOrb3.getHeight());

        vimOrb = TextureLoader.getTexture("theSleuthResources/images/vim_orb_1.png");
        UIAtlas.addRegion("vimOrb", vimOrb, 0, 0, vimOrb.getWidth(), vimOrb.getHeight());
        vimOrb2 = TextureLoader.getTexture("theSleuthResources/images/vim_orb_2.png");
        UIAtlas.addRegion("vimOrb2", vimOrb2, 0, 0, vimOrb2.getWidth(), vimOrb2.getHeight());
        vimOrb3 = TextureLoader.getTexture("theSleuthResources/images/vim_orb_3.png");
        UIAtlas.addRegion("vimOrb3", vimOrb3, 0, 0, vimOrb3.getWidth(), vimOrb3.getHeight());

        largeImaginOrb = TextureLoader.getTexture("theSleuthResources/images/orb_big_imagination.png");
        UIAtlas.addRegion("bigImaginOrb", largeImaginOrb, 0, 0, largeImaginOrb.getWidth(), largeImaginOrb.getHeight());

        largePulchOrb = TextureLoader.getTexture("theSleuthResources/images/orb_big_pulch.png");
        UIAtlas.addRegion("bigPulchOrb", largePulchOrb, 0, 0, largePulchOrb.getWidth(), largePulchOrb.getHeight());

        largeVimOrb = TextureLoader.getTexture("theSleuthResources/images/orb_big_vim.png");
        UIAtlas.addRegion("bigVimOrb", largeVimOrb, 0, 0, largeVimOrb.getWidth(), largeVimOrb.getHeight());

        logger.info("Loading badge image and mod options");
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);

        ModPanel settingsPanel = new ModPanel();

        String allCharsText = "Make some relics available for all characters? (Requires restart.)";

        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton(allCharsText,
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                allCharacters, settingsPanel, (label) -> {
        }, (button) -> {
            allCharacters = button.enabled;
            try {
                SpireConfig config = new SpireConfig("sleuthMod", "sleuthConfig", sleuthSettings);
                config.setBool(ALL_CHARACTERS, allCharacters);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        settingsPanel.addUIElement(enableNormalsButton);

        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        BaseMod.addEvent(QuestOfSpirit.ID, QuestOfSpirit.class, Exordium.ID);
        BaseMod.addEvent(QuestOfSpirit.ID, QuestOfSpirit.class, TheCity.ID);
        BaseMod.addEvent(QuestOfSpirit.ID, QuestOfSpirit.class, TheBeyond.ID);

        BaseMod.addSaveField("TheSleuthChar", TheSleuthObject);
        BaseMod.subscribe(TheSleuthObject);
        logger.info("Done loading badge Image and mod options");

        BaseMod.addPotion(Depotion.class, Color.BLUE, Color.GREEN, Color.RED, Depotion.POTION_ID, TheSleuthChar.Enums.THE_sleuth);
    }

    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");

        if (allCharacters) {
            BaseMod.addRelic(new JesterOfEncumbrance(), RelicType.SHARED);
            BaseMod.addRelic(new MegatonKey(), RelicType.SHARED);
            BaseMod.addRelic(new AffinityCrow(), RelicType.SHARED);
            BaseMod.addRelic(new ElfEgg(), RelicType.SHARED);
            if (!Loader.isModLoaded("vexMod")) {
                BaseMod.addRelic(new Spyglass(), RelicType.SHARED);
            }
        } else {
            BaseMod.addRelicToCustomPool(new JesterOfEncumbrance(), TheSleuthChar.Enums.COLOR_GRAY);
            BaseMod.addRelicToCustomPool(new MegatonKey(), TheSleuthChar.Enums.COLOR_GRAY);
            BaseMod.addRelicToCustomPool(new AffinityCrow(), TheSleuthChar.Enums.COLOR_GRAY);
            BaseMod.addRelicToCustomPool(new ElfEgg(), TheSleuthChar.Enums.COLOR_GRAY);
            if (!Loader.isModLoaded("vexMod")) {
                BaseMod.addRelicToCustomPool(new Spyglass(), TheSleuthChar.Enums.COLOR_GRAY);
            }
        }
        BaseMod.addRelicToCustomPool(new Typewriter(), TheSleuthChar.Enums.COLOR_GRAY);
        BaseMod.addRelicToCustomPool(new CallOfFluthlu(), TheSleuthChar.Enums.COLOR_GRAY);
        BaseMod.addRelicToCustomPool(new BowenStilsonDogg(), TheSleuthChar.Enums.COLOR_GRAY);
        BaseMod.addRelicToCustomPool(new JammedPrinter(), TheSleuthChar.Enums.COLOR_GRAY);
        BaseMod.addRelicToCustomPool(new SaltedMelon(), TheSleuthChar.Enums.COLOR_GRAY);
        BaseMod.addRelicToCustomPool(new Tectrixcalibur(), TheSleuthChar.Enums.COLOR_GRAY);
        BaseMod.addRelicToCustomPool(new HAMPEROFTHEJADEDFOOLSENNUI(), TheSleuthChar.Enums.COLOR_GRAY);

        logger.info("Done adding relics!");
    }

    @Override
    public void receiveEditCards() {

        BaseMod.addDynamicVariable(new SleuthSecondMagicNumber());

        logger.info("Adding cards");

        BaseMod.addCard(new Strike());
        BaseMod.addCard(new Defend());
        BaseMod.addCard(new TakeInventory());
        BaseMod.addCard(new Diplomacy());
        BaseMod.addCard(new VictoryPose());
        BaseMod.addCard(new KeyHandgun());
        BaseMod.addCard(new KeyringTommygun());
        BaseMod.addCard(new HairpinMachinegun());
        BaseMod.addCard(new WilsonBust());
        BaseMod.addCard(new SnoopDoggBust());
        BaseMod.addCard(new BiteHardly());
        BaseMod.addCard(new CandyCorn());
        BaseMod.addCard(new RetrieveArms());
        BaseMod.addCard(new Treaty());
        BaseMod.addCard(new UnDeEscalate());
        BaseMod.addCard(new FairShake());
        BaseMod.addCard(new TruffleShuffle());
        BaseMod.addCard(new BrassTacks());
        BaseMod.addCard(new RetainReturn());
        BaseMod.addCard(new BellyOTheWhale());
        BaseMod.addCard(new TripleScuffle());
        BaseMod.addCard(new Supermassive());
        BaseMod.addCard(new FoolPlate());
        BaseMod.addCard(new Mural());
        BaseMod.addCard(new Ignore());
        BaseMod.addCard(new Embarrass());
        BaseMod.addCard(new ForceDeck());
        BaseMod.addCard(new FudgeDeck());
        BaseMod.addCard(new FlaskOfWhiskey());
        BaseMod.addCard(new Fortify());
        BaseMod.addCard(new CandyLeather());
        BaseMod.addCard(new Ogle());
        BaseMod.addCard(new Pepperminthryl());
        BaseMod.addCard(new CandyMech());
        BaseMod.addCard(new TheZillyhammer());
        BaseMod.addCard(new CandidBullets());
        BaseMod.addCard(new ThinkAhead());
        BaseMod.addCard(new WizardSudoku());
        BaseMod.addCard(new MoustacheFire());
        BaseMod.addCard(new WeirdPuzzleShit());
        BaseMod.addCard(new Parry());
        BaseMod.addCard(new Distill());
        BaseMod.addCard(new HeartStab());
        BaseMod.addCard(new SpadeDig());
        BaseMod.addCard(new OpportunityCost());
        BaseMod.addCard(new ClubClubClub());
        BaseMod.addCard(new DiamondArmor());
        BaseMod.addCard(new CandyCane());
        BaseMod.addCard(new RollBoxcars());
        BaseMod.addCard(new TorsoFlail());
        BaseMod.addCard(new GummyWormZombie());
        BaseMod.addCard(new CandyCornVampire());
        BaseMod.addCard(new PezUzi());
        BaseMod.addCard(new CharismaBlast());
        BaseMod.addCard(new BenStillerBust());
        BaseMod.addCard(new ThrowHat());
        BaseMod.addCard(new Unscale());
        BaseMod.addCard(new Leer());
        BaseMod.addCard(new Taze());
        BaseMod.addCard(new Deprogram());
        BaseMod.addCard(new TootsieRollFrankenstein());
        BaseMod.addCard(new HitTheMattresses());
        BaseMod.addCard(new TruffleTrouble());
        BaseMod.addCard(new Fortune());
        BaseMod.addCard(new Tootshotgun());
        BaseMod.addCard(new Joust());
        BaseMod.addCard(new Frijolaegis());
        BaseMod.addCard(new KingpinBust());
        BaseMod.addCard(new HandCannon());
        BaseMod.addCard(new Conjure());
        BaseMod.addCard(new MobRule());
        BaseMod.addCard(new Appropriate());
        BaseMod.addCard(new LeverageHeight());
        BaseMod.addCard(new Diabeatdown());
        BaseMod.addCard(new FrighteningBeast());
        BaseMod.addCard(new MortholDryax());
        BaseMod.addCard(new OglogMRubbit());
        BaseMod.addCard(new HeroBust());
        BaseMod.addCard(new WeighIn());

        logger.info("Done adding cards!");
    }

    @Override
    public void receiveEditStrings() {
        logger.info("You seeing this?");
        logger.info("Beginning to edit strings for mod with ID: " + getModID());

        BaseMod.loadCustomStringsFile(CardStrings.class, getModID() + "Resources/localization/eng/SleuthMod-Card-Strings.json");

        BaseMod.loadCustomStringsFile(PowerStrings.class, getModID() + "Resources/localization/eng/SleuthMod-Power-Strings.json");

        BaseMod.loadCustomStringsFile(EventStrings.class, getModID() + "Resources/localization/eng/SleuthMod-Event-Strings.json");

        BaseMod.loadCustomStringsFile(RelicStrings.class, getModID() + "Resources/localization/eng/SleuthMod-Relic-Strings.json");

        BaseMod.loadCustomStringsFile(CharacterStrings.class, getModID() + "Resources/localization/eng/SleuthMod-Character-Strings.json");


        logger.info("Done edittting strings");
    }

    @Override
    public void receivePostBattle(AbstractRoom room) {
        if (AbstractDungeon.player instanceof TheSleuthChar) {
            ((TheSleuthChar) AbstractDungeon.player).tempImagine = 0;
            if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) {
                if (AbstractDungeon.actNum == 1 && ((TheSleuthChar) AbstractDungeon.player).questOfSpirit == 1) {
                    if (AbstractDungeon.ascensionLevel < 15) {
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, new SaltedMelon());
                    }
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, new SaltedMelon());
                }
                if (AbstractDungeon.actNum == 2 && ((TheSleuthChar) AbstractDungeon.player).questOfSpirit == 2) {
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, new Tectrixcalibur());
                }
                if (AbstractDungeon.actNum == 3 && ((TheSleuthChar) AbstractDungeon.player).questOfSpirit == 3) {
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, new HAMPEROFTHEJADEDFOOLSENNUI());
                }
            }
        }
    }

    @Override
    public void receiveStartGame() {
        if (!thindone) {
            newHitbox.hb.resize(230 * Settings.scale, 160 * Settings.scale);
            newHitbox.hb.move(256*Settings.scale, 600*Settings.scale);
        }
        thindone = true;
    }

    @Override
    public void receivePostUpdate() {
        if (AbstractDungeon.player == null) return;
        if (AbstractDungeon.player.hasRelic(MegatonKey.ID)) MegatonKey.relicBullshit();
    }


    @Override
    public void receiveCardUsed(AbstractCard card) {
        if (card instanceof AbstractSleuthCard) {
            if (((AbstractSleuthCard) card).wizarded) {
                ((AbstractSleuthCard) card).wizarded = false;
            }
        }
        if (card.purgeOnUse) {
            ArrayList<AbstractCard> beasts = new ArrayList<>();
            for (AbstractCard q : AbstractDungeon.player.hand.group) {
                if (q instanceof FrighteningBeast) {
                    beasts.add(q);
                }
            }
            for (AbstractCard q : beasts) {
                AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(q.makeStatEquivalentCopy(), Settings.WIDTH / 2, Settings.HEIGHT / 2));
                AbstractDungeon.player.hand.removeCard(q);
                AbstractDungeon.player.hand.moveToDeck(q, false);
            }
            beasts.clear();
            for (AbstractCard q : AbstractDungeon.player.drawPile.group) {
                if (q instanceof FrighteningBeast) {
                    beasts.add(q);
                }
            }
            for (AbstractCard q : beasts) {
                AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(q.makeStatEquivalentCopy(), Settings.WIDTH / 2, Settings.HEIGHT / 2));
                AbstractDungeon.player.drawPile.removeCard(q);
                AbstractDungeon.player.drawPile.moveToDeck(q, false);
            }
            beasts.clear();
            for (AbstractCard q : AbstractDungeon.player.drawPile.group) {
                if (q instanceof FrighteningBeast) {
                    beasts.add(q);
                }
            }
            for (AbstractCard q : beasts) {
                AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(q.makeStatEquivalentCopy(), Settings.WIDTH / 2, Settings.HEIGHT / 2));
                AbstractDungeon.player.discardPile.removeCard(q);
                AbstractDungeon.player.discardPile.moveToDeck(q, false);
            }
            beasts.clear();
        }
    }

    @Override
    public int receiveOnPlayerDamaged(int damageAmount, DamageInfo info) {
        if (AbstractDungeon.player.hasRelic(AffinityCrow.ID)) {
            if (info.owner instanceof AbstractMonster) {
                if (AffinityCrow.monsterHash.containsKey(info.owner) && info.type == DamageInfo.DamageType.NORMAL && AffinityCrow.monsterHashTwo.contains(info.owner)) {
                    AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                        @Override
                        public void update() {
                            AffinityCrow.monsterHashTwo.remove(info.owner);
                            this.isDone = true;
                        }
                    });
                    return AffinityCrow.monsterHash.get(info.owner);
                }
            }
            if (damageAmount > 30) {
                return 30;
            }
        }
        return damageAmount;
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/SleuthMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }
}
