package micronaut_test_music;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import reactor.core.publisher.Mono;

@MicronautTest
public class HelloClientSpec {

    @Inject
    HelloClient client;

    @Test
    public void testHelloWorldResponse() {
    	Assertions.assertEquals("Hello World", Mono.from(client.hello()).block());
    }
}
