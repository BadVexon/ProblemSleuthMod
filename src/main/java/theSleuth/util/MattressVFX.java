package theSleuth.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import theSleuth.SleuthMod;

public class MattressVFX extends AbstractGameEffect {
    private static Texture[] imgs;

    private AbstractCreature ac;

    private float framenum = 0;

    public MattressVFX(AbstractCreature ac) {
        if (imgs == null) {
            imgs = new Texture[5];
            imgs[0] = TextureLoader.getTexture(SleuthMod.makeVFXPath("Mattress1.png"));
            imgs[1] = TextureLoader.getTexture(SleuthMod.makeVFXPath("Mattress2.png"));
            imgs[2] = TextureLoader.getTexture(SleuthMod.makeVFXPath("Mattress3.png"));
            imgs[3] = TextureLoader.getTexture(SleuthMod.makeVFXPath("Mattress4.png"));
        }
        this.ac = ac;

    }

    public void update() {
        this.framenum += Gdx.graphics.getDeltaTime();
        System.out.println(framenum + " is the current frame number.");
        if (this.framenum > 0.2) {
            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE.cpy());
        if (framenum <= 0.05) {
            sb.draw(imgs[0], ac.hb.cX - imgs[0].getWidth() / 2F, ac.hb.cY - imgs[0].getHeight() / 2F, imgs[0].getWidth() / 2F, imgs[0].getHeight() / 2F, imgs[0].getWidth(), imgs[0].getHeight(), this.scale, this.scale, this.rotation, 0, 0, imgs[0].getWidth(), imgs[0].getHeight(), false, false);
        } else if (framenum > 0.05 && framenum <= 0.1) {
            sb.draw(imgs[1], ac.hb.cX - imgs[1].getWidth() / 2F, ac.hb.cY - imgs[1].getHeight() / 2F, imgs[1].getWidth() / 2F, imgs[1].getHeight() / 2F, imgs[1].getWidth(), imgs[1].getHeight(), this.scale, this.scale, this.rotation, 0, 0, imgs[1].getWidth(), imgs[1].getHeight(), false, false);
        } else if (framenum > 0.1 && framenum <= 0.15) {
            sb.draw(imgs[2], ac.hb.cX - imgs[2].getWidth() / 2F, ac.hb.cY - imgs[2].getHeight() / 2F, imgs[2].getWidth() / 2F, imgs[2].getHeight() / 2F, imgs[2].getWidth(), imgs[2].getHeight(), this.scale, this.scale, this.rotation, 0, 0, imgs[2].getWidth(), imgs[2].getHeight(), false, false);
        } else if (framenum > 0.15 && framenum <= 0.2) {
            sb.draw(imgs[3], ac.hb.cX - imgs[3].getWidth() / 2F, ac.hb.cY - imgs[3].getHeight() / 2F, imgs[3].getWidth() / 2F, imgs[3].getHeight() / 2F, imgs[3].getWidth(), imgs[3].getHeight(), this.scale, this.scale, this.rotation, 0, 0, imgs[3].getWidth(), imgs[3].getHeight(), false, false);
        }
    }

    public void dispose() {

    }
}