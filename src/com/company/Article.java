package com.company;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Article {
    private final String title;
    private final String category;
    private final String postTime;
    private final String url;
    
    public Article(Element el,String baseUrl,String year){
        this.title = el.getElementsByClass("thumb-s__tit").text();
        this.url = Article.urlMaker(el.getElementsByClass("thumb-s__tit-link").attr("href").toString(),baseUrl);
        this.category = el.getElementsByClass("thumb-s__category").text();
        this.postTime = year+"/"+el.getElementsByClass("thumb-s__date").text();
    }

    @Override
    public String toString() {
        return "\""+this.title+"\",\""+this.category+"\",\""+this.postTime+"\",\""+this.url+"\"";
    }

    static String urlMaker(String relativeLocation, String base){
        if(relativeLocation=="" || relativeLocation==null){
            return "";
        }
        if(relativeLocation.contains("http") || relativeLocation.contains(".")){
            return relativeLocation;
        }else {
            return base+relativeLocation;
        }
    }
}
