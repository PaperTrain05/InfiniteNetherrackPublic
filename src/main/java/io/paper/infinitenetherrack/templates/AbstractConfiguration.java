package io.paper.infinitenetherrack.templates;

import io.paper.infinitenetherrack.Main;

public class AbstractConfiguration {
    public transient Main main = null;
    public transient String path = "";

    public Main getHeart() {
        return this.main;
    }

    public String getPath() {
        return this.path;
    }

    public void setHeart(Main main) {
        this.main = main;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
