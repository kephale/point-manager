package com.kyleharrington.pointmanager;

import com.mchange.v2.csv.CsvBufferedReader;
import net.imglib2.RealPoint;
import net.imglib2.roi.geom.real.KDTreeRealPointCollection;
import net.imglib2.roi.geom.real.RealPointCollection;
import org.scijava.table.DefaultDoubleTable;
import org.scijava.table.DoubleTable;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PointManager  {

    RealPointCollection<RealPoint> points = null;

    public PointManager() {
        this(null);
    }

    public PointManager(RealPointCollection<RealPoint> points) {
        this.points = points;
    }

    public void readFromN5( String n5Path, String dataset ) {

    }

    public void writeToN5( String n5Path, String dataset ) {

    }

    private RealPointCollection<RealPoint> makePointCollection(List<RealPoint> newPoints) {
        return new KDTreeRealPointCollection<>(newPoints);
    }

    public List<RealPoint> toList() {
        List<RealPoint> l = new ArrayList<>();
        for( RealPoint p : points.points() ) {
            l.add(p);
        }
        return l;
    }

    public void addPoint( RealPoint p ) {
        List<RealPoint> l;
        if( points == null ) {
            l = new ArrayList<>();
        } else {
            l = toList();
        }
        l.add(p);
        points = makePointCollection(l);
    }

    public void printPoints() {
        System.out.println("Displaying " + points.size() + " points");
        for( RealPoint point : points.points() ) {
            System.out.println(point);
        }
    }

	public DoubleTable toDoubleTable() {
        List<RealPoint> thesePoints = toList();
		int numDims= thesePoints.get(0).numDimensions();
		DoubleTable dt = new DefaultDoubleTable(numDims, thesePoints.size());

		for( int p=0; p<thesePoints.size(); p++ ) {
			RealPoint point = thesePoints.get(p);
			for( int d=0; d < numDims; d++ ) {
				dt.setValue(d, p, point.getDoublePosition(d));
			}
		}

		return dt;
	}

    public static void main(String... args) {
        PointManager pm = new PointManager();

        pm.addPoint(new RealPoint(0, 17, 0));
        pm.addPoint(new RealPoint(10, 17, 0));

        pm.printPoints();
    }

    /**
     * Read a csv. Expects a header
     * @param inputPath
     * @throws IOException
     */
    public void open(String inputPath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(inputPath));
        String line = reader.readLine();

        String header = line;
        String[] cols = header.split(",");

        int numDimensions = cols.length;

        List<RealPoint> l = new ArrayList<>();
        while( line != null ) {
            String[] parts = line.split(",");

            RealPoint pt = new RealPoint(numDimensions);
            for( int d = 0; d < numDimensions; d++ ) {
                pt.setPosition(Double.parseDouble(parts[d]), d);
            }

            l.add(pt);
            line = reader.readLine();
        }

        points = makePointCollection(l);
    }
}
