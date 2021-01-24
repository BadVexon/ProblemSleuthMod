package theSleuth.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;

public class SparkCurvyEffect extends AbstractGameEffect {
    private Vector2 pos;
    private float speed;
    private float speedStart;
    private float speedTarget;
    private float waveIntensity;
    private float waveSpeed;
    private float zaggyTimer;
    private TextureAtlas.AtlasRegion img;
    private ArrayList<Vector2> positions;

    public SparkCurvyEffect(float x, float y, Color color, boolean renderBehind) {
        this.pos = new Vector2();
        this.positions = new ArrayList<>();
        this.img = ImageMaster.STRIKE_LINE;
        this.duration = MathUtils.random(0.35F, 0.6F);
        this.startingDuration = this.duration;
        this.pos.x = x - (float) this.img.packedWidth / 2.0F;
        this.pos.y = y - (float) this.img.packedHeight / 2.0F;
        this.speed = MathUtils.random(1600.0F, 1900.0F) * Settings.scale;
        this.speedStart = this.speed;
        this.color = color;
        this.renderBehind = false;
        this.rotation = MathUtils.random(360.0F);
        this.speedTarget = MathUtils.random(0.1F, 0.5F);
        this.zaggyTimer = MathUtils.random(0.1F, 0.25F);
    }

    public SparkCurvyEffect(float x, float y, Color color, boolean renderBehind, float startDur, float startSpeed, float parentStartSpeed, float startRotation, float speedTarg) {
        this.pos = new Vector2();
        this.positions = new ArrayList<>();
        this.img = ImageMaster.STRIKE_LINE;
        this.duration = startDur;
        this.startingDuration = this.duration;
        this.pos.x = x - (float) this.img.packedWidth / 2.0F;
        this.pos.y = y - (float) this.img.packedHeight / 2.0F;
        this.speed = startSpeed;
        this.speedStart = parentStartSpeed;
        this.color = color;
        this.renderBehind = false;
        this.rotation = startRotation;
        this.speedTarget = speedTarg;
        this.zaggyTimer = MathUtils.random(0.1F, 0.25F);
    }

    public SparkCurvyEffect(float x, float y) {
        this(x, y, Color.GOLDENROD, true);
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

        zaggyTimer -= Gdx.graphics.getDeltaTime();

        if (this.zaggyTimer < 0.0F) {
            if (this.duration >= 0.5F) {
                if ( MathUtils.random(3.0F) <= 1.0F) {
                    AbstractDungeon.effectsQueue.add(new SparkCurvyEffect((this.pos.x - (float) (this.img.packedWidth / 2.0F)), (this.pos.y - (float) (this.img.packedHeight / 2.0F)), Color.YELLOW, false, this.duration, this.speed, this.speedStart, this.rotation + MathUtils.random(-45.0F, 45.0F), this.speedTarget));
                }
                this.rotation += MathUtils.random(0.0F, 360.0F);
                this.zaggyTimer = MathUtils.random(0.1F, 0.25F);
            }
            else {
                this.rotation += MathUtils.random(-10.0F, 10.0F);
                if ( MathUtils.random(2.0F) <= 1.0F) {
                    this.rotation += 80.0F;
                }
                else {
                    this.rotation -= 80.0F;
                }
                this.zaggyTimer = MathUtils.random(0.02F, 0.05F);
            }
        }

        this.scale = Settings.scale * this.duration / this.startingDuration * 0.75F;
        super.update();
    }

    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);
        Color tmp = this.color.cpy();
        tmp.a = 0.25F;

        for (int i = this.positions.size() - 1; i > 0; --i) {
            sb.setColor(tmp);
            tmp.a *= 0.95F;
            if (tmp.a > 0.05F) {
                float var10004 = (float) this.img.packedWidth / 2.0F;
                float var10005 = (float) this.img.packedHeight / 2.0F;
                float var10006 = (float) this.img.packedWidth;
                float var10007 = (float) this.img.packedHeight;
                sb.draw(this.img, ((Vector2) this.positions.get(i)).x, ((Vector2) this.positions.get(i)).y, var10004, var10005, var10006, var10007, this.scale * 2.0F, this.scale * 2.0F, this.rotation);
            }
        }

        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}