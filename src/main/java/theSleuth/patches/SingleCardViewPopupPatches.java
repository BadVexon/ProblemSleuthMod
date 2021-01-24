package theSleuth.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import theSleuth.SleuthMod;
import theSleuth.cards.AbstractSleuthCard;

public class SingleCardViewPopupPatches {
    private static TextureAtlas.AtlasRegion bigImaginOrb = SleuthMod.UIAtlas.findRegion("bigImaginOrb");
    private static TextureAtlas.AtlasRegion bigPulchOrb = SleuthMod.UIAtlas.findRegion("bigPulchOrb");
    private static TextureAtlas.AtlasRegion bigVimOrb = SleuthMod.UIAtlas.findRegion("bigVimOrb");

    @SpirePatch(clz = SingleCardViewPopup.class, method = "renderCost")
    public static class RenderSecondCostPatch {
        private static void renderHelper(SpriteBatch sb, float x, float y, TextureAtlas.AtlasRegion img) {
            if (img != null) {
                sb.draw(img, x + img.offsetX - (float) img.originalWidth / 2.0F, y + img.offsetY - (float) img.originalHeight / 2.0F, (float) img.originalWidth / 2.0F - img.offsetX, (float) img.originalHeight / 2.0F - img.offsetY, (float) img.packedWidth, (float) img.packedHeight, Settings.scale, Settings.scale, 0.0F);
            }
        }

        @SpirePostfixPatch
        public static void patch(SingleCardViewPopup __instance, SpriteBatch sb) {
            float bleh = -450;
            AbstractCard C = (AbstractCard) ReflectionHacks.getPrivate(__instance, SingleCardViewPopup.class, "card");
            if (C instanceof AbstractSleuthCard) {
                if (((AbstractSleuthCard) C).pulch > 0) {
                    renderHelper(sb, (float) Settings.WIDTH / 2.0F - 275 * Settings.scale, (float) Settings.HEIGHT / 2.0F + (380.0F + bleh) * Settings.scale, bigPulchOrb);
                    FontHelper.renderFont(sb, FontHelper.SCP_cardEnergyFont, Integer.toString(((AbstractSleuthCard) C).pulch), (float) Settings.WIDTH / 2.0F - 300 * Settings.scale, (float) Settings.HEIGHT / 2.0F + (404.0F + bleh) * Settings.scale, Color.WHITE);
                    bleh -= 100;
                }
                if (((AbstractSleuthCard) C).vim > 0) {
                    renderHelper(sb, (float) Settings.WIDTH / 2.0F - 275 * Settings.scale, (float) Settings.HEIGHT / 2.0F + (380.0F + bleh) * Settings.scale, bigVimOrb);
                    FontHelper.renderFont(sb, FontHelper.SCP_cardEnergyFont, Integer.toString(((AbstractSleuthCard) C).vim), (float) Settings.WIDTH / 2.0F - 300 * Settings.scale, (float) Settings.HEIGHT / 2.0F + (404.0F + bleh) * Settings.scale, Color.WHITE);
                    bleh -= 100;
                }
                if (((AbstractSleuthCard) C).imagin > 0) {
                    renderHelper(sb, (float) Settings.WIDTH / 2.0F - 275 * Settings.scale, (float) Settings.HEIGHT / 2.0F + (380.0F + bleh) * Settings.scale, bigImaginOrb);
                    FontHelper.renderFont(sb, FontHelper.SCP_cardEnergyFont, Integer.toString(((AbstractSleuthCard) C).imagin), (float) Settings.WIDTH / 2.0F - 300 * Settings.scale, (float) Settings.HEIGHT / 2.0F + (404.0F + bleh) * Settings.scale, Color.WHITE);
                }
            }
        }
    }
}