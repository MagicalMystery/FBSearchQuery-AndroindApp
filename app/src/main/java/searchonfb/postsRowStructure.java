package com.example.fbsearch.searchonfb;


public class postsRowStructure {
    public String imgurl;
    public String title;
    public String time;
    public String message;

//    public postsRowStructure(){
//        super();
//    }

    public postsRowStructure(String imgurl, String title, String time, String message) {
//        super();
        this.imgurl = imgurl;
        this.title = title;
        this.time = time;
        this.message = message;
    }

}
