package com.jszt.vo;

public class NoticeVo {
	
	/**
     * 短信id
     */
    private int id;
	
	/**
     * 短信发送时间
     */
    private String noticeTime;
	
    /**
     * 短信发送内容
     */
    private String noticeContent;

	public String getNoticeTime() {
		return noticeTime;
	}

	public void setNoticeTime(String noticeTime) {
		this.noticeTime = noticeTime;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
    
    

}
