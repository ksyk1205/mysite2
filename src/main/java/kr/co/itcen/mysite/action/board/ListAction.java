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
		String page=request.getParameter("page");		
		int o_page=Integer.parseInt(page);
		
		String keyword = request.getParameter("keyword");
		if(keyword == null) {
			keyword = "";
		}
		
		List<BoardVo> vo = new BoardDao().getList((o_page-1)*5,keyword);
		request.setAttribute("board", vo );
		
		WebUtils.forward(request, response, "/WEB-INF/views/board/list.jsp");

	}

}
