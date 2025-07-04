package com.example.home;

public class Users {
    private String name;
    private String time;        // 原text1
    private String content;     // 原text2
    private String likeCount;   // 原text3
    private String commentCount;// 原text4
    private int userimgID;
    private int moreOptionsIcon;
    private int contentImage;
    private int likeIcon;
    private int commentIcon;
    private int shareIcon;

    // 构造函数和Getter/Setter方法
    public Users(String name, String time, String content, String likeCount, String commentCount,
                 int userimgID, int moreOptionsIcon, int contentImage,
                 int likeIcon, int commentIcon, int shareIcon) {
        this.name = name;
        this.time = time;
        this.content = content;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.userimgID = userimgID;
        this.moreOptionsIcon = moreOptionsIcon;
        this.contentImage = contentImage;
        this.likeIcon = likeIcon;
        this.commentIcon = commentIcon;
        this.shareIcon = shareIcon;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public int getUserimgID() {
        return userimgID;
    }

    public void setUserimgID(int userimgID) {
        this.userimgID = userimgID;
    }

    public int getMoreOptionsIcon() {
        return moreOptionsIcon;
    }

    public void setMoreOptionsIcon(int moreOptionsIcon) {
        this.moreOptionsIcon = moreOptionsIcon;
    }

    public int getContentImage() {
        return contentImage;
    }

    public void setContentImage(int contentImage) {
        this.contentImage = contentImage;
    }

    public int getLikeIcon() {
        return likeIcon;
    }

    public void setLikeIcon(int likeIcon) {
        this.likeIcon = likeIcon;
    }

    public int getCommentIcon() {
        return commentIcon;
    }

    public void setCommentIcon(int commentIcon) {
        this.commentIcon = commentIcon;
    }

    public int getShareIcon() {
        return shareIcon;
    }

    public void setShareIcon(int shareIcon) {
        this.shareIcon = shareIcon;
    }

}