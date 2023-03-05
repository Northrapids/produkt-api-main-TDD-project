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

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    // Mock the ProductRepository
    @Mock
    private ProductRepository repository;

    // Inject the ProductRepository mock into the ProductService
    @InjectMocks
    private ProductService underTest;

    // Create a captor to capture the Product object that is passed to the save() method of the repository
    @Captor
    ArgumentCaptor<Product> productCaptor;

    // getAllProducts() -----------------------------------------------------------------------------------

    // Test that the getAllProducts() method calls the findAll() method of the repository exactly once
    @Test
    void whenGetAllProducts_thenExactlyOneInteractionWithRepositoryMethodFindAll() {
        // when
        underTest.getAllProducts();
        // then
        verify(repository, times(1)).findAll();
        verifyNoMoreInteractions(repository);
    }

    // getAllCategories() --------------------------------------------------------------------------------------------

    // Test that the getAllCategories() method calls the findAllCategories() method of the repository exactly once
    @Test
    void whenGetAllCategories_thenExactlyOneInteractionWithRepositoryMethodGetByCategory() {
        // when
        underTest.getAllCategories();
        // then
        verify(repository, times(1)).findAllCategories();
        verifyNoMoreInteractions(repository);

    }


    // getProductsByCategory() -----------------------------------------------------------------

    // Test that the getProductsByCategory() method calls the findByCategory() method of the repository with the correct argument
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

    @Test
    void whenGetProductsByIdWithNonExistingId_thenThrowException() {
        // given
        Integer id = 13;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // when, then
        assertThrows(EntityNotFoundException.class, () -> underTest.getProductById(id));
        verify(repository, times(1)).findById(id);
        verifyNoMoreInteractions(repository);
    }

    // addProduct() -------------------------------------------------------------------------------------------------

    @Test
    void whenAddingAProduct_thenSaveMethodShouldBeCalled() {
        // given
        Product product = new Product("Dator",4000.0,"","","");

        // when
        underTest.addProduct(product);

        // then
        verify(repository).save(productCaptor.capture());
        assertEquals(product, productCaptor.getValue());
    }

    // Test that the addProduct() method throws a BadRequestException when given a product with a duplicate title
    @Test
    void whenAddingProductWithDuplicateTitle_thenThrowError() {
        //given
        String title = "vår test-title";
        Product product = new Product(title,34.0,"","","");
        given(repository.findByTitle("vår test-title")).willReturn(Optional.of(product));

        // then
        BadRequestException exception = assertThrows(BadRequestException.class,
                // when
                () -> underTest.addProduct(product));
        verify(repository, times(1)).findByTitle(title);
        verify(repository, never()).save(any());
        assertEquals("En produkt med titeln: vår test-title finns redan", exception.getMessage());
    }

    // updateProduct() -----------------------------------------------------------------------------------------------


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
        System.out.println(product);

        /*
        Product updateProduct = new Product();
        updateProduct.setTitle("Halsband");
        updateProduct.setPrice(4000.0);
        updateProduct.setCategory("Smycken");
        updateProduct.setDescription("fint o ha");
        updateProduct.setImage("nyUrlTillBild");
         */

        Product updateProduct = new Product(
                "Halsband",
                4000.0,
                "Smycken",
                "fint o ha",
                "nyUrlTillBild"
        );
        // updateProduct.setId(id);

        System.out.println(updateProduct);

        // when
        when(repository.findById(id)).thenReturn(Optional.of(updateProduct));
        when(repository.save(updateProduct)).thenReturn(updateProduct);

        Product result = underTest.updateProduct(updateProduct, id);

        // then
        verify(repository).save(productCaptor.capture());
        assertAll(
                () -> assertEquals("Halsband", result.getTitle()),
                () -> assertEquals(4000.0, result.getPrice()),
                () -> assertEquals("Smycken", result.getCategory()),
                () -> assertEquals("fint o ha", result.getDescription()),
                () -> assertEquals("nyUrlTillBild", result.getImage())
        );
    }

    @Test
    void whenUpdateProductWithFindById_thenUpdateProductByIdAndThrowException() {

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
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, ()-> {
            underTest.updateProduct(product,id);
        });

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



/*
    @Test
    void getAllProducts() {
        // klar
    }

    @Test
    void getAllCategories() {
        // klar
    }

    @Test
    void getProductsByCategory() {
        // klar
    }

    @Test
    void getProductById() {
        // klar
    }

    @Test
    void addProduct() {
        // klar
    }

    @Test
    void updateProduct() {
        // klar
    }

    @Test
    void deleteProduct() {
        // klar
    }

 */

}