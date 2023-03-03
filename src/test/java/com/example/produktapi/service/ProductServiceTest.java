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
import org.mockito.internal.verification.NoMoreInteractions;
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
    /*

    @Test
    void whenGetProductsByCategoryWithValidCategory_thenReturnsProductsReturnedByRepository() {
        // given
        String category = "electronics";
        List<Product> products = Arrays.asList(new Product(), new Product());

        // when
        List<Product> result = underTest.getProductsByCategory(category);

        // then
        assertEquals(products, result);
        verify(repository, times(1)).findByCategory(category);
        verifyNoMoreInteractions(repository);
    }



    // Test that the getProductsByCategory() method throws an EntityNotFoundException when the category does not exist
    @Test
    void whenGetProductsByCategoryWithNonexistentCategory_thenThrowsEntityNotFoundException() {
        // given
        String category = "nonexistent category";
        when(repository.findByCategory(category)).thenReturn(Collections.emptyList());
        // when, then
        assertThrows(EntityNotFoundException.class, () -> underTest.getProductsByCategory(category));
        verify(repository, times(1)).findByCategory(category);
        verifyNoMoreInteractions(repository);
    }

     */


    /*
    @Test
    void givenAnExistingCategory_whenGetProductsByCategory_thenReceivesAnNonEmptyList() {
        // given
        String category = "electronics";
        Product product = new Product("test", 10.0, category, "test", "urlTillBild");
        given(repository.findByCategory(category)).willReturn(Collections.singletonList(product));

        // when
        List<Product> productsByCategory = underTest.getProductsByCategory(category);

        // then
        verify(repository, times(1)).findByCategory(category);
        verifyNoMoreInteractions(repository);
        assertFalse(productsByCategory.isEmpty());
        assertEquals(product, productsByCategory.get(0));
    }

     */

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


    /*
    @Test
    void whenAddingNonExistingProduct_thenReturnProduct() {
        // given
        Product addNewProduct = new Product();
        addNewProduct.setTitle("Test product");
        when(repository.findByTitle("Test product")).thenReturn(Optional.empty());

        // when
        Product savedProduct = underTest.addProduct(addNewProduct);

        // then
        verify(repository, times(1)).findByTitle("Test product");
        verify(repository, times(1)).save(productCaptor.capture());
        verifyNoMoreInteractions(repository);

        Product capturedProduct = productCaptor.getValue();
        assertEquals(addNewProduct, savedProduct);
        assertEquals(addNewProduct.getTitle(), savedProduct.getTitle());
    }

     */

    // updateProduct() -----------------------------------------------------------------------------------------------
    @Test
    void whenUpdateProduct_thenFindByIdAndSaveAreCalledOnceWithCorrectArguments() {
        // given
        Integer id = 1;
        Product existingProduct = new Product("existing product", 1.0,"electronics","bra o ha","urlTillBild");
        existingProduct.setId(1);
        Product updateProduct = new Product("update  product", 2.0,"jewelery","fint o ha","urlTillBild");
        updateProduct.setId(1);
        when(repository.findById(id)).thenReturn(Optional.of(existingProduct));
        when(repository.save(any())).thenReturn(updateProduct);

        // when
        Product result = underTest.updateProduct(updateProduct, id);

        // then
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(productCaptor.capture());
        verifyNoMoreInteractions(repository);

        assertEquals(updateProduct, productCaptor.getValue());
        assertEquals(updateProduct, result);
    }

    /*

    @Test
    void whenUpdateProduct_thenProductIsSaved() {
        // given
        Integer productId = 1;
        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setTitle("Existing Product");
        existingProduct.setPrice(9.99);

        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setTitle("Updated Product");
        updatedProduct.setPrice(12.99);

        when(repository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(repository.save(any(Product.class))).thenReturn(updatedProduct);

        // when
        Product result = underTest.updateProduct(updatedProduct, productId);

        // then
        verify(repository, times(1)).findById(productId);
        verify(repository, times(1)).save(productCaptor.capture());
        verifyNoMoreInteractions(repository);

        Product capturedProduct = productCaptor.getValue();
        assertEquals(updatedProduct, capturedProduct);
        assertEquals(updatedProduct.getTitle(), result.getTitle());
        assertEquals(updatedProduct.getPrice(), result.getPrice());
    }

     */

    /*

    @Test
    void whenUpdateProduct_thenProductIsSavedWithNewValues() {
        // given
        Integer productId = 1;
        String newTitle = "New Title";
        Double newPrice = 9.99;
        String newCategory = "New Category";
        String newDescription = "New Description";
        String newImage = "newImage";


        Product updatedProduct = new Product(newTitle, newPrice, newCategory, newDescription, newImage);
        updatedProduct.setId(productId);
        when(repository.findById(productId)).thenReturn(Optional.of(new Product("Old Title", 10.0, " old Category","Old Description", "Old Image")));

        // when
        Product result = underTest.updateProduct(updatedProduct, productId);

        // then
        verify(repository, times(1)).findById(productId);
        verify(repository, times(1)).save(productCaptor.capture());
        verifyNoMoreInteractions(repository);
        Product capturedProduct = productCaptor.getValue();
        assertEquals(updatedProduct, capturedProduct);
        assertEquals(updatedProduct.getTitle(), capturedProduct.getTitle());
        assertEquals(updatedProduct.getDescription(), capturedProduct.getDescription());
        assertEquals(updatedProduct.getPrice(), capturedProduct.getPrice());
        assertEquals(productId, capturedProduct.getId());
        assertEquals(updatedProduct, result);
    }

     */

    /*

    @Test
    void testUpdateProduct() {
        // Arrange
        Integer productId = 1;
        String productTitle = "Product 1";
        String updatedProductTitle = "Updated Product 1";
        Product product = new Product();
        product.setId(productId);
        product.setTitle(productTitle);
        Product updatedProduct = new Product();
        updatedProduct.setTitle(updatedProductTitle);
        when(repository.findById(productId)).thenReturn(Optional.of(product));

        // Act
        Product result = underTest.updateProduct(updatedProduct, productId);

        // Assert
        verify(repository, times(1)).findById(productId);
        verify(repository, times(1)).save(productCaptor.capture());
        assertEquals(updatedProductTitle, productCaptor.getValue().getTitle());
        assertSame(updatedProduct, result);
    }

     */

    /*
    @Test
    void testUpdateProduct2() {
        // Arrange
        Integer productId = 1;
        String productTitle = "Product 1";
        String updatedProductTitle = "Updated Product 1";
        Product product = new Product();
        product.setId(productId);
        product.setTitle(productTitle);
        Product updatedProduct = new Product();
        updatedProduct.setTitle(updatedProductTitle);
        when(repository.findById(productId)).thenReturn(Optional.of(product));
        when(repository.save(any(Product.class))).thenReturn(updatedProduct);

        // Act
        Product result = ProductService.updateProduct(updatedProduct, productId);

        // Assert
        assertNotNull(result);
        assertEquals(updatedProductTitle, result.getTitle());
        verify(repository, times(1)).findById(productId);
        verify(repository, times(1)).save(any(Product.class));
    }

     */

    /*

    @Test
    void testUpdateProduct3() {
        // Arrange
        Integer productId = 1;
        String productTitle = "Product 1";
        String updatedProductTitle = "Updated Product 1";
        Product product = new Product();
        product.setId(productId);
        product.setTitle(productTitle);
        Product updatedProduct = new Product();
        updatedProduct.setTitle(updatedProductTitle);
        when(repository.findById(productId)).thenReturn(Optional.of(product));

        // Act
        Product result = underTest.updateProduct(updatedProduct, productId);

        // Assert
        verify(repository, times(1)).findById(productId);
        verify(repository, times(1)).save(productCaptor.capture());
        assertEquals(updatedProductTitle, productCaptor.getValue().getTitle());
        assertSame(updatedProduct, result);
    }

     */









    @Test
    void getAllProducts() {
    }

    @Test
    void getAllCategories() {
    }

    @Test
    void getProductsByCategory() {
    }

    @Test
    void getProductById() {
    }

    @Test
    void addProduct() {
    }

    @Test
    void updateProduct() {
    }

    @Test
    void deleteProduct() {
    }
}