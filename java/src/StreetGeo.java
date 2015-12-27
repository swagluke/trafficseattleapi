import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Scanner;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by lukezhang on 12/24/15.
 */
public class StreetGeo
{
    private static final String URL = "http://maps.googleapis.com/maps/api/geocode/json";
    private static final String filelocation = System.getProperty("user.home") + "/Programming/TrafficSeattle/data/geolocation.csv";
    public static void main(String[] args) throws IOException, InterruptedException
    {
        Scanner scanner = new Scanner(new File("/Users/lukezhang/Programming/TrafficSeattle/data/data.json"));
        FileWriter writer = new FileWriter(filelocation);
        while(scanner.hasNextLine()){
            String line = StringUtils.substringBetween(scanner.nextLine(),"\"stname\":\"","\",\"year\"")+",Seattle,WA";
            //System.out.println(line);
            if(!getJSONByGoogle(line).contains("null"))
            {
                String result = getJSONByGoogle(line);
                String[] splited = result.split("\\s+");
                if(splited.length==2)
                {
                    writer.append(line+","+splited[0] + "," + splited[1]);
                    writer.append("\n");
                }
            }
        }
        writer.flush();
        writer.close();
        scanner.close();
    }
    public static String getJSONByGoogle(String fullAddress) throws IOException, InterruptedException
    {
        URL url = new URL(URL + "?address="
                + URLEncoder.encode(fullAddress, "UTF-8") + "&sensor=false");
        URLConnection conn = url.openConnection();
        ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
        IOUtils.copy(conn.getInputStream(), output);
        output.close();
        String realoutput = output.toString();
        String lat = StringUtils.substringBetween(realoutput,"lat\" : ",",\n" + "                  \"lng");
        String lng = StringUtils.substringBetween(realoutput,"\"lng\" :","\n" + "               },");
        //String streetname = StringUtils.substringBetween(realoutput,"\"short_name\" : \"",",\n" + "               \"types\" :");
        return lat+lng;
    }
}
