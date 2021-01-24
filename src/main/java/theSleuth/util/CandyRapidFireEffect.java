package theSleuth.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import theSleuth.SleuthMod;

public class CandyRapidFireEffect extends AbstractGameEffect {

    public static int flighttime;

    public static final float gravity = 0.5F * Settings.scale;
    public static final float frictionX = 0.1F * Settings.scale;
    public static final float frictionY = 0.2F * Settings.scale;

    public static final int dispersalspeed = 1;
    public boolean finishedAction;
    private CandyInfo letsago;

    public CandyRapidFireEffect(AbstractMonster target, int checkup) {
        letsago = new CandyInfo(target, checkup);
        flighttime = 15;
    }

    @Override
    public void render(SpriteBatch sb) {
        letsago.render(sb);
        sb.setColor(Color.WHITE);
    }

    public void update() {
        boolean finishedEffect = true;

        int wahoo = letsago.update();

        if (wahoo != 3) {
            finishedEffect = false;
        }

        if (wahoo == 1) {
            finishedAction = true;
        }

        if (finishedEffect) {
            this.isDone = true;
        }
    }

    public void dispose() {

    }

    class CandyInfo {
        private float x;
        private float y;
        private float targetX;
        private float targetY;
        private float rotation;
        private float radialvelocity;
        private float bounceplane;
        private float opacity;
        private int hit;
        private int frames;
        private AbstractCreature ac;
        private Texture image;

        public CandyInfo(AbstractCreature ac, int bleh) {
            targetX = ac.hb.cX + MathUtils.random(ac.hb.width) - ac.hb.width * 1 / 4;
            targetY = ac.hb.cY + MathUtils.random(ac.hb.height) - ac.hb.height * 1 / 4;

            x = AbstractDungeon.player.hb.cX;
            y = AbstractDungeon.player.hb.cY;

            this.ac = ac;

            hit = 0;
            frames = 0;

            bounceplane = ac.hb.y + MathUtils.random(ac.hb.height / 4, ac.hb.height / 4);

            opacity = 1F;


            if (bleh == 9) {
                this.image = TextureLoader.getTexture(SleuthMod.makeVFXPath("CandyBulletSuperRare.png"));
            } else if (bleh == 10) {
                this.image = TextureLoader.getTexture(SleuthMod.makeVFXPath("CandyBulletRare.png"));
            } else if (bleh == 0) {
                this.image = TextureLoader.getTexture(SleuthMod.makeVFXPath("CandyBullet1.png"));
            } else if (bleh == 1) {
                this.image = TextureLoader.getTexture(SleuthMod.makeVFXPath("CandyBullet2.png"));
            } else if (bleh == 2) {
                this.image = TextureLoader.getTexture(SleuthMod.makeVFXPath("CandyBullet3.png"));
            } else if (bleh == 3) {
                this.image = TextureLoader.getTexture(SleuthMod.makeVFXPath("CandyBullet4.png"));
            } else if (bleh == 4) {
                this.image = TextureLoader.getTexture(SleuthMod.makeVFXPath("CandyBullet5.png"));
            } else if (bleh == 5) {
                this.image = TextureLoader.getTexture(SleuthMod.makeVFXPath("CandyBullet6.png"));
            } else if (bleh == 6) {
                this.image = TextureLoader.getTexture(SleuthMod.makeVFXPath("CandyBullet7.png"));
            } else if (bleh == 7) {
                this.image = TextureLoader.getTexture(SleuthMod.makeVFXPath("CandyBullet8.png"));
            } else if (bleh == 8) {
                this.image = TextureLoader.getTexture(SleuthMod.makeVFXPath("CandyBullet9.png"));
            }

            rotation = (MathUtils.random(-30.0F, 30.0F));
            radialvelocity = (MathUtils.random(-10.0F, 10.0F));
        }

        public void render(SpriteBatch sb) {
            sb.setColor(1F, 1F, 1F, opacity);
            sb.draw(this.image, (this.x - (this.image.getWidth() / 2F)), (this.y - (this.image.getHeight() / 2F)), this.image.getWidth() / 2F, this.image.getHeight() / 2F, this.image.getWidth(), this.image.getHeight(), Settings.scale, Settings.scale, this.rotation, 0, 0, this.image.getWidth(), this.image.getHeight(), false, false);
        }

        public int update() {
            if (hit == 0) {
                x = AbstractDungeon.player.hb.cX + (targetX - AbstractDungeon.player.hb.cX) / (float) flighttime * frames;
                y = AbstractDungeon.player.hb.cY + (targetY - AbstractDungeon.player.hb.cY) / (float) flighttime * frames;

                if (frames++ == flighttime) {
                    frames = 0;
                    hit = 1;

                    radialvelocity = MathUtils.random(-30, 30);

                    targetX = (targetX - ac.hb.cX - ac.hb.width / 4) / 4;
                    targetY = (targetY - ac.hb.cY) / 4;
                }
            } else {
                this.targetX += (this.targetX > 0 ? -frictionX : frictionX);

                if (y + this.targetY <= bounceplane) {
                    this.targetY = Math.abs(this.targetY);
                    if (this.targetY > 1 * Settings.scale) {
                        this.radialvelocity = MathUtils.random(-30, 30);
                    } else {
                        this.radialvelocity = 0;
                    }
                    hit = 2;
                } else {
                    this.targetY -= (this.targetY > 0 ? frictionY : -frictionY);
                    this.targetY -= gravity;
                }
                x += targetX;
                y += targetY;
                rotation += radialvelocity;

                if (hit > 1) {
                    if (opacity <= 0F) {
                        opacity = 0F;
                        hit = 3;
                    } else {
                        opacity -= dispersalspeed / 300F;
                    }
                }
            }
            return hit;
        }
    }
}
