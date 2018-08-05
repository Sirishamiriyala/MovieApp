package com.example.sirisha.moviecopy;

/**
 * Created by sirisha on 18-05-2018.
 */

public class RetrospectPOJO {
    String review_writer,review_des,review_id,review_url;

    public RetrospectPOJO( String writer, String dec) {
        this.review_writer=writer;
        this.review_des=dec;
    }

    public String getReview_writer() {
        return review_writer;
    }

    public void setReview_writer(String review_writer) {
        this.review_writer = review_writer;
    }

    public String getReview_des() {
        return review_des;
    }

    public void setReview_des(String review_des) {
        this.review_des = review_des;
    }

    public String getReview_id() {
        return review_id;
    }

    public void setReview_id(String review_id) {
        this.review_id = review_id;
    }

    public String getReview_url() {
        return review_url;
    }

    public void setReview_url(String review_url) {
        this.review_url = review_url;
    }
}
