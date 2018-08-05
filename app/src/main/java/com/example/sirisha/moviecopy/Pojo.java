package com.example.sirisha.moviecopy;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sirisha on 15-05-2018.
 */

class Pojo {

    String poster2,Poster,title,analysis,rel_date,lang,mtitle;
    String vote_count;
    String vote_avg;
    String Ident;
    String popularity;
    boolean video,adult;


    public Pojo(String poster2, String poster, String title,
                String analysis, String rel_date, String vote_avg,
                String vote_count,String popularity, boolean video,
                String ident, boolean adult, String mtitle, String lang) {
        this.poster2=poster2;
        this.Poster=poster;
        this.title=title;
        this.analysis=analysis;
        this.rel_date=rel_date;
        this.lang=lang;
        this.mtitle=mtitle;
        this.vote_count=vote_count;
        this.vote_avg=vote_avg;
        this.Ident=ident;
        this.popularity=popularity;
        this.video=video;
        this.adult=adult;

    }

    public Pojo(String ident, String poster2,String poster, String vote_avg, String analysis, String rel_date, String mtitle) {
        this.Ident=ident;
        this.poster2=poster2;
        this.Poster=poster;
        this.vote_avg=vote_avg;
        this.analysis=analysis;
        this.rel_date=rel_date;
        this.mtitle=mtitle;
    }

    public String getPoster2() {
        return poster2;
    }

    public void setPoster2(String poster2) {
        this.poster2 = poster2;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public String getRel_date() {
        return rel_date;
    }

    public void setRel_date(String rel_date) {
        this.rel_date = rel_date;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getMtitle() {
        return mtitle;
    }

    public void setMtitle(String mtitle) {
        this.mtitle = mtitle;
    }

    public String getVote_count() {
        return vote_count;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

    public String getVote_avg() {
        return vote_avg;
    }

    public void setVote_avg(String vote_avg) {
        this.vote_avg = vote_avg;
    }

    public String getIdent() {
        return Ident;
    }

    public void setIdent(String ident) {
        Ident = ident;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public boolean isVideo() {
        return video;}

    public void setVideo(boolean video) {
        this.video = video;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }
}
