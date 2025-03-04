package com.br.demo.service;

import com.br.demo.dto.CategoriaDTO;
import com.br.demo.model.Categoria;
import com.br.demo.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<CategoriaDTO> listarCategorias(){
        return categoriaRepository.findAll().stream()
                .map(c -> new CategoriaDTO(c.getId(), c.getNome(), c.getDescricao()))
                .collect(Collectors.toList());
    }

    public CategoriaDTO buscarPorId(Long id){
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrado"));
        return new CategoriaDTO(categoria.getId(), categoria.getNome(), categoria.getDescricao());
    }

    public CategoriaDTO criarCategoria(CategoriaDTO categoriaDTO){
        Categoria novaCategoria = new Categoria(categoriaDTO.getId(), categoriaDTO.getNome(), categoriaDTO.getDescricao());
        Categoria categoriaSalva = categoriaRepository.save(novaCategoria);
        return new CategoriaDTO(categoriaSalva.getId(), categoriaSalva.getNome(), categoriaSalva.getDescricao());
    }

    public CategoriaDTO atualizarCategoria(Long id, CategoriaDTO requestDTO){
        Categoria categoriaExistente = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        categoriaExistente.setId(requestDTO.getId());
        categoriaExistente.setNome(requestDTO.getNome());
        categoriaExistente.setDescricao(requestDTO.getDescricao());

        Categoria categoriaAtualizada = categoriaRepository.update(categoriaExistente);
        return new CategoriaDTO(categoriaAtualizada.getId(), categoriaAtualizada.getNome(), categoriaAtualizada.getDescricao());

    }

    public void excluirCategoria(Long id){
        categoriaRepository.deleteById(id);
    }


}
