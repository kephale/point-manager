package com.kyleharrington.pointmanager.test;

import com.kyleharrington.pointmanager.PointManager;
import net.imglib2.RealPoint;
import org.junit.Test;
import org.scijava.table.DoubleTable;

import java.io.IOException;

public class PointManagerTest {
    @Test
    public void displayTable(){
        net.imagej.ImageJ imagej = new net.imagej.ImageJ();

		PointManager pointManager = new PointManager();

		pointManager.addPoint(new RealPoint(0, 0, 0));
		pointManager.addPoint(new RealPoint(0, 17, 0));
		pointManager.addPoint(new RealPoint(0, 0, 17));
		pointManager.addPoint(new RealPoint(0, 0, 17));

		DoubleTable table = pointManager.toDoubleTable();

		imagej.ui().show(table);

        assert true;
    }

    @Test
    public void readCSV() throws IOException {
        net.imagej.ImageJ imagej = new net.imagej.ImageJ();

		PointManager pointManager = new PointManager();
		pointManager.open(PointManager.class.getClassLoader().getResource("test.csv").getPath());

		DoubleTable table = pointManager.toDoubleTable();

		imagej.ui().show(table);

        assert true;
    }
}
