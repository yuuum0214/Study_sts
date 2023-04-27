package com.co.kr.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.co.kr.domain.ImgBoardContentDomain;
import com.co.kr.domain.ImgBoardFileDomain;
import com.co.kr.domain.ImgBoardListDomain;

@Mapper
public interface UploadImgMapper {

	//list
	public List<ImgBoardListDomain> imgboardList();
	
	//파일 입력
	public void imgfileUpload(ImgBoardFileDomain imgboardFileDomain);
	//파일 업데이트
	public void bdimgFileUpdate(ImgBoardFileDomain imgboardFileDomain);
	//파일 삭제
	public void bdimgFileRemove(ImgBoardFileDomain imgboardFileDomain);
	
	//콘텐츠 관련
	public void imgContentUpload(ImgBoardContentDomain imgboardcontentDomain);
	public void bdimgContentUpdate(ImgBoardContentDomain imgboardcontentDomain);
	public void bdimgContentRemove(HashMap<String, Object> map);
	
	
	public ImgBoardListDomain iboardSelectOne(HashMap<String, Object> map);
	public List<ImgBoardFileDomain> iboardSelectOneFile(HashMap<String, Object> map);

	
}
