package com.wja.keren.user.home.view;

import java.io.Serializable;

public class PublicModel implements Serializable {
    private String content;
    private int index;

    public String getContent() {
        return content;
    }

    public PublicModel(String content, int index) {
        super();
        this.content = content;
        this.index = index;
    }

    public PublicModel() {
        super();
    }


    public void setContent(String content) {
        this.content = content;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
