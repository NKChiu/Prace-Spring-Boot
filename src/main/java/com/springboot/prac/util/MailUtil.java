package com.springboot.prac.util;

import com.springboot.prac.model.OutputDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

@Component
@Slf4j
public class MailUtil {

    private static String mailSmtpHost;
    private static String mailSmtpPort;
    private static String mailAuth;
    private static String mailStarttlsEnable;
    private static String mailEnableSSL;
    private static String senderName;
    private static String senderAddress;
    private static String senderPwd;
    private static String mailDebug;

    @Autowired
    public MailUtil(@Value("${mail.smtp.host}") String mailSmtpHost,
                    @Value("${mail.smtp.port}") String mailSmtpPort,
                    @Value("${mail.smtp.auth}") String mailAuth,
                    @Value("${mail.smtp.starttls.enable}") String mailStarttlsEnable,
                    @Value("${mail.smtp.EnableSSL.enable}") String mailEnableSSL,
                    @Value("${mail.send.name}") String senderName,
                    @Value("${mail.send.address}") String senderAddress,
                    @Value("${mail.send.password}") String senderPwd,
                    @Value("${mail.debug}") String mailDebug){
        MailUtil.mailSmtpHost = mailSmtpHost;
        MailUtil.mailSmtpPort = mailSmtpPort;
        MailUtil.mailAuth = mailAuth;
        MailUtil.mailStarttlsEnable = mailStarttlsEnable;
        MailUtil.mailEnableSSL = mailEnableSSL;
        MailUtil.senderName = senderName;
        MailUtil.senderAddress = senderAddress;
        MailUtil.senderPwd = senderPwd;
        MailUtil.mailDebug = mailDebug;
    }

    /**
     * @deescription 設定共用參數
     */
    static{
        System.setProperty("mail.mime.splitlongparameters", "false"); // 不用限制附件名稱
    }

    /**
     * @deescription 寄信(沒有 cc 收件者)
     * @param sendAddress 寄件者
     * @param receiverAddress 收件者
     * @param subject 主旨
     * @param content 內容
     * @param files 接受 List<String> (附件路徑), List<File> (附件)
     * @return
     */
    public static OutputDto sendMail(String sendAddress, List<String> receiverAddress, String subject, String content, Object files){
        return sendMail(sendAddress, receiverAddress, null, subject, content, files);
    }

    /**
     * @deescription 寄信(沒有 附件)
     * @param sendAddress 寄件者
     * @param receiverAddress 收件者
     * @param ccAddress cc 收件者
     * @param subject 主旨
     * @param content 內容
     * @return
     */
    public static OutputDto sendMail(String sendAddress, List<String> receiverAddress, List<String> ccAddress, String subject, String content){
        return sendMail(sendAddress, receiverAddress, ccAddress, subject, content, null);
    }

    /**
     * @deescription 寄信(沒有 cc 收件者 及 附件)
     * @param sendAddress 寄件者
     * @param receiverAddress 收件者
     * @param subject 主旨
     * @param content 內容
     * @return
     */
    public static OutputDto sendMail(String sendAddress, List<String> receiverAddress, String subject, String content){
        return sendMail(sendAddress, receiverAddress, null, subject, content, null);
    }


    /**
     * @deescription 寄信核心功能
     * @param sendAddress 寄件者
     * @param receiverAddress 收件者
     * @param ccAddress cc 收件者
     * @param subject 主旨
     * @param content 內容
     * @param files 接受 List<String> (附件路徑), List<File> (附件)
     * @return
     */
    public static OutputDto sendMail(String sendAddress, List<String> receiverAddress, List<String> ccAddress, String subject, String content, Object files){

        OutputDto output = new OutputDto();
        String errMsg = "";

        try{
            log.info("sendMail start");

            // 設定 mimeMessage 參數設定
            MimeMessage msg = setMimeMessage();

            // 設定寄件者
            msg.setFrom(new InternetAddress(sendAddress, senderName));

            // 設定 收件者
            setReceiver(msg, Message.RecipientType.TO, receiverAddress);
            log.info("receiver: " + receiverAddress.toString());
            setReceiver(msg, Message.RecipientType.CC, ccAddress);
            log.info("cc receivers: " + ccAddress.toString());

            // 設定 主旨
            msg.setSubject(subject);

            // 設定 內文 及 附件
            Multipart mp = new MimeMultipart();
            mp = setContent(mp, content);
            mp = setAttachment(mp, files);
            msg.setContent(mp);

            // 寄信
            Transport.send(msg);
            log.info("sendMail end");

            output.setSuccess(true);
            return output;

        }catch (Exception e){
            errMsg = "寄信失敗";
            log.error(errMsg + e.getMessage());
            output.setSuccess(false);
            output.setReturnMessage(errMsg);
            return output;
        }

    }

    /*********************************************** private method **********************************************************************/

    /**
     * @deescription 設定 mimeMessage 參數設定
     * @return MimeMessage
     */
    private static MimeMessage setMimeMessage(){
        Properties props = System.getProperties();
        props.put("mail.smtp.host", mailSmtpHost);
        props.put("mail.smtp.port", mailSmtpPort);
        props.put("mail.smtp.auth", mailAuth);
        props.put("mail.smtp.starttls.enable", mailStarttlsEnable);
        props.put("mail.smtp.EnableSSL.enable", mailEnableSSL);
        props.put("mail.debug", mailDebug);

        Session session = Session.getInstance(props, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        senderAddress, senderPwd);// Specify the Username and the PassWord
            }
        });

        MimeMessage msg = new MimeMessage(session);
        return msg;
    }

    /**
     * @deescription 設定寄件者
     * @return void
     */
    private static void setReceiver(MimeMessage msg, Message.RecipientType recipientType, List<String> receiverAddress) throws MessagingException {
        if(receiverAddress != null && receiverAddress.size() > 0){
            InternetAddress[] addresses = new InternetAddress[receiverAddress.size()];

            for(int i=0; i < receiverAddress.size(); i++){
                addresses[i] = new InternetAddress(receiverAddress.get(i));
            }
            msg.setRecipients(recipientType, addresses);
        }
    }

    /**
     * @deescription 設定 收件者
     * @return Multipart
     */
    private static Multipart setContent(Multipart mp, String content) throws MessagingException {
        MimeBodyPart mbp = new MimeBodyPart();
        mbp.setContent(content, "text/html; charset=MS950");
        mp.addBodyPart(mbp);
        return mp;
    }

    /**
     * @deescription 設定 內文 及 附件
     * @return Multipart
     */
    private static Multipart setAttachment(Multipart mp, Object files) throws MessagingException, IOException {

        if(files != null && files instanceof List && ((List<?>) files).size() > 0){

            if(((List<?>) files).get(0) instanceof File){
                log.info("attachments: " + files.toString());
                for(File f : (List<File>) files){
                    MimeBodyPart mbp = new MimeBodyPart();
                    mbp.attachFile(f);
                    mbp.setFileName(MimeUtility.encodeText(f.getName(), "MS950", "B"));
                    mp.addBodyPart(mbp);
                }
            }

            if(((List<?>) files).get(0) instanceof String){
                log.info("attachments: " + files.toString());
                for(String path : (List<String>) files){
                    MimeBodyPart mbp = new MimeBodyPart();
                    FileDataSource fds = new FileDataSource(path);
                    mbp.setDataHandler(new DataHandler(fds));
                    mbp.setFileName(MimeUtility.encodeText(fds.getName(), "MS950", "B"));
                    mp.addBodyPart(mbp);
                }
            }

        }

        return mp;
    }
}
