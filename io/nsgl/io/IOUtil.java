package nsgl.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class IOUtil {
	/** 
	 * Default charset used by NSGL
	 */
	public static final String CHARSET = "UTF-8";

	public static ByteArrayOutputStream toByteArrayOS( InputStream is ) throws IOException{
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		int MAX = 1000000;
		int k=0;
		byte[] buffer = new byte[MAX];  
		do{
			k=is.read(buffer);
			if( k>0 ) os.write(buffer, 0, k);
		}while(k>0);
		return os;
	}

	public static byte[] toByteArray( InputStream is ) throws IOException{ return toByteArrayOS(is).toByteArray(); }
		
	public static String toString( InputStream is, String charsetName ) throws IOException{ return toByteArrayOS(is).toString(charsetName); }

	public static String toString( InputStream is ) throws IOException{ return toString(is,CHARSET); }
	
	public static InputStream toInputStream( String str, String charsetName ) { return new ByteArrayInputStream(str.getBytes(Charset.forName(charsetName))); }

	public static InputStream toInputStream( String str ) { return toInputStream(str,CHARSET); }

	public static InputStream toInputStream( byte[] blob ) { return new ByteArrayInputStream(blob); }
}