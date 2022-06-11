package algorithm.geneticAlgorithm.executables.analisys;

import lombok.Builder;
import lombok.Getter;
import solution.evaluators.SolutionEvaluator;

@Builder
@Getter
public class EvaluatorInfo {

    private final SolutionEvaluator solutionEvaluator;

    private final String filename;

}
