package edu.kh.project.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.project.member.model.dao.MemberDAO;
import edu.kh.project.member.model.vo.Member;


@Service // 비즈니스 로직을 가진 클래스임을 명시 + bean 등록
public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberDAO dao; // DAO bean을 의존성 주입
	
	@Autowired
	private BCryptPasswordEncoder bcrypt; // spring-security.xml에서 등록한 bean 의존성 주입
	
	@Override
	public Member login(Member inputMember) {
		
		Member loginMember = dao.login(inputMember.getMemberEmail());
		
		if(loginMember != null) { // 아이디 정상 입력
			if(bcrypt.matches(inputMember.getMemberPw(), loginMember.getMemberPw())) {
				
				loginMember.setMemberPw(null);
		} else {
			loginMember = null;
			}
		}
		
		
		
		return loginMember;
	}




}
