package com.springboot.prac.controller;

import com.springboot.prac.model.OutputDto;
import com.springboot.prac.util.MailUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api")
@Api(tags = "testEmail")
public class TestEmailController {

    @Value("${mail.send.address}")
    private String sendAddress;

    @GetMapping("/getxxx")
    public String getxxxx(){
        return "getxxx";
    }

    @PostMapping("/testEmail/sendMailTest")
    public OutputDto sendMailTest(@ApiParam(value = "附件") @RequestParam(value = "file", required = false)MultipartFile file,
                                  @ModelAttribute EmailInputDto emailInputDto){

        OutputDto output = new OutputDto();

        String defaultReceiverAddress = "nkchiu418@gmail.com";
        String dirPath = "C:\\Users\\user\\Desktop\\testfile";

        List<String> toMailList = new ArrayList<>();
        String receiverAddress = emailInputDto.getReceiverAddress();
        if (StringUtils.isEmpty(receiverAddress)) {
            toMailList.add(defaultReceiverAddress);
        }else{
            String[] splitReceiverAddress = receiverAddress.split(";");
            if(splitReceiverAddress.length == 0){
                toMailList.add(defaultReceiverAddress);
            }else {
                for(String address : splitReceiverAddress){
                    toMailList.add(address);
                }
            }
        }

        List<String> ccMailList = new ArrayList<>();
        String ccReceiverAddress = emailInputDto.getCcReceiverAddress();
        if(!StringUtils.isEmpty(ccReceiverAddress)){
            String[] splitCcReceiverAddress = ccReceiverAddress.split(";");
            if(splitCcReceiverAddress.length != 0){
                for (String address : splitCcReceiverAddress){
                    ccMailList.add(address);
                }
            }
        }


        String subject = StringUtils.isEmpty(emailInputDto.getSubject()) ? "我是主旨" : emailInputDto.getSubject().trim();

        String content = StringUtils.isEmpty(emailInputDto.getContent()) ? "我是內容" : emailInputDto.getContent().trim();

        List<String> attList = new ArrayList<>();
        if(emailInputDto.isNeedAttachFile()){

            if(Objects.nonNull(file) && !file.isEmpty()){
                Path p = Paths.get(dirPath, file.getOriginalFilename());
                File dir = new File(dirPath);
                if(!dir.exists()){
                    dir.mkdir();
                }

                try(OutputStream os = Files.newOutputStream(p)){
                    os.write(file.getBytes());
                } catch (IOException e) {
                    log.error(e.getMessage());
                }

                attList.add(p.toString());
            }
        }


        output = MailUtil.sendMail(sendAddress, toMailList, ccMailList, subject, content, attList);

        return output;
    }
}

@Data
class EmailInputDto{

    @ApiModelProperty(value = "收件者", example = "nkchiu418@gmail.com")
    private String receiverAddress;
    @ApiModelProperty(value = "cc收件者")
    private String ccReceiverAddress;
    @ApiModelProperty(value = "主旨", example = "我是主旨")
    private String subject;
    @ApiModelProperty(value = "內容", example = "我是內容")
    private String content;
    @ApiModelProperty(value = "是否需要附件")
    private boolean isNeedAttachFile;

}
