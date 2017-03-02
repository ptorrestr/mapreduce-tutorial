package tutorial.mr.task3.streaming;

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import com.google.common.collect.Lists;

import scala.Tuple2;

public class Stream {

	public static void main(String[] args) throws InterruptedException {
		SparkConf conf = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCount");
		try( JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(5)) ){
			// Open a streaming connection on port 9999
			JavaReceiverInputDStream<String> lines = jssc.socketTextStream("localhost", 9999);
			
			// Pipeline for any data on port 9999
			// Tokenization
			JavaDStream<String> words = lines.flatMap( p -> Lists.newArrayList(p.split("\\s")).iterator());
			// Transform words into pairs (word, counter)
			JavaPairDStream<String, Integer> pairs = words.mapToPair( p -> new Tuple2<>( p,1));
			// Counting words
			JavaPairDStream<String, Integer> wordCounts = pairs.reduceByKey( (p, q) -> p + q );
			
			// Print top 10 words
			wordCounts.print();
			
			// Once the pipeline is defined, we start it
			jssc.start();
			jssc.awaitTermination();
		}
	}
}
