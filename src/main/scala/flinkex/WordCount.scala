package flinkex

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.scala._

object WordCount {

  val SOCKET_PORT = 9292

  // Flink provides two types of shuffling operations.
  //
  // 1. Within Tuple-based structure.
  // {{
  //   stream.keyBy(0).timeWindow(Time.seconds(1)).sum(1)
  // }}
  //
  // 2. Field matching, via reflection.
  // {{
  //   stream.keyBy("word").timeWindow(Time.seconds(1)).sum("count")
  // }}
  //
  // For comparison, the Spark style.
  // i.e.
  // {{
  //   stream.map((_, 1)).timeWindow(Time.seconds(1)).reduceByKey(_ + _)
  // }}
  //
  case class WordWithCount(word: String, count: Int)

  def main(args: Array[String]): Unit = {
    // Retrieve execution env
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    // Delimiter with newline and retry for 3 times.
    val text = env.socketTextStream("localhost", SOCKET_PORT, '\n', 3)

    val wordCount = text
      .flatMap(line => line.split(" "))
      .map(w => (w, 1))
      .keyBy(0) // Partition, and then parallelized window
      .timeWindow(Time.seconds(5), Time.seconds(1)) // Sliding window, with size 5 and 1 sec slide.
      .sum(1)

    wordCount.print().setParallelism(1)
    env.execute("Socket Window WordCount")
  }

}
