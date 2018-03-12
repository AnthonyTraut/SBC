package sbc;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;

public class Main {
	
	/*PREFIX dbr:    <http://dbpedia.org/resource/>
PREFIX dbo:    <http://dbpedia.org/ontology/>
PREFIX dct:    <http://purl.org/dc/terms/>
PREFIX owl:    <http://www.w3.org/2002/07/owl#>
PREFIX prov:   <http://www.w3.org/ns/prov#>
PREFIX qb:     <http://purl.org/linked-data/cube#>
PREFIX qudt:   <http://qudt.org/1.1/schema/qudt#>
PREFIX rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs:   <http://www.w3.org/2000/01/rdf-schema#>
PREFIX schema: <http://schema.org/>
PREFIX skos:   <http://www.w3.org/2004/02/skos/core#>
PREFIX unit:   <http://qudt.org/vocab/unit#>
PREFIX xsd:    <http://www.w3.org/2001/XMLSchema#>
PREFIX sdmx:   <http://purl.org/linked-data/sdmx#>*/

	public static void main(String args[]){
		/*Query query = QueryFactory.create("PREFIX dbo:<http://dbpedia.org/ontology/>\n"
				+ "PREFIX dbpedia: <http://dbpedia.org/>\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
				+ "SELECT DISTINCT ?class (COUNT(?instance) AS ?instanceCount)\n"
				+ "WHERE {?instance a ?class.}\n"
				+ "GROUP BY ?class\n"
				+ "ORDER BY DESC(?instanceCount)\n"
				+ "LIMIT 10 OFFSET 0");
		
		Query query2 = QueryFactory.create("PREFIX dbo:<http://dbpedia.org/ontology/>\n"
				+ "PREFIX dbpedia: <http://dbpedia.org/>\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
				+ "SELECT DISTINCT ?parentName ?parentclass ?superclass\n"
				+ "WHERE{\n"
				+ "<http://dbpedia.org/ontology/Film> (rdfs:subClassOf)* ?parentclass.\n"
				+ "?parentclass rdfs:subClassOf ?superclass.\n"
				+ "?parentclass rdf:type owl:Class .\n"
				+ "?superclass rdfs:label ?parentName.\n"
				+ "?superclass rdf:type owl:Class .\n"
				+ "}\n"
				+ "LIMIT 100 OFFSET 0\n");
		
		Query query3 = QueryFactory.create("PREFIX dbo:<http://dbpedia.org/ontology/>\n"
				+ "PREFIX dbpedia: <http://dbpedia.org/>\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
				+ "SELECT DISTINCT ?subclass ?superclass\n"
				+ "WHERE{\n"
				+ "?subclass (rdfs:subClassOf)*  owl:Thing.\n"
				+ "?subclass rdfs:subClassOf ?superclass.\n"
				+ "?subclass rdf:type owl:Class.\n"
				+ "?superclass rdf:type owl:Class.\n"
				+ "}\n"
				+ "LIMIT 100 OFFSET 0\n");
		
	    String sparqlService = "http://dbpedia.org/sparql";
	
	    // Query service, no update, no graph store protocol.
	    try ( QueryExecution qexec = QueryExecutionFactory.sparqlService(sparqlService, query3); ) {
	    	ResultSet rs = qexec.execSelect();

	    	// use result set formatter
	    	ResultSetFormatter.out(rs);
	    }*/
	    
	    Frame frame = new Frame();
	}	
}