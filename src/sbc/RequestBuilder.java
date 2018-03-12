package sbc;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;

public class RequestBuilder {
	private static String PREFIX = 	"PREFIX dbo:<http://dbpedia.org/ontology/>\n"
								+ "PREFIX dbpedia: <http://dbpedia.org/>\n"
								+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
								+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
								+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n";
	
	private static String sparqlService  = "http://dbpedia.org/sparql";
	
	
	public static Query A1_R1(String classe , int limit){
		StringBuilder sb = new StringBuilder();
		sb.append(PREFIX);
		sb.append("SELECT DISTINCT ?relation ?otherclass\n");
		sb.append("WHERE{\n");
		sb.append("<"+ classe +"> ?relation ?otherclass.\n");
		//sb.append("?otherclass rdf:type owl:Class .\n"); // si on veux tout prendre rafinage possible
		sb.append("FILTER (?relation != rdfs:label ).\n");
		sb.append("FILTER (?relation != rdfs:comment).\n");
		sb.append("}\n");
		sb.append("GROUP BY ?relation ?otherclass\n");
		sb.append("LIMIT "+limit+" OFFSET 0\n");
		System.out.println(sb.toString());
		
		return QueryFactory.create(sb.toString());
	}
	
	public static Query A1_R2(String classe , int limit){
		StringBuilder sb = new StringBuilder();
		sb.append(PREFIX);
		sb.append("SELECT DISTINCT ?relation ?otherclass\n");
		sb.append("WHERE{\n");
		sb.append("?otherclass ?relation <"+ classe +">.\n");
		//sb.append("?otherclass rdf:type owl:Class .\n"); // si on veux tout prendre rafinage possible
		sb.append("FILTER (?relation != rdf:type) .\n");
		sb.append("}\n");
		sb.append("GROUP BY ?relation ?otherclass\n");
		sb.append("LIMIT "+limit+" OFFSET 0\n");
		System.out.println(sb.toString());
		
		return QueryFactory.create(sb.toString());
	}
	
	public static Query A2_R1(String classe , int limit){
		StringBuilder sb = new StringBuilder();
		sb.append(PREFIX);
		sb.append("SELECT DISTINCT ?parentclass ?superclass\n");
		sb.append("WHERE{\n");
		sb.append("<"+ classe +"> (rdfs:subClassOf)* ?parentclass .\n");
		//sb.append("?otherclass rdf:type owl:Class .\n"); // si on veux tout prendre rafinage possible
		sb.append("?parentclass rdfs:subClassOf ?superclass .\n");
		sb.append("}\n");
		//sb.append("GROUP BY ?relation ?otherclass\n");
		sb.append("LIMIT "+limit+" OFFSET 0\n");
		System.out.println(sb.toString());
		
		return QueryFactory.create(sb.toString());
	}
	
	public static Query A2_R2(String classe , int limit){
		StringBuilder sb = new StringBuilder();
		sb.append(PREFIX);
		sb.append("SELECT DISTINCT ?subclass ?superclass\n");
		sb.append("WHERE{\n");
		sb.append("?subclass (rdfs:subClassOf)+ <"+ classe +">.\n");
		sb.append("?subclass rdfs:subClassOf ?superclass.\n");
		sb.append("?subclass rdf:type owl:Class.\n");
		sb.append("?superclass rdf:type owl:Class.\n"); 
		sb.append("}\n");
		//sb.append("GROUP BY ?relation ?otherclass\n");
		sb.append("LIMIT "+limit+" OFFSET 0\n");
		System.out.println(sb.toString());
		
		return QueryFactory.create(sb.toString());
	}
	
	public static Query A3(String classe , int limit){
		StringBuilder sb = new StringBuilder();
		sb.append(PREFIX);
		sb.append("SELECT DISTINCT ?parentclass ?superclass\n");
		sb.append("WHERE{\n");
		sb.append("<http://dbpedia.org/ontology/Planet> (rdfs:subClassOf)+ ?parentclass .\n");
		sb.append("?subclass rdfs:subClassOf ?superclass.\n");
		sb.append("?subclass rdf:type owl:Class.\n");
		sb.append("?superclass rdf:type owl:Class.\n"); 
		sb.append("}\n");
		//sb.append("GROUP BY ?relation ?otherclass\n");
		sb.append("LIMIT "+limit+" OFFSET 0\n");
		
		System.out.println(sb.toString());
		
		return QueryFactory.create(sb.toString());
	}
	
	public static void RunQuery(Query q){
		try ( QueryExecution qexec = QueryExecutionFactory.sparqlService(sparqlService, q); ) {
	    	ResultSet rs = qexec.execSelect();
	    	ResultSetFormatter.out(rs);
	    	//ResultSetFormatter.out(rs); // use result set formatter
	  }
	}

	public static void main(String[] args) {
		//RunQuery(A1_R1("http://dbpedia.org/ontology/CelestialBody", 100));
		//RunQuery(A1_R2("http://dbpedia.org/ontology/CelestialBody", 100));
		RunQuery(A2_R1("http://dbpedia.org/ontology/CelestialBody", 100));
		RunQuery(A2_R2("http://dbpedia.org/ontology/CelestialBody", 1000));
	}
	
}
