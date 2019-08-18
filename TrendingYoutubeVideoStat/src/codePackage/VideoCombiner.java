package codePackage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class VideoCombiner  extends	Reducer<Text, VideoWritables, Text, VideoWritables> {

	 private ArrayList<String> temp;
	 private VideoWritables viddata;

	protected void reduce(Text key, Iterable<VideoWritables> values, Context c) throws IOException, java.lang.InterruptedException
	    { 
		 //used to store videos in different countries
		Map<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>(); 
					
		VideoWritables data = null;
		
		Iterator<VideoWritables> valuesIter = values.iterator();
		
		while(valuesIter.hasNext()){
			data = valuesIter.next();
			if(map.containsKey(data.getVideoId().toString())){
				temp = new ArrayList<String>( map.get(data.getVideoId().toString()));
				if(!temp.contains(data.getCountryName().toString())){
					temp.add( data.getCountryName().toString());
					map.put(data.getVideoId().toString(), temp );	
				}
			} else{
				temp = new ArrayList<String>();
				temp.add( data.getCountryName().toString());
				map.put(data.getVideoId().toString(), temp );
			}
		}
		
		for(Map.Entry<String, ArrayList<String>> entry : map.entrySet()){		
			viddata = new VideoWritables(entry.getKey(), entry.getValue().size());		
			c.write(key, viddata);
		}
	   }
}
