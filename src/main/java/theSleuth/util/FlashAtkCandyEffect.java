package theSleuth.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import theSleuth.SleuthMod;

public class FlashAtkCandyEffect extends AbstractGameEffect {
    private static int blockSound = 0;
    public Texture texture;
    private float x;
    private float y;
    private float sY;
    private float tY;
    private static final float DURATION = 0.6F;
    private boolean triggered;
    public boolean playedSound;

    public FlashAtkCandyEffect(float x, float y) {
        this.triggered = false;
        this.duration = 0.6F;
        this.startingDuration = 0.6F;
        this.texture = TextureLoader.getTexture(SleuthMod.makeVFXPath("WeirdMint.png"));
        if (this.texture != null) {
            this.x = x - (float) this.texture.getWidth() / 2.0F;
            y -= (float) this.texture.getHeight() / 2.0F;
        }

        this.color = Color.WHITE.cpy();
        this.scale = Settings.scale;

        this.y = y + 80.0F * Settings.scale;
        this.sY = this.y;
        this.tY = y;
    }

    private void playSound() {
        this.playBlockSound();
    }

    private void playBlockSound() {
        if (blockSound == 0) {
            CardCrawlGame.sound.play("BLOCK_GAIN_1");
        } else if (blockSound == 1) {
            CardCrawlGame.sound.play("BLOCK_GAIN_2");
        } else {
            CardCrawlGame.sound.play("BLOCK_GAIN_3");
        }

        ++blockSound;
        if (blockSound > 2) {
            blockSound = 0;
        }

    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
            this.color.a = 0.0F;
        } else if (this.duration < 0.2F) {
            this.color.a = this.duration * 5.0F;
        } else {
            this.color.a = Interpolation.fade.apply(1.0F, 0.0F, this.duration * 0.75F / 0.6F);
        }

        this.y = Interpolation.exp10In.apply(this.tY, this.sY, this.duration / 0.6F);
        if (this.duration < 0.4F && !this.triggered) {
            this.triggered = true;
        }
    }

    public void render(SpriteBatch sb) {
        if (this.texture != null) {
            sb.setColor(this.color);
            sb.draw(this.texture, this.x, this.y, this.texture.getWidth(), this.texture.getHeight());
            if (!playedSound) {
                this.playSound();
                playedSound = true;
            }
        }

    }

    public void dispose() {
    }
}