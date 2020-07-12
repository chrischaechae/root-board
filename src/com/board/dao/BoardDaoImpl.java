package com.board.dao;

import com.board.VO.BoardVO;
import com.board.dao.BoardDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

@Component
public class BoardDaoImpl extends SqlSessionDaoSupport implements BoardDao {
	
	public List<BoardVO> list(Map<String, Object> map) {
		List<BoardVO> list = getSqlSession().selectList("boardList", map);
		return list;
	}

	public int getCount(Map<String, Object> map) {
		return ((Integer) getSqlSession().selectOne("boardCount", map)).intValue();
	}

	@Override
	public void add(Map<String, Object> map) {
		System.out.println("다오"+map.get("upload"));
		getSqlSession().insert("write",map);		
	}

	@Override
	public BoardVO detail(int seq) {
		BoardVO bean=getSqlSession().selectOne("detail", seq);
		return bean;
		
	}

	@Override
	public void update(Map<String, Object> map) {
		getSqlSession().update("update",map);
		
	}

	@Override
	public void delete(int seq) {
		getSqlSession().delete("delete",seq);
	}

	@Override
	public int cnt(int seq) {
		return ((int) getSqlSession().update("cnt", seq));		
	}

	@Override
	public void deletefile(Map<String, Object> map) {
		getSqlSession().update("updatefile",map);
		
	}

	@Override
	public void refwri(HashMap<String, Object> map) {
		System.out.println("다오댓길"+map.get("ref"));
		getSqlSession().insert("repwri",map);
		
	}

	@Override
	public void updaterep(HashMap<String, Object> map) {
		getSqlSession().update("updaterep",map);
		
	}

	@Override
	public void updatestep(HashMap<String, Object> map) {
		getSqlSession().update("updatestep",map);
		
	}

}
