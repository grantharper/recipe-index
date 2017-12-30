package org.grantharper.recipe.bootstrap;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.grantharper.recipe.model.Book;
import org.grantharper.recipe.model.Ingredient;
import org.grantharper.recipe.model.Recipe;
import org.grantharper.recipe.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DatabaseLoader implements CommandLineRunner
{

    @Autowired
    private BookRepository bookRepo;

    @Override
    public void run(String... args) throws Exception
    {
        Ingredient i1 = new Ingredient();
        i1.setName("cheese");
        Ingredient i2 = new Ingredient();
        i2.setName("flour");
        Ingredient i3 = new Ingredient();
        i3.setName("tomatoes");

        Book slt = new Book();
        slt.setAuthor("various");
        slt.setTitle("Sur La Table");

        Recipe r1 = new Recipe();
        r1.setTitle("pizza");
        r1.setPageNumber(3);
        Recipe r2 = new Recipe();
        r2.setTitle("mac and cheese");
        r2.setPageNumber(5);

        i1.setRecipes(Stream.of(r1, r2).collect(Collectors.toSet()));
        i2.setRecipes(Stream.of(r1, r2).collect(Collectors.toSet()));
        i3.setRecipes(Stream.of(r1).collect(Collectors.toSet()));
        
        r1.setIngredients(Stream.of(i1, i2, i3).collect(Collectors.toSet()));
        r2.setIngredients(Stream.of(i1, i2).collect(Collectors.toSet()));
        
        slt.setRecipes(Stream.of(r1, r2).collect(Collectors.toSet()));
        r1.setBook(slt);
        r2.setBook(slt);

        bookRepo.save(slt);

    }

}
