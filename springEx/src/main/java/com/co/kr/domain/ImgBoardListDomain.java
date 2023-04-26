package com.co.kr.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderMethodName = "builder")
public class ImgBoardListDomain {
	
	private String ibdSeq;
	private String mbId;
	private String ibdTitle;
	private String ibdCreateAt;
	private String ibdUpdateAt;

}
