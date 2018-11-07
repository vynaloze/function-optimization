package com.vynaloze.fo;

import com.vynaloze.fo.functions.Domain;
import com.vynaloze.fo.functions.TestFunction;
import org.jzy3d.chart.Chart;
import org.jzy3d.chart.ChartLauncher;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.Scatter;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;

import java.util.Arrays;
import java.util.List;

public class Plotter {
    public static void plot(final Results results, final TestFunction testFunction) {
        final List<Coord> points = results.getPoints();
        final int size = points.size();
        final Coord3d[] coord3ds = new Coord3d[size];
        for (int i = 0; i < size; i++) {
            final Coord p = points.get(i);
            coord3ds[i] = new Coord3d(p.x, p.y, p.z);
        }

        final Scatter scatter = new Scatter(coord3ds, Color.RED);
        scatter.setWidth(4);
        final Scatter finalPoint = new Scatter(new Coord3d[]{new Coord3d(points.get(size - 1).x, points.get(size - 1).y, points.get(size - 1).z)}, Color.GREEN);
        finalPoint.setWidth(8);

        final Domain domain = testFunction.getDomain();
        final Range xrange = new Range((float) domain.getRanges().get(0).getMin(), (float) domain.getRanges().get(0).getMax());
        final Range yrange = new Range((float) domain.getRanges().get(1).getMin(), (float) domain.getRanges().get(1).getMax());
        final int steps = 100;

        // Create a surface drawing that function
        final Shape surface = Builder.buildOrthonormal(new OrthonormalGrid(xrange, steps, yrange, steps), new Mapper() {
            @Override
            public double f(final double v, final double v1) {
                return testFunction.apply(Arrays.asList(v, v1));
            }
        });
        surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface.getBounds().getZmin(), surface.getBounds().getZmax(), new Color(1, 1, 1, .5f)));
        surface.setFaceDisplayed(true);
        surface.setWireframeDisplayed(false);
        surface.setWireframeColor(Color.BLACK);

        // Create a chart and add scatter
        final Chart chart = AWTChartComponentFactory.chart(Quality.Advanced, "awt");
        chart.getAxeLayout().setMainColor(Color.BLACK);
        chart.getView().setBackgroundColor(Color.WHITE);
        chart.getScene().getGraph().add(surface);
        chart.getScene().getGraph().add(scatter);
        chart.getScene().getGraph().add(finalPoint);
        chart.addMouseCameraController();
        chart.addKeyboardCameraController();
        ChartLauncher.openChart(chart);
    }
}
