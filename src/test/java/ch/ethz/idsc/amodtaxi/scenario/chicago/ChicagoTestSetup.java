package ch.ethz.idsc.amodtaxi.scenario.chicago;

import java.io.File;

import ch.ethz.idsc.amodeus.util.io.GZHandler;
import ch.ethz.idsc.amodeus.util.io.Locate;
import ch.ethz.idsc.amodtaxi.scenario.ScenarioLabels;
import ch.ethz.idsc.amodtaxi.scenario.ScenarioSetup;

/* package */ enum ChicagoTestSetup {
    ;

    public static void in(File workingDir) throws Exception {
        ChicagoGeoInformation.setup();
        File resourcesDir = new File(Locate.repoFolder(CreateChicagoScenario.class, "amodtaxi"), //
                "resources/test/chicagoScenario");
        ScenarioSetup.in(workingDir, resourcesDir, ScenarioLabels.networkGz);
        GZHandler.extract(new File(workingDir, ScenarioLabels.networkGz), new File(workingDir, ScenarioLabels.network));
    }
}
