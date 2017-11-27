package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        // write your code here
        String url = "http://news.mynavi.jp/list/headline/";
        ArrayList<Article> articleList = crawl(url);

        String header = "件名,カテゴリ,ニュース日時,URL";
        exportArticles(articleList,header);
    }

    public static void exportArticles(List<Article> articleList,String header) throws IOException{
        PrintWriter pw = new PrintWriter(System.getProperty("user.dir")+"/files/mynavi_articles.csv","UTF-8");
        pw.write(header+"\n");
        for (Article article:
             articleList) {
            pw.write(article.toString()+"\n");
        }
        pw.close();
    }
    public static ArrayList<Article> crawl(String currentPage) throws IOException {
        String siteName = "http://"+currentPage.split("/")[2];
        
        Document doc = Jsoup.connect(currentPage).get();
        Elements els = doc.getElementsByClass("thumb-s__item");
        String year = doc.getElementsByClass("thumb-s__update").text().substring(0,4);

        ArrayList<Article> articles = new ArrayList<>();
        for (Element el : els) {
            Article newArticle = new Article(el,siteName,year);
            if(!articles.contains(newArticle)) {
                articles.add(newArticle);
            }
        }
        return articles;
    }

    /*public static void jsoupSample() throws IOException {

        String rootUrl = "http://news.mynavi.jp/";

        Document doc = Jsoup.connect(rootUrl).get();
        Elements els = doc.getElementsByClass("news_tab_tabbox_left_list");

        int tabIdx = 0;
        for (Element el : els) {
            tabIdx++;
            if (tabIdx == 1) {
                System.out.println("■総合");
            } else if (tabIdx == 2) {
                continue;
            } else if (tabIdx == 3) {
                System.out.println("■ビジネス");
            } else if (tabIdx == 4) {
                System.out.println("■デジタル");
            } else if (tabIdx == 5) {
                System.out.println("■ライフ");
            } else if (tabIdx == 6) {
                System.out.println("■エンタメ");
            }

            for (Element child : el.children()) {
                //ニュースの件名
                String title = child.text();

                //ニュースのURL
                String url = child.child(0).attr("href");
                url = rootUrl + url;

                System.out.println("\t" + title + "\n\t(" + url + ")\n");
            }
        }
    }*/
}
