
Create new directory for the project

$ hadoop fs -mkdir /user/cloudera/sparkweblog

Upload sample data to the directory

$ hadoop fs -put access_log /user/cloudera/sparkweblog

Launch Jar file by submitting it to spark

$ spark-submit --class edu.mum.UriHitCount \
--master local --deploy-mode client --executor-memory 1g \
--name UriHitCount --conf "spark.app.id=UriHitCount" \
sparkweblogminer-0.0.1-SNAPSHOT.jar \
hdfs://localhost:8020/user/cloudera/sparkweblog/access_log \
hdfs://localhost:8020/user/cloudera/sparkweblog/urihitcount.out
