/**
 * FileName: NewsEntity
 * Author:   Phil
 * Date:     8/3/2018 21:32
 * Description: (上传)图文消息素材实体
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.shangchao.entity;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈(上传)图文消息素材实体〉
 *
 * @create 8/3/2018 21:32
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
public class UploadNewsEntity implements Serializable {

    private static final long serialVersionUID = -7911349297802099639L;

    private List<UploadNewsArticle> articles = new ArrayList<>();

    public void addArticle(UploadNewsArticle article) {
        this.articles.add(article);
    }

    @Getter
    @Setter
    @ToString
    public static class UploadNewsArticle {

        @SerializedName("thumb_media_id")
        private String thumbMediaId; // 图文消息缩略图的media_id，可以在基础支持-上传多媒体文件接口中获得

        private String author; // 图文消息的作者

        private String title; // 图文消息的标题

        @SerializedName("content_source_url")
        private String contentSourceUrl; // 图文消息点击阅读原文的链接

        private String content; // 图文消息页面的内容，支持HTML标签

        private String digest; // 图文消息的描述

        @SerializedName("show_conver_pic")
        private int showConverPic; // 是否显示为封面 1表示显示为封面 0 不显示为封面

        @SerializedName("need_open_comment")
        private int needOpenComment; // Uint32 是否打开评论，0不打开，1打开
        @SerializedName("only_fans_can_comment")
        private int onlyFansCanComment; // Uint32 是否粉丝才可评论，0所有人可评论，1粉丝才可评论

    }

}
