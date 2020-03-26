package com.kyleharrington.pointmanager;

import graphics.scenery.BoundingGrid;
import graphics.scenery.Group;
import graphics.scenery.Sphere;
import net.imglib2.RealPoint;
import sc.iview.SciView;
import sc.iview.vector.ClearGLVector3;

import java.util.List;

public class SciViewUtils {
    static public void showPoints(PointManager pm, SciView sv) {
        List<RealPoint> points = pm.toList();
        float radius = 10f;
        double[] pos = new double[points.get(0).numDimensions()];

        Group g = new Group();

        for( RealPoint point : points ) {
            point.localize(pos);
            Sphere sphere = new Sphere(radius, 20);
            sphere.setPosition(new ClearGLVector3((float)pos[0], (float)pos[1], (float)pos[2]).source());
            g.addChild(sphere);
        }
        sv.addNode(g);

        BoundingGrid bg = new BoundingGrid();
        bg.setNode(g);
    }
}
