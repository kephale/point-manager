/**
 * License: GPL
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License 2
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.kyleharrington.pointmanager;

import bdv.util.BdvFunctions;
import graphics.scenery.Node;
import graphics.scenery.PointCloud;
import net.imglib2.RealPoint;
import org.scijava.table.*;
import picocli.CommandLine;
import picocli.CommandLine.Option;
import sc.iview.SciView;

import java.util.concurrent.Callable;

/**
 *
 *
 * @author Kyle Harrington &lt;janelia@kyleharrington.com&gt;
 */
public class Main implements Callable<Void> {

    /**com/kyleharrington/pointmanager/Main.java:28
     * Main should operate on IterableRealInterval
     * This could be a List of RealPoint
     * It should be easy to associate Main to a BDV or a sciview
     *
     * BDV:
     *  - this means managing the overlay of landmarks in a BDV
     *
     * sciview:
     *  - this means providing a Node with child points
     *  can connect to an existing sciview instance
     *
     * should be usable from CLI (e.g. a text mode for point browsing)
     */

	// Command line
	@Option(names = {"-i", "--input"}, required = false, description = "path to a csv, like /home/kharrington/test.csv")
	private String inputPath = null;

//	@Option(names = {"--n5input"}, required = false, description = "container path *AND* dataset path, e.g. -i /nrs/flyem/tmp/VNC.n5/volumes/input")
//	private String n5Path = null;

	@Option(names = {"--showTable"}, required = false, description = "flag to show a the table in GUI")
	private boolean showTable = false;

	@Option(names = {"--showSciview"}, required = false, description = "flag to show a collection of points in sciview")
	private boolean showSciview = false;

	@Option(names = {"--showBDV"}, required = false, description = "flag to show a collection of points in BigDataViewer")
	private boolean showBDV = false;

	private PointManager pointManager;

	public Main() {

	}

	public static final void main(String... args) {
		if( args.length == 0 ) {
			args = new String[]{
					"--input", "/home/kharrington/git/point-manager/src/test/resources/test.csv",
					"--showTable",
					"--showSciview"
			};
		}

		CommandLine.call(new Main(), args);
	}

	@Override
	public final Void call() throws Exception {
		net.imagej.ImageJ imagej;

		SciView sciview = null;
		if( showSciview ) {
			sciview = SciView.createSciView();
		 	imagej = new net.imagej.ImageJ(sciview.getScijavaContext());
		} else {
			imagej = new net.imagej.ImageJ();
		}

		//imagej.ui().showUI();

		pointManager = new PointManager();

		// File IO
		if( inputPath != null ) {
			pointManager.open(inputPath);
		}

		if( showTable ) {
			DoubleTable table = pointManager.toDoubleTable();
			imagej.ui().show(table);
		}

		if( showSciview ) {
			sciview.getFloor().setVisible(false);

			SciViewUtils.showPoints(pointManager, sciview);
		}

		if( showBDV ) {
			// TODO port from BigWarpOverlay
			sciview.getFloor().setVisible(false);

			SciViewUtils.showPoints(pointManager, sciview);
		}

		return null;
	}


}
