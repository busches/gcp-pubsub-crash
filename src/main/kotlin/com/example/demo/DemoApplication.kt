package com.example.demo

import io.grpc.ManagedChannelBuilder
import java.util.logging.Level
import java.util.logging.Logger


fun main() {
    val root: Logger = Logger.getLogger("")
    root.setLevel(Level.ALL)
    for (handler in root.getHandlers()) {
        handler.setLevel(Level.ALL)
    }

    PubSubPublisher()
}

class PubSubPublisher {
    init {
        // This is the only line we actually need for it go to boom
        ManagedChannelBuilder.forTarget("localhost:7000").usePlaintext().build()
    }
}
