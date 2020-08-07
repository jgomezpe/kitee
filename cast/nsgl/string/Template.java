package nsgl.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nsgl.generic.array.Vector;
import nsgl.generic.collection.Indexed;
import nsgl.parse.Regex;

public class Template {
	public static Pattern get(char c) {
		String cs = (Regex.escapechar(c)?"\\\\":"")+c;
		return Pattern.compile(cs+"([^\\\\"+cs+"]|\\\\[\\\\"+cs+"])*"+cs);
	}
	
	/**
	 * Obtains a String from a template by replacing the set of tags with their associated values. A tag is limited both sides by a character <i>c</i>. 
	 * For example, if <i>str='lorem ·X· dolor ·haha· amet'</i>, <i>c='·'</i> and <i>dictionary={'X':'ipsum', 'haha':'sit' }
	 * then this method will return the string <i>lorem ipsum dolor sit amet'</i>
	 * @param str Template used for generating the String
	 * @param dictionary Set of pairs <i>(TAG,value)</i> used for replacing each <i>TAG</> by its corresponding <i>value</i>
	 * @param c Enclosing tag character
	 * @return A String from a template by replacing the set of tags with their associated values. 
	 */
	public static String get(String str, Indexed<String,String> dictionary, char c){
		Pattern pattern = get(c);
		Matcher matcher = pattern.matcher(str);
		int start = 0;
		if( matcher.find(start) ){
			StringBuilder sb = new StringBuilder();
		    	do{
		    	    	int nstart = matcher.start();
			    	String matched = matcher.group();
			    	String tag = matched.substring(1, matched.length()-1);
			    	tag = tag.replace("\\"+c, ""+c);
			    	sb.append(str.substring(start,nstart));
			    	String txt = dictionary.get(tag);
			    	if( txt==null ) txt = tag;
			    	sb.append(txt);
			    	start = nstart+matched.length();
			}while( matcher.find() );
			return sb.toString();
		}else return str; 
	}
	
	/**
	 * Obtains the set of tags defined in a String template. A tag is limited both sides by a character <i>c</i>. For example, if <i>str='lorem·X·ipsum·haha· quia'</i>
	 * and <i>c='·'</i> then this method will return the array of tags <i>['X', 'haha']</i>
	 * @param str Template used for generating the String
	 * @param c Enclosing tag character
	 * @return A dictionary, set of pairs <i>(TAG,value)</i>, containing each <i>TAG</> in the template
	 */
	public static String[] tags(String str, char c){
		Vector<String> array = new Vector<String>();
		Pattern pattern = get(c);
		Matcher matcher = pattern.matcher(str);
		int start = 0;
		if( matcher.find(start) ){
		    	do{
			    	String matched = matcher.group();
			    	String tag = matched.substring(1, matched.length()-1);
			    	tag = tag.replace("\\"+c, ""+c);
			    	array.add(tag);
			}while( matcher.find() );
		}
		String[] a = new String[array.size()];
		for( int i=0; i<a.length; i++) a[i] = array.get(i);
		return a;
	}	
}