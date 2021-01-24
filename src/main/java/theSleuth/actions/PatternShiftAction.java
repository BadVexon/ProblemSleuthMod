




package theSleuth.actions;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.monsters.exordium.Hexaghost;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;

public class PatternShiftAction extends AbstractGameAction {
    private AbstractPlayer p;
    private AbstractMonster m;
    private boolean upgraded;
    private AbstractCard transformToCard;
    private AbstractCard transformee;
    public int seed = 0;
    public static int StabbyMcStabs = 1;
    public static boolean entangleReset = false;
    public static boolean champThresholdReached;
    public static int champNumTurns;
    public static int forgeTimes;

    public PatternShiftAction(AbstractPlayer p, AbstractMonster m) {
        this.p = p;
        this.m = m;
    }

    public void update() {
        this.isDone = nextIntent(this.m);
    }

    public static boolean nextIntent(AbstractMonster m) {
        String var7 = m.id;
        byte var8 = -1;
        switch (var7.hashCode()) {
            case -2013219467:
                if (var7.equals("Looter")) {
                    var8 = 19;
                }
                break;
            case -1979300011:
                if (var7.equals("Mugger")) {
                    var8 = 20;
                }
                break;
            case -1888845576:
                if (var7.equals("AcidSlime_L")) {
                    var8 = 23;
                }
                break;
            case -1864267823:
                if (var7.equals("Exploder")) {
                    var8 = 13;
                }
                break;
            case -1822080001:
                if (var7.equals("Sentry")) {
                    var8 = 10;
                }
                break;
            case -1814052995:
                if (var7.equals("Snecko")) {
                    var8 = 2;
                }
                break;
            case -1786171586:
                if (var7.equals("AwakenedOne")) {
                    var8 = 1;
                }
                break;
            case -1689291368:
                if (var7.equals("Shelled Parasite")) {
                    var8 = 7;
                }
                break;
            case -1508851536:
                if (var7.equals("Cultist")) {
                    var8 = 8;
                }
                break;
            case -1448791466:
                if (var7.equals("BronzeAutomaton")) {
                    var8 = 16;
                }
                break;
            case -1373462065:
                if (var7.equals("SpikeSlime_L")) {
                    var8 = 29;
                }
                break;
            case -1238252950:
                if (var7.equals("Transient")) {
                    var8 = 15;
                }
                break;
            case -1081372426:
                if (var7.equals("SlaverRed")) {
                    var8 = 27;
                }
                break;
            case -762313271:
                if (var7.equals("GremlinWizard")) {
                    var8 = 24;
                }
                break;
            case -205124620:
                if (var7.equals("WrithingMass")) {
                    var8 = 22;
                }
                break;
            case -154575565:
                if (var7.equals("JawWorm")) {
                    var8 = 9;
                }
                break;
            case 77123:
                if (var7.equals("Maw")) {
                    var8 = 30;
                }
                break;
            case 2086121:
                if (var7.equals("Byrd")) {
                    var8 = 6;
                }
                break;
            case 2126015:
                if (var7.equals("Deca")) {
                    var8 = 11;
                }
                break;
            case 2135986:
                if (var7.equals("Donu")) {
                    var8 = 12;
                }
                break;
            case 65070879:
                if (var7.equals("Champ")) {
                    var8 = 4;
                }
                break;
            case 644100489:
                if (var7.equals("Hexaghost")) {
                    var8 = 25;
                }
                break;
            case 792505876:
                if (var7.equals("BanditBear")) {
                    var8 = 17;
                }
                break;
            case 1082688770:
                if (var7.equals("TheGuardian")) {
                    var8 = 31;
                }
                break;
            case 1273130812:
                if (var7.equals("TheCollector")) {
                    var8 = 21;
                }
                break;
            case 1434486691:
                if (var7.equals("Lagavulin")) {
                    var8 = 26;
                }
                break;
            case 1464625717:
                if (var7.equals("SlimeBoss")) {
                    var8 = 28;
                }
                break;
            case 1675216745:
                if (var7.equals("BanditLeader")) {
                    var8 = 18;
                }
                break;
            case 2016064280:
                if (var7.equals("TimeEater")) {
                    var8 = 14;
                }
                break;
            case 2017619858:
                if (var7.equals("Chosen")) {
                    var8 = 0;
                }
                break;
            case 2039534506:
                if (var7.equals("Dagger")) {
                    var8 = 3;
                }
                break;
            case 2088239794:
                if (var7.equals("SnakeMage")) {
                    var8 = 5;
                }
        }

        int count;
        boolean isAttacking;
        int idleCount;
        label208:
        switch (var8) {
            case 0:
            case 1:
            case 2:
                ReflectionHacks.setPrivate(m, m.getClass(), "firstTurn", false);
                break;
            case 3:
                if (m.nextMove == 2) {
                    AbstractDungeon.actionManager.addToBottom(new LoseHPAction(m, m, m.currentHealth));
                }
            case 4:
                ReflectionHacks.setPrivate(m, m.getClass(), "numTurns", champNumTurns);
                ReflectionHacks.setPrivate(m, m.getClass(), "forgeTimes", forgeTimes);
                ReflectionHacks.setPrivate(m, m.getClass(), "thresholdReached", champThresholdReached);
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                ReflectionHacks.setPrivate(m, m.getClass(), "firstMove", false);
                break;
            case 11:
                isAttacking = (Boolean) ReflectionHacks.getPrivate(m, m.getClass(), "isAttacking");
                ReflectionHacks.setPrivate(m, m.getClass(), "isAttacking", !isAttacking);
                break;
            case 12:
                isAttacking = (Boolean) ReflectionHacks.getPrivate(m, m.getClass(), "isAttacking");
                ReflectionHacks.setPrivate(m, m.getClass(), "isAttacking", !isAttacking);
                break;
            case 13:
                int turnCount = (Integer) ReflectionHacks.getPrivate(m, m.getClass(), "turnCount");
                ReflectionHacks.setPrivate(m, m.getClass(), "turnCount", turnCount + 1);
                if (turnCount + 1 < 3) {
                    AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(m, m, "Explosive", 1));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new ExplosionSmallEffect(m.hb.cX, m.hb.cY), 0.1F));
                    AbstractDungeon.actionManager.addToBottom(new SuicideAction(m));
                    AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(AbstractDungeon.player, new int[]{30, 30, 30, 30, 30}, DamageType.THORNS, AttackEffect.FIRE, true));
                }
                break;
            case 14:
                ReflectionHacks.setPrivate(m, m.getClass(), "firstTurn", false);
                if (m.currentHealth < m.maxHealth / 2) {
                    ReflectionHacks.setPrivate(m, m.getClass(), "usedHaste", true);
                }
                break;
            case 15:
                count = (Integer) ReflectionHacks.getPrivate(m, m.getClass(), "count");
                ReflectionHacks.setPrivate(m, m.getClass(), "count", count + 1);
                if (m.hasPower("Fading") && m.getPower("Fading").amount == 1) {
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new ExplosionSmallEffect(m.hb.cX, m.hb.cY), 0.1F));
                    AbstractDungeon.actionManager.addToBottom(new SuicideAction(m));
                }

                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(m, m, "Fading", 1));
                break;
            case 16:
                count = (Integer) ReflectionHacks.getPrivate(m, m.getClass(), "numTurns");
                if (count >= 4) {
                    count = 0;
                }

                ReflectionHacks.setPrivate(m, m.getClass(), "numTurns", count);
                break;
            case 17:
                switch (m.nextMove) {
                    case 1:
                        m.setMove((byte) 3, Intent.ATTACK_DEFEND, ((DamageInfo) m.damage.get(1)).base);
                        break;
                    case 2:
                        m.setMove((byte) 3, Intent.ATTACK_DEFEND, ((DamageInfo) m.damage.get(1)).base);
                        break;
                    case 3:
                        m.setMove((byte) 1, Intent.ATTACK_DEFEND, ((DamageInfo) m.damage.get(0)).base);
                }

                m.createIntent();
                return true;
            case 18:
                switch (m.nextMove) {
                    case 1:
                        m.setMove((byte) 1, Intent.ATTACK, ((DamageInfo) m.damage.get(0)).base);
                        break;
                    case 2:
                        m.setMove((byte) 3, Intent.ATTACK_DEBUFF, ((DamageInfo) m.damage.get(1)).base);
                        break;
                    case 3:
                        if (AbstractDungeon.ascensionLevel >= 17) {
                            m.setMove((byte) 1, Intent.ATTACK_DEFEND, ((DamageInfo) m.damage.get(0)).base);
                        } else {
                            m.setMove((byte) 3, Intent.ATTACK_DEBUFF, ((DamageInfo) m.damage.get(1)).base);
                        }
                }

                m.createIntent();
                return true;
            case 19:
            case 20:
                switch (m.nextMove) {
                    case 1:
                        int slashCount = (Integer) ReflectionHacks.getPrivate(m, m.getClass(), "slashCount");
                        m.rollMove();
                        if (slashCount == 1) {
                            m.setMove((byte) 4, Intent.ATTACK, ((DamageInfo) m.damage.get(1)).base);
                        }

                        ReflectionHacks.setPrivate(m, m.getClass(), "slashCount", slashCount + 1);
                        break;
                    case 2:
                        m.setMove((byte) 3, Intent.ESCAPE);
                        break;
                    case 3:
                        AbstractDungeon.actionManager.addToBottom(new EscapeAction(m));
                        break;
                    case 4:
                        m.setMove((byte) 2, Intent.DEFEND);
                }

                m.createIntent();
                return true;
            case 21:
                int turnsTaken = (Integer) ReflectionHacks.getPrivate(m, m.getClass(), "turnsTaken") + 1;
                ReflectionHacks.setPrivate(m, m.getClass(), "turnsTaken", turnsTaken);
                ReflectionHacks.setPrivate(m, m.getClass(), "initialSpawn", false);
                if (m.intent == Intent.STRONG_DEBUFF) {
                    ReflectionHacks.setPrivate(m, m.getClass(), "ultUsed", true);
                }
                break;
            case 22:
                if (m.intent == Intent.STRONG_DEBUFF) {
                    ReflectionHacks.setPrivate(m, m.getClass(), "usedMegaDebuff", true);
                }
                break;
            case 23:
                ReflectionHacks.setPrivate(m, m.getClass(), "splitTriggered", false);
                break;
            case 24:
                int currentCharge = (Integer) ReflectionHacks.getPrivate(m, m.getClass(), "currentCharge");
                if (currentCharge >= 3) {
                    m.setMove((byte) 1, Intent.ATTACK, ((DamageInfo) m.damage.get(0)).base);
                } else if (m.intent == Intent.ATTACK) {
                    if (AbstractDungeon.ascensionLevel >= 17) {
                        m.setMove((byte) 1, Intent.ATTACK, ((DamageInfo) m.damage.get(0)).base);
                    } else {
                        m.setMove((byte) 2, Intent.UNKNOWN);
                        ReflectionHacks.setPrivate(m, m.getClass(), "currentCharge", 0);
                    }
                } else {
                    m.setMove((byte) 2, Intent.UNKNOWN);
                    ReflectionHacks.setPrivate(m, m.getClass(), "currentCharge", currentCharge + 1);
                }

                m.createIntent();
                return true;
            case 25:
                Hexaghost hexa = (Hexaghost) m;
                if (m.intent == Intent.UNKNOWN) {
                    AbstractDungeon.actionManager.addToTop(new ChangeStateAction(m, "Activate"));
                    idleCount = AbstractDungeon.player.currentHealth / 12 + 1;
                    ((DamageInfo) m.damage.get(2)).base = idleCount;
                    m.applyPowers();
                    m.setMove((byte) 1, Intent.ATTACK, idleCount, 6, true);
                    m.createIntent();
                    return true;
                }

                hexa = (Hexaghost) m;
                idleCount = (Integer) ReflectionHacks.getPrivate(hexa, Hexaghost.class, "orbActiveCount");
                if (idleCount == 6) {
                    hexa.changeState("Deactivate");
                } else {
                    hexa.changeState("Activate Orb");
                }
                break;
            case 26:
                switch (m.nextMove) {
                    case 1:
                        ReflectionHacks.setPrivate(m, m.getClass(), "debuffTurnCount", 0);
                    case 2:
                    case 4:
                    default:
                        break label208;
                    case 3:
                        int debuffTurnCount = (Integer) ReflectionHacks.getPrivate(m, m.getClass(), "debuffTurnCount");
                        ReflectionHacks.setPrivate(m, m.getClass(), "debuffTurnCount", debuffTurnCount + 1);
                        break label208;
                    case 5:
                        idleCount = (Integer) ReflectionHacks.getPrivate(m, m.getClass(), "idleCount");
                        ReflectionHacks.setPrivate(m, m.getClass(), "idleCount", idleCount + 1);
                        if (idleCount + 1 >= 3) {
                            ReflectionHacks.setPrivate(m, m.getClass(), "isOutTriggered", true);
                            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(m, "OPEN"));
                            m.setMove((byte) 3, Intent.ATTACK, ((DamageInfo) m.damage.get(0)).base);
                            m.createIntent();
                            return true;
                        }
                        break label208;
                }
            case 27:
                ReflectionHacks.setPrivate(m, m.getClass(), "firstMove", false);
                if (m.nextMove == 2) {
                    ReflectionHacks.setPrivate(m, m.getClass(), "usedEntangle", true);
                }
                break;
            case 28:
                ReflectionHacks.setPrivate(m, m.getClass(), "firstTurn", false);
                switch (m.nextMove) {
                    case 1:
                        m.setMove((byte) 4, Intent.STRONG_DEBUFF);
                        break label208;
                    case 2:
                        m.setMove((byte) 1, Intent.ATTACK, ((DamageInfo) m.damage.get(1)).base);
                        break label208;
                    case 3:
                        m.setMove((byte) 4, Intent.STRONG_DEBUFF);
                        break label208;
                    case 4:
                        m.setMove((byte) 2, Intent.UNKNOWN);
                    default:
                        break label208;
                }
            case 29:
                ReflectionHacks.setPrivate(m, m.getClass(), "splitTriggered", false);
                break;
            case 30:
                ReflectionHacks.setPrivate(m, m.getClass(), "roared", true);
                break;
            case 31:
                switch (m.nextMove) {
                    case 1:
                        m.setMove((byte) 3, Intent.ATTACK, ((DamageInfo) m.damage.get(1)).base);
                        break;
                    case 2:
                        m.setMove((byte) 7, Intent.STRONG_DEBUFF);
                        break;
                    case 3:
                        m.setMove((byte) 4, Intent.ATTACK_BUFF, (Integer) ReflectionHacks.getPrivate(m, m.getClass(), "twinSlamDamage"), 2, true);
                        break;
                    case 4:
                        m.setMove((byte) 5, Intent.ATTACK, (Integer) ReflectionHacks.getPrivate(m, m.getClass(), "whirlwindDamage"), (Integer) ReflectionHacks.getPrivate(m, m.getClass(), "whirlwindCount"), true);
                        AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(m, "Offensive Mode"));
                        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(m, m, "Sharp Hide"));
                        break;
                    case 5:
                        m.setMove((byte) 6, Intent.DEFEND);
                        break;
                    case 6:
                        m.setMove((byte) 2, Intent.ATTACK, ((DamageInfo) m.damage.get(0)).base);
                        break;
                    case 7:
                        m.setMove((byte) 5, Intent.ATTACK, (Integer) ReflectionHacks.getPrivate(m, m.getClass(), "whirlwindDamage"), (Integer) ReflectionHacks.getPrivate(m, m.getClass(), "whirlwindCount"), true);
                }

                m.createIntent();
                return true;
        }

        m.rollMove();
        m.createIntent();
        return true;
    }

    public static boolean restorePreviewedSpecialCases(AbstractMonster m) {
        String var5 = m.id;
        byte var6 = -1;
        switch (var5.hashCode()) {
            case -2016632539:
                if (var5.equals("GiantHead")) {
                    var6 = 4;
                }
                break;
            case -2013219467:
                if (var5.equals("Looter")) {
                    var6 = 8;
                }
                break;
            case -1979300011:
                if (var5.equals("Mugger")) {
                    var6 = 9;
                }
                break;
            case -1864267823:
                if (var5.equals("Exploder")) {
                    var6 = 3;
                }
                break;
            case -1448791466:
                if (var5.equals("BronzeAutomaton")) {
                    var6 = 6;
                }
                break;
            case -1425972984:
                if (var5.equals("SpireShield")) {
                    var6 = 19;
                }
                break;
            case -1238252950:
                if (var5.equals("Transient")) {
                    var6 = 5;
                }
                break;
            case -1081372426:
                if (var5.equals("SlaverRed")) {
                    var6 = 7;
                }
                break;
            case -896455079:
                if (var5.equals("CorruptHeart")) {
                    var6 = 18;
                }
                break;
            case -322859430:
                if (var5.equals("SpireSpear")) {
                    var6 = 20;
                }
                break;
            case -17368990:
                if (var5.equals("BookOfStabbing")) {
                    var6 = 11;
                }
                break;
            case 77123:
                if (var5.equals("Maw")) {
                    var6 = 16;
                }
                break;
            case 2126015:
                if (var5.equals("Deca")) {
                    var6 = 1;
                }
                break;
            case 2135986:
                if (var5.equals("Donu")) {
                    var6 = 2;
                }
                break;
            case 65070879:
                if (var5.equals("Champ")) {
                    var6 = 10;
                }
                break;
            case 644100489:
                if (var5.equals("Hexaghost")) {
                    var6 = 17;
                }
                break;
            case 1273130812:
                if (var5.equals("TheCollector")) {
                    var6 = 12;
                }
                break;
            case 1434486691:
                if (var5.equals("Lagavulin")) {
                    var6 = 13;
                }
                break;
            case 1620219459:
                if (var5.equals("SphericGuardian")) {
                    var6 = 15;
                }
                break;
            case 2017619858:
                if (var5.equals("Chosen")) {
                    var6 = 14;
                }
                break;
            case 2039534506:
                if (var5.equals("Dagger")) {
                    var6 = 0;
                }
        }

        int count;
        int turnCount;
        boolean isAttacking;
        int orbActiveCount;
        switch (var6) {
            case 0:
                m.tint.changeColor(Color.WHITE.cpy(), 0.6F);
                break;
            case 1:
                isAttacking = (Boolean) ReflectionHacks.getPrivate(m, m.getClass(), "isAttacking");
                ReflectionHacks.setPrivate(m, m.getClass(), "isAttacking", !isAttacking);
                break;
            case 2:
                isAttacking = (Boolean) ReflectionHacks.getPrivate(m, m.getClass(), "isAttacking");
                ReflectionHacks.setPrivate(m, m.getClass(), "isAttacking", !isAttacking);
                break;
            case 3:
                m.tint.changeColor(Color.WHITE.cpy(), 0.6F);
                turnCount = (Integer) ReflectionHacks.getPrivate(m, m.getClass(), "turnCount");
                ReflectionHacks.setPrivate(m, m.getClass(), "turnCount", turnCount - 1);
                break;
            case 4:
                count = (Integer) ReflectionHacks.getPrivate(m, m.getClass(), "count");
                ReflectionHacks.setPrivate(m, m.getClass(), "count", count + 1);
                break;
            case 5:
                count = (Integer) ReflectionHacks.getPrivate(m, m.getClass(), "count");
                ReflectionHacks.setPrivate(m, m.getClass(), "count", count - 1);
                m.tint.changeColor(Color.WHITE.cpy(), 0.6F);
                break;
            case 6:
                count = (Integer) ReflectionHacks.getPrivate(m, m.getClass(), "numTurns");
                if (count <= 0) {
                    count = 4;
                }

                ReflectionHacks.setPrivate(m, m.getClass(), "numTurns", count);
                break;
            case 7:
                if (entangleReset) {
                    ReflectionHacks.setPrivate(m, m.getClass(), "usedEntangle", false);
                    entangleReset = false;
                }
                break;
            case 8:
            case 9:
                m.tint.changeColor(Color.WHITE.cpy(), 0.6F);
                break;
            case 10:
                ReflectionHacks.setPrivate(m, m.getClass(), "numTurns", champNumTurns);
                ReflectionHacks.setPrivate(m, m.getClass(), "forgeTimes", forgeTimes);
                ReflectionHacks.setPrivate(m, m.getClass(), "thresholdReached", champThresholdReached);
                break;
            case 11:
                ReflectionHacks.setPrivate(m, m.getClass(), "stabCount", StabbyMcStabs);
                break;
            case 12:
                int turnsTaken = (Integer) ReflectionHacks.getPrivate(m, m.getClass(), "turnsTaken");
                ReflectionHacks.setPrivate(m, m.getClass(), "turnsTaken", turnsTaken - 1);
                break;
            case 13:
                switch (m.nextMove) {
                    case 1:
                        ReflectionHacks.setPrivate(m, m.getClass(), "debuffTurnCount", 2);
                        return true;
                    case 2:
                    case 4:
                    default:
                        return true;
                    case 3:
                        orbActiveCount = (Integer) ReflectionHacks.getPrivate(m, m.getClass(), "debuffTurnCount");
                        ReflectionHacks.setPrivate(m, m.getClass(), "debuffTurnCount", orbActiveCount - 1);
                        return true;
                    case 5:
                        int idleCount = (Integer) ReflectionHacks.getPrivate(m, m.getClass(), "idleCount");
                        ReflectionHacks.setPrivate(m, m.getClass(), "idleCount", idleCount - 1);
                        return true;
                }
            case 14:
                if (m.nextMove == 4) {
                    ReflectionHacks.setPrivate(m, m.getClass(), "usedHex", false);
                }
                break;
            case 15:
                if (m.nextMove == 4) {
                    ReflectionHacks.setPrivate(m, m.getClass(), "secondMove", true);
                }
                break;
            case 16:
                turnCount = (Integer) ReflectionHacks.getPrivate(m, m.getClass(), "turnCount");
                ReflectionHacks.setPrivate(m, m.getClass(), "turnCount", turnCount - 1);
                break;
            case 17:
                Hexaghost hexa = (Hexaghost) m;
                orbActiveCount = (Integer) ReflectionHacks.getPrivate(hexa, Hexaghost.class, "orbActiveCount");
                if (orbActiveCount == 0) {
                    ReflectionHacks.setPrivate(hexa, Hexaghost.class, "orbActiveCount", 6);
                } else {
                    ReflectionHacks.setPrivate(hexa, Hexaghost.class, "orbActiveCount", orbActiveCount - 1);
                }
                break;
            case 18:
                turnCount = (Integer) ReflectionHacks.getPrivate(m, m.getClass(), "moveCount");
                ReflectionHacks.setPrivate(m, m.getClass(), "moveCount", turnCount - 1);
                break;
            case 19:
                turnCount = (Integer) ReflectionHacks.getPrivate(m, m.getClass(), "moveCount");
                ReflectionHacks.setPrivate(m, m.getClass(), "moveCount", turnCount - 1);
                break;
            case 20:
                turnCount = (Integer) ReflectionHacks.getPrivate(m, m.getClass(), "moveCount");
                ReflectionHacks.setPrivate(m, m.getClass(), "moveCount", turnCount - 1);
        }

        return true;
    }

    public static boolean previewNextIntent(AbstractMonster m) {
        String var5 = m.id;
        byte var6 = -1;
        switch (var5.hashCode()) {
            case -2013219467:
                if (var5.equals("Looter")) {
                    var6 = 9;
                }
                break;
            case -1979300011:
                if (var5.equals("Mugger")) {
                    var6 = 10;
                }
                break;
            case -1888845576:
                if (var5.equals("AcidSlime_L")) {
                    var6 = 16;
                }
                break;
            case -1864267823:
                if (var5.equals("Exploder")) {
                    var6 = 5;
                }
                break;
            case -1786171586:
                if (var5.equals("AwakenedOne")) {
                    var6 = 0;
                }
                break;
            case -1448791466:
                if (var5.equals("BronzeAutomaton")) {
                    var6 = 8;
                }
                break;
            case -1373462065:
                if (var5.equals("SpikeSlime_L")) {
                    var6 = 22;
                }
                break;
            case -1238252950:
                if (var5.equals("Transient")) {
                    var6 = 7;
                }
                break;
            case -1081372426:
                if (var5.equals("SlaverRed")) {
                    var6 = 20;
                }
                break;
            case -762313271:
                if (var5.equals("GremlinWizard")) {
                    var6 = 17;
                }
                break;
            case -205124620:
                if (var5.equals("WrithingMass")) {
                    var6 = 15;
                }
                break;
            case -17368990:
                if (var5.equals("BookOfStabbing")) {
                    var6 = 11;
                }
                break;
            case 77123:
                if (var5.equals("Maw")) {
                    var6 = 23;
                }
                break;
            case 2126015:
                if (var5.equals("Deca")) {
                    var6 = 3;
                }
                break;
            case 2135986:
                if (var5.equals("Donu")) {
                    var6 = 4;
                }
                break;
            case 65070879:
                if (var5.equals("Champ")) {
                    var6 = 2;
                }
                break;
            case 644100489:
                if (var5.equals("Hexaghost")) {
                    var6 = 18;
                }
                break;
            case 792505876:
                if (var5.equals("BanditBear")) {
                    var6 = 12;
                }
                break;
            case 1082688770:
                if (var5.equals("TheGuardian")) {
                    var6 = 24;
                }
                break;
            case 1273130812:
                if (var5.equals("TheCollector")) {
                    var6 = 14;
                }
                break;
            case 1434486691:
                if (var5.equals("Lagavulin")) {
                    var6 = 19;
                }
                break;
            case 1464625717:
                if (var5.equals("SlimeBoss")) {
                    var6 = 21;
                }
                break;
            case 1675216745:
                if (var5.equals("BanditLeader")) {
                    var6 = 13;
                }
                break;
            case 2016064280:
                if (var5.equals("TimeEater")) {
                    var6 = 6;
                }
                break;
            case 2039534506:
                if (var5.equals("Dagger")) {
                    var6 = 1;
                }
        }

        int count;
        boolean isAttacking;
        int orbActiveCount;
        label189:
        switch (var6) {
            case 0:
                ReflectionHacks.setPrivate(m, m.getClass(), "firstTurn", false);
                break;
            case 1:
                if (m.nextMove == 2) {
                    m.tint.changeColor(Color.CLEAR.cpy(), 0.6F);
                    m.setMove((byte) 0, Intent.NONE);
                    m.createIntent();
                    return true;
                }
                break;
            case 2:
                champThresholdReached = (Boolean) ReflectionHacks.getPrivate(m, m.getClass(), "thresholdReached");
                champNumTurns = (Integer) ReflectionHacks.getPrivate(m, m.getClass(), "numTurns");
                forgeTimes = (Integer) ReflectionHacks.getPrivate(m, m.getClass(), "forgeTimes");
                break;
            case 3:
                isAttacking = (Boolean) ReflectionHacks.getPrivate(m, m.getClass(), "isAttacking");
                ReflectionHacks.setPrivate(m, m.getClass(), "isAttacking", !isAttacking);
                break;
            case 4:
                isAttacking = (Boolean) ReflectionHacks.getPrivate(m, m.getClass(), "isAttacking");
                ReflectionHacks.setPrivate(m, m.getClass(), "isAttacking", !isAttacking);
                break;
            case 5:
                int turnCount = (Integer) ReflectionHacks.getPrivate(m, m.getClass(), "turnCount") + 1;
                ReflectionHacks.setPrivate(m, m.getClass(), "turnCount", turnCount);
                if (turnCount == 3) {
                    m.tint.changeColor(Color.RED.cpy(), 0.6F);
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new ExplosionSmallEffect(m.hb.cX, m.hb.cY), 0.1F));
                    m.setMove((byte) 0, Intent.NONE);
                    m.createIntent();
                    return true;
                }
                break;
            case 6:
                if (m.currentHealth < m.maxHealth / 2) {
                    ReflectionHacks.setPrivate(m, m.getClass(), "usedHaste", true);
                }
                break;
            case 7:
                count = (Integer) ReflectionHacks.getPrivate(m, m.getClass(), "count");
                ReflectionHacks.setPrivate(m, m.getClass(), "count", count + 1);
                if (m.hasPower("Fading") && m.getPower("Fading").amount == 1) {
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new ExplosionSmallEffect(m.hb.cX, m.hb.cY), 0.1F));
                    m.tint.changeColor(Color.CLEAR.cpy(), 0.6F);
                    m.setMove((byte) 0, Intent.NONE);
                    m.createIntent();
                    return true;
                }
                break;
            case 8:
                count = (Integer) ReflectionHacks.getPrivate(m, m.getClass(), "numTurns");
                if (count >= 4) {
                    count = 0;
                }

                ReflectionHacks.setPrivate(m, m.getClass(), "numTurns", count);
                break;
            case 9:
            case 10:
                switch (m.nextMove) {
                    case 1:
                        int slashCount = (Integer) ReflectionHacks.getPrivate(m, m.getClass(), "slashCount");
                        m.rollMove();
                        if (slashCount == 1) {
                            m.setMove((byte) 4, Intent.ATTACK, ((DamageInfo) m.damage.get(1)).base);
                        }
                        break;
                    case 2:
                        m.setMove((byte) 3, Intent.ESCAPE);
                        break;
                    case 3:
                        m.tint.changeColor(Color.CLEAR.cpy(), 0.6F);
                        m.setMove((byte) 0, Intent.NONE);
                        break;
                    case 4:
                        m.setMove((byte) 2, Intent.DEFEND);
                }

                m.createIntent();
                return true;
            case 11:
                StabbyMcStabs = (Integer) ReflectionHacks.getPrivate(m, m.getClass(), "stabCount");
                break;
            case 12:
                switch (m.nextMove) {
                    case 1:
                        m.setMove((byte) 3, Intent.ATTACK_DEFEND, ((DamageInfo) m.damage.get(1)).base);
                        break;
                    case 2:
                        m.setMove((byte) 3, Intent.ATTACK_DEFEND, ((DamageInfo) m.damage.get(1)).base);
                        break;
                    case 3:
                        m.setMove((byte) 1, Intent.ATTACK_DEFEND, ((DamageInfo) m.damage.get(0)).base);
                }

                m.createIntent();
                return true;
            case 13:
                switch (m.nextMove) {
                    case 1:
                        m.setMove((byte) 1, Intent.ATTACK, ((DamageInfo) m.damage.get(0)).base);
                        break;
                    case 2:
                        m.setMove((byte) 3, Intent.ATTACK_DEBUFF, ((DamageInfo) m.damage.get(1)).base);
                        break;
                    case 3:
                        if (AbstractDungeon.ascensionLevel >= 17) {
                            m.setMove((byte) 1, Intent.ATTACK_DEFEND, ((DamageInfo) m.damage.get(0)).base);
                        } else {
                            m.setMove((byte) 3, Intent.ATTACK_DEBUFF, ((DamageInfo) m.damage.get(1)).base);
                        }
                }

                m.createIntent();
                return true;
            case 14:
                int turnsTaken = (Integer) ReflectionHacks.getPrivate(m, m.getClass(), "turnsTaken") + 1;
                ReflectionHacks.setPrivate(m, m.getClass(), "turnsTaken", turnsTaken);
                ReflectionHacks.setPrivate(m, m.getClass(), "initialSpawn", false);
                if (m.intent == Intent.STRONG_DEBUFF) {
                    ReflectionHacks.setPrivate(m, m.getClass(), "ultUsed", true);
                }
                break;
            case 15:
                if (m.intent == Intent.STRONG_DEBUFF) {
                    ReflectionHacks.setPrivate(m, m.getClass(), "usedMegaDebuff", true);
                }
                break;
            case 16:
                ReflectionHacks.setPrivate(m, m.getClass(), "splitTriggered", false);
                break;
            case 17:
                int currentCharge = (Integer) ReflectionHacks.getPrivate(m, m.getClass(), "currentCharge");
                if (currentCharge >= 3) {
                    m.setMove((byte) 1, Intent.ATTACK, ((DamageInfo) m.damage.get(0)).base);
                } else if (m.intent == Intent.ATTACK) {
                    if (AbstractDungeon.ascensionLevel >= 17) {
                        m.setMove((byte) 1, Intent.ATTACK, ((DamageInfo) m.damage.get(0)).base);
                    } else {
                        m.setMove((byte) 2, Intent.UNKNOWN);
                    }
                } else {
                    m.setMove((byte) 2, Intent.UNKNOWN);
                }

                m.createIntent();
                return true;
            case 18:
                Hexaghost hexa = (Hexaghost) m;
                if (m.intent == Intent.UNKNOWN) {
                    orbActiveCount = AbstractDungeon.player.currentHealth / 12 + 1;
                    ((DamageInfo) m.damage.get(2)).base = orbActiveCount;
                    m.applyPowers();
                    m.setMove((byte) 1, Intent.ATTACK, orbActiveCount, 6, true);
                    m.createIntent();
                    return true;
                }

                orbActiveCount = (Integer) ReflectionHacks.getPrivate(hexa, Hexaghost.class, "orbActiveCount");
                if (orbActiveCount == 6) {
                    ReflectionHacks.setPrivate(hexa, Hexaghost.class, "orbActiveCount", 0);
                } else {
                    ReflectionHacks.setPrivate(hexa, Hexaghost.class, "orbActiveCount", orbActiveCount + 1);
                }
                break;
            case 19:
                switch (m.nextMove) {
                    case 1:
                        ReflectionHacks.setPrivate(m, m.getClass(), "debuffTurnCount", 0);
                    case 2:
                    case 4:
                    default:
                        break label189;
                    case 3:
                        int debuffTurnCount = (Integer) ReflectionHacks.getPrivate(m, m.getClass(), "debuffTurnCount");
                        ReflectionHacks.setPrivate(m, m.getClass(), "debuffTurnCount", debuffTurnCount + 1);
                        break label189;
                    case 5:
                        orbActiveCount = (Integer) ReflectionHacks.getPrivate(m, m.getClass(), "idleCount");
                        ReflectionHacks.setPrivate(m, m.getClass(), "idleCount", orbActiveCount + 1);
                        if (orbActiveCount + 1 >= 3) {
                            m.setMove((byte) 3, Intent.ATTACK, ((DamageInfo) m.damage.get(0)).base);
                            m.createIntent();
                            return true;
                        }
                        break label189;
                }
            case 20:
                ReflectionHacks.setPrivate(m, m.getClass(), "firstMove", false);
                if (m.nextMove == 2) {
                    ReflectionHacks.setPrivate(m, m.getClass(), "usedEntangle", true);
                    entangleReset = true;
                }
                break;
            case 21:
                ReflectionHacks.setPrivate(m, m.getClass(), "firstTurn", false);
                switch (m.nextMove) {
                    case 1:
                        m.setMove((byte) 4, Intent.STRONG_DEBUFF);
                        break label189;
                    case 2:
                        m.setMove((byte) 1, Intent.ATTACK, ((DamageInfo) m.damage.get(1)).base);
                        break label189;
                    case 3:
                        m.setMove((byte) 4, Intent.STRONG_DEBUFF);
                        break label189;
                    case 4:
                        m.setMove((byte) 2, Intent.UNKNOWN);
                    default:
                        break label189;
                }
            case 22:
                ReflectionHacks.setPrivate(m, m.getClass(), "splitTriggered", false);
                break;
            case 23:
                ReflectionHacks.setPrivate(m, m.getClass(), "roared", true);
                break;
            case 24:
                switch (m.nextMove) {
                    case 1:
                        m.setMove((byte) 3, Intent.ATTACK, ((DamageInfo) m.damage.get(1)).base);
                        break;
                    case 2:
                        m.setMove((byte) 7, Intent.STRONG_DEBUFF);
                        break;
                    case 3:
                        m.setMove((byte) 4, Intent.ATTACK_BUFF, (Integer) ReflectionHacks.getPrivate(m, m.getClass(), "twinSlamDamage"), 2, true);
                        break;
                    case 4:
                        m.setMove((byte) 5, Intent.ATTACK, (Integer) ReflectionHacks.getPrivate(m, m.getClass(), "whirlwindDamage"), (Integer) ReflectionHacks.getPrivate(m, m.getClass(), "whirlwindCount"), true);
                        break;
                    case 5:
                        m.setMove((byte) 6, Intent.DEFEND);
                        break;
                    case 6:
                        m.setMove((byte) 2, Intent.ATTACK, ((DamageInfo) m.damage.get(0)).base);
                        break;
                    case 7:
                        m.setMove((byte) 5, Intent.ATTACK, (Integer) ReflectionHacks.getPrivate(m, m.getClass(), "whirlwindDamage"), (Integer) ReflectionHacks.getPrivate(m, m.getClass(), "whirlwindCount"), true);
                }

                m.createIntent();
                return true;
        }

        m.rollMove();
        m.createIntent();
        return true;
    }
}
