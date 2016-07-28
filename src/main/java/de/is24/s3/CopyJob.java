package de.is24.s3;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.util.List;

import static com.amazonaws.services.s3.model.ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION;
import static java.util.stream.Collectors.toList;

public class CopyJob {
    private static final String DEFAULT_SOURCE_BUCKET = "copy-test-source-west-kjaeaklh";
    private static final String DEFAULT_TARGET_BUCKET = "copy-test-target-central-djkfh";

    private final AmazonS3Client s3Client;
    private final ObjectMetadata objectMetadata;
    private final String sourceBucket, targetBucket;

    private CopyJob(final String sourceBucket, final String targetBucket) {
        s3Client = new AmazonS3Client(new DefaultAWSCredentialsProviderChain());
        this.sourceBucket = sourceBucket;
        this.targetBucket = targetBucket;

        objectMetadata = new ObjectMetadata();
        objectMetadata.setSSEAlgorithm(AES_256_SERVER_SIDE_ENCRYPTION);
    }

    public static CopyJob initJob() {
        return new CopyJob(DEFAULT_SOURCE_BUCKET, DEFAULT_TARGET_BUCKET);
    }

    public static CopyJob initJob(final String sourceBucket, final String targetBucket) {
        return new CopyJob(sourceBucket, targetBucket);
    }

    public List<String> findFilenames() {
        final List<S3ObjectSummary> objectSummaries = s3Client.listObjects(sourceBucket).getObjectSummaries();
        return objectSummaries.stream().map(S3ObjectSummary::getKey).collect(toList());
    }

    public void copyFile(final String filename) {
        final CopyObjectRequest copyObjectRequest =
                new CopyObjectRequest(sourceBucket, filename, targetBucket, filename)
                        .withNewObjectMetadata(objectMetadata);
        try {
            s3Client.copyObject(copyObjectRequest);
            System.out.println("copied " + filename);
        } catch (AmazonS3Exception s3Exception) {
            System.err.println("failed to copy " + filename);
        }
    }
}
