/* amodeus - Copyright (c) 2018, ETH Zurich, Institute for Dynamic Systems and Control */
package ch.ethz.idsc.amodtaxi.scenario;

import java.io.File;
import java.io.IOException;

import ch.ethz.idsc.amodtaxi.osm.StaticMapCreator;
import org.matsim.api.core.v01.network.Network;
import org.matsim.core.network.algorithms.NetworkCleaner;
import org.matsim.core.network.io.NetworkWriter;

import ch.ethz.idsc.amodeus.matsim.NetworkLoader;
import ch.ethz.idsc.amodeus.util.io.GZHandler;
import ch.ethz.idsc.amodeus.util.math.GlobalAssert;
import ch.ethz.idsc.amodeus.util.network.LinkModes;

public enum ScenarioBasicNetworkPreparer {
    ;

    public static Network run(File processingDir) {
        // load the pt2matsim network
        Network networkpt2Matsim = NetworkLoader.fromNetworkFile(StaticMapCreator.getNetworkFileName(processingDir).orElseThrow());
        GlobalAssert.that(!networkpt2Matsim.getNodes().isEmpty());

        // remove links on which cars cannot drive
        LinkModes linkModes = new LinkModes("car");
        Network filteredNetwork = NetworkCutterUtils.modeFilter(networkpt2Matsim, linkModes);

        // cleanup the network
        new NetworkCleaner().run(filteredNetwork);

        // save the network
        final File fileExport = new File(processingDir, ScenarioLabels.network);
        final File fileExportGz = new File(processingDir, ScenarioLabels.networkGz);
        {
            // write the modified population to file
            NetworkWriter nw = new NetworkWriter(filteredNetwork);
            nw.write(fileExportGz.toString());
        }

        // extract gz file
        try {
            GZHandler.extract(fileExportGz, fileExport);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filteredNetwork;
    }
}