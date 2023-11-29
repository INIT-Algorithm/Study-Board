package com.example.study.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
	private String content;
	private Long userId;
	private Long boardId;
	private Long parent;	//부모댓글 id
}
