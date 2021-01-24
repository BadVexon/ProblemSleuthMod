package theSleuth.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import theSleuth.SleuthMod;
import theSleuth.cards.AbstractSleuthCard;
import theSleuth.characters.TheSleuthChar;

public class ExtraEnergyOrbPatch {
    private static TextureAtlas.AtlasRegion imaginOrb1 = SleuthMod.UIAtlas.findRegion("imaginOrb");
    private static TextureAtlas.AtlasRegion imaginOrb2 = SleuthMod.UIAtlas.findRegion("imaginOrb2");
    private static TextureAtlas.AtlasRegion imaginOrb3 = SleuthMod.UIAtlas.findRegion("imaginOrb3");

    private static TextureAtlas.AtlasRegion pulchOrb1 = SleuthMod.UIAtlas.findRegion("pulchOrb");
    private static TextureAtlas.AtlasRegion pulchOrb2 = SleuthMod.UIAtlas.findRegion("pulchOrb2");
    private static TextureAtlas.AtlasRegion pulchOrb3 = SleuthMod.UIAtlas.findRegion("pulchOrb3");

    private static TextureAtlas.AtlasRegion vimOrb1 = SleuthMod.UIAtlas.findRegion("vimOrb");
    private static TextureAtlas.AtlasRegion vimOrb2 = SleuthMod.UIAtlas.findRegion("vimOrb2");
    private static TextureAtlas.AtlasRegion vimOrb3 = SleuthMod.UIAtlas.findRegion("vimOrb3");


