import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.FileWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by lukezhang on 12/24/15.
 */
public class GetData
{

    private final String USER_AGENT = "Mozilla/5.0";
    private final String start = "stname\":\"";
    private final String end = "\",\"year";
    private final String yearstart = "\"year\":\"";
    private final String yearend = ".0\"}";
    private final String filelocation = System.getProperty("user.home") + "/Programming/TrafficSeattle/data/data.json";
    public static void main(String[] args) throws Exception {

        GetData http = new GetData();
        System.out.println("Send Http GET request");
        http.sendGet();
    }

    // HTTP GET request
    private void sendGet() throws Exception {

        String url = "https://data.seattle.gov/resource/rn6u-vkuv.json";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        //Pattern p = Pattern.compile(Pattern.quote(start) + "(.*?)" + Pattern.quote(end));
        FileWriter writer = new FileWriter(filelocation);
        while ((inputLine = in.readLine()) != null) {
            //Matcher m = p.matcher(inputLine);
            //while (m.find())
            //{
               // String streetname = m.group(1)+",Seattle,WA";
                //System.out.println(streetname);
                writer.append(inputLine);
                writer.append("\n");
                /*response.append(m.group(1));
                response.append(",Seattle,WA");
                response.append("\n");*/
            //}
        }
        writer.flush();
        writer.close();
        in.close();
    }
}
