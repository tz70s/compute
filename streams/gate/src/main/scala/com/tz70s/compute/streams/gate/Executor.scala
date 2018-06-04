/*
 * Copyright 2018 Tzu-Chiao Yeh.
 * Licensed under MIT license.
 */
package com.tz70s.compute.streams.gate

import org.apache.flink.streaming.api.scala._

object Executor {

  def main(args: Array[String]) = {

    if (args.length != 2) {
      System.err.println("Not enough arguments.")
      System.exit(1)
    }

    val host = args(0)
    val port = args(1).toInt

    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val text = env.socketTextStream(host, port)
    val counts = text
      .flatMap { _.toLowerCase.split("\\\\W+") filter (_.nonEmpty) }
      .map((_, 1))
      .keyBy(0)
      .sum(1)

    // Sink - print to stdout here.
    counts print

    env.execute("Scala socket text stream word count.")
  }
}
