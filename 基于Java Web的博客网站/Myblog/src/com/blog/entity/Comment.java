package com.blog.entity;

import java.util.Date;

public class Comment {
	private int cId;
	private int articleId;
	private String cContent;
	private Date cDate;
	private String cNickname;
	private String cEmail;
	public int getcId() {
		return cId;
	}
	public void setcId(int cId) {
		this.cId = cId;
	}
	public int getArticleId() {
		return articleId;
	}
	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}
	public String getcContent() {
		return cContent;
	}
	public void setcContent(String cContent) {
		this.cContent = cContent;
	}
	public Date getcDate() {
		return cDate;
	}
	public void setcDate(Date cDate) {
		this.cDate = cDate;
	}
	public String getcNickname() {
		return cNickname;
	}
	public void setcNickname(String cNickname) {
		this.cNickname = cNickname;
	}
	public String getcEmail() {
		return cEmail;
	}
	public void setcEmail(String cEmail) {
		this.cEmail = cEmail;
	}
	
}
