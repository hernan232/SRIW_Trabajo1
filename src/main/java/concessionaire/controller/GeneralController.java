/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concessionaire.controller;

import javax.swing.DefaultListModel;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFWriter;
import org.apache.jena.reasoner.ReasonerRegistry;

/**
 *
 * @author Admin
 */
public class GeneralController {

    public ResultSet executeQueryToIntegration(String queryString) {
        Model model = ModelFactory.createDefaultModel();
        model.read("https://sriw-trabajo1-ontologies.herokuapp.com/ontologies/integration_ontology.owl", "RDF/XML");
        InfModel inferenceModel = ModelFactory.createInfModel(ReasonerRegistry.getOWLReasoner(), model);

        RDFWriter writer = model.getWriter();
        String querys;

        Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, inferenceModel);

        ResultSet results = qexec.execSelect();
        return results;
    }

    public ResultSet executeQueryToEndPoint(String queryString, String endpoint) {
        Query query = QueryFactory.create(queryString);
        QueryExecution queryExec = QueryExecutionFactory.sparqlService(endpoint, query);

        ResultSet resultSet = queryExec.execSelect();
        return resultSet;
    }

    public DefaultListModel getClasses() {
        String query = "SELECT DISTINCT ?clase \n"
                + "FROM <http://35.224.217.230:8890/ontologies/concesionario>\n"
                + "WHERE {\n"
                + "	?clase rdf:type owl:Class .\n"
                + "	?clase owl:equivalentClass ?otra_clase .\n"
                + "	FILTER(?clase != ?otra_clase)\n"
                + "}";

        ResultSet allClasses = this.executeQueryToIntegration(query);

        DefaultListModel listModel = new DefaultListModel();
        while (allClasses.hasNext()) {
            QuerySolution soln = allClasses.nextSolution();
            listModel.addElement(soln.getResource("?x"));
        }
        return listModel;
    }

    public void getAttributeFromClass(String entity) {

    }

    public void getInstancesFromClass(String entity, boolean indirectQuery, String filter) {

    }

    public void getAttributesFromInstance(String instance) {

    }
}
