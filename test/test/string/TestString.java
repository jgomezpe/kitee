package test.string;

import java.io.IOException;

import nsgl.character.CharacterSequence;
import nsgl.generic.hashmap.HashMap;
import nsgl.string.I18N;
import nsgl.string.Parse;
import nsgl.string.Template;
import nsgl.stringify.Stringifyable;

public class TestString {
	public static void template() {
	    String demo = "·Probe of template· changing\n ·Elixir\\· method· without concepts..";
	    String[] tags = Template.tags(demo, '·');
	    System.out.println("===============Tags in text==============");
	    for( String tag:tags) System.out.println(tag);
	    HashMap<String, String> dict = new HashMap<String, String>();
	    System.out.println( "================Without dictionary================" );
	    String changed = Template.get(demo, dict , '·');
	    System.out.println( changed );
	    System.out.println( "================With dictionary================" );
	    dict.set("Probe of template", "New probe of template");
	    dict.set("Elixir· method", "Escaping character management");
	    changed = Template.get(demo, dict , '·');
	    System.out.println( changed );
	    System.out.println( "================Dictionario español================" );
	    dict.set("Probe of template", "Prueba de patrón");
	    dict.set("Elixir· method", "Metodo de Elixir");
	    changed = Template.get(demo, dict , '·');
	    System.out.println( changed );
	}
	
	public static void i18n() {
	    System.out.println("==========Setting language to espanish==============");
	    HashMap<String, String> dict = new HashMap<String, String>();
	    dict.set("File not found", "Archivo no encontrado");
	    dict.set("Unexpected", "Inesperado");
	    dict.set("at", "en");
	    dict.set("at", "en");
	    dict.set("row", "fila");
	    dict.set("columna", "columna");
	    dict.set("Invalid String", "Cadena Inválida");
	    dict.set("Invalid index", "Índice inválido");
	    I18N.set(dict);
	}
	
	public static void i18n_exception() {
	    System.out.println("==========Throws an exception==============");
	    String x = "Hello";
	    try {
		for( int i=0; i<=x.length(); i++ )
		    System.out.print(x.charAt(i));
	    }catch(Exception e) {
		System.err.println(I18N.process("·Invalid index·"));
	    }
	}
	
	public static void parse( String s) {
	    System.out.println("==========Parse an string==============");
	    System.out.println(s);
	    Parse p = new Parse();
	    CharacterSequence txt = new CharacterSequence(s);
	    try {
		System.out.println(Stringifyable.cast(s).stringify());
		Object c = p.parse(txt);
		System.out.println(c);
		System.out.println(Stringifyable.cast(c).stringify());
	    } catch (IOException e) {
		System.err.println(I18N.process(e.getMessage())+":"+s);
	    }
	}
		
	
	public static void main( String[] args ) {
	    template(); // Uncomment to test template
	    i18n(); // Uncomment to test demo Spanish dictionary for i18n 
	    i18n_exception(); // Uncomment to test i18n for exceptions
	    parse("\"Domino \\n \\\"Player\""); // Uncomment to test parser of a valid string
	    parse("\"Domino \n Player\""); // Uncomment to test parser of an invalid string
	}
}