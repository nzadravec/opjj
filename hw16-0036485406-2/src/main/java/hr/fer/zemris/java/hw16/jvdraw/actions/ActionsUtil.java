package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.Color;
import hr.fer.zemris.java.hw16.jvdraw.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.Point;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.Line;

public class ActionsUtil {

	public static List<GeometricalObject> loadObjectsFrom(Path path) throws IOException {
		List<String> lines = Files.readAllLines(path);
		List<GeometricalObject> objects = new ArrayList<>();
		for(String line : lines) {
			if(line.isEmpty()) {
				continue;
			}
			
			String[] ss = line.split(" ");
			String objectName = ss[0];
			GeometricalObject object = null;
			switch (objectName) {
			case "LINE":
				Point start = new Point(Integer.parseInt(ss[1]), Integer.parseInt(ss[2]));
				Point end = new Point(Integer.parseInt(ss[3]), Integer.parseInt(ss[4]));
				Color color = new Color(Integer.parseInt(ss[5]), Integer.parseInt(ss[6]),Integer.parseInt(ss[7]));
				object = new Line(start, end, color);
				break;
			case "CIRCLE":
				Point center = new Point(Integer.parseInt(ss[1]), Integer.parseInt(ss[2]));
				int radius = Integer.parseInt(ss[3]);
				color = new Color(Integer.parseInt(ss[4]), Integer.parseInt(ss[5]),Integer.parseInt(ss[6]));
				object = new Circle(center, radius, color);
				break;
			case "FCIRCLE":
				center = new Point(Integer.parseInt(ss[1]), Integer.parseInt(ss[2]));
				radius = Integer.parseInt(ss[3]);
				Color outlineColor = new Color(Integer.parseInt(ss[4]), Integer.parseInt(ss[5]),Integer.parseInt(ss[6]));
				Color areaColor = new Color(Integer.parseInt(ss[7]), Integer.parseInt(ss[8]),Integer.parseInt(ss[9]));
				object = new FilledCircle(center, radius, outlineColor, areaColor);
				break;
			default:
				throw new RuntimeException("Unknown geometrical object " + objectName);
			}
			
			objects.add(object);
		}
		
		return objects;
	}

}
