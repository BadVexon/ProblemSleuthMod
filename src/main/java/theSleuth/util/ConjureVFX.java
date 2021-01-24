package theSleuth.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import theSleuth.SleuthMod;

public class ConjureVFX extends AbstractGameEffect {
    private static Texture[] imgs;

    private AbstractCreature ac;
    private boolean floored;


    private float framenum = 0;

    public ConjureVFX(AbstractCreature ac) {
        if (imgs == null) {
            imgs = new Texture[5];
            imgs[0] = TextureLoader.getTexture(SleuthMod.makeVFXPath("Conjure1.png"));
            imgs[1] = TextureLoader.getTexture(SleuthMod.makeVFXPath("Conjure2.png"));
            imgs[2] = TextureLoader.getTexture(SleuthMod.makeVFXPath("Conjure3.png"));
            imgs[3] = TextureLoader.getTexture(SleuthMod.makeVFXPath("Conjure4.png"));
            imgs[4] = TextureLoader.getTexture(SleuthMod.makeVFXPath("Conjure5.png"));
        }

        this.ac = ac;
        if (ac.hb_y <= 40) {
            floored = true;
        }

    }

    public void update() {
        this.framenum += Gdx.graphics.getDeltaTime();
        System.out.println(framenum + " is the current frame number.");
        if (this.framenum > 0.31) {
            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
        int boost = 0;
        if (floored) {
            boost = 40;
        }
        sb.setColor(Color.WHITE.cpy());
        if (framenum <= 0.05) {
            sb.draw(imgs[0], ac.hb.cX - imgs[0].getWidth() / 2F, (ac.hb.cY - imgs[0].getHeight() / 2F) + boost, imgs[0].getWidth() / 2F, (imgs[0].getHeight() / 2F) + boost, imgs[0].getWidth(), imgs[0].getHeight() + boost, this.scale, this.scale, this.rotation, 0, 0, imgs[0].getWidth(), imgs[0].getHeight() + boost, false, false);
        } else if (framenum > 0.05 && framenum <= 0.11) {
            sb.draw(imgs[1], ac.hb.cX - imgs[1].getWidth() / 2F, (ac.hb.cY - imgs[1].getHeight() / 2F) + boost, imgs[1].getWidth() / 2F, (imgs[1].getHeight() / 2F) + boost, imgs[1].getWidth(), imgs[1].getHeight() + boost, this.scale, this.scale, this.rotation, 0, 0, imgs[1].getWidth(), imgs[1].getHeight() + boost, false, false);
        } else if (framenum > 0.11 && framenum <= 0.19) {
            sb.draw(imgs[2], ac.hb.cX - imgs[2].getWidth() / 2F, (ac.hb.cY - imgs[2].getHeight() / 2F) + boost, imgs[2].getWidth() / 2F, (imgs[2].getHeight() / 2F) + boost, imgs[2].getWidth(), imgs[2].getHeight() + boost, this.scale, this.scale, this.rotation, 0, 0, imgs[2].getWidth(), imgs[2].getHeight() + boost, false, false);
        } else if (framenum > 0.19 && framenum <= 0.29) {
            sb.draw(imgs[3], ac.hb.cX - imgs[3].getWidth() / 2F, (ac.hb.cY - imgs[3].getHeight() / 2F) + boost, imgs[3].getWidth() / 2F, (imgs[3].getHeight() / 2F) + boost, imgs[3].getWidth(), imgs[3].getHeight() + boost, this.scale, this.scale, this.rotation, 0, 0, imgs[3].getWidth(), imgs[3].getHeight() + boost, false, false);
        } else if (framenum > 0.29 && framenum <= 0.31) {
            sb.draw(imgs[4], ac.hb.cX - imgs[4].getWidth() / 2F, (ac.hb.cY - imgs[4].getHeight() / 2F) + boost, imgs[4].getWidth() / 2F, (imgs[4].getHeight() / 2F) + boost, imgs[4].getWidth(), imgs[4].getHeight() + boost, this.scale, this.scale, this.rotation, 0, 0, imgs[4].getWidth(), imgs[4].getHeight() + boost, false, false);
        }
    }

    public void dispose() {

    }
}