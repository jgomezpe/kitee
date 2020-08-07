package nsgl.blob;

public class Stringifier implements nsgl.stringify.Stringifier{
    protected char get(int b ) {
	if( b < 10 ) return (char)('0' + b);
	b -= 10;
	if( b < 26 ) return (char)('A' + b);
	b -= 26;
	if( b < 26 ) return (char)('a' + b);
	b -= 26;
	if( b == 0 ) return '_';
	return '~';
    }
    
    protected String get(byte[] blob, int begin ) {
	int a,b,c;
	String txt = "";
	int x = blob.length-begin;
	if( x<=0 ) return "";
	switch(x) {
		case 1:
		    a = blob[begin] & 0xFF;
		    txt += get(a>>2);
		    txt += get((a&3)<<4);
		    return txt; 
		case 2:
		    a = blob[begin] & 0xFF;
		    b = blob[begin+1] & 0xFF;
		    txt += get(a>>2);
		    txt += get(((a&3)<<4)+(b>>4));
		    txt += get((b&15)<<2);
		    return txt; 
		default:
		    a = blob[begin] & 0xFF;
		    b = blob[begin+1] & 0xFF;
		    c = blob[begin+2] & 0xFF;
		    txt += get(a>>2);
		    txt += get(((a&3)<<4)+(b>>4));
		    txt += get(((b&15)<<2)+(c>>6));
		    txt += get(c&63);
		    return txt;
	}
    }
    
    public String stringify(byte[] blob) {
	StringBuilder sb = new StringBuilder();
	sb.append('#');
	for( int i=0; i<blob.length; i+=3 ) sb.append(get(blob,i));
	return sb.toString();
    }

    @Override
    public String stringify(Object obj) { return stringify((byte[])obj); }
    
    public static void main( String[] args ) {
	String txt = "Este texto es de locos \n \t vamos a ver";
	byte[] b = txt.getBytes();
	Stringifier s = new Stringifier();
	String enc = s.stringify(b);
	System.out.println(enc);
	System.out.println(txt);
	for( int i=0; i<256; i++ ) {
	    byte bc = (byte)i;
	    System.out.println(bc & 0xFF);
	}
    }
}