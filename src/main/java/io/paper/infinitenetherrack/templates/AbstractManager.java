package io.paper.infinitenetherrack.templates;

import io.paper.infinitenetherrack.Main;

public class AbstractManager {
    public Main main;
    public boolean loaded = false;

    public void initialize() {
    }

    public void deinitialize() {
    }

    public AbstractManager(Main main) {
        this.main = main;
    }

    public Main getHeart() {
        return this.main;
    }

    public boolean isLoaded() {
        return this.loaded;
    }

    public void setHeart(Main main) {
        this.main = main;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }
}
