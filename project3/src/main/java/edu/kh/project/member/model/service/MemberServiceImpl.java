package edu.kh.project.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.project.member.model.dao.MemberDAO;
import edu.kh.project.member.model.vo.Member;


@Service
public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberDAO dao;
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	@Override
	public Member login(Member inputMember) {
	
		Member loginMember = dao.login(inputMember.getMemberEmail());
		
		if(loginMember != null) {
		
		if(bcrypt.matches(inputMember.getMemberPw(), loginMember.getMemberPw())) {
			
			loginMember.setMemberPw(null);
		} else {
			loginMember = null;
			}
		}
		return loginMember;
	}


}
