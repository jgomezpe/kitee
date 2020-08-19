package nsgl.blob;

import java.io.IOException;
import java.util.Base64;
import java.util.Base64.Decoder;

import nsgl.character.CharacterSequence;

public class Parse implements nsgl.parse.Parse{ 
	
	public static final String TAG = "Blob";
	
	protected int length;

	/**
	 * Creates a recovering method for strings
	 */
	public Parse() {} 

	@Override
	public Object parse(CharacterSequence input) throws IOException {
	    Decoder dec = Base64.getMimeDecoder();
	    Object obj = dec.decode(input.toString());
	    length = input.length();
	    return obj;
	}

	@Override
	public String matched_as() { return TAG; }
}