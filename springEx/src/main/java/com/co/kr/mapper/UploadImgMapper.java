package com.co.kr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.co.kr.domain.ImgBoardListDomain;

@Mapper
public interface UploadImgMapper {

	//list
	public List<ImgBoardListDomain> imgboardList();
	
}
