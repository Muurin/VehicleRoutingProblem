package utilTests;

import solution.SolutionContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import testUtil.ObjectInitializers;
import util.SolutionUtil;

public class SolutionUtilTests {


	@Test
	public void findNearestLocationFrom(){
		SolutionContext solutionContext= ObjectInitializers.createSolutionContextExample1();
		Assertions.assertEquals("3", SolutionUtil.findNearestLocationFromNotVisited(solutionContext.getCustomers().values(),solutionContext.getCustomers().get("5"), solutionContext.getServicedCustomers()).getId());
	}

	@Test
	public void anyCustomersInNeedOfService(){
		Assertions.assertTrue(SolutionUtil.anyCustomersInNeedOfService(ObjectInitializers.createSolutionContextExample1()));
	}

}
