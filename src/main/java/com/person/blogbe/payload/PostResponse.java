package com.person.blogbe.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private List<PostDto> data;
    private int pageNo;
    private int pageSize;
    private long totalRecords;
    private int totalPage;
    private boolean last;
}
