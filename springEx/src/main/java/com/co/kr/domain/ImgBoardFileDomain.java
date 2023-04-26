package com.co.kr.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderMethodName = "builder")
public class ImgBoardFileDomain {
	
	private Integer ibdSeq;
	private String mbId;
	private String upOriginalImgFileName;
	private String upNewImgFileName;
	private String upImgFilePath;
	private Integer upImgFileSize;

}
