package com.board.dao;

import com.board.VO.BoardVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

public abstract interface BoardDao {
	
	public abstract List<BoardVO> list(Map<String, Object> paramMap);

	public abstract int getCount(Map<String, Object> paramMap);

	void add(Map<String, Object> paramMap);
	
	BoardVO detail(int seq);

	void update(Map<String, Object> paramMap);

	public abstract void delete(int seq);

	public abstract int cnt(int seq);

	public abstract void deletefile(Map<String, Object> paramMap);

	public abstract void refwri(HashMap<String, Object> map);

	public abstract void updaterep(HashMap<String, Object> map);

	public abstract void updatestep(HashMap<String, Object> map);
	
}
