




package theSleuth.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;

public class BeatdownCurvy extends AbstractGameEffect {
    private Vector2 pos;
    private float speed;
    private float speedStart;
    private float speedTarget;
    private float waveIntensity;
    private float waveSpeed;
    private AtlasRegion img;
    private ArrayList<Vector2> positions;

    public BeatdownCurvy(float x, float y, boolean renderBehind) {
        this.pos = new Vector2();
        this.positions = new ArrayList();
        this.img = ImageMaster.STRIKE_LINE_2;
        this.duration = MathUtils.random(0.8F, 1.1F);
        this.startingDuration = this.duration;
        this.pos.x = x - (float)this.img.packedWidth / 2.0F;
        this.pos.y = y - (float)this.img.packedHeight / 2.0F;
        this.speed = MathUtils.random(400.0F, 900.0F) * Settings.scale;
        this.speedStart = this.speed;
        this.speedTarget = MathUtils.random(200.0F, 300.0F) * Settings.scale;
        if (MathUtils.randomBoolean()) {
            this.color = new Color(1F, 0F, 0.976F, 1.0F);
        } else {
            this.color = new Color(1F, 0F, 0.976F, 1.0F);
        }
        this.renderBehind = false;
        this.rotation = MathUtils.random(360.0F);
        this.waveIntensity = MathUtils.random(5.0F, 30.0F);
        this.waveSpeed = MathUtils.random(-20.0F, 20.0F);
        this.speedTarget = MathUtils.random(0.1F, 0.5F);
    }

    public BeatdownCurvy(float x, float y) {
        this(x, y,  true);
    }

    public void update() {
        this.positions.add(this.pos);
        Vector2 tmp = new Vector2(MathUtils.cosDeg(this.rotation), MathUtils.sinDeg(this.rotation));
        tmp.x *= this.speed * Gdx.graphics.getDeltaTime();
        tmp.y *= this.speed * Gdx.graphics.getDeltaTime();
        this.speed = Interpolation.pow2OutInverse.apply(this.speedStart, this.speedTarget, 1.0F - this.duration / this.startingDuration);
        Vector2 var10000 = this.pos;
        var10000.x += tmp.x;
        var10000 = this.pos;
        var10000.y += tmp.y;
        this.rotation += MathUtils.cos(this.duration * this.waveSpeed) * this.waveIntensity * Gdx.graphics.getDeltaTime() * 60.0F;
        this.scale = Settings.scale * this.duration / this.startingDuration * 0.75F;
        super.update();
    }

    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);
        Color tmp = this.color.cpy();
        tmp.a = 0.25F;

        for(int i = this.positions.size() - 1; i > 0; --i) {
            sb.setColor(tmp);
            tmp.a *= 0.95F;
            if (tmp.a > 0.05F) {
                float var10004 = (float)this.img.packedWidth / 2.0F;
                float var10005 = (float)this.img.packedHeight / 2.0F;
                float var10006 = (float)this.img.packedWidth;
                float var10007 = (float)this.img.packedHeight;
                sb.draw(this.img, ((Vector2)this.positions.get(i)).x, ((Vector2)this.positions.get(i)).y, var10004, var10005, var10006, var10007, this.scale * 2.0F, this.scale * 2.0F, this.rotation);
            }
        }

        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}
