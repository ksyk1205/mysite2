package kr.co.itcen.mysite.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int o_page=1;
		if(request.getParameter("page")!=null) {
			String page=request.getParameter("page");		
			o_page=Integer.parseInt(page);
			
		}
		
		String keyword = request.getParameter("keyword");
		if(keyword == null) {
			keyword = "";
		}
		int pagenumber=((o_page-1)/5)*5;
		List<BoardVo> vo = new BoardDao().getList((o_page-1)*5,keyword);
		
		int total_count = new BoardDao().Count();
		int index =total_count-((o_page-1)*5);
		
		request.setAttribute("o_page", o_page);
		request.setAttribute("pagenumber", pagenumber);
		request.setAttribute("total_count", total_count);
		request.setAttribute("index", index);
		request.setAttribute("vo", vo );
		
		
		
		
		
		
		
		WebUtils.forward(request, response, "/WEB-INF/views/board/list.jsp");

	}

}
