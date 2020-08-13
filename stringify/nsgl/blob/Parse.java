package nsgl.blob;

import java.io.IOException;
import java.util.Base64;
import java.util.Base64.Decoder;

import nsgl.character.CharacterSequence;

public class Parse implements nsgl.parse.Parse{ 
	
	public static final String TAG = "Blob";

	/**
	 * Creates a recovering method for strings
	 */
	public Parse() {} 

	@Override
	public Object parse(CharacterSequence input) throws IOException {
	    Decoder dec = Base64.getMimeDecoder();
	    return dec.decode(input.toString());
	}
}