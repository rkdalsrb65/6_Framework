package edu.kh.project.member.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.project.member.model.service.MemberService;
import edu.kh.project.member.model.service.MemberServiceImpl;
import edu.kh.project.member.model.vo.Member;

@Controller // 현재 클래스가 컨트롤러라는 것을 의미 + bean 등록
@SessionAttributes({"loginMember", "message", "test2"}) // 저장할 값
public class MemberController {
	
	@Autowired // di 어노테이션
	private MemberService service;
	
	@PostMapping("/member/login")
	public String login(Member inputMember,
			Model model, // 데이터 전달용 객체 (데이터를 Map 형식으로 저장하여 전달)
			RedirectAttributes ra, // 메세지 출력 (alert 같은)
			@RequestParam(value="saveId", required=false) String saveId, // 체크박스 값 얻어오기
			HttpServletResponse resp, // 쿠키 전달용
			@RequestHeader(value="referer") String referer // 요청 이전 주소
			) {
		
		// 서비스 호출 후 결과 반환 받기
		Member loginMember = service.login(inputMember);
		
		String path = null; // 리다이렉트 경로를 저장할 변수
		
		if(loginMember != null) { // 로그인 멤버의 값이 있다면
			path = "/"; // 메인 페이지
			
			model.addAttribute("loginMember", loginMember);
			
			// 쿠키 생성
			Cookie cookie = new Cookie(
					"saveId", loginMember.getMemberEmail()); // 로그인된 이메일을 saveId에 저장
			
			// 쿠키 유지 시간 지정
			if(saveId != null) { // 체크 되었을 때
				
				cookie.setMaxAge(60 * 60 * 24 * 365); // 1년동안 쿠키 유지
				
			} else { // 체크 안되었을 때
				// 쿠키 0초 == 생성과 동시에 삭제
				cookie.setMaxAge(0);
				
			}
			
			// 쿠키가 사용되는 경로 지정
			cookie.setPath("/"); // localhost 밑에 모든 경로에서 사용
			
			// 생성된 쿠키를 응답 객체에 담아서 클라이언트에게 전달
			resp.addCookie(cookie);
			
		} else {
			
			path = referer; // 로그인 요청 전 페이지 주소
			
			ra.addFlashAttribute("message", "아이디 / 비밀번호 불 일 치 !");
			
		}
		
		return "redirect:" + path;
	}

	// 로그인 페이지 이동
	@GetMapping("/member/login")
	public String loginPage() {
		return "member/login";
	}

	// 로그아웃
	@GetMapping("/member/logout")
	public String logout(SessionStatus status) {
		status.setComplete(); // 세션 무효화
		return "redirect:/";
	}	
	
}