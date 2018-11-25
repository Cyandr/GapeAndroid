package com.gape.cyandr.game.core;

public class LTimer {
    long currentTick;
    long delay;
    boolean action = true;

    public LTimer(long delay) {
        this.delay = delay;
    }

    public boolean action(long elapsedTime) {
        if (!action) {
            return false;
        }
        currentTick += elapsedTime;
        if (currentTick >= this.delay) {
            currentTick -= this.delay;
            return true;
        }
        return false;
    }

    public void setDelay(long delay) {
        this.delay = delay;

    }

    public void refresh() {
        this.currentTick = 0;
    }

    public void start() {
        this.action = true;
    }

    public void stop() {
        this.action = false;
    }
}
