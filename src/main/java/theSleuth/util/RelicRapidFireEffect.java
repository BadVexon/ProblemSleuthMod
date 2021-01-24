package theSleuth.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class RelicRapidFireEffect extends AbstractGameEffect {

    public static int flighttime;

    public static final float gravity = 0.5F * Settings.scale;
    public static final float frictionX = 0.1F * Settings.scale;
    public static final float frictionY = 0.2F * Settings.scale;

    public static final int dispersalspeed = 1;
    public boolean finishedAction;
    private RelicInfo letsago;

    public RelicRapidFireEffect(AbstractRelic relic, AbstractMonster target) {
        letsago = new RelicInfo(relic, target);
        flighttime = 15;
    }

    public RelicRapidFireEffect(AbstractRelic relic, AbstractMonster target, int flight) {
        letsago = new RelicInfo(relic, target);
        flighttime = flight;
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

    class RelicInfo {
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
        private AbstractRelic ar;

        public RelicInfo(AbstractRelic ar, AbstractCreature ac) {
            targetX = ac.hb.cX + MathUtils.random(ac.hb.width) - ac.hb.width * 1 / 4;
            targetY = ac.hb.cY + MathUtils.random(ac.hb.height) - ac.hb.height * 1 / 4;

            x = AbstractDungeon.player.hb.cX;
            y = AbstractDungeon.player.hb.cY;

            this.ar = ar;
            this.ac = ac;

            hit = 0;
            frames = 0;

            bounceplane = ac.hb.y + MathUtils.random(ac.hb.height / 4, ac.hb.height / 4);

            opacity = 1F;

            rotation = 0;
            radialvelocity = 0;
        }

        public void render(SpriteBatch sb) {
            sb.setColor(1F, 1F, 1F, opacity);
            sb.draw(ar.img, this.x - 64.0F, this.y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale, Settings.scale, this.rotation, 0, 0, 128, 128, false, false);
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
