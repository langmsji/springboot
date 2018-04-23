package com.mall.entity;

import javax.persistence.*;

@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "from_uid")
    private Integer fromUserId;
    @Column(name = "to_uid")
    private Integer toUserId;
    @Column(name = "goods_id")
    private Integer goodsId;
    @Column(name = "comment_date")
    private String conmmentDate;
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getConmmentDate() {
        return conmmentDate;
    }

    public void setConmmentDate(String conmmentDate) {
        this.conmmentDate = conmmentDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
