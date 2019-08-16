import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;

import javax.swing.text.Document;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.xml.sax.InputSource;

class IPTAS{
	static InputStream is = null;
	public static void main(String[]args) throws IOException, JSONException, InterruptedException {
		PrintWriter pr=null;
		try {
		 pr= new PrintWriter(new File("FF_AS_Countries.txt"));
		Scanner sc = new Scanner(new File("FFTOQ.txt"));
		int c=0;
		while(sc.hasNextLine()) {
			if(c==5)
			{
				pr.close();
				System.exit(0);
			}
			//c++;
			String q =sc.nextLine().trim();
			
			URL url = new URL(q);
			
			int posOfSubstr = q.lastIndexOf("/");
			pr.print(q.substring(posOfSubstr+1)+" ");
			
			
			URLConnection openConnection = url.openConnection();
			openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36");
			is = openConnection.getInputStream();
			BufferedReader bR = new BufferedReader(  new InputStreamReader(is));
			String line = "";

			StringBuilder responseStrBuilder = new StringBuilder();
			while((line =  bR.readLine()) != null){

			    responseStrBuilder.append(line);
			}

	//System.out.println(responseStrBuilder.toString());
	org.jsoup.nodes.Document doc = Jsoup.parse(responseStrBuilder.toString());

	org.jsoup.select.Elements rows = doc.select("tr");
	int counter=0;
	for(org.jsoup.nodes.Element row :rows)
	{
		if(counter==3||counter==4) {
			  org.jsoup.select.Elements columns = row.select("td");
			    
			    for (org.jsoup.nodes.Element column:columns)
			    {
			    	pr.print(column.text()+" ");
			    }
		}
	  counter++;
	    
	}
	pr.println();
			Thread.sleep(4000);
		}
		
		}catch(Exception ex) {
			pr.close();
		}
		
	}
	
	
}