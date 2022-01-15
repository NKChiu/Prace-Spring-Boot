package com.springboot.prac.myunit;

import com.springboot.prac.PracApplication;
import com.springboot.prac.config.TestDataSourceConfig;
import com.springboot.prac.util.MailUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {PracApplication.class, TestDataSourceConfig.class})
public class MailUnitTest {


    @Value("${mail.send.address}")
    private String sendAddress;

    @Test
    public void testSendMail(){

        List<String> toMailList = new ArrayList<>();
        toMailList.add("nkchiu418@gmail.com");

        List<String> ccMailList = new ArrayList<>();
        ccMailList.add("nkchiu418@hotmail.com.tw");

        String subject = "我是主旨";

        String content = "我是內容";

        List<String> attList = new ArrayList<>();
        attList.add("C:\\Users\\user\\Desktop\\testfile\\testFile.txt");
        attList.add("C:\\Users\\user\\Desktop\\testfile\\testFile1.txt");

        //List<File> attList = new ArrayList<>();
        //File file1 = new File("C:\\Users\\user\\Desktop\\testfile\\testFile.txt");
        //attList.add(file1);
        //File file2 = new File("C:\\Users\\user\\Desktop\\testfile\\testFile1.txt");
        //attList.add(file2);

        MailUtil.sendMail(sendAddress, toMailList, ccMailList, subject, content, attList);
    }
}
