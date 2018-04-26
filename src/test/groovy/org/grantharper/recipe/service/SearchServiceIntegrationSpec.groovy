package org.grantharper.recipe.service

import org.apache.http.HttpHost
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.search.SearchHits
import org.grantharper.recipe.domain.RecipeSearchResult
import spock.lang.Specification

class SearchServiceIntegrationSpec extends Specification
{

  SearchService searchService;

  def setup()
  {
    searchService = new SearchService(new RestHighLevelClient(
            RestClient.builder(new HttpHost("localhost", 9200, "http"),
                    new HttpHost("localhost", 9201, "http"))))
  }

  def cleanup()
  {
    searchService.getRestHighLevelClient().close()
  }

  def "search the elasticsearch index for ingredients"() {
    when: "index is searched"
    SearchHits searchHits = searchService.searchRecipeIndexByIngredients("asparagus")

    then: "results are located"
    searchHits.hits.length > 0
  }

  def "pull back entire recipe based on id"()
  {
    when: "id is sent to index"
    String id = "SurLaTable201"
    RecipeSearchResult recipeSearchResult = searchService.getRecipeById(id)


    then: "entire recipes is returned"
    recipeSearchResult.getPageNumber() == "201"

  }
}
