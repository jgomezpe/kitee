package nsgl.blob;

import java.util.Base64;
import java.util.Base64.Encoder;

public class Stringifier implements nsgl.stringify.Stringifier{
    public static String delimiter = "";
   
    public String stringify(byte[] blob) {
	Encoder enc = Base64.getMimeEncoder();
	String txt = enc.encodeToString(blob);
	StringBuilder sb = new StringBuilder(); 
	sb.append(delimiter);
	char c;
	for( int i=0; i<txt.length(); i++ ) {
	    c = txt.charAt(i);
	    if( c != '\r' && c != '\n' ) sb.append(c);
	}
	sb.append(delimiter);
	return sb.toString();
    }

    @Override
    public String stringify(Object obj) { return stringify((byte[])obj); }
}