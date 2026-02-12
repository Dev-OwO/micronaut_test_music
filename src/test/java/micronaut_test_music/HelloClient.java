package micronaut_test_music;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import org.reactivestreams.Publisher;
import io.micronaut.core.async.annotation.SingleResult;

@Client("/hello")
public interface HelloClient {

    @Get(consumes = MediaType.TEXT_PLAIN)
    @SingleResult
    Publisher<String> hello();
}
