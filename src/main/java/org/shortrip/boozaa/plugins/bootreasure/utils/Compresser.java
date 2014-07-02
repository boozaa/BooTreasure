package org.shortrip.boozaa.plugins.bootreasure.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.apache.commons.io.IOUtils;


public class Compresser {

	public static byte[] compress(byte[] content) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
			gzipOutputStream.write(content);
			gzipOutputStream.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		// TODO Debug à retirer
		float ratio 	= (1.0f * content.length / byteArrayOutputStream.size());
		int original 	= content.length ;
		int compressed	= byteArrayOutputStream.size();
		
		return byteArrayOutputStream.toByteArray();
	}

	public static byte[] decompress(byte[] contentBytes) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			IOUtils.copy(new GZIPInputStream(new ByteArrayInputStream(contentBytes)), out);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return out.toByteArray();
	}

	
}
