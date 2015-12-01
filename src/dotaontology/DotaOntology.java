/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dotaontology;

import additionalClass.DLQueryEngine;
import java.io.File;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

import additionalClass.DLQueryPrinter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.semanticweb.owlapi.expression.ParserException;

/**
 *
 * @author Teofebano
 */
public class DotaOntology {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws OWLOntologyCreationException, IOException, ParserException {
        // TODO code application logic here
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        File file = new File("data/pesawat.owl");
        // Load the local copy
        OWLOntology family = manager.loadOntologyFromOntologyDocument(file);
        System.out.println("Loaded ontology: " + family);
        OWLReasoner reasoner = new Reasoner.ReasonerFactory().createReasoner(family);
        
        ShortFormProvider shortFormProvider = new SimpleShortFormProvider();
        // Create the DLQueryPrinter helper class. This will manage the
        // parsing of input and printing of results
        DLQueryPrinter dlQueryPrinter = new DLQueryPrinter(new DLQueryEngine(reasoner, shortFormProvider), shortFormProvider);
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
        while (true) {
            System.out
                    .println("Type a class expression in Manchester Syntax and press Enter (or press x to exit):");
            String classExpression = br.readLine();
            // Check for exit condition
            if (classExpression == null || classExpression.equalsIgnoreCase("x")) {
                break;
            }
            dlQueryPrinter.askQuery(classExpression.trim());
            System.out.println();
        }
    }

}
