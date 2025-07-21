package com.se.ecommerce_service.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se.ecommerce_service.dto.TagsRequestDTO;
import com.se.ecommerce_service.model.Tags;
import com.se.ecommerce_service.response.BaseResponse;
import com.se.ecommerce_service.response.Message;
import com.se.ecommerce_service.service.TagsService;
import com.se.ecommerce_service.validation.Update;


@RestController
@RequestMapping("/tags")
public class TagsController {
    private final TagsService tagsService;

    public TagsController(TagsService tagsService) {
        this.tagsService = tagsService;
    }
    
    @PostMapping("/add")
    public ResponseEntity<BaseResponse<?>> insertTags(@RequestBody TagsRequestDTO tag) {
        boolean ok = tagsService.save(tag.getTagName());
        BaseResponse<?> response = new BaseResponse<>();
        response.setErrorCode(ok ? 0 : 1);
        response.setMessage(ok ? Message.SUCCESS : Message.FAIL);
        return ResponseEntity.ok().body(response);
    }
    
    @GetMapping("/find-all")
    public ResponseEntity<BaseResponse<List<Tags>>> findAllTags() {
        BaseResponse<List<Tags>> response = new BaseResponse<>();
        response.setData(tagsService.findAll());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<BaseResponse<Optional<Tags>>> findById(@PathVariable UUID id) {
        BaseResponse<Optional<Tags>> response = new BaseResponse<>();
        response.setData(tagsService.findById(id));
        return ResponseEntity.ok().body(response);
    }

    @Validated(Update.class)
    @PostMapping("/update")
    public ResponseEntity<BaseResponse<?>> updateTags(@RequestBody  TagsRequestDTO dto) {
        boolean ok = tagsService.update(dto);
        
        BaseResponse<?> response = new BaseResponse<>();
        response.setErrorCode(ok ? 0 : 1);
        response.setMessage(ok ? Message.SUCCESS : Message.FAIL);
        return ResponseEntity.ok().body(response);
    }
    
    
}
