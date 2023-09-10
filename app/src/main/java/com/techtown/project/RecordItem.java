package com.techtown.project;

public class RecordItem {
    private int _id;
    private String date;
    private String comment;
    private String video_title;
    private String video_id;
    public RecordItem(int _id,String date,String comment,String video_title,String video_id)
    {
        this._id=_id;
        this.date=date;
        this.comment=comment;
        this.video_id=video_id;
        this.video_title=video_title;
    }
    public int get_id() { return _id; }
    public void set_id(int _id) { this._id=_id; }
    public String getDate() { return date; }
    public void setDate(String date) {
        this.date = date;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getVideo_title() {
        return video_title;
    }
    public void setVideo_title(String video_title) {
        this.video_title = video_title;
    }
    public String getVideo_id() {
        return video_id;
    }
    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }
}
