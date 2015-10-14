package org.webframe.tools.systemUtil;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 邮件工具类
 * @author 张永葑
 */
public class EmailUtil {
	
	public static final String[] SOHU_EMAIL = {"smtp.sohu.com","25"}; // 搜狐邮箱配置
	public static final String[] SINA_EMAIL = {"smtp.sina.com","587"}; // 新浪邮箱配置
	public static final String[] QQ_EMAIL = {"smtp.qq.com","25"}; // qq邮箱配置
	
	/**
	 * 发送邮件
	 * @param emailType 邮箱类型，如新浪，搜狐，qq ，该类有静态常量可供选择，如需自定义发送到类型的邮箱，则该变量格式为["邮箱host","邮箱port"]
	 * @param fromAccount 发件人账户
	 * @param fromPassword 发件人密码
	 * @param toAccount 收件人账户
	 * @param subject 邮件主题
	 * @param content 邮件内容
	 */
	public static void sendEmailMessage(String[] emailType,String fromAccount,String fromPassword,String toAccount, String subject,String content){
		Properties properties = new Properties();
		properties.setProperty("mail.transport.protocol", "smtp"); // 发送邮件协议
		properties.setProperty("mail.smtp.auth", "true"); // 需要验证
		properties.setProperty("mail.debug", "true"); // 设置debug模式 后台输出邮件发送的过程
		Session session = Session.getInstance(properties);
		session.setDebug(true); // debug模式
		Message messgae = new MimeMessage(session); // 邮件信息
		try {
			messgae.setFrom(new InternetAddress(fromAccount)); // 设置发送人
			messgae.setText(content); // 设置邮件内容
			messgae.setSubject(subject); // 设置邮件主题
			// 发送邮件
			Transport tran = session.getTransport(); 
			tran.connect(emailType[0], Integer.parseInt(emailType[1]), fromAccount, fromPassword);//连接到QQ邮箱服务器
			tran.sendMessage(messgae, new Address[] { new InternetAddress(toAccount) });// 设置邮件接收人
			tran.close();
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}