package org.grantharper.recipe.service;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
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
  private static final String DOC_TYPE = "doc";

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

  public RecipeSearchResult getRecipeById(String id)
  {
    logger.info("querying index for id=" + id);

    GetRequest getRequest = new GetRequest(RECIPE_INDEX_NAME, DOC_TYPE, id);
    RecipeSearchResult recipeSearchResult = new RecipeSearchResult();
    try {
      GetResponse getResponse = restHighLevelClient.get(getRequest);
      if (getResponse.isExists()) {
        Map<String, Object> recipeMap = getResponse.getSourceAsMap();
        recipeSearchResult = convertResultMaptoRecipeSearchResult(recipeMap);
      }
    } catch (IOException e) {
      logger.error("error retrieving recipe with id=" + id, e);
    }
    return recipeSearchResult;
  }

  public List<RecipeSearchResult> searchElasticsearchForIngredients(String ingredientSearch)
  {
    logger.info("performing search: " + ingredientSearch);

    SearchHits searchHits = searchRecipeIndexByIngredients(ingredientSearch);
    List<RecipeSearchResult> locatedRecipes = new ArrayList<>();

    for (SearchHit searchHit : searchHits.getHits()) {
      Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
      RecipeSearchResult recipePage = convertResultMaptoRecipeSearchResult(sourceAsMap);
      locatedRecipes.add(recipePage);
    }

    return locatedRecipes;
  }

  RecipeSearchResult convertResultMaptoRecipeSearchResult(Map<String, Object> sourceAsMap)
  {
    String pageId = (String) sourceAsMap.get("pageId");
    String book = (String) sourceAsMap.get("book");
    String title = (String) sourceAsMap.get("title");
    List<String> ingredients = (List<String>) sourceAsMap.get("ingredients");
    String result = "title: " + title + ", location: " + book + "-" + pageId;
    logger.debug(result);
    RecipeSearchResult recipeSearchResult = new RecipeSearchResult();
    recipeSearchResult.setBook(book);
    recipeSearchResult.setTitle(title);
    recipeSearchResult.setPageNumber(pageId);
    recipeSearchResult.setIngredients(ingredients);
    return recipeSearchResult;
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
    boolQueryBuilder = getBoolQueryBuilderMatchStyle(ingredientSearch, boolQueryBuilder);
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
