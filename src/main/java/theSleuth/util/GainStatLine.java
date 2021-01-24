




package theSleuth.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class GainStatLine extends AbstractGameEffect {
    private float x;
    private float y;
    private Vector2 speedVector;
    private float speed;
    private AtlasRegion img;

    public GainStatLine(float x, float y, Color colosr, double initialAngle) {
        if (MathUtils.randomBoolean()) {
            this.img = ImageMaster.STRIKE_LINE;
        } else {
            this.img = ImageMaster.STRIKE_LINE_2;
        }

        this.duration = 0.5F;
        this.startingDuration = 0.5F;
        this.x = x - (float) this.img.packedWidth / 2.0F;
        this.y = y - (float) this.img.packedHeight / 2.0F;
        this.speed = MathUtils.random(20.0F * Settings.scale, 40.0F * Settings.scale);

        initialAngle = (initialAngle * (3.14 / 180));
        initialAngle -= 3.14;

        this.speedVector = new Vector2((float) Math.cos(initialAngle), (float) Math.sin(initialAngle));
        this.speedVector.nor();
        this.speedVector.angle();
        this.rotation = this.speedVector.angle();
        this.speedVector.x *= this.speed;
        this.speedVector.y *= this.speed;
        this.color = colosr;
    }


    public void update() {
        this.speed -= Gdx.graphics.getDeltaTime() * 60.0F;
        this.speedVector.nor();
        Vector2 var10000 = this.speedVector;
        var10000.x *= this.speed * Gdx.graphics.getDeltaTime() * 60.0F;
        var10000 = this.speedVector;
        var10000.y *= this.speed * Gdx.graphics.getDeltaTime() * 60.0F;
        this.x += this.speedVector.x;
        this.y += this.speedVector.y;
        this.scale = Settings.scale * this.duration / 0.5F;
        super.update();
    }

    public void render(SpriteBatch sb) {
        if (!this.isDone) {
            sb.setColor(this.color);
            sb.draw(this.img, this.x, this.y, (float) this.img.packedWidth / 2.0F, (float) this.img.packedHeight / 2.0F, (float) this.img.packedWidth, (float) this.img.packedHeight, this.scale, this.scale, this.rotation);
        }

    }

    public void dispose() {
    }
}
