package io.github.nbgraciano.commerce_api.service;


import io.github.nbgraciano.commerce_api.entity.Category;
import io.github.nbgraciano.commerce_api.entity.Product;
import io.github.nbgraciano.commerce_api.entity.dto.Category.CategoryResponseDTO;
import io.github.nbgraciano.commerce_api.entity.dto.Product.ProductRequestDTO;
import io.github.nbgraciano.commerce_api.entity.dto.Product.ProductResponseDTO;
import io.github.nbgraciano.commerce_api.entity.mappers.ProductMapper;
import io.github.nbgraciano.commerce_api.exception.DuplicateEntityException;
import io.github.nbgraciano.commerce_api.exception.EntityNotFoundException;
import io.github.nbgraciano.commerce_api.repository.CategoryRepository;
import io.github.nbgraciano.commerce_api.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductMapper mapper;

    @Test
    @DisplayName("Encontrar Product pelo Id")
    void findById(){
        UUID productId=UUID.randomUUID();
        UUID categoryId=UUID.randomUUID();

        Category category= new Category(categoryId,"Eletronicos");
        CategoryResponseDTO categoryResponse= new CategoryResponseDTO(categoryId,"Eletronicos");
        Product product=new Product(productId,"Mouse","mouse gamer",new BigDecimal(150),10,category);
        ProductResponseDTO responseDTO=new ProductResponseDTO(productId,"Mouse","mouse gamer",new BigDecimal(150),10,categoryResponse);
        when(repository.findById(productId)).thenReturn(Optional.of(product));
        when(mapper.toResponse(product)).thenReturn(responseDTO);

        ProductResponseDTO result= service.findById(productId);

        assertEquals(productId,result.id());
        assertEquals("Mouse",result.name());

        verify(repository).findById(productId);
        verify(mapper).toResponse(product);
    }

    @Test
    @DisplayName("Deve lancar exceção ao nao encontrar o Product")
    void erroFindById(){
        UUID productId=UUID.randomUUID();

        when(repository.findById(productId)).thenReturn(Optional.empty());
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.findById(productId));

        assertEquals("Product not found",ex.getMessage());

        verify(mapper,never()).toResponse(any(Product.class));
    }

    @Test
    @DisplayName("Deve criar Product")
    void create(){
        UUID categoryId=UUID.randomUUID();
        UUID productId=UUID.randomUUID();

        Category category= new Category(categoryId,"Eletronicos");
        CategoryResponseDTO categoryResponse= new CategoryResponseDTO(categoryId,"Eletronicos");

        Product product=new Product(productId,"Mouse","mouse gamer",new BigDecimal(150),10,category);
        ProductRequestDTO requestDTO= new ProductRequestDTO("Mouse","mouse gamer",new BigDecimal(150),10,categoryId);
        ProductResponseDTO responseDTO= new ProductResponseDTO(productId,"Mouse","mouse gamer",new BigDecimal(150),10,categoryResponse);

        when(repository.existsByNameAndCategoryId(requestDTO.name(),requestDTO.categoryId())).thenReturn(false);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(mapper.toEntity(requestDTO)).thenReturn(product);
        when(mapper.toResponse(product)).thenReturn(responseDTO);
        when(repository.save(product)).thenReturn(product);
        ProductResponseDTO result=service.create(requestDTO);

        assertEquals("Mouse",result.name());
        assertEquals("Eletronicos",result.category().name());

        verify(repository).save(product);

    }

    @Test
    @DisplayName("Deve lancar exceção ao existsByNameAndCategoryId")
    void erroCreate(){
        UUID categoryId=UUID.randomUUID();
        UUID productId=UUID.randomUUID();

        Category category= new Category(categoryId,"Eletronicos");
        CategoryResponseDTO categoryResponse= new CategoryResponseDTO(categoryId,"Eletronicos");

        Product product=new Product(productId,"Mouse","mouse gamer",new BigDecimal(150),10,category);
        ProductRequestDTO requestDTO= new ProductRequestDTO("Mouse","mouse gamer",new BigDecimal(150),10,categoryId);

        when(repository.existsByNameAndCategoryId(requestDTO.name(),requestDTO.categoryId())).thenReturn(true);
        DuplicateEntityException ex = assertThrows(DuplicateEntityException.class, () -> service.create(requestDTO));

        assertEquals("Product already exists",ex.getMessage());

      verify(repository,never()).save(product);
      verify(mapper,never()).toEntity(requestDTO);
      verify(mapper,never()).toResponse(product);

    }

    @Test
    @DisplayName("Deve lancar exceção ao nao encontrar Category")
    void erroCreateCategory(){
        UUID categoryId=UUID.randomUUID();
        UUID productId=UUID.randomUUID();

        Category category= new Category(categoryId,"Eletronicos");
        CategoryResponseDTO categoryResponse= new CategoryResponseDTO(categoryId,"Eletronicos");

        Product product=new Product(productId,"Mouse","mouse gamer",new BigDecimal(150),10,category);
        ProductRequestDTO requestDTO= new ProductRequestDTO("Mouse","mouse gamer",new BigDecimal(150),10,categoryId);

        when(repository.existsByNameAndCategoryId(requestDTO.name(),requestDTO.categoryId())).thenReturn(false);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.create(requestDTO));

        assertEquals("Category not found",ex.getMessage());

        verify(repository,never()).save(product);
        verify(mapper,never()).toResponse(product);
        verify(mapper,never()).toEntity(requestDTO);
    }

    @Test
    @DisplayName("Deve retornar todos Product")
    void findAll() {

        UUID productId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();

        Category category = new Category(
                categoryId,
                "Eletronicos"
        );

        Product product = new Product(
                productId,
                "Mouse",
                "mouse gamer",
                new BigDecimal("150"),
                10,
                category
        );

        CategoryResponseDTO categoryResponse =
                new CategoryResponseDTO(
                        categoryId,
                        "Eletronicos"
                );

        ProductResponseDTO responseDTO =
                new ProductResponseDTO(
                        productId,
                        "Mouse",
                        "mouse gamer",
                        new BigDecimal("150"),
                        10,
                        categoryResponse
                );

        when(repository.findAll())
                .thenReturn(List.of(product));

        when(mapper.toResponse(List.of(product)))
                .thenReturn(List.of(responseDTO));

        List<ProductResponseDTO> result =
                service.findAll(null, null);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(responseDTO, result.get(0));

        verify(repository).findAll();

        verify(mapper).toResponse(List.of(product));
    }

    @Test
    @DisplayName("Deve realizar o delete")
    void delete(){
        UUID productId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();

        Category category = new Category(
                categoryId,
                "Eletronicos"
        );

        Product product = new Product(
                productId,
                "Mouse",
                "mouse gamer",
                new BigDecimal("150"),
                10,
                category
        );
        when(repository.findById(productId)).thenReturn(Optional.of(product));

        service.delete(productId);

        verify(repository).findById(productId);

    }

    @Test
    @DisplayName("Deve lancar excecao ao nao encontrar o Product")
    void erroDelete(){
        UUID productId = UUID.randomUUID();
        when(repository.findById(productId)).thenReturn(Optional.empty());
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.delete(productId));

        assertEquals("Product not found",ex.getMessage());

        verify(repository,never()).delete(any(Product.class));


    }

    @Test
    @DisplayName("Deve realiar o update")
    void update(){
        UUID productId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();

        Category category = new Category(
                categoryId,
                "Eletronicos"
        );

        Product product = new Product(
                productId,
                "Mouse",
                "mouse gamer",
                new BigDecimal("150"),
                10,
                category
        );

        CategoryResponseDTO categoryResponse =
                new CategoryResponseDTO(
                        categoryId,
                        "Eletronicos"
                );

        ProductResponseDTO responseDTO =
                new ProductResponseDTO(
                        productId,
                        "Teclado",
                        "teclado gamer",
                        new BigDecimal("350"),
                        5,
                        categoryResponse
                );
        ProductRequestDTO requestDTO= new ProductRequestDTO(
                "Teclado",
                "teclado gamer",
                new BigDecimal("350"),
                5,
                categoryId
        );
        when(repository.existsByNameAndCategoryIdAndIdNot(requestDTO.name(),requestDTO.categoryId(),productId)).thenReturn(false);
        when(repository.findById(productId)).thenReturn(Optional.of(product));
        when(categoryRepository.findById(requestDTO.categoryId())).thenReturn(Optional.of(category));
        when(mapper.toResponse(product)).thenReturn(responseDTO);
        when(repository.save(product)).thenReturn(product);

        ProductResponseDTO result=service.update(productId,requestDTO);
        assertEquals("Teclado",result.name());
        assertEquals("teclado gamer",result.description());

        verify(repository).findById(productId);
        verify(mapper).toResponse(product);
    }
}
