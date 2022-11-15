package edu.kh.project.member.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.kh.project.member.model.vo.Member;

@Repository // 퍼시스턴스 레이어, 영속성(파일, DB)을 가진 클래스 + bean 등록
public class MemberDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	public Member login(String memberEmail) {
		return sqlSession.selectOne("memberMapper.login", memberEmail);
	}



}
