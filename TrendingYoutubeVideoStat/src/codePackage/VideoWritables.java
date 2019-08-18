package codePackage;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableUtils;

public class VideoWritables implements Writable {
	
	private String videoId;
	private String countryName;
	private int size;
	
	//constructor
	public VideoWritables()
	{
		set("","");
	}
	
	public VideoWritables(String vidId, int size)
	{
		setsize(vidId, size);
	}

	private void setsize(String video_id, int size) {
		this.videoId = 	video_id;
		this.size = size;
	}

	void set(String video_id, String country) {
		this.videoId = video_id;
		this.countryName = country;
	}
	
	public int getsize(){
		return this.size;
	}
	
	public String getVideoId(){
		return this.videoId;
	}
	
	public String getCountryName(){
		return this.countryName;
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		this.videoId = WritableUtils.readString(in);
		this.countryName = WritableUtils.readString(in);
		this.size = WritableUtils.readVInt(in);
	}

	@Override
	public void write(DataOutput out) throws IOException {
		WritableUtils.writeString(out, this.videoId);
		WritableUtils.writeString(out, this.countryName);
		WritableUtils.writeVInt(out, this.size);
	}
}
