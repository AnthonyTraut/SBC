package sbc;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;

public class Request {
	private static String PREFIX = "PREFIX dbo:<http://dbpedia.org/ontology/>\n"
			+ "PREFIX dbpedia: <http://dbpedia.org/>\n"
			+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
			+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
			+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n";
	
	private static String sparqlService  = "http://dbpedia.org/sparql";
	
	
	public ResultSet A1_R1(String classe , int limit){
		
		Query query = QueryFactory.create(
				PREFIX
				+ "?relation ?otherclass"
				+"WHERE{"
				     + classe +" ?relation ?otherclass ."
				     +"?otherclass rdf:type owl:Class ."
				     +"FILTER (?relation != rdfs:label AND ?relation != rdfs:comment) ."
				+"}"
				+"GROUP BY ?relation"
				+"LIMIT "+limit+" OFFSET 0");
		
		 try ( QueryExecution qexec = QueryExecutionFactory.sparqlService(sparqlService, query); ) {
		    	ResultSet rs = qexec.execSelect();
		    	return rs;
		    	//ResultSetFormatter.out(rs); // use result set formatter
		  }
	}
	
	public ResultSet A1_R2(String classe , int limit){
		
		Query query = QueryFactory.create(
				PREFIX
				+ "?relation ?otherclass"
				+"WHERE{"
				     +"?otherclass ?relation"+ classe +"  ."
				     +"?otherclass rdf:type owl:Class ."
				     +"FILTER (?relation != rdf:type) ."
				+"}"
				+"GROUP BY ?relation"
				+"LIMIT "+limit+" OFFSET 0");
		
		 try ( QueryExecution qexec = QueryExecutionFactory.sparqlService(sparqlService, query); ) {
		    	ResultSet rs = qexec.execSelect();
		    	return rs;
		    	//ResultSetFormatter.out(rs); // use result set formatter
		  }
	}

	
	
}
