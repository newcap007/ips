package ips;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.Random;


public class HeatMapDataCreater {

	public String lineSeparator = System.getProperty("line.separator");
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		dataCreater("D:/888/casm/ips/src/main/webapp/json/heatMap.geojson");
	}

	   
    private static void dataCreater(String outFilePath){
    	try {
			StringBuilder resultBuilder = new StringBuilder("{")
					.append("\"type\": \"FeatureCollection\",")
					.append("\"features\": [");
			StringBuilder recordBuilder = new StringBuilder();
			for(int i=0; i<100; i++)
			{
			    Random random = new Random();
			    int lngAbs = random.nextInt(3018)%(3018-2665+1) + 2665;
			    int latAbs = random.nextInt(7992)%(7992-6625+1) + 6625;
			    BigDecimal lng = new BigDecimal(116.420).add(new BigDecimal(lngAbs).divide(new BigDecimal(10000000))).setScale(7,BigDecimal.ROUND_DOWN);
			    BigDecimal lat = new BigDecimal(39.947).add(new BigDecimal(latAbs).divide(new BigDecimal(10000000))).setScale(7,BigDecimal.ROUND_DOWN);
			    recordBuilder.append("{ \"type\": \"Feature\", \"geometry\": { \"type\": \"Point\", \"coordinates\": [ ")
			    		.append(lng).append(", ").append(lat).append(" ] } },");
			}
			for(int i=0; i<50; i++)
			{
			    Random random = new Random();
			    int lngAbs = random.nextInt(3018)%(3018-2932+1) + 2932;
			    int latAbs = random.nextInt(6625)%(6625-5417+1) + 5417;
			    BigDecimal lng = new BigDecimal(116.420).add(new BigDecimal(lngAbs).divide(new BigDecimal(10000000))).setScale(7,BigDecimal.ROUND_DOWN);
			    BigDecimal lat = new BigDecimal(39.947).add(new BigDecimal(latAbs).divide(new BigDecimal(10000000))).setScale(7,BigDecimal.ROUND_DOWN);
			    recordBuilder.append("{ \"type\": \"Feature\", \"geometry\": { \"type\": \"Point\", \"coordinates\": [ ")
			    		.append(lng).append(", ").append(lat).append(" ] } },");
			}
			for(int i=0; i<100; i++)
			{
			    Random random = new Random();
			    int lngAbs = random.nextInt(3137)%(3137-1816+1) + 1816;
			    int latAbs = random.nextInt(7669)%(7669-7359+1) + 7359;
			    BigDecimal lng = new BigDecimal(116.420).add(new BigDecimal(lngAbs).divide(new BigDecimal(10000000))).setScale(7,BigDecimal.ROUND_DOWN);
			    BigDecimal lat = new BigDecimal(39.947).add(new BigDecimal(latAbs).divide(new BigDecimal(10000000))).setScale(7,BigDecimal.ROUND_DOWN);
			    recordBuilder.append("{ \"type\": \"Feature\", \"geometry\": { \"type\": \"Point\", \"coordinates\": [ ")
			    		.append(lng).append(", ").append(lat).append(" ] } },");
			}
			for(int i=0; i<30; i++)
			{
			    Random random = new Random();
			    int lngAbs = random.nextInt(3433)%(3433-3137+1) + 3137;
			    int latAbs = random.nextInt(7777)%(7777-7428+1) + 7428;
			    BigDecimal lng = new BigDecimal(116.420).add(new BigDecimal(lngAbs).divide(new BigDecimal(10000000))).setScale(7,BigDecimal.ROUND_DOWN);
			    BigDecimal lat = new BigDecimal(39.947).add(new BigDecimal(latAbs).divide(new BigDecimal(10000000))).setScale(7,BigDecimal.ROUND_DOWN);
			    recordBuilder.append("{ \"type\": \"Feature\", \"geometry\": { \"type\": \"Point\", \"coordinates\": [ ")
			    		.append(lng).append(", ").append(lat).append(" ] } },");
			}
			recordBuilder.setLength(recordBuilder.length() - 1);
			resultBuilder.append(recordBuilder).append("]").append("}");
			File outFile=new File(outFilePath);   
		    if(!outFile.exists())   
		    {   
		        try {   
		        	outFile.createNewFile();   
		        } catch (IOException e) {   
		            // TODO Auto-generated catch block   
		            e.printStackTrace();   
		        }   
		    } 
		    OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(outFilePath),"UTF-8"); 
		    out.write(resultBuilder.toString());
		    out.flush();
		    out.close();   
		} catch (Exception e) {

			System.out.println("Error" + e); 

		}
    }    
}
