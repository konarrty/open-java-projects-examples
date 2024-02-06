package com.example.constructionmanagementspring.mapper;

import com.example.constructionmanagementspring.dto.CashOrderDto;
import com.example.constructionmanagementspring.dto.UnitDto;
import com.example.constructionmanagementspring.model.CashOrder;
import com.example.constructionmanagementspring.model.Unit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CashOrderMapper extends DtoMapper<CashOrder, CashOrderDto> {


}