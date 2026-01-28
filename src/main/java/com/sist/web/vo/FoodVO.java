package com.sist.web.vo;

import lombok.Data;

/*
	controller : 화면 연결 => 브라우저에서 화면 제어
	restcontroller : 다른 프로그램 연동 ========> Spring AI
						=> react / vue = 화면 UI
						   -----------
						   	| 데이터 => 관리 (tanStack-query, Pinia)
						   	=> 상태 관리 (state)
						   	  -------
						   	  | 데이터형의 문제 => TypeScript
						   	 => spring / nodejs / dango
						   	 ----------------------------
						   	 공통
						   	 => Spring(JSP), DataBase
						   	 => AWS, Docker
	
	service : BI(기능 수행)
	vo		: 데이터를 모아서 한 번에 전송할 목적
	mapper / repository : 데이터베이스 연동
	기타
		security / websocket
		
	------------------------------------------------------------------------
	부트 : 
			@SpringBootApplication => 웹 구동
			Controller : @RestController / @Controller
			Service	   : @Service
			Repository : @Repository
			Entity     : 데이터 베이스 연동 => 컬럼명이 동일 => JPA
			등록		   : 동작에 필요한 설정
							application.properties / application.yml
			WebFlux	   : @GetMapping
				|		 @PostMapping / @PutMapping / @DeleteMapping
				| 멀티미디어 전송 => 실시간 스트리밍
				| Spring AI
			Dependency / Injection(DI)
				=> @AutoWired
				=> @RequiredArgsContructor(lombok) => 생성자를 통해서 주입
				-----------------------------------------------------
				user = controller = service = mapper = db
				
		Spring - Boot
		1. 메인 클래스 생성
		2. Bean생성 + DI
		3. Controller
		4. Service
		5. SQL 문장 => Repository
		6. 응답 => 브라우저 UI 갱신
		
		
		Spring-Boot
			=> Spring 프레임워크 기반의 빠른 애플리케이션 개발 도구
			=> 설정 최소화
			=> 내장서버(Tomcat)
				----------- CI / CD
		=> WebSocket / Security (JWT)
		   MyBatis / JPA
		   JSP / ThymeLeaf
		=> 자바 / 오라클
			| 코틀린
		----------------------------------------
		Pinia : 동작
			Vue3 => 상태 관리
			데이터, 로딩 상태 유지
			UI 자동 갱신
		----------------------------------------
		
		

*/
@Data
public class FoodVO {
	private int fno, hit;
	private String name, type, phone, address, theme, price, time, parking, poster, images, content;
	private double score;
}
