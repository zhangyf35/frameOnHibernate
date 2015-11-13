package org.webframe.tools.means;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

/**
 * 验证码工具类，提供验证码生成功能
 * @author 张永葑
 */
public class Captcha {
	
	private int width = 80; // 图片的宽度
	private int height = 30; // 图片的高度
	private int codeCount = 4; // 验证码字符个数
	private int lineCount = 120; // 验证码干扰线数
	private String code = null; // 验证码
	private BufferedImage buffImg = null; // 验证码图片Buffer
	private char[] codeSequence = {'1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'z', 'x', 'w', 'm', 'h', 'y' }; // 验证码字符序列
	private int[] codecolor = {0x000000, 0x3F3580, 0xB43A83, 0x1D7F1D, 0x4594F6, 0x8C5E5A}; // 验证码颜色集
	
	/**
	 * 构造器
	 */
	public Captcha() {
		this.createCode();
	}

	/**
	 * 构造器
	 * @param width 图片宽度
	 * @param height 图片高度
	 */
	public Captcha(int width, int height) {
		this.width = width;
		this.height = height;
		this.createCode();
	}

	/**
	 * 构造器
	 * @param width 图片宽度
	 * @param height 图片高度
	 * @param codeCount 字符个数
	 * @param lineCount 干扰线条数
	 */
	public  Captcha(int width, int height, int codeCount, int lineCount) {
		this.width = width;
		this.height = height;
		this.codeCount = codeCount;
		this.lineCount = lineCount;
		this.createCode();
	}
	
	/**
	 * 构造器
	 * @param text 字符内容
	 * @param width 图片宽度
	 * @param height 图片高度
	 * @param lineCount 干扰线条数
	 */
	public  Captcha(String text, int width, int height, int lineCount) {
		this.width = width;
		this.height = height;
		this.lineCount = lineCount;
		this.createCode(text);
	}
	
	/**
	 * 创建验证码图片
	 */
	private void createCode() {
		// 参数初始化
		int x = 0;
		int fontHeight = 0;
		int codeY = 0;
		int red = 0;
		int green = 0;
		int blue = 0;
		// 每个字符的宽度
		x = width / codeCount ;
		// 字体的高度
		fontHeight = height - 2;
		codeY = height - 4;
		// 图像buffer
		buffImg = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
		Graphics2D g = buffImg.createGraphics();
		// 生成随机数
		Random random = new Random();
		// 填充图像颜色
		g.setColor(new Color(0xE1E6F6));
		g.fillRect(0, 0, width, height);
		// 创建字体
		Font font =new Font("Calibri", Font.BOLD, fontHeight);
		g.setFont(font);
		for (int i = 0; i < lineCount; i++) {
			int xs = random.nextInt(width);
			int ys = random.nextInt(height);
			int xe = xs+random.nextInt(width/8);
			int ye = ys+random.nextInt(height/8);
			red = random.nextInt(255);
			green = random.nextInt(255);
			blue = random.nextInt(255);
			g.setColor(new Color(red, green, blue));
			g.drawLine(xs, ys, xe, ye);
		}
		// randomCode记录随机产生的验证码
		StringBuffer randomCode = new StringBuffer();
		// 随机产生codeCount个字符的验证码
		for (int i = 0; i < codeCount; i++) {
			String strRand = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
			// 产生随机的颜色值，让输出的每个字符的颜色值都将不同。
			int fontColor = codecolor[random.nextInt(codecolor.length)];
			g.setColor(new Color(fontColor));
			g.drawString(strRand, i * x, codeY);
			// 将产生的四个随机数组合在一起。
			randomCode.append(strRand);
		}
		// 得到生成的验证码
		code = randomCode.toString();
	}
	
	/**
	 * 根据固定字符串生成验证码图片
	 */
	private void createCode(String text) {
		// 参数初始化
		int x = 0;
		//int fontHeight = 0;
		int codeY = 0;
		int red = 0;
		int green = 0;
		int blue = 0;
		// 每个字符的宽度
		codeCount = text.length();
		x = width / codeCount ;
		// 字体的高度
		//fontHeight = height - 2;
		codeY = height - 4;
		// 图像buffer
		buffImg = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
		Graphics2D g = buffImg.createGraphics();
		// 生成随机数
		Random random = new Random();
		// 填充图像颜色
		g.setColor(new Color(0xE1E6F6));
		g.fillRect(0, 0, width, height);
		// 创建字体
//		Font font = new Font("", Font.BOLD, fontHeight);
//		g.setFont(font);
		for (int i = 0; i < lineCount; i++) {
			int xs = random.nextInt(width);
			int ys = random.nextInt(height);
			int xe = xs+random.nextInt(width/8);
			int ye = ys+random.nextInt(height/8);
			red = random.nextInt(255);
			green = random.nextInt(255);
			blue = random.nextInt(255);
			g.setColor(new Color(red, green, blue));
			g.drawLine(xs, ys, xe, ye);
		}
		// textCode记录字符串被分解的字符
		StringBuffer textCode = new StringBuffer();
		// 随机产生codeCount个字符的验证码
		for (int i=0; i<text.length(); i++) {
			char ch = text.charAt(i);
			String strRand = String.valueOf(ch);
			g.setColor(Color.BLACK);
			g.drawString(strRand, i * x, codeY);
			// 将产生的字符组合在一起。
			textCode.append(strRand);
		}
		// 得到生成的图形码
		code = textCode.toString();
	}
	
	/**
	 * 将验证码图片保存到本地磁盘
	 * @param path 保存路径
	 * @throws IOException
	 */
	public void write(String path) throws IOException {
		OutputStream sos = new FileOutputStream(path);
		this.write(sos);
	}
	/**
	 * 将验证码图片输出到前端
	 * @param response
	 * @throws IOException
	 */
	public void write(HttpServletResponse response) throws IOException {
		// 设置响应的类型格式为图片格式  
        response.setContentType("image/jpeg");  
        // 禁止图像缓存  
        response.setHeader("Pragma", "no-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
        this.write(response.getOutputStream());
	}
	
	/**
	 * 给定输出流输出验证码
	 * @param sos
	 * @throws IOException
	 */
	private void write(OutputStream sos) throws IOException{
		ImageIO.write(buffImg, "png", sos);
		sos.close();
	}
	
	/**
	 * 获得缓冲图片
	 * @return BufferedImage
	 */
	public BufferedImage getBuffImg() {
		return buffImg;
	}
	
	/**
	 * 获取验证码
	 * @return String
	 */
	public String getCode() {
		return code;
	}
	
}