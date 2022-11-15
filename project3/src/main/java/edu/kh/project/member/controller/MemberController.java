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

@Controller
@SessionAttributes({"loginMember", "message", "test2"})
public class MemberController {
	
	
	@Autowired
	private MemberService service;
	
	@PostMapping("/member/login")
	public String login(Member inputMember,
			Model model,
			RedirectAttributes ra,
			@RequestParam(value="saveId", required=false) String saveId,
			HttpServletResponse resp,
			@RequestHeader(value="referer") String referer
			) {
		
		Member loginMember = service.login(inputMember);
		
		String path = null;
		
		if(loginMember != null) {
			model.addAttribute("loginMember", loginMember);
			Cookie cookie = new Cookie("saveId", loginMember.getMemberEmail());

			if(saveId != null) {
				
				cookie.setMaxAge(60 * 60 * 24 * 365);
				
			} else {
				cookie.setMaxAge(0);
			}
			cookie.setPath("/");
			resp.addCookie(cookie);
		} else {
			path = referer;
			ra.addFlashAttribute("message", "아이디 또는 비밀번호가 일치하지 않습니다");
		}
		return  "redirect:" + path;
	}
	
	// 로그인 페이지 이동
	@GetMapping("/member/login")
	public String loginPage() {
		return "member/login";
	}
	
	// 로그아웃
	@GetMapping("/member/logout")
	public String logout(SessionStatus status) {
		status.setComplete();
		return "redirect:/";
	}
}