package com.example.produktapi.repository;

import com.example.produktapi.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*; // gör att man inte behöver skriva Assertions framför varje assert

// @DataJpaTest används när vi vill enhetstesta vårt repository
@DataJpaTest
class ProductRepositoryTest {

    // @Autowired används när vi vill enhetstesta vårt ProductRepository
    @Autowired
    private ProductRepository underTest;

    @Test
    void simpleTest() {
        List<Product> products = underTest.findAll();
        assertFalse(products.isEmpty());
    }

    @Test
    void whenSearchingForExistingTitle_thenReturnThatProduct() {
        // given
        String title = "En dator";
        Product product = new Product(
                title,
                25000.0,
                "Elektronik",
                "bra o ha",
                "urlTillBild");
        underTest.save(product);

        // when
        Optional<Product> optionalProduct = underTest.findByTitle("En dator");

        // then

        // ett sätt att skriva 3 tester
        // assertTrue(optionalProduct.isPresent());
        //assertFalse(optionalProduct.isEmpty());
        //assertEquals(title, optionalProduct.get().getTitle());

        // // ett annat sätt att skriva 3 tester
        Assertions.assertAll(
                () -> assertTrue(optionalProduct.isPresent()),
                () -> assertFalse(optionalProduct.isEmpty()),
                () -> assertEquals(title, optionalProduct.get().getTitle())
        );
    }

    @Test
    void whenSearchingForNonExistingTitle_thenReturnEmptyOptional() {
        // given
        String title = "En title som absolut inte finns";

        // when
        Optional<Product> optionalProduct = underTest.findByTitle(title);

        // then
        assertAll(
                () -> assertFalse(optionalProduct.isPresent()),
                () -> assertTrue(optionalProduct.isEmpty()),
                () -> assertThrows(NoSuchElementException.class, () -> optionalProduct.get())
        );
    }

    @Test
    void whenSearchingForExistingCategory_thenReturnListOfProducts() {
        // given
        String category = "Elektronik";

        Product product1 = new Product(
                "En dator",
                25000.0,
                category,
                "bra o ha",
                "urlTillBild");

        Product product2 = new Product(
                "En mobil",
                12000.0,
                category,
                "bra o ha",
                "urlTillBild");
        underTest.saveAll(List.of(product1, product2));

        // when
        List<Product> products = underTest.findByCategory(category);

        // then
        assertAll(
                //() -> assertFalse(products.),
                () -> assertFalse(products.isEmpty()), // kollar om listan är tom
                () -> assertEquals(2, products.size()), // kollar hur många object listan innehåller
                () -> assertTrue(products.containsAll(List.of(product1, product2))) // kollar om alla produkter finns i listan
        );
    }

    @Test
    void whenSearchingForNonExistingCategory_thenReturnEmptyList() {
        // given
        String category = "En kategori som absolut inte finns";

        // when
        List<Product> products = underTest.findByCategory(category);

        // then
        assertAll(
                () -> assertTrue(products.isEmpty()), // om listan är tom returnera sant
                () -> assertEquals(0, products.size()), // förväntad 0 object i listan, returnera antalet i listan
                () -> assertNotNull(products),
                () -> assertNotEquals(null, products),
                () -> assertFalse(products.stream().findFirst().isPresent())
        );
    }

    @Test
    void whenFindingAllCategories_thenReturnListOfCategories() {

        // when
        List<String> categories = underTest.findAllCategories();

        // then
        assertAll(
                () -> assertEquals(4, categories.size()),
                () -> assertTrue(categories.containsAll(List.of(
                        "electronics",
                        "jewelery",
                        "men's clothing",
                        "women's clothing"
                )))
        );
    }

    @Test
    void whenNoCategoriesExist_thenReturnEmptyList() {
        // given
        underTest.deleteAll();

        // when
        List<String> categories = underTest.findAllCategories();

        // then
        assertTrue(categories.isEmpty());
    }


    /*
    @Test
    void whenFindingAllCategories_thenReturnListOfCategories() {
        // given

        String category1 = "Elektronik";
        String category2 = "Smycken";
        String category3 = "Kläder";
        String category4 = "Smink";



        Product product1 = new Product(
                "En dator",
                25000.0,
                category1,
                "bra o ha",
                "urlTillBild");

        Product product2 = new Product(
                "Ett halsband",
                4000.0,
                category2,
                "fint o ha",
                "urlTillBild");

        Product product3 = new Product(
                "En T-shirt",
                199.0,
                category3,
                "bra o ha",
                "urlTillBild");

        Product product4 = new Product(
                "Ett läppstift",
                75.0,
                category4,
                "fint o ha",
                "urlTillBild");

        underTest.saveAll(List.of(product1, product2, product3, product4));

        // when
        List<String> categories = underTest.findAllCategories();

        // then
        assertAll(
                () -> assertEquals(4, categories.size())
                //() -> assertTrue(categories.containsAll(List.of("Elektronik", "Smycken", "Kläder", "Smink")))
        );
    }
    */

    /*



    /*
    @Test
    void findByCategory() {

    }

     */

    /*
    klar
    @Test
    void findByTitle() {
    }

     */

    @Test
    void findAllCategories() {
    }
}