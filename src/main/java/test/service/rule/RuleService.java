package test.service.rule;

import java.util.Map;

import test.service.exception.RuleServiceException;

/**
 * rule service interface
 * @author minkeat.wong
 *
 */
public interface RuleService {

	Map<String, String> processRules(Map<String, Object> paramMap) throws RuleServiceException;
}
