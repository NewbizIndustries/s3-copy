cross-region S3-copy files w/ filename containing emojies
=========================================================

Copying files with filenames containing emojies using the AWS SDK for Java works if both S3 buckets are in the same region. 

Cross region, some files cannot be copied. S3 fails with ```Couldn't parse the specified URI.```

# How to reproduce the issue

* create two S3 buckets (sourceBucket, targetBucket) in two different regions
* copy the files in ```files``` to sourceBucket
* build the test app
```
git clone https://github.com/NewbizIndustries/s3-copy.git
cd s3-copy
mvn install
```
* run the app
```
java -jar target/s3-copy-1.0-SNAPSHOT-jar-with-dependencies.jar <sourceBucket> <targetBucket>
```