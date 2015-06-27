/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ptux;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import ptux.KDTree;

/**
 *
 * @author Vasileios Gerodimos
 * A.M. 2025200900014
 * 
 * @author Vasileios Stamos
 * A.M. 2025200900070
 */
public class ReverseGeoCode {
    KDTree<GeoName> kdTree;
    ArrayList<GeoName> arPlaceNames;
    
    // Get placenames from http://download.geonames.org/export/dump/
    public ReverseGeoCode( InputStream placenames, Boolean majorOnly ) throws IOException {
        
        arPlaceNames = new ArrayList<GeoName>();
        // Read the geonames file in the directory
        BufferedReader in = new BufferedReader(new InputStreamReader(placenames));
        String str;
        try {
            while ((str = in.readLine()) != null) {
                GeoName newPlace = new GeoName(str);
                if ( !majorOnly || newPlace.majorPlace ) {
                    arPlaceNames.add(new GeoName(str));
                }
            }

        } catch (IOException ex) {
            in.close(); 
            throw ex;
        }
        in.close();
        kdTree = new KDTree<GeoName>(arPlaceNames);
    }

    public GeoName nearestPlace(double latitude, double longitude) {
        return kdTree.findNearest(new GeoName(latitude,longitude));
    }

    public ArrayList<GeoName> getArPlaceNames() {
        return arPlaceNames;
    }
    
}
