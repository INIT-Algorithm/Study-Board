package com.example.study.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Likes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "like_id")
	private Long likesId;

	@ManyToOne(targetEntity = Board.class)
	@JoinColumn(name = "board_id")
	private Board board;

	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "user_id")
	private User user;
}
