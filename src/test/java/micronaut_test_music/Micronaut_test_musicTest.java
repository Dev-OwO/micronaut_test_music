package micronaut_test_music;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import jakarta.inject.Inject;

@MicronautTest
class Micronaut_test_musicTest {

    @Inject
    EmbeddedApplication<?> application;
    
    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void testItWorks() {
        Assertions.assertTrue(application.isRunning());
    }

    @Test
    void testHelloWorldResponse() {
    	HttpResponse<String> response = client.toBlocking()
                .exchange(HttpRequest.GET("/playlist"), String.class);
    	Assertions.assertEquals(200, response.status().getCode());
    }

}
