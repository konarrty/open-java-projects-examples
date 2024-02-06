package com.example.constructionmanagementspring.mapper;

import com.example.constructionmanagementspring.dto.TenderNoteDto;
import com.example.constructionmanagementspring.model.TenderNote;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TenderNoteMapper extends DtoMapper<TenderNote, TenderNoteDto> {
}
