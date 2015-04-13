package co.m4solutions.drools.authorization.engine;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

import co.m4solutions.drools.authorization.model.RequestInfoHolder;

/**
 * This is a sample class to launch a rule.
 */
public class DroolsEngine {

	private static DroolsEngine droolsEngine;
	private static KnowledgeBase kbase;
	private static StatefulKnowledgeSession ksession;

	private DroolsEngine() {
		try {
			kbase = readKnowledgeBase();
			ksession = kbase.newStatefulKnowledgeSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static DroolsEngine getInstance() {
		if (droolsEngine == null) {
			droolsEngine = new DroolsEngine();
		}

		return droolsEngine;
	}

	public RequestInfoHolder validate(RequestInfoHolder obj) {
		ksession.insert(obj);
		ksession.fireAllRules();
		return obj;

	}

	private static KnowledgeBase readKnowledgeBase() throws Exception {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory
				.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("ControlVentas.drl"),
				ResourceType.DRL);
		KnowledgeBuilderErrors errors = kbuilder.getErrors();
		if (errors.size() > 0) {
			for (KnowledgeBuilderError error : errors) {
				System.err.println(error);
			}
			throw new IllegalArgumentException("Could not parse knowledge.");
		}
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
		return kbase;
	}
}