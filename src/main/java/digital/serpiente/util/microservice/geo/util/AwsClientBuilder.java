package digital.serpiente.util.microservice.geo.util;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.location.LocationClient;
import software.amazon.awssdk.services.location.LocationClientBuilder;

public interface AwsClientBuilder {
    public static LocationClient createAmzRouteS3Client(String accessKeyId, String secretKeyId) {
        StaticCredentialsProvider awsCredentials = StaticCredentialsProvider
                .create(AwsBasicCredentials.create(accessKeyId, secretKeyId));
        Region ohioAwsRegion = Region.US_EAST_2;
        LocationClientBuilder locationClientBuilder = LocationClient.builder().credentialsProvider(awsCredentials)
                .region(ohioAwsRegion);
        return locationClientBuilder.build();
    }
}