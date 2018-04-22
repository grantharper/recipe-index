package org.grantharper.recipe.service;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.grantharper.recipe.domain.RecipePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchService
{
  private static final Logger logger = LoggerFactory.getLogger(SearchService.class);
  private static final String RECIPE_INDEX_NAME = "recipe";

  private RestHighLevelClient restHighLevelClient;

  @Autowired
  public SearchService(RestHighLevelClient restHighLevelClient)
  {
    this.restHighLevelClient = restHighLevelClient;
  }

  public List<RecipePage> searchElasticsearchForIngredients(String ingredientSearch)
  {
      logger.info("performing search: " + ingredientSearch);

      SearchHits searchHits = searchRecipeIndexByIngredients(ingredientSearch);
      List<RecipePage> locatedRecipes = new ArrayList<>();

      for(SearchHit searchHit: searchHits.getHits()){
        Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
        String pageId = (String) sourceAsMap.get("pageId");
        String book = (String) sourceAsMap.get("book");
        String title = (String) sourceAsMap.get("title");
        String result = "title: " + title + ", location: " + book + "-" + pageId;
        logger.info(result);
        RecipePage recipePage = new RecipePage();
        recipePage.setBook(book);
        recipePage.setTitle(title);
        recipePage.setPageNumber(pageId);
        //recipePage.setIngredients();
        locatedRecipes.add(recipePage);
      }

      return locatedRecipes;
    }

  SearchHits searchRecipeIndexByIngredients(String ingredientSearch){
    SearchRequest searchRequest = new SearchRequest(RECIPE_INDEX_NAME);
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    searchSourceBuilder.query(createIngredientQuery(ingredientSearch));
    searchRequest.source(searchSourceBuilder);
    SearchResponse searchResponse = performSearch(searchRequest);
    return searchResponse.getHits();
  }

  BoolQueryBuilder createIngredientQuery(String ingredientSearch)
  {
    List<TermQueryBuilder> booleanTerms = new ArrayList<>();
    BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
    String[] ingredientTerms = ingredientSearch.split(" ");
    for(String ingredientTerm: ingredientTerms)
    {
      boolQueryBuilder = boolQueryBuilder.must(QueryBuilders.termQuery("ingredients", ingredientTerm));
    }
    boolQueryBuilder = boolQueryBuilder.must(QueryBuilders.termQuery("book", "Sur La Table"));
    return boolQueryBuilder;
  }


  SearchResponse performSearch(SearchRequest searchRequest) {
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
