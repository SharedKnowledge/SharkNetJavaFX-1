package net.sharksystem.sharknet.api;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * Created by timol on 01.06.2016.
 */
public class ImplContent implements Content {
	String fileExtension, message;
	InputStream file;

	public ImplContent (String message){
		this.message = message;
		fileExtension = null;
		file = null;
	}

	public ImplContent(InputStream file, String fileExtension){
		this.file = file;
		this.fileExtension = fileExtension;
		this.message = null;
	}

	public ImplContent(InputStream file, String fileExtension, String message){
		this.file = file;
		this.fileExtension = fileExtension;
		this.message = message;
	}

	@Override
	public String getFileExtension() {
		return fileExtension;
	}

	@Override
	public InputStream getFile() {

		return swapFile();
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public void setMessage(String message) {
		this.message = message;

	}

	private InputStream swapFile()  {
		int read = 0;
		byte[] bytes = new byte[8192];

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			while ((read = file.read(bytes)) != -1)
                bos.write(bytes,0,read);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] ba = bos.toByteArray();
		return new ByteArrayInputStream(ba);

	}
}
