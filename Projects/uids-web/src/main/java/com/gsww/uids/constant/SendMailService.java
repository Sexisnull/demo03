package com.gsww.uids.constant;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class SendMailService
{
  private boolean sendMail(MailSenderInfo mailInfo)
  {
    boolean isSuccess = true;

    EmailAuthenticator authenticator = null;
    Properties pro = mailInfo.getProperties();

    if (mailInfo.isValidate()) {
      authenticator = new EmailAuthenticator(mailInfo.getUserName(), 
        mailInfo.getPassword());
    }

    Session sendMailSession = Session.getInstance(pro, authenticator);

    sendMailSession.setDebug(mailInfo.getDebug());
    try
    {
      Message mailMessage = new MimeMessage(sendMailSession);

      Address from = new InternetAddress(mailInfo.getFromAddress());

      mailMessage.setFrom(from);

      Address to = new InternetAddress(mailInfo.getToAddress());

      mailMessage.setRecipient(Message.RecipientType.TO, to);

      mailMessage.setSubject(mailInfo.getSubject());

      mailMessage.setSentDate(new Date());

      Multipart mainPart = new MimeMultipart();

      BodyPart html = new MimeBodyPart();

      if (mailInfo.isTxt()) {
        html.setText(mailInfo.getContent());
      }
      else {
        html.setHeader("Content-Type", "text/html;charset=" + mailInfo.getCode());
        html.setContent(mailInfo.getContent(), "text/html;charset=" + mailInfo.getCode());
      }
      mainPart.addBodyPart(html);

      List<String> fileList = mailInfo.getFileList();
      MimeBodyPart bodyPart;
      for (String filePath : fileList) {
        bodyPart = new MimeBodyPart();

        FileDataSource fileDataSource = new FileDataSource(filePath);
        bodyPart.setDataHandler(new DataHandler(fileDataSource));

        bodyPart.setDisposition("attachment");

        bodyPart.setFileName(MimeUtility.encodeWord(fileDataSource.getName()));
        mainPart.addBodyPart(bodyPart);
      }

      List<String> picList = mailInfo.getPicList();
      for (String picFilePath : picList) {
        MimeBodyPart bp = new MimeBodyPart();

        FileDataSource fileDataSource = new FileDataSource(picFilePath);
        bp.setDataHandler(new DataHandler(fileDataSource));

        bp.setDisposition("inline");

        bp.setFileName(MimeUtility.encodeWord(fileDataSource.getName()));
        mainPart.addBodyPart(bp);
      }
      mailMessage.setContent(mainPart);

      Transport.send(mailMessage);
    } catch (AddressException e) {
      System.out.println("---11--");
      e.printStackTrace();
      return false;
    } catch (MessagingException e) {
      System.out.println("---22--");
      e.printStackTrace();
      return false;
    } catch (UnsupportedEncodingException e) {
      System.out.println("---33--");
      e.printStackTrace();
      return false;
    }
    return isSuccess;
  }

  public boolean sendMail(String content, String toMail, String title, String sender, boolean isTxt)
  {
    MailSenderInfo mailInfo = new MailSenderInfo();

    mailInfo.setMailServerHost(JisSettings.getSettings().getEmailSmtp());
    mailInfo.setMailServerPort(JisSettings.getSettings().getEmailPort());

    mailInfo.setValidate(true);

    mailInfo.setDebug(false);
    mailInfo.setUserName(JisSettings.getSettings().getEmailBox());

    mailInfo.setPassword(JisSettings.getSettings().getEmailPassword());
    mailInfo.setFromAddress(JisSettings.getSettings().getEmailBox());
    mailInfo.setToAddress(toMail);

    mailInfo.setIsTxt(isTxt);

    mailInfo.setCode("gb2312");

    mailInfo.setSubject(title);
    mailInfo.setContent(content);

    return sendMail(mailInfo);
  }
}
