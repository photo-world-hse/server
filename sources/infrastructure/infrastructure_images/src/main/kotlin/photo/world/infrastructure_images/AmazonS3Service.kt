package photo.world.infrastructure_images

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.DeleteObjectRequest
import com.amazonaws.services.s3.model.PutObjectRequest
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import photo.world.domain.images.ImageService
import java.io.File
import java.util.*

@Service
class AmazonS3Service : ImageService {

    private lateinit var s3client: AmazonS3

    @Value("\${aws.endpointUrl}")
    private lateinit var endpointUrl: String

    @Value("\${aws.bucketName}")
    private lateinit var bucketName: String

    @Value("\${aws.accessKey}")
    private lateinit var accessKey: String

    @Value("\${aws.secretKey}")
    private lateinit var secretKey: String

    @Value("\${aws.region}")
    private lateinit var region: String

    override fun uploadImage(file: File, email: String): String {
        val fileName = generateFileName(file, email)
        s3client.putObject(
            PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead)
        )
        file.delete()
        return "$endpointUrl/$bucketName/$fileName"
    }

    override fun deleteImage(fileName: String) {
        s3client.deleteObject(DeleteObjectRequest(bucketName, fileName))
    }

    @PostConstruct
    private fun initializeAmazon() {
        val credentials = BasicAWSCredentials(this.accessKey, this.secretKey)
        this.s3client = AmazonS3ClientBuilder.standard()
            .withCredentials(AWSStaticCredentialsProvider(credentials))
            .withEndpointConfiguration(
                AwsClientBuilder.EndpointConfiguration(endpointUrl, region)
            )
            .build()
    }

    private fun generateFileName(file: File, email: String): String {
        return "${email}-${Date().time}-${file.name.replace(" ", "_")}"
    }
}