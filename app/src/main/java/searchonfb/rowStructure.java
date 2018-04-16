package com.example.fbsearch.searchonfb;


public class rowStructure {
    public String imgurl;
    public String title;
    public String starState;
    public String details_id;
    public String type;

    int position;
    public rowStructure(){
        super();
    }

    public rowStructure(int position,String type,String imgurl, String title,String starState,String details_id) {
        super();
        this.imgurl = imgurl;
        this.title = title;
        this.starState = starState;
        this.details_id = details_id;
        this.position=position;
        this.type=type;
    }

}
