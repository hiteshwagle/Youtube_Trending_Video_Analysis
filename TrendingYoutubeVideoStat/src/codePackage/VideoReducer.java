package codePackage;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class VideoReducer  extends Reducer <Text, VideoWritables, Text, Text> {
	
	private String cat_name = "";
	private static DecimalFormat df2 = new DecimalFormat(".##");

	Map<String, String> map = new HashMap<String, String>(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		put("1","Film & Animation");
		put("2","Autos & Vehicles");
		put("10","Music");
		put("15","Pets & Animals");
		put("17","Sports");
		put("19","Travel & Events");
		put("20","Gaming");
		put("22","People & Blogs");
		put("23","Comedy");
		put("24","Entertainment");
		put("25","News & Politics");
		put("26","Howto & Style");
		put("27","Education");
		put("28","Science & Technology");
		put("29","Nonprofits & Activism");
		put("30","Movies");
		put("43","Shows");
		put("44","Trailers");
	}};
	
	
	@Override
	protected void reduce(Text key, Iterable<VideoWritables> value, Context c) throws IOException, InterruptedException {
	
		double sum = 0;
		int count = 0;
		double avg;
		VideoWritables data = null;
		
		Iterator<VideoWritables> valuesIter = value.iterator();
		
		while(valuesIter.hasNext()){
			count++;
			data = valuesIter.next();
			sum = sum + data.getsize();
		}
		
		avg = sum/count;
		
		if(map.containsKey(key.toString())){
			cat_name = map.get(key.toString());
			c.write(new Text(cat_name), new Text(df2.format(avg)));
		}

	}
	
}
