package com.airdrop.service;

import com.airdrop.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @author: zengdavy
 * @date: 2018/9/21
 * @description:
 */
@Service
public class NotificationService {
    public JavaMailSender javaMailSender;

    @Autowired
    public NotificationService(JavaMailSender javaMailSender){
        this.javaMailSender=javaMailSender;
    }
    public void sendNotification(User user, HttpServletRequest request) throws Exception{
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom("davymimocrys@gmail.com");
        mail.setSubject("请查收您的邮箱激活码");
        Integer	yzm=	(int)((Math.random()*9+1)*1000);
        ServletContext app2 = request.getServletContext();
        app2.setAttribute("yzm",yzm);
        //request.getSession().setAttribute(request.getSession().getId().toString(), yzm);


        mail.setText("您的激活码 是"+yzm);

        javaMailSender.send(mail);
    }
}

