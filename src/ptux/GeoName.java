/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ptux;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;
import java.util.Comparator;
import ptux.KDNodeComparator;

/**
 *
 * @author Vasileios Gerodimos
 * A.M. 2025200900014
 * 
 * @author Vasileios Stamos
 * A.M. 2025200900070
 */
public class GeoName extends KDNodeComparator<GeoName> {
    public String name;
    public boolean majorPlace; // Major or minor place
    public double latitude;
    public double longitude;
    public double point[] = new double[3]; // The 3D coordinates of the point
    public String country, names[];

    GeoName(String data) {
        names = data.split("\t");
//        name = names[1];
        name = names[2];
        majorPlace = names[6].equals("P");       
        latitude = Double.parseDouble(names[4]);
        longitude = Double.parseDouble(names[5]);
        setPoint();
        country = names[8];    
    }

    GeoName(Double latitude, Double longitude) {
        name = country = "Search";
        this.latitude = latitude;
        this.longitude = longitude;
        setPoint();
    }

    private void setPoint() {
        point[0] = cos(toRadians(latitude)) * cos(toRadians(longitude));
        point[1] = cos(toRadians(latitude)) * sin(toRadians(longitude));
        point[2] = sin(toRadians(latitude));
    }

    @Override
    public String toString() {
//        for(String g:names){
//            System.out.println("apo arxeio: "+g);
//        }
        return name+" - "+country;
    }

    @Override
    protected Double squaredDistance(Object other) {
        GeoName location = (GeoName)other;
        double x = this.point[0] - location.point[0];
        double y = this.point[1] - location.point[1];
        double z = this.point[2] - location.point[2];
        return (x*x) + (y*y) + (z*z);
    }

    @Override
    protected Double axisSquaredDistance(Object other, Integer axis) {
        GeoName location = (GeoName)other;
        Double distance = point[axis] - location.point[axis];
        return distance * distance;
    }

    @Override
    protected Comparator<GeoName> getComparator(Integer axis) {
        return GeoNameComparator.values()[axis];
    }

    protected static enum GeoNameComparator implements Comparator<GeoName> {
        x {
            @Override
            public int compare(GeoName a, GeoName b) {
                return Double.compare(a.point[0], b.point[0]);
            }
        },
        y {
            @Override
            public int compare(GeoName a, GeoName b) {
                return Double.compare(a.point[1], b.point[1]);
            }
        },
        z {
            @Override
            public int compare(GeoName a, GeoName b) {
                return Double.compare(a.point[2], b.point[2]);
            }
        };
    }
}
