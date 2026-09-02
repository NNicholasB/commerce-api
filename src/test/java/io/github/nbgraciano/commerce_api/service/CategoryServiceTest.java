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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
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

        Category category = new Category();
        category.setName("Eletronicos");

        CategoryResponseDTO response =
                new CategoryResponseDTO(UUID.randomUUID(), "Eletronicos");

        List<Category> categories = List.of(category);
        List<CategoryResponseDTO> responses = List.of(response);

        when(repository.findAll()).thenReturn(categories);

        when(mapper.toResponse(categories))
                .thenReturn(responses);

        List<CategoryResponseDTO> responseDTO = service.findAll(null);

        assertEquals(1, responseDTO.size());
        assertEquals("Eletronicos", responseDTO.get(0).name());

        verify(repository).findAll();
        verify(mapper).toResponse(categories);
    }

    @Test
    void buscarTodasCategoriasPorNome(){
        Category category=new Category();
        category.setName("Eletronicos");

        List<Category> categories= List.of(category);

        CategoryResponseDTO responseDTO= new CategoryResponseDTO(UUID.randomUUID(),"Eletronicos");

        List<CategoryResponseDTO> responses=List.of(responseDTO);

        when(repository.findByNameContainingIgnoreCase("Elet")).thenReturn(categories);

        when(mapper.toResponse(categories)).thenReturn(responses);

        List<CategoryResponseDTO> result=service.findAll("Elet");

        assertEquals(1,result.size());
        assertEquals("Eletronicos",result.get(0).name());

        verify(repository).findByNameContainingIgnoreCase("Elet");
        verify(mapper).toResponse(categories);
    }

    @Test
    void buscarTodasCategoriasNomeVazio(){
        Category category=new Category();
        category.setName("Eletronicos");

        when(repository.findByNameContainingIgnoreCase("zzz")).thenReturn(List.of());

        when(mapper.toResponse(List.of())).thenReturn(List.of());

        List<CategoryResponseDTO> result=service.findAll("zzz");

        assertTrue(result.isEmpty());
        verify(repository).findByNameContainingIgnoreCase("zzz");
        verify(mapper).toResponse(List.of());
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

    @Test
    void erroUpdateCategoriaExistente() {

        UUID id = UUID.randomUUID();

        Category category = new Category();
        category.setId(id);
        category.setName("Eletronicos");

        CategoryRequestDTO requestDTO =
                new CategoryRequestDTO("Eletronicos");

        when(repository.findById(id))
                .thenReturn(Optional.of(category));

        when(repository.existsByNameAndIdNot(
                category.getName(), id))
                .thenReturn(true);

        assertThrows(
                DuplicateEntityException.class,
                () -> service.update(id, requestDTO)
        );

        verify(repository).findById(id);

        verify(repository).existsByNameAndIdNot(
                category.getName(), id);

        verify(repository, never())
                .save(any(Category.class));

        verify(mapper, never())
                .toResponse(any(Category.class));
    }

    @Test
    void erroUpdateCategoriaNaoExistente() {
    UUID id=UUID.randomUUID();
    CategoryRequestDTO requestDTO= new CategoryRequestDTO("Eletronicos");

    when(repository.findById(id)).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class,()->service.update(id,requestDTO));

    verify(repository).findById(id);
    verify(repository,never()).save(any(Category.class));
    verify(mapper,never()).toResponse(any(Category.class));
}
}