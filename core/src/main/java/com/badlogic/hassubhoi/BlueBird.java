package com.badlogic.hassubhoi;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class BlueBird extends Bird {
    private boolean hasSplit = false;

    public BlueBird(Texture texture) {
        super(texture);
        setSize(70,70);
    }

    @Override
    public void activateSpecialAbility() {
        if (!hasSplit) {
            hasSplit = true;
            // split into three birds
            Stage stage = getStage();
            if (stage != null) {
                // creating two additional blue birds
                BlueBird bird1 = new BlueBird(texture);
                BlueBird bird2 = new BlueBird(texture);

                bird1.position.set(this.position);
                bird2.position.set(this.position);

                float speedMultiplier = 1.5f;
                bird1.velocity.set(this.velocity.cpy().rotate(15)); // choose angle as needed
                bird2.velocity.set(this.velocity.cpy().rotate(-15));

                bird1.isLaunched = true;
                bird2.isLaunched = true;

                stage.addActor(bird1);
                stage.addActor(bird2);
            }
        }
    }
}
