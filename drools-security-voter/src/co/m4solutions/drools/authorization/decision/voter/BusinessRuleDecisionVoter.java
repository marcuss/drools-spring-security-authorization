package co.m4solutions.drools.authorization.decision.voter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.webflow.execution.RequestContextHolder;

import co.m4solutions.drools.authorization.engine.DroolsEngine;
import co.m4solutions.drools.authorization.model.CerberoBusinessInfoHolder;
import co.m4solutions.drools.authorization.model.RequestInfoHolder;

public class BusinessRuleDecisionVoter implements AccessDecisionVoter {
	// AccessDecisionManager {
	public static final int ACCESS_GRANTED = 1;

	// Field descriptor #4 I
	public static final int ACCESS_ABSTAIN = 0;

	// Field descriptor #4 I
	public static final int ACCESS_DENIED = -1;

	// ~ Instance fields
	// ================================================================================================

	private String rolePrefix = "ROLE_";

	// ~ Methods
	// ========================================================================================================
	public String getRolePrefix() {
		return rolePrefix;
	}

	/**
	 * Allows the default role prefix of <code>ROLE_</code> to be overriden. May
	 * be set to an empty value, although this is usually not desireable.
	 * 
	 * @param rolePrefix
	 *            the new prefix
	 */
	public void setRolePrefix(String rolePrefix) {
		this.rolePrefix = rolePrefix;
	}

	public boolean supports(ConfigAttribute attribute) {
		if ((attribute.getAttribute() != null)
				&& attribute.getAttribute().startsWith(getRolePrefix())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This implementation supports any type of class, because it does not query
	 * the presented secure object.
	 * 
	 * @param clazz
	 *            the secure object
	 * 
	 * @return always <code>true</code>
	 */
	public boolean supports(Class clazz) {
		return true;
	}

	@Override
	public int vote(Authentication authentication, Object object,
			Collection attributes) {

		int result = ACCESS_ABSTAIN;
		Iterator iter = attributes.iterator();
		while (iter.hasNext()) {
			ConfigAttribute attribute = (ConfigAttribute) iter.next();

			if (this.supports(attribute)) {
				@SuppressWarnings("unused")
				Collection<? extends GrantedAuthority> authorities = authentication
						.getAuthorities();
				// Attempt to find a matching granted authority

				// Obtain the current user data getPrincipal()

				RequestInfoHolder businessObj = new RequestInfoHolder();
				businessObj
						.setFlowScopeVariables(new HashMap<String, Object>());
				businessObj.getFlowScopeVariables().putAll(
						RequestContextHolder.getRequestContext().getFlowScope()
								.asMap()); // Variables Flow
				// RequestContextHolder.getRequestContext().getRequestScope().asMap()
				businessObj.getFlowScopeVariables().putAll(
						RequestContextHolder.getRequestContext()
								.getConversationScope().asMap());
				businessObj.setFlowViewName(RequestContextHolder
						.getRequestContext().getCurrentState().getId());
				businessObj.setFlowName(RequestContextHolder
						.getRequestContext().getActiveFlow().getId());
				businessObj.setFlowExecutedAction(RequestContextHolder
						.getRequestContext().getCurrentEvent().getId());
				businessObj.setRequestUrl(RequestContextHolder
						.getRequestContext().getFlowExecutionUrl()); // Url
				// businessObj.setRolPersona(authorities.toArray()[0].getAuthority());

				CerberoBusinessInfoHolder.setData(businessObj);
				// businessObj.getVariables().get("hotel")
				// LLAMAR A DROOLS

				DroolsEngine.getInstance().validate(businessObj);

				if (businessObj.isResultado()) {
					return ACCESS_GRANTED;
				} else {
					RequestContextHolder.getRequestContext()
							.getConversationScope()
							.put("mensajeRegla", businessObj.getMessage());
					return ACCESS_DENIED;
				}

			}
		}
		return result;
	}

}
