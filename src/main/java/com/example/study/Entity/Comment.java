package com.example.study.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Long id;

	private String content;

	@ManyToOne(targetEntity = Board.class)
	@JoinColumn(name = "board_id")
	private Board board;

	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "user_id")
	private User user;

	//depth
	@ColumnDefault("0")
	private Long depth;

	//parent-> 부모 댓글 id
	@ColumnDefault("0")
	private Long parent;

	@ColumnDefault("false")
	private Boolean isDeleted;
}
