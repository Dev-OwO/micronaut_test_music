package micronaut_test_music;

import io.micronaut.context.annotation.ConfigurationProperties;

@ConfigurationProperties("micronaut.router.static-resources.music")
public record MusicConfig (String paths, String mapping) {

	public String getMusicPath() {
		return paths.replace("file:", "");
	}
	
	public String getMusicUrl() {
		return mapping.replace("**", "");
	}
}
