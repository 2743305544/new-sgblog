package com.example.domain.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddArticleDto {

    @JsonProperty(value = "id")
    private Long id;
    @JsonProperty(value = "title")
    private String title;
    @JsonProperty(value = "content")
    private String content;
    @JsonProperty(value = "summary")
    private String summary;
    @JsonProperty(value = "categoryId")
    private Long categoryId;
    @JsonProperty(value = "thumbnail")
    private String thumbnail;
    @JsonProperty(value = "isTop")
    private String isTop;

    @JsonProperty(value = "viewCount")
    private Long viewCount;
    @JsonProperty(value = "isComment")
    private String isComment;
    @JsonProperty(value = "tags")
    private List<Long> tags;

}
