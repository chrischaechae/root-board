package com.board.controller;

import com.board.VO.BoardVO;
import com.board.dao.BoardDao;
import com.board.paging.Paging;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ListController {
	@SuppressWarnings("unused")
	private Logger log = Logger.getLogger(getClass());
	private int pageSize = 10;
	private int blockCount = 10;
		
	@Autowired
	private BoardDao boardDao;

	@RequestMapping({ "/board/list.do" })
	public ModelAndView process(
			@RequestParam(value = "pageNum", defaultValue = "1") int currentPage,
			@RequestParam(value = "keyField", defaultValue = "") String keyField,
			@RequestParam(value = "keyWord", defaultValue = "") String keyWord) {
		String pagingHtml = "";
		@SuppressWarnings({ "unchecked", "rawtypes" })
		HashMap<String, Object> map = new HashMap();
		map.put("keyField", keyField);
		map.put("keyWord", keyWord);

		int count = this.boardDao.getCount(map);

		Paging page = new Paging(keyField, keyWord, currentPage, count,
				this.pageSize, this.blockCount, "list.do");

		pagingHtml = page.getPagingHtml().toString();

		map.put("start", Integer.valueOf(page.getStartCount()));
		map.put("end", Integer.valueOf(page.getEndCount()));

		List<BoardVO> list = null;
		if (count > 0) {
			list = this.boardDao.list(map);
		} else {
			list = Collections.emptyList();
		}
		int number = count - (currentPage - 1) * this.pageSize;

		ModelAndView mav = new ModelAndView();
		mav.setViewName("boardList");
		mav.addObject("count", Integer.valueOf(count));
		mav.addObject("currentPage", Integer.valueOf(currentPage));
		mav.addObject("list", list);
		mav.addObject("pagingHtml", pagingHtml);
		mav.addObject("number", Integer.valueOf(number));

		return mav;
	}
	//글쓰기페이지로 이동
	@RequestMapping({"/board/write.do"})
	public ModelAndView write(){
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("write");
		return mav;
	}
	//insert 쿼리문 실행
	@RequestMapping(value="/board/add.do")
	public String add(HttpServletRequest request) throws Exception{
		System.out.println("d");
		String savePath="/home/mega/eclipse/workspace/spring/upload";
		System.out.println("경로확인"+savePath);
		int sizelimit=1024*1024*15;
		MultipartRequest multi=new MultipartRequest(request,savePath,sizelimit,"utf-8",new DefaultFileRenamePolicy());
		HashMap<String, Object> map = new HashMap();
		map.put("title",multi.getParameter("title"));
		map.put("name",multi.getParameter("name"));
		map.put("pass",multi.getParameter("pass"));
		map.put("content",multi.getParameter("content"));
		map.put("upload",multi.getFilesystemName("upload"));
		map.put("oriupload",multi.getOriginalFileName("upload"));
		String oriupload=multi.getOriginalFileName("upload");
		String name=multi.getParameter("name");
		System.out.println("확인ri업로드:"+oriupload);
		System.out.println("확인이름:"+name);
		boardDao.add(map);
		//boardDao.add(bean);
		//System.out.println("bean:"+bean.getTitle());
		return "redirect:list.do";
	}
	//detail페이지로 이동
	@RequestMapping({ "/board/detail.do"})
	public String detail(Model model, @RequestParam(value="seq",required=false) int seq){
		BoardVO bean=null;
		boardDao.cnt(seq);
		boardDao.detail(seq);
		bean=boardDao.detail(seq);
		model.addAttribute("bean",bean);
		
		return "detail";
	}
	//수정페이지로 이동
	@RequestMapping(value="/board/edit.do")
	public String edit(Model model,@RequestParam(value="seq",required=false) int seq) throws IOException{
		BoardVO bean=null;
		boardDao.detail(seq);
		bean=boardDao.detail(seq);
		model.addAttribute("bean",bean);
		return "edit";
	}
	//update실행
	@RequestMapping(value="/board/update.do",method=RequestMethod.POST)
	public String update(HttpServletRequest request) throws IOException{
		//
		String savePath="/home/mega/eclipse/workspace/spring/upload";
		System.out.println("경로확인"+savePath);
		int sizelimit=1024*1024*15;
		MultipartRequest multi=new MultipartRequest(request,savePath,sizelimit,"utf-8",new DefaultFileRenamePolicy());
		HashMap<String, Object> map = new HashMap();
		map.put("seq",multi.getParameter("seq"));
		map.put("title",multi.getParameter("title"));
		map.put("name",multi.getParameter("name"));
		map.put("pass",multi.getParameter("pass"));
		map.put("content",multi.getParameter("content"));
		System.out.println(multi.getParameter("existfile"));
		if(multi.getFilesystemName("upload")==null){
			map.put("upload",multi.getParameter("existfile"));
			map.put("oriupload",multi.getParameter("existfile"));
		}else if(multi.getFilesystemName("upload")!=null){
			map.put("upload", multi.getFilesystemName("upload"));
			map.put("oriupload",multi.getOriginalFileName("upload"));
			//새파일 업로드하면 기존 파일 서버에서 삭제
			String folder="/home/mega/eclipse/workspace/spring/upload";
			String file=folder+"/"+multi.getParameter("existfile");
			File delfile=new File(file);
			if(delfile.exists()){
				delfile.delete();
			}else{
				System.out.println("파일이 존재하지 않습니다.");
			}
		}
		String upload=multi.getFilesystemName("upload");
		String name=multi.getParameter("name");
		System.out.println("확인업로드:"+upload);
		System.out.println("확인이름:"+name);
		boardDao.update(map);
		//
		//boardDao.update(bean);
		return "redirect:detail.do?seq="+multi.getParameter("seq");
	}
	//delete 실행
	@RequestMapping(value="/board/delete.do",method=RequestMethod.POST)
	public String delete(int seq,String upload){
		String folder="/home/mega/eclipse/workspace/spring/upload";
		String file=folder+"/"+upload;
		File delfile=new File(file);
		if(delfile.exists()){
			delfile.delete();
		}else{
			System.out.println("파일이 존재하지 않습니다.");
		}
		boardDao.delete(seq);
		return "redirect:list.do";
		
	}
	@RequestMapping(value="/board/download.do")
	public void download(@RequestParam(value="oriupload",required=false) String oriupload,HttpServletResponse response) throws ServletException,IOException{
		System.out.println("컨트롤러업로드"+oriupload);
		String filename=oriupload;//파일이름 받음
		String folder="/home/mega/eclipse/workspace/spring/upload";
		String filepath=folder+"/"+filename;
		System.out.println("filepath은="+filepath);
		String encfilename=new String(filename.getBytes("utf-8"),"ISO-8859-1");
		//response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
		response.setContentType("application/download;UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + encfilename + "\"");
       response.setHeader("Content-Transfer-Encoding", "binary");
       response.setHeader("Pragma", "no-cache;");
       response.setHeader("Expires", "-1;");
       
       try(
               FileInputStream fis = new FileInputStream(filepath);
               OutputStream out = response.getOutputStream();
       ){
       	    int readCount = 0;
       	    byte[] buffer = new byte[1024];
           while((readCount = fis.read(buffer)) != -1){
           		out.write(buffer,0,readCount);
           }
       }catch(Exception ex){
           throw new RuntimeException("file Save Error");
       }
				
	}
	@RequestMapping(value="/board/reply.do")
	public String reply(Model model,@RequestParam(value="seq",required=false) int seq) throws IOException{
		BoardVO bean=null;
		boardDao.detail(seq);
		bean=boardDao.detail(seq);
		model.addAttribute("bean",bean);
		return "reply";
	}
	@RequestMapping(value="/board/replywri.do")
	public String replywir(HttpServletRequest request) throws IOException{
		System.out.println("답글연결");
		
		//
		String savePath="/home/mega/eclipse/workspace/spring/upload";
		System.out.println("경로확인"+savePath);
		int sizelimit=1024*1024*15;
		MultipartRequest multi=new MultipartRequest(request,savePath,sizelimit,"utf-8",new DefaultFileRenamePolicy());
		HashMap<String, Object> map = new HashMap();
		map.put("title",multi.getParameter("title"));
		map.put("name",multi.getParameter("name"));
		map.put("pass",multi.getParameter("pass"));
		map.put("content",multi.getParameter("content"));
		map.put("ref",multi.getParameter("ref"));
		map.put("indent",multi.getParameter("indent"));
		map.put("step",multi.getParameter("step"));
		System.out.println(multi.getParameter("existfile"));
		if(multi.getFilesystemName("upload")==null){
			map.put("upload",multi.getParameter("existfile"));
			map.put("oriupload",multi.getParameter("existfile"));
		}else if(multi.getFilesystemName("upload")!=null){
			map.put("upload", multi.getFilesystemName("upload"));
			map.put("oriupload",multi.getOriginalFileName("upload"));
			//새파일 업로드하면 기존 파일 서버에서 삭제
			String folder="/home/mega/eclipse/workspace/spring/upload";
			String file=folder+"/"+multi.getParameter("existfile");
			File delfile=new File(file);
			if(delfile.exists()){
				delfile.delete();
			}else{
				System.out.println("파일이 존재하지 않습니다.");
			}
		}
		String upload=multi.getFilesystemName("upload");
		String ref=multi.getParameter("seq");
		System.out.println("확인ref:"+ref);
		boardDao.refwri(map);
		boardDao.updaterep(map);
		boardDao.updatestep(map);
		//
		
		return "redirect:list.do";
	}
}
