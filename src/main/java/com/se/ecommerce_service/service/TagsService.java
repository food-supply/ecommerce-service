package com.se.ecommerce_service.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.se.ecommerce_service.dto.TagsRequestDTO;
import com.se.ecommerce_service.model.Tags;
import com.se.ecommerce_service.repository.TagsRepository;

@Service
public class TagsService {
    private final TagsRepository tagsRepository;

    public TagsService(TagsRepository tagsRepository) {
        this.tagsRepository = tagsRepository;
    }
    
    public boolean save (String name){
        return tagsRepository.insertTags(name);
    }

    public List<Tags> findAll (){
        return tagsRepository.findAll();
    }

    public Optional<Tags> findById(UUID id){
        return tagsRepository.findById(id);
    }

    public boolean update(TagsRequestDTO dto){
        return tagsRepository.updateTags(dto);
    }
}
