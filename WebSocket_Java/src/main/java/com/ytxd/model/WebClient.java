package com.ytxd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author CYC
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebClient {

    private String content;

    private String sender;

    private Date sendtime;

    private String title;

    private int warningcode;

    private int warninglevel;


}
