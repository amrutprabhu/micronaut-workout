package com.amrut.prabhu;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import io.micronaut.function.aws.runtime.AbstractMicronautLambdaRuntime;
import java.net.MalformedURLException;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import io.micronaut.core.annotation.Nullable;

public class OrderLambdaRuntime extends AbstractMicronautLambdaRuntime<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent, Order, Order> {

    public static void main(String[] args) {
        try {
            new OrderLambdaRuntime().run(args);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Nullable
    protected RequestHandler<Order, Order> createRequestHandler(String... args) {
        return new OrderRequestHandler();
    }
}