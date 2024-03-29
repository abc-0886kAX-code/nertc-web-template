package com.ytxd.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author CYC
 */
@Getter
@Setter
public class Data {

    @JsonProperty("keywords")
    private String keywords;

    @JsonProperty("pageIndex")
    private int pageIndex;

    @JsonProperty("pageSize")
    private int pageSize;

    @JsonProperty("relaString")
    private String relaString;

    // getters and setters

}
