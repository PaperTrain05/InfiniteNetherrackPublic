package io.paper.infinitenetherrack.templates;

import io.paper.infinitenetherrack.Heart;

public class AbstractConfiguration {
    public transient Heart heart = null;
    public transient String path = "";

    public Heart getHeart() {
        return this.heart;
    }

    public String getPath() {
        return this.path;
    }

    public void setHeart(Heart heart) {
        this.heart = heart;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
