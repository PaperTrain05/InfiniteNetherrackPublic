package io.paper.infinitenetherrack.templates;

import io.paper.infinitenetherrack.Heart;

public class AbstractManager {
    public Heart heart;
    public boolean loaded = false;

    public void initialize() {
    }

    public void deinitialize() {
    }

    public AbstractManager(Heart heart) {
        this.heart = heart;
    }

    public Heart getHeart() {
        return this.heart;
    }

    public boolean isLoaded() {
        return this.loaded;
    }

    public void setHeart(Heart heart) {
        this.heart = heart;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }
}
