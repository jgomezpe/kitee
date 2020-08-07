package nsgl.blob;

import java.io.IOException;
import java.util.Vector;

import nsgl.character.CharacterSequence;
import nsgl.parse.Regex;

public class Parse extends Regex{
	
	public static final String TAG = "Blob";

	/**
	 * Creates a recovering method for strings
	 */
	public Parse() { super("#[0-9A-Za-z_~]+", TAG); }

	public int get( char c ) {
	    if( '0' <= c && c <='9' ) return c - '0';
	    if( 'A' <= c && c <='Z' ) return 10 + c - 'A';
	    if( 'a' <= c && c <='z' ) return 36 + c - 'a';
	    if( c=='_') return 62;
	    return 63;
	}
	
	public byte[] get(String code, int begin) {
	    int a,b,c,d;
	    int length = code.length() - begin;
	    if( length <= 1 ) return new byte[0];
	    switch(length) {
	    	case 2:
	    	    a = get(code.charAt(begin));
	    	    b = get(code.charAt(begin+1));
	    	    return new byte[]{(byte)((a<<2)+(b>>4))};
	    	case 3:
	    	    a = get(code.charAt(begin));
	    	    b = get(code.charAt(begin+1));
	    	    c = get(code.charAt(begin+2));
	    	    return new byte[]{(byte)((a<<2)+(b>>4)),(byte)(((b&15)<<4)+(c>>2))};
	    	default:
	    	    a = get(code.charAt(begin));
	    	    b = get(code.charAt(begin+1));
	    	    c = get(code.charAt(begin+2));
	    	    d = get(code.charAt(begin+3));
	    	    return new byte[]{(byte)((a<<2)+(b>>4)),(byte)(((b&15)<<4)+(c>>2)),(byte)(((c&3)<<6)+d)};
	    }
	}
	
	@Override
	protected Object instance(CharacterSequence input, String matched) throws IOException{
		String code = matched.substring(1);
		Vector<byte[]> buffer = new Vector<byte[]>();
		int size = 0;
		for( int i=0; i<code.length(); i+=4 ) {
		    buffer.add(get( code, i ));
		    size += buffer.get(buffer.size()-1).length;
		}
		byte[] blob = new byte[size];
		size = 0;
		for( int i=0; i<buffer.size(); i++ ) {
		    System.arraycopy(buffer.get(i), 0, blob, size, buffer.get(i).length);
		    size += buffer.get(i).length;
		}
		
		return blob;
	}
	
	public static void main( String[] args ) {
	    Parse p = new Parse();
	    CharacterSequence txt = new CharacterSequence("#HNDqPI1qPNXqRo1bSo1aPI1iRsDlSo0A80aWTc5jRtCWOI1sPN8");
	    try {
		byte[] c = (byte[])p.parse(txt);
		System.out.println("Hello Moto..." + new String(c));
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
}