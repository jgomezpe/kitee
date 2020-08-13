package test.blob;

import java.io.IOException;

import nsgl.blob.Parse;
import nsgl.blob.Stringifier;
import nsgl.character.CharacterSequence;

public class TestBlob {
	public static String encode() {
		String txt = "Este texto es de locos \n \t vamos a ver lo que no debemos dejar es que deje cambios de línea cuando este condificando este texto";
		byte[] b = txt.getBytes();
		Stringifier s = new Stringifier();
        	String enc = s.stringify(b);
        	System.out.println(enc);
        	System.out.println(txt);
        	return enc;
        }
    
	public static String decode(){
	    Parse p = new Parse();
	    CharacterSequence txt = new CharacterSequence("RXN0ZSB0ZXh0byBlcyBkZSBsb2NvcyAKIAkgdmFtb3MgYSB2ZXI=");
	    try {
		byte[] c = (byte[])p.parse(txt);
		String dec = new String(c);
		System.out.println(dec);
		return dec;
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	    return null;
	} 
	
	public static void main(String[] args) {
	    encode(); // Uncomment for testing encode in Base64
	    decode(); // Uncomment for testing decode in Base64
	}
}
