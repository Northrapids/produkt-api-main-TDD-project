package com.example.produktapi.service;


import com.example.produktapi.exception.BadRequestException;
import com.example.produktapi.exception.EntityNotFoundException;
import com.example.produktapi.model.Product;
import com.example.produktapi.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class) // Sätter upp miljön för Mockning
class ProductServiceTest {

    // Annoterar det som skall mockas, en "falsk" ProductRepository
    @Mock
    private ProductRepository repository;

    // Skapar en instans av klassen och injetar det som är annoterat med @Mock (ProductService)
    @InjectMocks
    private ProductService underTest;

    // Captor gör det möjligt att testa om produkten som skickas till save() metoden i productRepository matchar det i testet
    @Captor
    ArgumentCaptor<Product> productCaptor;

    // getAllProducts() -----------------------------------------------------------------------------------

    // Testar om getAllProducts() kallar på findAll() i repository en gång
    @Test
    void whenGetAllProducts_thenExactlyOneInteractionWithRepositoryMethodFindAll() {
        // when
        underTest.getAllProducts();
        // then
        verify(repository, times(1)).findAll();
        verifyNoMoreInteractions(repository);
    }

    // getAllCategories() --------------------------------------------------------------------------------------------

    // Testar att getAllCategories() kallar på findAllCategories() i repository en gång
    @Test
    void whenGetAllCategories_thenExactlyOneInteractionWithRepositoryMethodFindAllCategories() {
        // when
        underTest.getAllCategories();
        // then
        verify(repository, times(1)).findAllCategories();
        verifyNoMoreInteractions(repository);

    }


    // getProductsByCategory() -----------------------------------------------------------------

    // Testar om getProductsByCategory() kallar på findByCategory() i repository me rätt argument
    @Test
    void whenGetProductsByCategoryWithValidCategory_thenFindByCategoryIsCalledWithCorrectArgument() {
        // given
        String category = "electronics";

        // when
        underTest.getProductsByCategory(category);

        // then
        verify(repository, times(1)).findByCategory(category);
        verifyNoMoreInteractions(repository);
    }

    // getProductById() ---------------------------------------------------------------------------------------------

    // Testar om getProductById() hämtar ut en produkt med angivet id
    @Test
    void whenGetProductsByIdWithExistingId_thenReturnProduct() {
        // given
        Integer id = 1;
        Product product = new Product("",1.0,"","","");
        product.setId(id);
        when(repository.findById(id)).thenReturn(Optional.of(product));

        // when
        Product result = underTest.getProductById(id);

        // then
        assertEquals(product, result);
        verify(repository, times(1)).findById(id);
        verifyNoMoreInteractions(repository);
    }

    // Testar om getProductById() kastar ett exception om ett angivet id inte existerar
    @Test
    void whenGetProductsByIdWithNonExistingId_thenThrowException() {
        // given
        Integer id = 1;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // when, then
        assertThrows(EntityNotFoundException.class, () -> underTest.getProductById(id));
        verify(repository, times(1)).findById(id);
        verifyNoMoreInteractions(repository);
    }

    // addProduct() -------------------------------------------------------------------------------------------------

    // Testar om addProduct() kan lägga till en och sparar produkt i repository
    @Test
    void whenAddProduct_thenSaveMethodShouldBeCalled() {
        // given
        Product product = new Product("Dator",4000.0,"","","");

        // when
        underTest.addProduct(product);

        // then
        verify(repository).save(productCaptor.capture());
        assertEquals(product, productCaptor.getValue());
    }

    // Testar om addProduct() kastar en BadRequestException om man försöker lägga till en produkt som redan existerar
    @Test
    void whenAddingProductWithDuplicateTitle_thenThrowError() {
        //given
        String title = "vår test-title";
        Product product = new Product(title,34.0,"","","");
        // simulerar att en produkt redan finns i databasen med mock och returnerar en optional med den titeln
        given(repository.findByTitle(title)).willReturn(Optional.of(product));

        // then
        BadRequestException exception = assertThrows(BadRequestException.class,
                // when
                () -> underTest.addProduct(product));
        verify(repository, times(1)).findByTitle(title);
        verify(repository, never()).save(any()); // verifierar att produkten Inte sparas
        assertEquals("En produkt med titeln: " + title + " finns redan", exception.getMessage());
    }


    // updateProduct() -----------------------------------------------------------------------------------------------


    // Testar om updateProduct() uppdaterar rätt produkt med findByID()
    // Fick ändra metoden för att få testet rätt då den inte sparade updatedProdukt
    @Test
    void whenUpdateProductWithFindById_thenUpdateProductById() {

        // given
        Integer id = 1;
        Product product = new Product(
                "Dator",
                25000.00,
                "Elektronik",
                "bra o ha",
                "urlTillBild"
        );
        product.setId(id);

        Product updatedProduct = new Product(
                "Halsband",
                4000.0,
                "Smycken",
                "fint o ha",
                "nyUrlTillBild"
        );

        // when
        // Simulerar att den ursprungliga produkten hämtas från databasen med mock
        when(repository.findById(id)).thenReturn(Optional.of(product));

        // Kallar på metoden som ska testas för att uppdatera produkten med id
        underTest.updateProduct(updatedProduct, id);

        // then
        // verifierar att save() funkar med rätt produkt
        verify(repository).save(productCaptor.capture());
        // och att findById() med rätt id
        verify(repository, times(1)).findById(id);

        // Kontrollerar att uppdaterad och sparad produkt matchar varandra
        assertEquals(updatedProduct, productCaptor.getValue());
    }

    @Test
    void whenTryingTOUpdateNonExitingProductWithUpdateProductAndFindById_thenThrowException() {

        //given
        Integer id = 1;
        Product product = new Product(
                "Dator",
                25000.00,
                "Elektronik",
                "bra o ha",
                "urlTillBild"
        );
        product.setId(id);

        // when
        // Simulerar att den ursprungliga produkten inte finns i databasen med mock
        when(repository.findById(id)).thenReturn(Optional.empty());

        // metoden som testas när man försöker uppdatera en produkt som inte finns
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, ()-> {
            underTest.updateProduct(product,id);
        });

        // then
        // kontrollerar att felmeddelandet matchar det förväntade
        assertEquals("Produkt med id " + id + " hittades inte", exception.getMessage());
    }

    // deleteProduct() -------------------------------------------------------------------------------------------
    @Test
    void whenDeleteProductWithValidId_thenFindByIDAndDeleteByIdAreCalled() {

        // given
        Integer id = 1;
        Product product = new Product(
                "Dator",
                25000.00,
                "Elektronik",
                "bra o ha",
                "urlTillBild"
        );
        product.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(product));

        // when
        underTest.deleteProduct(id);

        // then
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).deleteById(id);
        verifyNoMoreInteractions(repository);
    }
    @Test
    void whenDeleteProductWithInvalidId_thenThrowException() {
        // given
        Integer id = 1;

        when(repository.findById(id)).thenReturn(Optional.empty());

        // when, then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            underTest.deleteProduct(id);
        });

        assertEquals("Produkt med id " + id + " hittades inte", exception.getMessage());
        verify(repository, times(1)).findById(id);
        verifyNoMoreInteractions(repository);
    }


    /*
    @Test
    void testGithubActions() {
        assertEquals(3,5);
    }

     */

}