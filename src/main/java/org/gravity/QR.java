package org.gravity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.encoder.QRCode;

public class QR {

	private String message = null;
	private String path = null;
	private String filename = null;
	
	/**
	 * Constructor with 3 simple parameter to produce a QR
	 * 
	 * @param message the message to encode into QR code
	 * @param path the path where the QR code image to be placed
	 * @param filename the name of the QR code file to be generated
	 * <p>
	 * Example:
	 * <p>
	 * QR qr = new QR("foo bar","/path/to/image","foobar");
	 */
	public QR(String message, String path, String filename){
		this.message = message;
		this.path = path;
		this.filename = filename;
	}

	/**
	 * pojo function to get message
	 *
	 * @return	 	message behind QRcode
	 */
	public String getMessage(){
		return message;
	}

	/**
	 * pojo function to set message
	 *
	 * @param message String to set as message 
	 */
	public void setMessage(String message){
		this.message = message;
	}

	/**
	 * pojo function to get path in String
	 *
	 * @return		path of QR file
	 */
	public String getPath(){
		return path;
	}

	/**
	 * pojo function to set path
	 *
	 * @param path String to set as path
	 */
	public void setPath(String path){
		this.path = path;
	}

	/**
	 * pojo function to get filename in String
	 *
	 * @return		filename of QR
	 */
	public String getFilename(){
		return filename;
	}

	/**
	 * pojo function to set filename
	 *
	 * @param filename String to set as filename
	 */
	public void setFilename(String filename){
		this.filename = filename;
	}

	/**
	 * Encodes message in this.message to qr code bufferedImage
	 *
	 * @return		bufferedImage of QR, which is meant to be written to a file of to be manipulated into other image usages
	 */
	public BufferedImage encodeQR()
	{
		QRCodeWriter qrWriter = new QRCodeWriter();
		BitMatrix bitmatrix = new BitMatrix(128, 128);
		BufferedImage bufferedImage = new BufferedImage(128, 128, BufferedImage.TYPE_INT_RGB);
		
		try{
			bitmatrix = qrWriter.encode(message, BarcodeFormat.QR_CODE, 128, 128);
			bufferedImage.createGraphics();
			
			Graphics2D graphics2d = (Graphics2D) bufferedImage.getGraphics();
			graphics2d.setColor(Color.WHITE);
			graphics2d.fillRect(0, 0, 128, 128);
			graphics2d.setColor(Color.BLACK);
			for (int i = 0; i < 128; i++) {
	            for (int j = 0; j < 128; j++) {
	                if (bitmatrix.get(i, j)) {
	                    graphics2d.fillRect(i, j, 1, 1);
	                }
	            }
	        }
			
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return bufferedImage;

	}

	/**
	 * creates this.filename as png image in the path given in this.path
	 *
	 * @param bufferedImage is the image passed down from encodeQR function
	 */
	public void generateQR(BufferedImage bufferedImage)
	{
		
		try{
			
			String filePath = path+filename+".png";
			System.out.println("output path: "+new File(filePath).getAbsolutePath());
			ImageIO.write(bufferedImage, "png", new File(filePath));
			
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public static void main(String[] args){
		QR qrCode = new QR(args[0],args[1],args[2]);	
		qrCode.generateQR(qrCode.encodeQR());
	}


	
}
