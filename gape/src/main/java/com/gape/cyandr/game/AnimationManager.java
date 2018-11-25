package com.gape.cyandr.game;



import java.util.LinkedList;

public class AnimationManager {
    public static LinkedList<Animation> animations = new LinkedList<Animation>();

    public static void add(Animation animation) {
        animations.add(animation);
    }

    public static void remove(Animation animation) {
        animations.add(animation);
    }

    public static void update(long elapsedTime) {
        for (Animation animation : animations) {
            animation.update(elapsedTime);
        }
    }
}
