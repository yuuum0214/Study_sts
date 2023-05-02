package com.co.kr.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.co.kr.domain.ImgBoardFileDomain;
import com.co.kr.domain.ImgBoardListDomain;
import com.co.kr.service.UploadImgService;
import com.co.kr.vo.ImgFileListVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ImgFileListController {

	
	
	@Autowired
	private UploadImgService uploadimgService;
	
	
	@PostMapping(value = "iupload")
	public ModelAndView ibdUpload(ImgFileListVO imgfileListVO, MultipartHttpServletRequest request, HttpServletRequest httpReq) throws IOException, ParseException {
		ModelAndView mav = new ModelAndView();
		int ibdSeq = uploadimgService.imgfileProcess(imgfileListVO, request, httpReq);//ImgFileListController 값 명시
		imgfileListVO.setIcontent(" ");
		imgfileListVO.setItitle(" ");//초기화

		
		mav = ibdSelectOneCall(imgfileListVO, String.valueOf(ibdSeq), request);
		mav.setViewName("imageboard/imageboardList.html");
		return mav;
	}

	public ModelAndView ibdSelectOneCall(@ModelAttribute("imgfileListVO") ImgFileListVO imgfileListVO, String ibdSeq, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		HashMap<String, Object> map = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		
		map.put("ibdSeq", Integer.parseInt(ibdSeq));
		ImgBoardListDomain imgboardListDomain = uploadimgService.iboardSelectOne(map);
		System.out.println("imgboardListDomain"+imgboardListDomain);
		List<ImgBoardFileDomain> fileList = uploadimgService.iboardSelectOneFile(map);
		
		for(ImgBoardFileDomain list : fileList) {
			String path = list.getUpImgFilePath().replaceAll("\\\\", "/");
			list.setUpImgFilePath(path);
		}
		mav.addObject("detail", imgboardListDomain);
		mav.addObject("files", fileList);
		
		session.setAttribute("files", fileList);
		
		return mav;
	}
	
	
}
