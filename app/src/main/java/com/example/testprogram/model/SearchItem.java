
package com.example.testprogram.model;

import java.util.ArrayList;
import java.util.List;

public class SearchItem {


    private ArrayList<Hit> hits = null;
    private Integer page;


    public ArrayList<Hit> getHits() {
        return hits;
    }

    public void setHits(ArrayList<Hit> hits) {
        this.hits = hits;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }


}
