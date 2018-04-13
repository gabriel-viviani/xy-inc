package com.xy.inc.service.mapper;

import com.xy.inc.domain.Poi;
import com.xy.inc.service.dto.PoiDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {})
public interface PoiMapper {
    PoiMapper INSTANCE = Mappers.getMapper(PoiMapper.class);

    PoiDTO poiToPoiDTO(Poi poi);

    Poi poiDTOToPoi(PoiDTO poiDTO);

    List<PoiDTO> poiListToPoiDTOList(List<Poi> poiList);

    List<Poi> poiDTOListToPoiList(List<PoiDTO> poiDTOList);
}