    @SpirePatch(clz = AbstractCard.class, method = "renderEnergy")
    public static class SecondEnergyRenderPatch {
        @SpirePostfixPatch
        public static void patch(AbstractCard __instance, SpriteBatch sb) {
            float bleh = -50;
            if (__instance instanceof AbstractSleuthCard) {
                if (CardCrawlGame.isInARun()) {
                    if (AbstractDungeon.player instanceof TheSleuthChar) {
                        if (((AbstractSleuthCard) __instance).pulch > 0) {
                            FontHelper.cardEnergyFont_L.getData().setScale(__instance.drawScale * 0.75F);
                            if (bleh == -50) {
                                renderHelper(sb, pulchOrb1, __instance.current_x, __instance.current_y, __instance);
                            } else if (bleh == -100) {
                                renderHelper(sb, pulchOrb2, __instance.current_x, __instance.current_y, __instance);
                            } else {
                                renderHelper(sb, pulchOrb3, __instance.current_x, __instance.current_y, __instance);
                            }
                            FontHelper.renderRotatedText(sb, FontHelper.cardEnergyFont_L, Integer.toString(((AbstractSleuthCard) __instance).pulch), __instance.current_x, __instance.current_y, -135.0F * __instance.drawScale * Settings.scale, bleh * __instance.drawScale * Settings.scale, __instance.angle, false, Color.WHITE.cpy());
                            bleh -= 50;
                        }
                        if (((AbstractSleuthCard) __instance).vim > 0) {
                            FontHelper.cardEnergyFont_L.getData().setScale(__instance.drawScale * 0.75F);
                            if (bleh == -50) {
                                renderHelper(sb, vimOrb1, __instance.current_x, __instance.current_y, __instance);
                            } else if (bleh == -100) {
                                renderHelper(sb, vimOrb2, __instance.current_x, __instance.current_y, __instance);
                            } else {
                                renderHelper(sb, vimOrb3, __instance.current_x, __instance.current_y, __instance);
                            }
                            FontHelper.renderRotatedText(sb, FontHelper.cardEnergyFont_L, Integer.toString(((AbstractSleuthCard) __instance).vim), __instance.current_x, __instance.current_y, -135.0F * __instance.drawScale * Settings.scale, bleh * __instance.drawScale * Settings.scale, __instance.angle, false, Color.WHITE.cpy());
                            bleh -= 50;
                        }
                        if (((AbstractSleuthCard) __instance).imagin > 0) {
                            FontHelper.cardEnergyFont_L.getData().setScale(__instance.drawScale * 0.75F);
                            if (bleh == -50) {
                                renderHelper(sb, imaginOrb1, __instance.current_x, __instance.current_y, __instance);
                            } else if (bleh == -100) {
                                renderHelper(sb, imaginOrb2, __instance.current_x, __instance.current_y, __instance);
                            } else {
                                renderHelper(sb, imaginOrb3, __instance.current_x, __instance.current_y, __instance);
                            }
                            FontHelper.renderRotatedText(sb, FontHelper.cardEnergyFont_L, Integer.toString(((AbstractSleuthCard) __instance).imagin), __instance.current_x, __instance.current_y, -135.0F * __instance.drawScale * Settings.scale, bleh * __instance.drawScale * Settings.scale, __instance.angle, false, Color.WHITE.cpy());
                        }
                    }
                } else {
                    if (((AbstractSleuthCard) __instance).pulch > 0) {
                        FontHelper.cardEnergyFont_L.getData().setScale(__instance.drawScale * 0.75F);
                        if (bleh == -50) {
                            renderHelper(sb, pulchOrb1, __instance.current_x, __instance.current_y, __instance);
                        } else if (bleh == -100) {
                            renderHelper(sb, pulchOrb2, __instance.current_x, __instance.current_y, __instance);
                        } else {
                            renderHelper(sb, pulchOrb3, __instance.current_x, __instance.current_y, __instance);
                        }
                        FontHelper.renderRotatedText(sb, FontHelper.cardEnergyFont_L, Integer.toString(((AbstractSleuthCard) __instance).pulch), __instance.current_x, __instance.current_y, -135.0F * __instance.drawScale * Settings.scale, bleh * __instance.drawScale * Settings.scale, __instance.angle, false, Color.WHITE.cpy());
                        bleh -= 50;
                    }
                    if (((AbstractSleuthCard) __instance).vim > 0) {
                        FontHelper.cardEnergyFont_L.getData().setScale(__instance.drawScale * 0.75F);
                        if (bleh == -50) {
                            renderHelper(sb, vimOrb1, __instance.current_x, __instance.current_y, __instance);
                        } else if (bleh == -100) {
                            renderHelper(sb, vimOrb2, __instance.current_x, __instance.current_y, __instance);
                        } else {
                            renderHelper(sb, vimOrb3, __instance.current_x, __instance.current_y, __instance);
                        }
                        FontHelper.renderRotatedText(sb, FontHelper.cardEnergyFont_L, Integer.toString(((AbstractSleuthCard) __instance).vim), __instance.current_x, __instance.current_y, -135.0F * __instance.drawScale * Settings.scale, bleh * __instance.drawScale * Settings.scale, __instance.angle, false, Color.WHITE.cpy());
                        bleh -= 50;
                    }
                    if (((AbstractSleuthCard) __instance).imagin > 0) {
                        FontHelper.cardEnergyFont_L.getData().setScale(__instance.drawScale * 0.75F);
                        if (bleh == -50) {
                            renderHelper(sb, imaginOrb1, __instance.current_x, __instance.current_y, __instance);
                        } else if (bleh == -100) {
                            renderHelper(sb, imaginOrb2, __instance.current_x, __instance.current_y, __instance);
                        } else {
                            renderHelper(sb, imaginOrb3, __instance.current_x, __instance.current_y, __instance);
                        }
                        FontHelper.renderRotatedText(sb, FontHelper.cardEnergyFont_L, Integer.toString(((AbstractSleuthCard) __instance).imagin), __instance.current_x, __instance.current_y, -135.0F * __instance.drawScale * Settings.scale, bleh * __instance.drawScale * Settings.scale, __instance.angle, false, Color.WHITE.cpy());
                    }
                }
            }
        }

        private static void renderHelper(SpriteBatch sb, TextureAtlas.AtlasRegion img, float drawX, float drawY, AbstractCard C) {
            sb.setColor(Color.WHITE);
            sb.draw(img, drawX + img.offsetX - (float) img.originalWidth / 2.0F, drawY + img.offsetY - (float) img.originalHeight / 2.0F, (float) img.originalWidth / 2.0F - img.offsetX, (float) img.originalHeight / 2.0F - img.offsetY, (float) img.packedWidth, (float) img.packedHeight, C.drawScale * Settings.scale, C.drawScale * Settings.scale, C.angle);
        }
    }
}