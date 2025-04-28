package com.example.grpc.greeting;

import io.grpc.stub.StreamObserver;

public class GreeterServiceImpl extends GreeterGrpc.GreeterImplBase {

    @Override
    public void sayHello(GreetingProto.HelloRequest request, StreamObserver<GreetingProto.HelloReply> responseObserver) {
        String name = request.getName();
        String message = "Hello, " + name + "!";
        GreetingProto.HelloReply reply = GreetingProto.HelloReply.newBuilder().setMessage(message).build();

        // Send the response
        responseObserver.onNext(reply);

        // Mark the call as complete
        responseObserver.onCompleted();
    }
}