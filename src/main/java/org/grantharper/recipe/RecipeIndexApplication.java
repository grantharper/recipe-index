package org.grantharper.recipe;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RecipeIndexApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecipeIndexApplication.class, args);
	}

	@Bean
	RestHighLevelClient restHighLevelClient(){
		return new RestHighLevelClient(
						RestClient.builder(new HttpHost("localhost", 9200, "http"),
										new HttpHost("localhost", 9201, "http")));
	}
}
