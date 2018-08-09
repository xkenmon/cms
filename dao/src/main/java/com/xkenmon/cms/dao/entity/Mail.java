package com.xkenmon.cms.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author AutoGenerator
 * @since 2018-08-06
 */
@TableName("cms_mail")
public class Mail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "mail_id", type = IdType.AUTO)
    private Integer mailId;

    /**
     * 发送人
     */
    private String mailInMail;

    /**
     * 接收者
     */
    private String mailToMail;

    private LocalDateTime mailSendDate;

    private LocalDateTime mailReceiveDate;

    private String mailContent;

    /**
     * 邮件的状态：0：未读，1：已读，2：已删除
     */
    private Integer mailRead;

    /**
     * 邮件的发送状态：0：已发送，1失败，2：草稿
     */
    private Integer mailFlagStatus;


    public Integer getMailId() {
        return mailId;
    }

    public void setMailId(Integer mailId) {
        this.mailId = mailId;
    }

    public String getMailInMail() {
        return mailInMail;
    }

    public void setMailInMail(String mailInMail) {
        this.mailInMail = mailInMail;
    }

    public String getMailToMail() {
        return mailToMail;
    }

    public void setMailToMail(String mailToMail) {
        this.mailToMail = mailToMail;
    }

    public LocalDateTime getMailSendDate() {
        return mailSendDate;
    }

    public void setMailSendDate(LocalDateTime mailSendDate) {
        this.mailSendDate = mailSendDate;
    }

    public LocalDateTime getMailReceiveDate() {
        return mailReceiveDate;
    }

    public void setMailReceiveDate(LocalDateTime mailReceiveDate) {
        this.mailReceiveDate = mailReceiveDate;
    }

    public String getMailContent() {
        return mailContent;
    }

    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }

    public Integer getMailRead() {
        return mailRead;
    }

    public void setMailRead(Integer mailRead) {
        this.mailRead = mailRead;
    }

    public Integer getMailFlagStatus() {
        return mailFlagStatus;
    }

    public void setMailFlagStatus(Integer mailFlagStatus) {
        this.mailFlagStatus = mailFlagStatus;
    }

    @Override
    public String toString() {
        return "Mail{" +
        ", mailId=" + mailId +
        ", mailInMail=" + mailInMail +
        ", mailToMail=" + mailToMail +
        ", mailSendDate=" + mailSendDate +
        ", mailReceiveDate=" + mailReceiveDate +
        ", mailContent=" + mailContent +
        ", mailRead=" + mailRead +
        ", mailFlagStatus=" + mailFlagStatus +
        "}";
    }
}
