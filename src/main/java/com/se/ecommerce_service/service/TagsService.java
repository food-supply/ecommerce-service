package com.se.ecommerce_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

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
}
