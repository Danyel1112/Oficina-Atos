package com.springproject.oficinaatos.service;

import com.springproject.oficinaatos.model.Peca;
import com.springproject.oficinaatos.repository.PecaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PecaService {

    private final PecaRepository pecaRepository;

    @Autowired
    public PecaService(PecaRepository pecaRepository) {
        this.pecaRepository = pecaRepository;
    }

    public List<Peca> listarPecas() {
        return pecaRepository.findAll();
    }

    public Optional<Peca> buscarPecaPorId(Long id) {
        return pecaRepository.findById(id);
    }

    public Peca cadastrarPeca(Peca peca) {
        return pecaRepository.save(peca);
    }

    public Peca editarPeca(Long id, Peca pecaAtualizada) {
        Optional<Peca> pecaExistente = pecaRepository.findById(id);
        if (pecaExistente.isPresent()) {
            Peca peca = pecaExistente.get();
            peca.setNome(pecaAtualizada.getNome());
            peca.setDescricao(pecaAtualizada.getDescricao());
            peca.setPreco(pecaAtualizada.getPreco());
            return pecaRepository.save(peca);
        }
        return null;
    }

    public boolean excluirPeca(Long id) {
        Optional<Peca> peca = pecaRepository.findById(id);
        if (peca.isPresent()) {
            pecaRepository.delete(peca.get());
            return true;
        }
        return false;
    }

    public List<Peca> buscarPecaPorNome(String nome) {
        return pecaRepository.findByNomeContaining(nome);
    }
}
