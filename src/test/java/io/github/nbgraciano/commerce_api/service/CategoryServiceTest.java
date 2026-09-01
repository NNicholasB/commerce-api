package io.github.nbgraciano.commerce_api.service;


import io.github.nbgraciano.commerce_api.entity.Category;
import io.github.nbgraciano.commerce_api.entity.dto.Category.CategoryRequestDTO;
import io.github.nbgraciano.commerce_api.entity.dto.Category.CategoryResponseDTO;
import io.github.nbgraciano.commerce_api.entity.mappers.CategoryMapper;
import io.github.nbgraciano.commerce_api.exception.DuplicateEntityException;
import io.github.nbgraciano.commerce_api.exception.EntityNotFoundException;
import io.github.nbgraciano.commerce_api.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository repository;

    @Mock
    private CategoryMapper mapper;

    @InjectMocks
    private CategoryService service;

    @Test
    void criarCategoria() {

        CategoryRequestDTO request =
                new CategoryRequestDTO("Eletronicos");

        Category category = new Category();
        category.setId(UUID.randomUUID());
        category.setName("Eletronicos");

        CategoryResponseDTO responseDTO =
                new CategoryResponseDTO(
                        category.getId(),
                        category.getName()
                );

        when(mapper.toEntity(any(CategoryRequestDTO.class)))
                .thenReturn(category);

        when(repository.save(any(Category.class)))
                .thenReturn(category);

        when(mapper.toResponse(any(Category.class)))
                .thenReturn(responseDTO);

        CategoryResponseDTO response = service.create(request);

        assertEquals("Eletronicos", response.name());

        verify(mapper).toEntity(any(CategoryRequestDTO.class));
        verify(repository).save(category);
        verify(mapper).toResponse(category);

        System.out.println("=================================");
        System.out.println("Categoria criada com sucesso!");
        System.out.println("ID: " + response.id());
        System.out.println("Nome: " + response.name());
        System.out.println("=================================");
    }

    @Test
    void naoDeveCriarCategoriaDuplicada(){
        CategoryRequestDTO request=new CategoryRequestDTO("Eletronicos");
        when(repository.existsByName(request.name())).thenReturn(true);
        assertThrows(DuplicateEntityException.class,()->service.create(request));

        verify(repository).existsByName(request.name());
        verify(mapper,never()).toEntity(any(CategoryRequestDTO.class));
        verify(repository,never()).save(any(Category.class));

    }

    @Test
    void buscarCategoriaPorId(){
        CategoryRequestDTO request=new CategoryRequestDTO("Eletronicos");
        Category category= new Category();
        category.setId(UUID.randomUUID());
        category.setName(request.name());

        CategoryResponseDTO responseDTO =
                new CategoryResponseDTO(
                        category.getId(),
                        category.getName()
                );
        when(repository.findById(category.getId())).thenReturn(Optional.of(category));


        when(mapper.toResponse(any(Category.class)))
                .thenReturn(responseDTO);

        CategoryResponseDTO response =
                service.findById(category.getId());

        assertEquals(category.getName(),response.name());
        assertEquals(category.getId(),response.id());

        verify(repository).findById(category.getId());
        System.out.println("------------------");
        System.out.println("Categoria achada:"+category.getName());
        System.out.println("------------------");

    }

    @Test
    void erroBuscarCategoriaPorId(){
        CategoryRequestDTO requestDTO=new CategoryRequestDTO("Eletronicos");
        Category category=new Category();
        category.setId(UUID.randomUUID());
        category.setName(requestDTO.name());

        when(repository.findById(any(category.getId().getClass()))).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()->service.findById(category.getId()));

        verify(repository).findById(category.getId());
        verify(mapper, never())
                .toResponse(any(Category.class));

    }

    @Test
    void buscarTodasCategorias() {
        Category category=new Category();
        category.setName("Eletronicos");
        when(repository.findAll()).thenReturn(List.of(category));

        List<CategoryResponseDTO> responseDTO=service.findAll(null);

        verify(repository).findAll();
    }

    @Test
    void deletarCategoriaPorId(){

        Category category=new Category();
        category.setId(UUID.randomUUID());
        category.setName("Eletronicos");
        when(repository.findById(category.getId())).thenReturn(Optional.of(category));

        service.deleteById(category.getId());

        verify(repository).findById(category.getId());
        verify(repository).delete(category);

    }

    @Test
    void erroDeletarCategoriaPorId(){
        Category category= new Category(UUID.randomUUID(),"Eletronicos");

        when(repository.findById(any())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()->service.deleteById(category.getId()));

        verify(repository).findById(any());
        verify(repository,never()).delete(any());
    }

    @Test
    void updateCategoria() {
        UUID id = UUID.randomUUID();

        CategoryRequestDTO requestDTO =
                new CategoryRequestDTO("Eletronicos");

        Category category = new Category();
        category.setId(id);
        category.setName("Livros");

        CategoryResponseDTO responseDTO =
                new CategoryResponseDTO(id, "Eletronicos");

        when(repository.findById(id))
                .thenReturn(Optional.of(category));

        when(repository.existsByNameAndIdNot("Eletronicos", id))
                .thenReturn(false);

        when(repository.save(category))
                .thenReturn(category);

        when(mapper.toResponse(category))
                .thenReturn(responseDTO);

        CategoryResponseDTO response =
                service.update(id, requestDTO);

        assertEquals("Eletronicos", response.name());

        verify(repository).findById(id);
        verify(repository).existsByNameAndIdNot("Eletronicos", id);
        verify(repository).save(category);
        verify(mapper).toResponse(category);
    }
}