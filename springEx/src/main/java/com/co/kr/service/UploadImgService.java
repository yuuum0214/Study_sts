package com.co.kr.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.co.kr.domain.ImgBoardFileDomain;
import com.co.kr.domain.ImgBoardListDomain;
import com.co.kr.vo.ImgFileListVO;

public interface UploadImgService {

	//전체리스트 조회
	public List<ImgBoardListDomain> imgboardList();
	
	// 인서트 및 업데이트
	public int imgfileProcess(ImgFileListVO imgfileListVO, MultipartHttpServletRequest request, HttpServletRequest httpReq);
	
	// 하나 삭제
	public void bdimgContentRemove(HashMap<String, Object> map);
	
	// 하나 삭제 
	public void bdimgFileRemove(ImgBoardFileDomain imgboardFileDomain);
	
	public ImgBoardListDomain iboardSelectOne(HashMap<String, Object> map);
	
	public List<ImgBoardFileDomain> iboardSelectOneFile(HashMap<String, Object> map);



}
