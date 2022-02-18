package com.example.demo

import com.google.api.gax.core.NoCredentialsProvider
import com.google.api.gax.grpc.GrpcTransportChannel
import com.google.api.gax.rpc.FixedTransportChannelProvider
import com.google.cloud.pubsub.v1.Publisher
import com.google.cloud.pubsub.v1.TopicAdminClient
import com.google.cloud.pubsub.v1.TopicAdminSettings
import com.google.pubsub.v1.ProjectTopicName
import io.grpc.ManagedChannelBuilder
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)

	PubSubPublisher()
}

class PubSubPublisher {
	private var internalPublisher: Publisher?

	init {
		val channel = ManagedChannelBuilder.forTarget("localhost:7000").usePlaintext().build()
		val channelProvider = FixedTransportChannelProvider.create(GrpcTransportChannel.create(channel))
		val credentialsProvider = NoCredentialsProvider.create()

		val topicName = ProjectTopicName.of("my-project", "test-topic")

		// GCP docs say to create this, but they don't use the variable for anything, having it changes nothing
		TopicAdminClient.create(
			TopicAdminSettings.newBuilder()
				.setTransportChannelProvider(channelProvider)
				.setCredentialsProvider(credentialsProvider)
				.build()
		)

		internalPublisher = Publisher.newBuilder(topicName)
			.setChannelProvider(channelProvider)
			.setCredentialsProvider(credentialsProvider)
			.build()
	}
}
