package theSleuth.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import theSleuth.SleuthMod;

public class HeartVFX extends AbstractGameEffect {
    private static float SPEED_DISAPPEAR = 0.5F;
    private static Texture[] imgs;

    private AbstractCreature ac;

    private float width;
    private float height;

    private float framenum = 0;

    public HeartVFX(AbstractCreature ac) {
        if (imgs == null) {
            imgs = new Texture[2];
            imgs[0] = TextureLoader.getTexture(SleuthMod.makeVFXPath("BlackHeart.png"));
            imgs[1] = TextureLoader.getTexture(SleuthMod.makeVFXPath("WhiteHeart.png"));
        }
        this.ac = ac;

        this.width = imgs[0].getWidth() * this.scale;
        this.height = imgs[0].getHeight() * this.scale;
    }

    public void update() {
        this.framenum += Gdx.graphics.getDeltaTime();
        if (framenum > 0.25) {
            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE.cpy());
        if (framenum <= 0.05) {
            sb.draw(imgs[0], ac.hb.cX - this.width / 2F, ac.hb.cY - this.height / 2F, this.width / 2F, this.height / 2F, this.width, this.height, this.scale, this.scale, this.rotation, 0, 0, imgs[0].getWidth(), imgs[0].getHeight(), false, false);
        } else if (framenum >= 0.2 && framenum <= 0.25) {
            sb.draw(imgs[1], ac.hb.cX - this.width / 2F, ac.hb.cY - this.height / 2F, this.width / 2F, this.height / 2F, this.width, this.height, this.scale, this.scale, this.rotation, 0, 0, imgs[1].getWidth(), imgs[1].getHeight(), false, false);
        }
    }

    public void dispose() {

    }
}