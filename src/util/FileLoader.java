package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;

public class FileLoader {

	public static File getResourceAsFile(String resourcePath) {
	    try {
	        InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(resourcePath);
	        if (in == null) {
	            return null;
	        }

	        File tempFile = File.createTempFile(String.valueOf(in.hashCode()), ".tmp");
	        tempFile.deleteOnExit();

	        try (FileOutputStream out = new FileOutputStream(tempFile)) {
	            //copy stream
	            byte[] buffer = new byte[1024];
	            int bytesRead;
	            while ((bytesRead = in.read(buffer)) != -1) {
	                out.write(buffer, 0, bytesRead);
	            }
	            out.close();
	        }
	        in.close();
	        return tempFile;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	public static String getResourceAsString(String resourcePath) {
		   try {
		        InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(resourcePath);
		        if (in == null) {
		            return null;
		        }
		        StringWriter writer = new StringWriter();
		        IOUtils.copy(in, writer, Charset.defaultCharset());
		        String str = writer.toString();
		        in.close();
		        writer.close();
		        return str;
		   } catch (Exception e) {
			   e.printStackTrace();
			   return null;
		   }
		   
	}
	
}
