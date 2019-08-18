package codePackage;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class ProgramDriver {

    public static void main(String[] args) throws IOException,  ClassNotFoundException,	 InterruptedException
    {
    Configuration conf = new Configuration();
	String [] files = new GenericOptionsParser(conf,args).getRemainingArgs();
	
	if(files.length !=2 ){
		System.err.println("Usage: VideoCount<in><out>");
		System.exit(2);
	}

	Job j = new Job(conf, "videoanalysis");
	//j.setNumReduceTasks(2);
	j.setJarByClass(ProgramDriver.class);
	
	j.setMapperClass(VideoMapper.class);
	j.setReducerClass(VideoReducer.class);
	j.setMapOutputKeyClass(Text.class);
	j.setMapOutputValueClass(VideoWritables.class);
	j.setOutputKeyClass(Text.class);
	j.setOutputValueClass(Text.class);
	j.setCombinerClass(VideoCombiner.class);
	FileInputFormat.addInputPath(j, new Path(files[0]));
	FileOutputFormat.setOutputPath(j, new Path(files[1]));
	System.exit(j.waitForCompletion(true)?0:1);
    }
	
}
