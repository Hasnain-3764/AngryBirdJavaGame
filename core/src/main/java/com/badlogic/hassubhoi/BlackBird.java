package com.badlogic.hassubhoi;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BlackBird extends Bird {
    private boolean hasExploded = false;

    public BlackBird(Texture texture) {
        super(texture);
    }

    @Override
    public void activateSpecialAbility() {
        if (!hasExploded) {
            hasExploded = true;
//            blackBirdExplode(); // to be implemented
            System.out.println("Black Bird explodes like a bomb!");
            // if retangles collide then explode
            if (getStage() != null) {
                Actor[] actorsArray = getStage().getActors().toArray(Actor.class);
                for (Actor actor : actorsArray) {
                    if (actor instanceof Pig) {
                        Pig pig = (Pig) actor;
                        if (this.getBoundingRectangle().overlaps(pig.getBoundingRectangle())) {
                            pig.takeDamage(20); // some damage value

                        }
                    } else if (actor instanceof Structure) {
                        Structure structure = (Structure) actor;
                        if (this.getBoundingRectangle().overlaps(structure.getBoundingRectangle())) {
                            structure.takeDamage(20); // some other damage value
                        }
                    }
                }
            }

            // remove the bird after explosion
            remove();
        }
    }
}
