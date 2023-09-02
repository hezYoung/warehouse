package com.young.mapper;

import com.young.pojo.Place;

import java.util.List;

public interface PlaceMapper {
    //查询所有产地
    public List<Place> findAllPlace();

}