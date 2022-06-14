package algorithm.geneticAlgorithm.executables.analisys;

import algorithm.geneticAlgorithm.model.PopulationFactory;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RunConfig {

    private PopulationFactory populationFactory;
    private String resultFilename;
}
