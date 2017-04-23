package edu.mum

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import scala.collection.immutable.ListMap
import java.io._
 
object UriHitCount  {

  def main(args: Array[String]) {
      
        val inputfile = args(0)
        val outputfile = args(1)
        
        val sc = new SparkContext(new SparkConf().setAppName("Spark Weblog Stats"))

        val p = new WebLogParser
        val log = sc.textFile(inputfile)
        
        val uris = log.map(line => p.parseRecordReturningNullObjectOnFailure(line).request)
               .filter(request => request != "")
               .map(request => request.split(" ")(1))   // a request looks like "GET /foo HTTP/1.1"
           
  
        for {
              line <- log
              if p.parseRecord(line) == None
            } yield line


        // works: use the previous example to get to a series of "(URI, COUNT)" pairs; (MapReduce like)
        val uriCount = log.map(p.parseRecordReturningNullObjectOnFailure(_).request)
                   .filter(request => request != "")  // filter out records that wouldn't parse properly
                   .map(_.split(" ")(1))              // get the uri field
                   .map(uri => (uri, 1))              // create a tuple for each record
                   .reduceByKey((a, b) => a + b)      // reduce to get this for each record: (/java/java_oo/up.png,2)
                   .collect                           // convert to Array[(String, Int)], which is Array[(URI, numOccurrences)]
      
         
        val uriHitCount = ListMap(uriCount.toSeq.sortWith(_._2 > _._2):_*)    // (/bar, 10), (/foo, 3), (/baz, 1)
      
        // print the top-20 most-hit URIs
        uriHitCount.take(20).foreach(println)
        
        val formatter = java.text.NumberFormat.getIntegerInstance  
        uriHitCount.take(50).foreach { pair =>
          val uri = pair._1
          val count = pair._2
          println(s"${formatter.format(count)} => $uri")
        }
            
           
        val file = new File(outputfile)
        val bw = new BufferedWriter(new FileWriter(file))
        
        for {
          record <- uriHitCount
          val uri = record._1
          val count = record._2
        } 
        
        bw.write(s"$count => $uri\n")
        bw.close
    }
    
}





