package org.grantharper.recipe.service;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.grantharper.recipe.domain.RecipeSearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SearchService
{
  private static final Logger logger = LoggerFactory.getLogger(SearchService.class);
  private static final String RECIPE_INDEX_NAME = "recipe";

  private RestHighLevelClient restHighLevelClient;

  RestHighLevelClient getRestHighLevelClient()
  {
    return restHighLevelClient;
  }

  @Autowired
  public SearchService(RestHighLevelClient restHighLevelClient)
  {
    this.restHighLevelClient = restHighLevelClient;
  }

  public List<RecipeSearchResult> searchElasticsearchForIngredients(String ingredientSearch)
  {
    logger.info("performing search: " + ingredientSearch);

    SearchHits searchHits = searchRecipeIndexByIngredients(ingredientSearch);
    List<RecipeSearchResult> locatedRecipes = new ArrayList<>();

    for (SearchHit searchHit : searchHits.getHits()) {
      Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
      String pageId = (String) sourceAsMap.get("pageId");
      String book = (String) sourceAsMap.get("book");
      String title = (String) sourceAsMap.get("title");
      List<String> ingredients = (List<String>) sourceAsMap.get("ingredients");
      String result = "title: " + title + ", location: " + book + "-" + pageId;
      logger.debug(result);
      RecipeSearchResult recipePage = new RecipeSearchResult();
      recipePage.setBook(book);
      recipePage.setTitle(title);
      recipePage.setPageNumber(pageId);
      recipePage.setIngredients(ingredients);
      locatedRecipes.add(recipePage);
    }

    return locatedRecipes;
  }

  String displayIngredients(List<String> ingredientArray)
  {
    return ingredientArray.stream().collect(Collectors.joining("<br/>"));
  }

  SearchHits searchRecipeIndexByIngredients(String ingredientSearch)
  {
    SearchRequest searchRequest = new SearchRequest(RECIPE_INDEX_NAME);
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    searchSourceBuilder.query(createIngredientQuery(ingredientSearch));
    searchRequest.source(searchSourceBuilder);
    SearchResponse searchResponse = performSearch(searchRequest);
    return searchResponse.getHits();
  }

  private BoolQueryBuilder getBoolQueryBuilderMatchStyle(String ingredientSearch, BoolQueryBuilder boolQueryBuilder)
  {
    return boolQueryBuilder.must(
            new MatchQueryBuilder("ingredients", ingredientSearch)
                    .operator(Operator.AND).fuzziness(Fuzziness.AUTO));
  }

  BoolQueryBuilder createIngredientQuery(String ingredientSearch)
  {
    List<TermQueryBuilder> booleanTerms = new ArrayList<>();
    BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
    //boolQueryBuilder = getBoolQueryBuilderTermStyle(ingredientSearch, boolQueryBuilder);
    boolQueryBuilder = getBoolQueryBuilderMatchStyle(ingredientSearch, boolQueryBuilder);
    boolQueryBuilder = boolQueryBuilder.must(QueryBuilders.termQuery("book", "Sur La Table"));
    return boolQueryBuilder;
  }

  private BoolQueryBuilder getBoolQueryBuilderTermStyle(String ingredientSearch, BoolQueryBuilder boolQueryBuilder)
  {
    String[] ingredientTerms = ingredientSearch.split(" ");
    for (String ingredientTerm : ingredientTerms) {
      boolQueryBuilder = boolQueryBuilder.must(QueryBuilders.termQuery("ingredients", ingredientTerm));
    }
    return boolQueryBuilder;
  }


  SearchResponse performSearch(SearchRequest searchRequest)
  {
    SearchResponse response;
    try {
      response = restHighLevelClient.search(searchRequest);
      return response;
    } catch (IOException e) {
      logger.error("searching failed", e);
      throw new RuntimeException();
    }

  }
}
