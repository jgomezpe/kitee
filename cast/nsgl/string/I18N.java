package nsgl.string;

import java.io.IOException;

import nsgl.generic.collection.Indexed;
import nsgl.generic.hashmap.HashMap;
import nsgl.json.JSON;

public class I18N {
    protected static Indexed<String, String> dictionary = new HashMap<String, String>();
    protected static final char c = '·';
    
    public static void clear() {
	dictionary = new HashMap<String, String>();
    }
    
    public static void set( String dictionary ) throws IOException{
	JSON json = new JSON(dictionary);
	for( String key:json.keys() ) {
	    String value = json.getString(key);
	    if( value != null ) I18N.dictionary.set(key, value);
	}
    }

    public static void set( Indexed<String, String> dictionary ) { I18N.dictionary = dictionary; }
    
    public static String process( String code ) { return Template.get(code, I18N.dictionary, c); }
}