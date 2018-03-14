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
	
	/**
	 * Query pour avoir les classes en relations avec la classe CLASS
	 * CLASS ?predicat ?class .
	 * @param CLASS
	 * @param LIMIT
	 * @return
	 */
	public static Query A1_R1(String CLASS , int LIMIT){
		StringBuilder sb = new StringBuilder();
		sb.append(PREFIX);
		sb.append("SELECT DISTINCT ?predicat ?class\n");
		sb.append("WHERE{\n");
		sb.append("<"+ CLASS +"> ?predicat ?class .\n");
		sb.append("?class rdf:type owl:Class .\n");
		sb.append("}\n");
		sb.append("GROUP BY ?predicat ?class\n");
		sb.append("LIMIT "+LIMIT+" OFFSET 0\n");
		//System.out.println(sb.toString());
		
		return QueryFactory.create(sb.toString());
	}
	
	/**
	 * Query pour avoir les classes en relations avec la classe CLASS
	 * ?class ?predicat CLASS .
	 * @param CLASS
	 * @param LIMIT
	 * @return
	 */
	public static Query A1_R2(String CLASS , int LIMIT){
		StringBuilder sb = new StringBuilder();
		sb.append(PREFIX);
		sb.append("SELECT DISTINCT ?predicat ?class\n");
		sb.append("WHERE{\n");
		sb.append("?class ?predicat <"+ CLASS +"> .\n");
		sb.append("?class rdf:type owl:Class .\n");
		sb.append("}\n");
		sb.append("GROUP BY ?predicat ?class\n");
		sb.append("LIMIT "+LIMIT+" OFFSET 0\n");
		//System.out.println(sb.toString());
		
		return QueryFactory.create(sb.toString());
	}
	
	/**
	 * Query pour avoir les predicats dont le domaine est la classe CLASS
	 * ?predicat rdfs:domain CLASS .
	 * @param CLASS
	 * @param LIMIT
	 * @return
	 */
	public static Query A1_R3(String CLASS , int LIMIT){
		StringBuilder sb = new StringBuilder();
		sb.append(PREFIX);
		sb.append("SELECT DISTINCT ?predicat\n");
		sb.append("WHERE{\n");
		sb.append("?predicat rdfs:domain <"+ CLASS +"> .\n");
		sb.append("}\n");
		sb.append("LIMIT "+LIMIT+" OFFSET 0\n");
		//System.out.println(sb.toString());
		
		return QueryFactory.create(sb.toString());
	}
	
	/**
	 * Query pour avoir les predicats dont le range est la classe CLASS
	 * ?predicat rdfs:range CLASS .
	 * @param CLASS
	 * @param LIMIT
	 * @return
	 */
	public static Query A1_R4(String CLASS , int LIMIT){
		StringBuilder sb = new StringBuilder();
		sb.append(PREFIX);
		sb.append("SELECT DISTINCT ?predicat\n");
		sb.append("WHERE{\n");
		sb.append("?predicat rdfs:range <"+ CLASS +"> .\n");
		sb.append("}\n");
		sb.append("LIMIT "+LIMIT+" OFFSET 0\n");
		//System.out.println(sb.toString());
		
		return QueryFactory.create(sb.toString());
	}
	
	/**
	 * Query pour la hiérachie montante de la classe CLASS
	 * CLASS (rdfs:subClassOf)* ?parent .
	 * @param CLASS
	 * @param LIMIT
	 * @return
	 */
	public static Query A2_R1(String CLASS , int LIMIT){
		StringBuilder sb = new StringBuilder();
		sb.append(PREFIX);
		sb.append("SELECT DISTINCT ?parent ?super\n");
		sb.append("WHERE{\n");
		sb.append("<"+ CLASS +"> (rdfs:subClassOf)* ?parent .\n");
		sb.append("?parent rdfs:subClassOf ?super .\n");
		sb.append("}\n");
		sb.append("LIMIT "+LIMIT+" OFFSET 0\n");
		//System.out.println(sb.toString());
		
		return QueryFactory.create(sb.toString());
	}
	
	public static Query A2_R1bis(String CLASS, int N){
		StringBuilder sb = new StringBuilder();
		sb.append(PREFIX);
		sb.append("SELECT DISTINCT ?parent ?super\n");
		sb.append("WHERE{\n");
		sb.append("<"+ CLASS +"> (rdfs:subClassOf){0,"+ N +"} ?parent .\n");
		sb.append("?parent rdfs:subClassOf ?super .\n");
		sb.append("}\n");
		//System.out.println(sb.toString());
		
		return QueryFactory.create(sb.toString());
	}
	
	/**
	 * Query pour la hiérachie descendante de la classe CLASS
	 * ?sub (rdfs:subClassOf)+ CLASS .
	 * @param CLASS
	 * @param LIMIT
	 * @return
	 */
	public static Query A2_R2(String CLASS, int LIMIT){
		StringBuilder sb = new StringBuilder();
		sb.append(PREFIX);
		sb.append("SELECT DISTINCT ?sub ?super\n");
		sb.append("WHERE{\n");
		sb.append("?sub (rdfs:subClassOf)+ <"+ CLASS +"> .\n");
		sb.append("?sub rdfs:subClassOf ?super.\n");
		sb.append("}\n");
		sb.append("LIMIT "+LIMIT+" OFFSET 0\n");
		//System.out.println(sb.toString());
		
		return QueryFactory.create(sb.toString());
	}
	
	public static Query A2_R2bis(String CLASS, int N){
		StringBuilder sb = new StringBuilder();
		sb.append(PREFIX);
		sb.append("SELECT DISTINCT ?sub ?super\n");
		sb.append("WHERE{\n");
		sb.append("?sub (rdfs:subClassOf){1,"+ N +"} <"+ CLASS +"> .\n");
		sb.append("?sub rdfs:subClassOf ?super.\n");
		sb.append("}\n");
		//System.out.println(sb.toString());
		
		return QueryFactory.create(sb.toString());
	}
	
	/**
	 * 
	 * @param CLASS
	 * @param LIMIT
	 * @return
	 */
	public static Query A3(String CLASS , int LIMIT){
		StringBuilder sb = new StringBuilder();
		sb.append(PREFIX);
		sb.append("SELECT (COUNT(?otherinstance) AS ?count) ?predicat\n");
		sb.append("WHERE{\n");
			sb.append("?instance rdf:type <"+ CLASS +"> .\n");
			sb.append("{\n");
				sb.append("?instance ?predicat ?otherinstance .\n");
				sb.append("?otherinstance rdf:type ?class .\n");
				sb.append("?class rdf:type owl:Class\n");
			sb.append("} UNION\n");
			sb.append("{\n");
				sb.append("?otherinstance ?predicat ?instance .\n");
				sb.append("?otherinstance rdf:type ?class .\n");
				sb.append("?class rdf:type owl:Class\n");
			sb.append("}\n");
		sb.append("}\n");
		sb.append("GROUP BY ?predicat\n");
		sb.append("ORDER BY DESC(?count)\n");
		sb.append("LIMIT "+LIMIT+" OFFSET 0\n");
		//System.out.println(sb.toString());
		
		return QueryFactory.create(sb.toString());
	}
	
	/**
	 * Query pour chercher les sous-classes de CLASS et leurs instances associées
	 * @param CLASS
	 * @param LIMIT
	 * @return
	 */
	public static Query B1(String CLASS , int LIMIT){
		StringBuilder sb = new StringBuilder();
		sb.append(PREFIX);
		sb.append("SELECT DISTINCT ?sub ?super (COUNT(?instance) AS ?count) \n");
		sb.append("WHERE{\n");
		sb.append("?sub (rdfs:subClassOf)* <"+ CLASS +"> .\n");
		sb.append("?sub rdfs:subClassOf ?super .\n");
		sb.append("?instance rdf:type ?sub .\n");
		sb.append("}\n");
		sb.append("GROUP BY ?sub ?super\n");
		sb.append("ORDER BY DESC(?count)\n");
		sb.append("LIMIT "+LIMIT+" OFFSET 0\n");
		//System.out.println(sb.toString());
		
		return QueryFactory.create(sb.toString());
	}
	
	/**
	 * Query pour chercher les instances qui sont objets d'un prédicat avec la RESSOURCE comme sujet
	 * @param RESSOURCE
	 * @param LIMIT
	 * @return
	 */
	public static Query C1(String RESSOURCE , int LIMIT){
		StringBuilder sb = new StringBuilder();
		sb.append(PREFIX);
		sb.append("SELECT DISTINCT ?class\n");
		sb.append("WHERE{\n");
		sb.append("<"+ RESSOURCE +"> rdf:type ?class .\n");
		sb.append("}\n");
		sb.append("LIMIT "+LIMIT+" OFFSET 0\n");
		//System.out.println(sb.toString());
		
		return QueryFactory.create(sb.toString());
	}
	
	/**
	 * Query pour chercher les instances qui sont sujets d'un prédicat avec la RESSOURCE comme objet
	 * @param RESSOURCE
	 * @param LIMIT
	 * @return
	 */
	public static Query C2(String RESSOURCE , int LIMIT){
		StringBuilder sb = new StringBuilder();
		sb.append(PREFIX);
		sb.append("SELECT DISTINCT ?predicat ?instance\n");
		sb.append("WHERE{\n");
		sb.append("<"+ RESSOURCE +"> ?predicat ?instance .\n");
		sb.append("?instance rdf:type ?classs .\n");
		sb.append("?class rdf:type owl:Class .\n");
		sb.append("}\n");
		sb.append("LIMIT "+LIMIT+" OFFSET 0\n");
		//System.out.println(sb.toString());
		
		return QueryFactory.create(sb.toString());
	}
	
	
	public static void RunQuery(Query q){
		try ( QueryExecution qexec = QueryExecutionFactory.sparqlService(sparqlService, q); ) {
	    	ResultSet rs = qexec.execSelect();
	    	ResultSetFormatter.out(rs);
	    	//ResultSetFormatter.out(rs); // use result set formatter
	  }
	}

	/**
	public static void main(String[] args) {
		//RunQuery(A1_R1("http://dbpedia.org/ontology/CelestialBody", 100));
		//RunQuery(A1_R2("http://dbpedia.org/ontology/CelestialBody", 100));
		//RunQuery(A1_R3("http://dbpedia.org/ontology/CelestialBody", 100));
		//RunQuery(A1_R4("http://dbpedia.org/ontology/CelestialBody", 100));
		//RunQuery(A2_R1("http://dbpedia.org/ontology/CelestialBody", 100));
		//RunQuery(A2_R1bis("http://dbpedia.org/ontology/CelestialBody", 100));
		//RunQuery(A2_R2("http://dbpedia.org/ontology/CelestialBody", 1000));
		//RunQuery(A2_R2bis("http://dbpedia.org/ontology/CelestialBody", 1000));
		//RunQuery(A3("http://dbpedia.org/ontology/Planet", 100));
		//RunQuery(B1("http://dbpedia.org/ontology/CelestialBody", 100));
		//RunQuery(C1("http://dbpedia.org/resource/Naboo", 100));
		//RunQuery(C2("http://dbpedia.org/resource/Naboo", 100));
	}*/
	
}
