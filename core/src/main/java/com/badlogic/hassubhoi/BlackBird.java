package com.badlogic.hassubhoi;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BlackBird extends Bird {
    private boolean hasExploded = false;
    float explosionRadius = 150f;

    public BlackBird(Texture texture) {
        super(texture);
    }

    @Override
    public void activateSpecialAbility() {
        if (!hasExploded) {
            hasExploded = true;

            System.out.println("Black Bird explodes like a bomb!");

            if (getStage() != null) {
                Actor[] actorsArray = getStage().getActors().toArray(Actor.class);
                for (Actor actor : actorsArray) {
                    if (actor instanceof Pig) {
                        Pig pig = (Pig) actor;
                        if (isWithinExplosionRadius(pig)) {
                            pig.takeDamage(40); // Adjust damage as needed
                        }
                    } else if (actor instanceof Structure) {
                        Structure structure = (Structure) actor;
                        if (isWithinExplosionRadius(structure)) {
                            structure.takeDamage(40); // Adjust damage as needed
                        }
                    }
                }
            }

            // remove the bird after explosion
            // audio / animation(to be implemented)
            remove();
        }
    }
    private boolean isWithinExplosionRadius(Actor actor) {
        float distance = Vector2.dst(this.getX(), this.getY(), actor.getX(), actor.getY());
        return distance <= explosionRadius;
    }
}
