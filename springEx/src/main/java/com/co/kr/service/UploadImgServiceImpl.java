package com.co.kr.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sound.midi.Patch;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.co.kr.code.Code;
import com.co.kr.domain.ImgBoardContentDomain;
import com.co.kr.domain.ImgBoardFileDomain;
import com.co.kr.domain.ImgBoardListDomain;
import com.co.kr.exception.RequestException;
import com.co.kr.mapper.UploadImgMapper;
import com.co.kr.util.CommonUtils;
import com.co.kr.vo.ImgFileListVO;

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

	@Override
	public int imgfileProcess(ImgFileListVO imgfileListVO, MultipartHttpServletRequest request, HttpServletRequest httpReq) {
		// TODO Auto-generated method stub
		// 여기부터 작성할게여욥
		
		//session 생성
		HttpSession session = httpReq.getSession();
		
		//content domain 생성
		ImgBoardContentDomain imgboardcontentDomain = ImgBoardContentDomain.builder()
			.mbId(session.getAttribute("id").toString())
			.ibdTitle(imgfileListVO.getItitle())
			.ibdContent(imgfileListVO.getIcontent())
			.build();
	
			if(imgfileListVO.getIsEdit() != null) {
				imgboardcontentDomain.setIbdSeq(Integer.parseInt(imgfileListVO.getSeq()));
				System.out.println("수정업데이트");
				//db 업데이트
				uploadimgMapper.bdimgContentUpdate(imgboardcontentDomain);
			} else {
				//db insert
				uploadimgMapper.imgContentUpload(imgboardcontentDomain);
				System.out.println("DB insert");
			}
			
			//file 데이터 DB 저장 시 쓰일 값
			int ibdSeq = imgboardcontentDomain.getIbdSeq();
			String mbId = imgboardcontentDomain.getMbId();
			
			List<MultipartFile> multipartFiles = request.getFiles("files");
			
			if(imgfileListVO.getIsEdit() != null) {
				List<ImgBoardFileDomain> imgfileList = null;
				
				for(MultipartFile multipartFile : multipartFiles) {
					if(!multipartFile.isEmpty()) {
						
						if(session.getAttribute("files") != null) {
							imgfileList = (List<ImgBoardFileDomain>) session.getAttribute("files");
							
							for(ImgBoardFileDomain list : imgfileList) {
								list.getUpImgFilePath();
								Path filePath = Paths.get(list.getUpImgFilePath());
								try {
									//파일삭제
									Files.deleteIfExists(filePath);
									//삭제
									bdimgFileRemove(list);
								} catch (DirectoryNotEmptyException e){
									throw RequestException.fire(Code.E404, "디렉토리가 존재하지 않습니다.", HttpStatus.NOT_FOUND);
								} catch (IOException e){
									e.printStackTrace();
								}
								
							}
						}
					}
				}
			}
			
			//새 파일 저장
			Path rootPath = Paths.get(new File("E://").toString(),"i_upload", File.separator).toAbsolutePath().normalize();
			File pathCheck = new File(rootPath.toString());
			
			//folder check
			if(!pathCheck.exists()) pathCheck.mkdirs();
			
			for(MultipartFile multipartFile : multipartFiles) {
				
				if(!multipartFile.isEmpty()) {
					String originalFileExtension;
					String contentType = multipartFile.getContentType();
					String origFilename = multipartFile.getOriginalFilename();
					
					if(ObjectUtils.isEmpty(contentType)) {
						break;
					}else {
						if(contentType.contains("image/jpeg")) {
							originalFileExtension = ".jpg";
						}else if(contentType.contains("image/png")) {
							originalFileExtension = ".png";
						}else if(contentType.contains("image/gif")) {
							originalFileExtension = ".gif";
						}else {
							break;
						}
					}
					
					String uuid = UUID.randomUUID().toString();
					String current = CommonUtils.currentTime();
					String newImgFileName = uuid + current + originalFileExtension;
					
					Path targetPath = rootPath.resolve(newImgFileName);
					
					File file = new File(targetPath.toString());
					
					try {
						multipartFile.transferTo(file);
						file.setWritable(true);
						file.setReadable(true);
						
						ImgBoardFileDomain imgboardFileDomain = ImgBoardFileDomain.builder()
							.ibdSeq(ibdSeq)
							.mbId(mbId)
							.upOriginalImgFileName(origFilename)
							.upNewImgFileName("resources/iupload/"+newImgFileName)
							.upImgFilePath(targetPath.toString())
							.upImgFileSize((int)multipartFile.getSize())
							.build();
						
						uploadimgMapper.imgfileUpload(imgboardFileDomain);
						System.out.println("i_upload done");
					} catch (IOException e) {
						throw RequestException.fire(Code.E404, "잘못된 업로드 파일", HttpStatus.NOT_FOUND);
					}
				}
			}
		
		//
		return ibdSeq;
	}

	@Override
	public void bdimgContentRemove(HashMap<String, Object> map) {
		uploadimgMapper.bdimgContentRemove(map);	
	}

	@Override
	public void bdimgFileRemove(ImgBoardFileDomain imgboardFileDomain) {
		uploadimgMapper.bdimgFileRemove(imgboardFileDomain);
	}
	
	@Override
	public List<ImgBoardFileDomain> iboardSelectOneFile(HashMap<String, Object> map){
		return uploadimgMapper.iboardSelectOneFile(map);
	}

	@Override
	public ImgBoardListDomain iboardSelectOne(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return uploadimgMapper.iboardSelectOne(map);
	}
	
}
