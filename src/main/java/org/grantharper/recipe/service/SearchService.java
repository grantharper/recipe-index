package org.grantharper.recipe.service;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.grantharper.recipe.domain.RecipeSearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class SearchService
{
  private static final Logger logger = LoggerFactory.getLogger(SearchService.class);
  private static final String RECIPE_INDEX_NAME = "recipe";
  private static final String DOC_TYPE = "doc";
  public static final String INGREDIENTS_FIELD_NAME = "ingredientsList";
  public static final String TITLE_FIELD_NAME = "title";
  public static final String BOOK_FIELD_NAME = "book";
  public static final String PAGE_ID_FIELD_NAME = "pageId";

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
      Map<String, HighlightField> highlightFieldMap = searchHit.getHighlightFields();
      RecipeSearchResult recipePage = convertResultMaptoRecipeSearchResult(sourceAsMap, highlightFieldMap);
      locatedRecipes.add(recipePage);
    }

    return locatedRecipes;
  }

  RecipeSearchResult convertResultMaptoRecipeSearchResult(Map<String, Object> sourceAsMap, Map<String, HighlightField> highlightFieldMap)
  {
    RecipeSearchResult recipeSearchResult = convertResultMaptoRecipeSearchResult(sourceAsMap);
    recipeSearchResult = getHighlightedIngredients(recipeSearchResult, highlightFieldMap);
    return recipeSearchResult;
  }

  RecipeSearchResult convertResultMaptoRecipeSearchResult(Map<String, Object> sourceAsMap)
  {
    String pageId = (String) sourceAsMap.get(PAGE_ID_FIELD_NAME);
    String book = (String) sourceAsMap.get(BOOK_FIELD_NAME);
    String title = (String) sourceAsMap.get(TITLE_FIELD_NAME);
    String result = "title: " + title + ", location: " + book + "-" + pageId;
    logger.debug(result);
    RecipeSearchResult recipeSearchResult = new RecipeSearchResult();
    recipeSearchResult.setBook(book);
    recipeSearchResult.setTitle(title);
    recipeSearchResult.setPageNumber(pageId);
    return recipeSearchResult;
  }

  RecipeSearchResult getHighlightedIngredients(RecipeSearchResult recipeSearchResult, Map<String, HighlightField> highlightFieldMap)
  {
    HighlightField highlight = highlightFieldMap.get(INGREDIENTS_FIELD_NAME);
    Text[] fragments = highlight.fragments();
    highlight.getFragments();
    List<String> highlightedIngredients = new ArrayList<>();
    for (Text fragment : fragments) {
      String[] fragmentString = fragment.string().split("\n");
      highlightedIngredients.addAll(Arrays.asList(fragmentString));
      logger.debug("highlight fragment=" + fragmentString);
    }
    recipeSearchResult.setIngredients(highlightedIngredients);
    return recipeSearchResult;
  }

  SearchHits searchRecipeIndexByIngredients(String ingredientSearch)
  {
    SearchRequest searchRequest = new SearchRequest(RECIPE_INDEX_NAME);
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    searchSourceBuilder = addHighlightingToResults(searchSourceBuilder);
    searchSourceBuilder.query(createIngredientQuery(ingredientSearch));
    searchRequest.source(searchSourceBuilder);
    SearchResponse searchResponse = performSearch(searchRequest);
    return searchResponse.getHits();
  }

  private BoolQueryBuilder getBoolQueryBuilderMatchStyle(String ingredientSearch, BoolQueryBuilder boolQueryBuilder)
  {
    return boolQueryBuilder.must(
            new MatchQueryBuilder(INGREDIENTS_FIELD_NAME, ingredientSearch)
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
      boolQueryBuilder = boolQueryBuilder.must(QueryBuilders.termQuery(INGREDIENTS_FIELD_NAME, ingredientTerm));
    }
    return boolQueryBuilder;
  }

  SearchSourceBuilder addHighlightingToResults(SearchSourceBuilder searchSourceBuilder)
  {
    HighlightBuilder highlightBuilder = new HighlightBuilder();
    HighlightBuilder.Field highlightIngredients = new HighlightBuilder.Field(INGREDIENTS_FIELD_NAME);
    highlightIngredients.highlighterType("unified");
    highlightIngredients.numOfFragments(0);

    highlightIngredients.preTags("<span class=\"highlight\">");
    highlightIngredients.postTags("</span>");
    highlightBuilder.field(highlightIngredients);
    searchSourceBuilder.highlighter(highlightBuilder);
    return searchSourceBuilder;
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
