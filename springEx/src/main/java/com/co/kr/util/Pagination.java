package com.co.kr.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

@Component
public class Pagination {

	public static Map<String, Object> pagination(int totalcount, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		//페이지 넘버 초기화
		//String pnum = request.getParameter("page");
		
		//페이지 넘버 초기화
		HttpSession session = request.getSession();
		String pnum = (String) session.getAttribute("page");
		
		System.out.println("pnum"+pnum);
		if (pnum == null) { pnum = "1"; }
		
		// 스트링을 인트로 파싱
		int rowNUM = Integer.parseInt(pnum);
		if(rowNUM < 0) {rowNUM = 1;}
		
		// 페이지네이션 범위정함 나머지 없거나 있으면 +1
		int pageNum;
		if (totalcount % 10 == 0) { 
			pageNum = totalcount / 10; 
		}else { 
			pageNum = (totalcount / 10) + 1; 
		}
		if(rowNUM > pageNum) { rowNUM = pageNum; }
		// 페이지네이션 중간범위 지정 -- 시작페이지 21번~27번 -- 끝페이지 30
		int temp = (rowNUM - 1) % 10; // 0,1,2 나머지 값
		int startpage = rowNUM - temp; // temp는 startpage보다 끝자리 항상 1작음 27-6
		int endpage = startpage + (10-1); // 10 20 30 40
	
		// startpage기준 무조건 +9 한 것이어서 pageCount 비교 후 pageCount 적용
		if (endpage > pageNum) { endpage = pageNum; } 
		
		// 쿼리 범위 지정
		int offset = (rowNUM - 1) * 10;
	
		map.put("rowNUM", rowNUM);
		map.put("pageNum", pageNum);
		map.put("startpage", startpage);
		map.put("endpage", endpage);
		map.put("offset", offset);

		return map;
	}
	
	public static Map<String, Object> uploadPagination(int Gtotal, int pageCount, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		//페이지 넘버 초기화
		String pnum = request.getParameter("Gnum");
		if (pnum == null) { pnum = "1"; }
		
		//스트링을 인트로로 파싱
		int rowNUM = Integer.parseInt(pnum);
		if(rowNUM < 0) {rowNUM = 1;}
		
		//페이지네이션 범위 정함
		int pageNum;
		if(Gtotal % pageCount == 0) { 
			pageNum = Gtotal / pageCount;
		}else {
			pageNum = (Gtotal / pageCount) + 1;
		}
		
		if(rowNUM > pageNum) { rowNUM = pageNum; }
		
		//페이지네이션 중간범위 지정
		int temp = (rowNUM - 1) % 10;
		int startpage = rowNUM - temp;
		int endpage = startpage + 9;
		
		//startpage기준 무조건 +9한 것이라 비교
		if (endpage > pageNum) { endpage = pageNum; }
		
		//쿼리 범위 지정
		int offset = (rowNUM - 1) * pageCount;
		
		map.put("rowNUM", rowNUM);
		map.put("startpage", startpage);
		map.put("endpage", endpage);
		map.put("pageNum", pageNum);
		map.put("offset", offset);
		
		return map;
	}
}
