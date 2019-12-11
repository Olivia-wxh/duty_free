package com.shangchao.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "ahg_reserve")
public class Reserve {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private String mobile;

    private String meetingTitle;

    private Integer reserveStatus;

    private Date meetingDate;

    private String start_time;

    private String end_time;

    private Integer mode;

    private Long meetingRoomId;

    private String comment;

    private String verificationCode;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


}