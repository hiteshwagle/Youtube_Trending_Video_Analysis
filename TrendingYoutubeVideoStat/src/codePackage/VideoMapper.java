package codePackage;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class VideoMapper extends Mapper<LongWritable, Text, Text, VideoWritables> {

	private Text cat_id = new Text();    //  GGYZ333519YS
	
    private VideoWritables data = new VideoWritables();    // will create object of writable class and initialize it

    protected void map(LongWritable key, Text value, Context c)	throws IOException, java.lang.InterruptedException
    {
    	
	String line = value.toString();    //video_id, trending_date, category_id, category, country, views, likes, dislikes
	
	if(!line.contains("video_id")){
		String[] words = line.split(",");  // [{video_id} {trending_date} {category_id} {category} {country} {views} {likes} {dislikes]
			cat_id.set(words[2].trim());             // category_id
			
			data.set(words[0].trim(), words[11].trim());  // video_id, country
			
			words = null;
			
			//if(!words[0].equals("video_id"))
			c.write(cat_id, data);
		}
    }
}
