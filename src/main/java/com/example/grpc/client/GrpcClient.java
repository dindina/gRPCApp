package com.example.grpc.client;


import com.example.grpc.greeting.GreeterGrpc;

import com.example.grpc.greeting.GreetingProto;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.concurrent.TimeUnit;

public class GrpcClient {

    private final ManagedChannel channel;
    private final GreeterGrpc.GreeterBlockingStub blockingStub;

    public GrpcClient(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext() // Insecure connection, for testing only
                .build();
        blockingStub = GreeterGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void greet(String name) {
        GreetingProto.HelloRequest request = GreetingProto.HelloRequest.newBuilder().setName(name).build();
        GreetingProto.HelloReply response = blockingStub.sayHello(request);
        System.out.println("Greeting: " + response.getMessage());
    }

    public static void main(String[] args) throws Exception {
        GrpcClient client = new GrpcClient("localhost", 50051);
        try {
            client.greet("World");
        } finally {
            client.shutdown();
        }
    }
}