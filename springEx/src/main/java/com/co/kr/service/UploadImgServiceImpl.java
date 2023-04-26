package com.co.kr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.co.kr.domain.ImgBoardListDomain;
import com.co.kr.mapper.UploadImgMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class UploadImgServiceImpl implements UploadImgService {

	@Autowired
	private UploadImgMapper uploadimgMapper;
	
	@Override
	public List<ImgBoardListDomain> imgboardList() {
		// TODO Auto-generated method stub
		return uploadimgMapper.imgboardList();
	}
	
}
