/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ptux;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Vasileios Gerodimos
 * A.M. 2025200900014
 * 
 * @author Vasileios Stamos
 * A.M. 2025200900070
 */
public class TagGenerator {

    private String year="--", monthNumber="--", monthChar="--", dateNumber="--", dateChar="--", location="--", dimensions="--", manufacturer="--", model="--";
    private int flagForOriginalDateTime;
    
    public TagGenerator(String f, boolean locationTagSelected, ReverseGeoCode reverseGeoCode) {
        File jpegfile = new File(f);
        
        try {
            double answerPlatos = 0, answerMikos = 0;
            flagForOriginalDateTime=0;
            Metadata metadata = ImageMetadataReader.readMetadata(jpegfile);
            

            for (Directory directory : metadata.getDirectories()) {
                for (com.drew.metadata.Tag tag : directory.getTags()) {
//                    System.out.println(tag);
                    String na = tag.getTagName();
                    String de = tag.getDescription();
                    
                    if (na.equals("Date/Time Original")) {
                        flagForOriginalDateTime=1;
                        System.out.println();
                        System.out.println(na + " ::: " + de);

                        String[] prinTeliko = de.split(" ");
                        System.out.println("Meta apo tin split exoume: ");
                        for (String t:prinTeliko)
                            System.out.println(t);
                        
                        String[] teliko = prinTeliko[0].split(":");
                        String o1 = teliko[0];
                        String o2 = teliko[1];
                        String o3 = teliko[2];
//                        String o4 = teliko[3];
//                        String o5 = teliko[4];
//                        String o6 = teliko[5];
                        
//                        System.out.println("\n\nH hmera einai: " + o1);
//                        dateChar = o1;
                        System.out.println("H hmerominia einai: " + o3);
                        dateNumber = o3;
                        System.out.println("H minas einai: " + o2);
                        monthNumber = o2;
                        System.out.println("H xronia einai: " + o1);
                        year = o1;
                        
                        String o7 = o3 + "/"+ o2+ "/"+ o1;
                        
                        //metatrepoume tin imerominia stin morfi pou theloume
                        //etsi wste na paroume tin imera kai ton mina me grammata
                        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date MyDate = newDateFormat.parse(o7);
                        newDateFormat.applyPattern("EEE d MMM yyyy");
                        String MyDate2 = newDateFormat.format(MyDate);
                        System.out.println(MyDate2);
                        
                        String[] telikoChar = MyDate2.split(" ");
                        
                        System.out.println("Meta apo tin split exoume: ");
                        for (String t:telikoChar)
                            System.out.println(t);
                        //pairnoume ta tria grammata tis imeras                        
                        String o11 = telikoChar[0];
                        dateChar = o11;
                        //pairnoume ta 3 grammata tou mina
                        String o13 = telikoChar[2];
                        monthChar = o13;
                    
                    }
                    else if (na.equals("Date/Time") && flagForOriginalDateTime==0) {
                        flagForOriginalDateTime=1;
                        System.out.println();
                        System.out.println(na + " ::: " + de);

                        String[] prinTeliko = de.split(" ");
                        System.out.println("Meta apo tin split exoume: ");
                        for (String t:prinTeliko)
                            System.out.println(t);
                        
                        String[] teliko = prinTeliko[0].split(":");
                        String o1 = teliko[0];
                        String o2 = teliko[1];
                        String o3 = teliko[2];
//                        String o4 = teliko[3];
//                        String o5 = teliko[4];
//                        String o6 = teliko[5];
                        
//                        System.out.println("\n\nH hmera einai: " + o1);
//                        dateChar = o1;
                        System.out.println("H hmerominia einai: " + o3);
                        dateNumber = o3;
                        System.out.println("H minas einai: " + o2);
                        monthNumber = o2;
                        System.out.println("H xronia einai: " + o1);
                        year = o1;
                        
                        String o7 = o3 + "/"+ o2+ "/"+ o1;
                        
                        //metatrepoume tin imerominia stin morfi pou theloume
                        //etsi wste na paroume tin imera kai ton mina me grammata
                        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date MyDate = newDateFormat.parse(o7);
                        newDateFormat.applyPattern("EEE d MMM yyyy");
                        String MyDate2 = newDateFormat.format(MyDate);
                        System.out.println(MyDate2);
                        
                        String[] telikoChar = MyDate2.split(" ");
                        
                        System.out.println("Meta apo tin split exoume: ");
                        for (String t:telikoChar)
                            System.out.println(t);
                        //pairnoume ta tria grammata tis imeras                        
                        String o11 = telikoChar[0];
                        dateChar = o11;
                        //pairnoume ta 3 grammata tou mina
                        String o13 = telikoChar[2];
                        monthChar = o13;
                    
                    }
                    else if (na.equals("Profile Date/Time") && flagForOriginalDateTime==0) {
                        System.out.println();
                        System.out.println(na + " ::: " + de);

                        String[] teliko = de.split(" ");

                        String o1 = teliko[0];
                        String o2 = teliko[1];
                        String o3 = teliko[2];
                        String o4 = teliko[3];
                        String o5 = teliko[4];
                        String o6 = teliko[5];
                        
                        System.out.println("\n\nH hmera einai: " + o1);
                        dateChar = o1;
                        System.out.println("H hmerominia einai: " + o3);
                        dateNumber = o3;
                        System.out.println("H minas einai: " + o2);
                        monthChar = o2;
                        System.out.println("H xronia einai: " + o6);
                        year = o6;
                        
                        
                        if(o2.equals("Jan") || o2.equals("January")){
                            monthNumber = "01";
                        }
                        else if(o2.equals("Feb") || o2.equals("February")){
                            monthNumber = "02";
                        }
                        else if(o2.equals("Mar") || o2.equals("March")){
                            monthNumber = "03";
                        }
                        else if(o2.equals("Apr") || o2.equals("April")){
                            monthNumber = "04";
                        }
                        else if(o2.equals("May") || o2.equals("May")){
                            monthNumber = "05";
                        }
                        else if(o2.equals("Jun") || o2.equals("June")){
                            monthNumber = "06";
                        }
                        else if(o2.equals("Jul") || o2.equals("July")){
                            monthNumber = "07";
                        }
                        else if(o2.equals("Aug") || o2.equals("August")){
                            monthNumber = "08";
                        }
                        else if(o2.equals("Sep") || o2.equals("September")){
                            monthNumber = "09";
                        }
                        else if(o2.equals("Oct") || o2.equals("October")){
                            monthNumber = "10";
                        }
                        else if(o2.equals("Nov") || o2.equals("November")){
                            monthNumber = "11";
                        }
                        else if(o2.equals("Dec") || o2.equals("December")){
                            monthNumber = "12";
                        }
                        
//                        String o7 = o3 + "/"+ o2+ "/"+ o6;
//                        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy");
//                        Date MyDate = newDateFormat.parse(o7);
//                        newDateFormat.applyPattern("EEEE d MMM yyyy");
//                        String MyDate2 = newDateFormat.format(MyDate);
//                        System.out.println(MyDate2);
                    }else if (na.equals("Image Height")) {
                        System.out.println();
                        System.out.println(na + " ::: " + de);

                        System.out.println("To upsos tis fwtografias einai: " + de);
                        String[] s = de.split(" ");
                        
                        dimensions = s[0];
                    } else if (na.equals("Image Width")) {
                        System.out.println();
                        System.out.println(na + " ::: " + de);
                        
                        System.out.println("To platos tis fwtografias einai: " + de);
                        String[] s = de.split(" ");
                        
                        dimensions += "x" + s[0];
                    } else if (na.equals("Make")) {
                        System.out.println();
                        System.out.println(na + " ::: " + de);

                        System.out.println("O kataskeuastis pou eftiakse tin camera einai: " + de);
                        manufacturer = de;
                    } else if (na.equals("Primary Platform")) {
                        System.out.println();
                        System.out.println(na + " ::: " + de);

                        System.out.println("To montelo pou travixtike einai: " + de);
                        model = de;
                    } else if (na.equals("Model")) {
                        System.out.println();
                        System.out.println(na + " ::: " + de);

                        System.out.println("H fwtografia travixtike apo: " + de);
                        model = de;
                    } else if (na.equals("GPS Latitude")) {
                        System.out.println("loc:" + locationTagSelected);
                        if(locationTagSelected==true){
                        System.out.println();
                        System.out.println(na + " ::: " + de);

                        String[] teliko = de.split(" ");

                        String o1 = teliko[0];
                        String o2 = teliko[1];
                        String o3 = teliko[2];

                        String[] teliko1 = o1.split("°");
                        String[] teliko2 = o2.split("'");
                        String[] teliko3 = o3.split("\"");

                        String o4 = teliko1[0];
                        String o5 = teliko2[0];
                        String o6 = teliko3[0];

                        o6 = o6.replaceAll(",", ".");

                        System.out.println("Mikos: " + de);

                        System.out.println("degrees:" + o1);
                        System.out.println("minutes:" + o2);
                        System.out.println("seconds:" + o3);

                        System.out.println("degrees:" + o4);
                        System.out.println("minutes:" + o5);
                        System.out.println("seconds:" + o6);

                        double degrees = Double.parseDouble(o4);
                        double minutes = Double.parseDouble(o5);
                        double seconds = Double.parseDouble(o6);

                        double decimal = ((minutes * 60) + seconds) / (60 * 60);

                        answerPlatos = degrees + decimal;

                        System.out.println("To platos einai: " + answerPlatos);
                        
                        if(answerPlatos!=0 && answerMikos!=0){                      
//                        ReverseGeoCode reverseGeoCode = new ReverseGeoCode(getClass().getResourceAsStream("settings/cities1000.txt"), true);
                        
                        String loc = reverseGeoCode.nearestPlace(answerPlatos, answerMikos).toString();
                        byte[] bytes = loc.getBytes("UTF-8");
                        String loc2 = new String(bytes,"UTF-8");
//                        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("loc.txt",true),"UTF-8"));
//                        pw.println(loc2);
                        System.out.println("Nearest to " + answerPlatos + ", " + answerMikos + " is " + loc2);
                        
                        
//                        loc2=loc2.replaceAll("[^a-zA-Z0-9 -]+", "A");
//                        char c[]=loc2.toCharArray();
//                        int ctr=0;
//                        for(int i=0;i<c.length;i++){
//                            if (c[i]=='-'){
//                                ctr=1;
//                            }
//                            if(ctr==0){
//                                c[i] = Character.toLowerCase(c[i]);
//                            }
//                        }
//                        c[0]= Character.toUpperCase(c[0]);
//                        loc2=new String(c);
                        
                        
                        location = loc2;
//                        pw.close();
                    }
                        
                        }
                    } else if (na.equals("GPS Longitude")) {
                        System.out.println("loc:" + locationTagSelected);
                        if(locationTagSelected==true){
                        System.out.println();
                        System.out.println(na + " ::: " + de);

                        String[] teliko = de.split(" ");

                        String o1 = teliko[0];
                        String o2 = teliko[1];
                        String o3 = teliko[2];

                        String[] teliko1 = o1.split("°");
                        String[] teliko2 = o2.split("'");
                        String[] teliko3 = o3.split("\"");

                        String o4 = teliko1[0];
                        String o5 = teliko2[0];
                        String o6 = teliko3[0];

                        o6 = o6.replaceAll(",", ".");

                        System.out.println("Mikos: " + de);

                        System.out.println("degrees:" + o1);
                        System.out.println("minutes:" + o2);
                        System.out.println("seconds:" + o3);

                        System.out.println("degrees:" + o4);
                        System.out.println("minutes:" + o5);
                        System.out.println("seconds:" + o6);

                        double degrees = Double.parseDouble(o4);
                        double minutes = Double.parseDouble(o5);
                        double seconds = Double.parseDouble(o6);

                        double decimal = ((minutes * 60) + seconds) / (60 * 60);

                        answerMikos = degrees + decimal;

                        System.out.println("To mikos einai: " + answerMikos);
                        
                        if(answerPlatos!=0 && answerMikos!=0){                    
//                        ReverseGeoCode reverseGeoCode = new ReverseGeoCode(getClass().getResourceAsStream("settings/cities1000.txt"), true);
                        
                        String loc = reverseGeoCode.nearestPlace(answerPlatos, answerMikos).toString();
                        byte[] bytes = loc.getBytes("UTF-8");
                        String loc2 = new String(bytes,"UTF-8");
//                        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("loc.txt",true),"UTF-8"));
//                        pw.println(loc2);
                        System.out.println("Nearest to " + answerPlatos + ", " + answerMikos + " is " + loc2);
                        
                        
//                        loc2=loc2.replaceAll("[^a-zA-Z0-9 -]+", "A");
//                        char c[]=loc2.toCharArray();
//                        int ctr=0;
//                        for(int i=0;i<c.length;i++){
//                            if (c[i]=='-'){
//                                ctr=1;
//                            }
//                            if(ctr==0){
//                                c[i] = Character.toLowerCase(c[i]);
//                            }
//                        }
//                        c[0]= Character.toUpperCase(c[0]);
//                        loc2=new String(c);
                        
                        
                        location = loc2;
//                        pw.close();
                    }
                        
                        
                        }
                    }
                    
                    
                    

                }
            }

        } catch (Exception e) {

        }
    }
    
    
    public String getYear() {
        return year;
    }

    public String getMonthNumber() {
        return monthNumber;
    }

    public String getMonthChar() {
        return monthChar;
    }

    public String getDateNumber() {
        return dateNumber;
    }

    public String getDateChar() {
        return dateChar;
    }

    public String getLocation() {
        return location;
    }

    public String getDimensions() {
        return dimensions;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

}
