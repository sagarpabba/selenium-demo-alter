package com.ocr;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

public class OCRUtil {
	
	private static final String IMAGE_FORMAT = "jpg";

	public static String recognizeValidation(InputStream in) throws Exception {
		File tmpFile = File.createTempFile("img", "." + IMAGE_FORMAT);
		OutputStream out = new FileOutputStream(tmpFile);
		IOUtils.copy(in, out);
		IOUtils.closeQuietly(out);
		return format(OCR.recognizeText(tmpFile, IMAGE_FORMAT));
	}

	private static String format(String str) {
		if (StringUtils.isBlank(str)) {
			return null;
		}
		StringBuffer sb = new StringBuffer(str.length());
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (Character.isDigit(c) || Character.isLetter(c)) {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	
}
