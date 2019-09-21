package kr.co.itcen.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kr.co.itcen.mysite.vo.BoardVo;




public class BoardDao {
	
	public Boolean insert(BoardVo vo) {
		Boolean result = false;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			connection = getConnection();
			
			String sql = "select ifnull(max(g_no)+1,1) from board";
			pstmt = connection.prepareStatement(sql);
			
			rs=pstmt.executeQuery();
			int group_no = 0 ; 
			

			while(rs.next()) {
				int gr_no = rs.getInt(1);
				group_no = gr_no;
			}
						
			String sql1 = "insert into board values(null, ?, ?, 0, now(), ?, 1, 0, ?)";
			pstmt = connection.prepareStatement(sql1);
			pstmt.setString(1,vo.getTitle());
			pstmt.setString(2,vo.getContents());
			pstmt.setInt(3,group_no);
			pstmt.setLong(4, vo.getUser_no());
			int count = pstmt.executeUpdate();
			result = (count == 1);
			

			
		} catch (SQLException e) {
			System.out.println("insert_error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(stmt != null) {
					stmt.close();
				}
				
				if(pstmt != null) {
					pstmt.close();
				}
				
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;		
	}
	public List<BoardVo> getList() {
		List<BoardVo> result = new ArrayList<BoardVo>();
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = getConnection();
			
			String sql = 
				"select a.user_no, a.title ,b.name, a.hit ,date_format(reg_date, '%Y-%m-%d %h:%i:%s'),depth,a.no"
				+ " from board a, user b "
				+ "where a.user_no =b.no"
				+ " order by a.no desc" ;
			pstmt = connection.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				Long user_no = rs.getLong(1);
				String title =rs.getString(2);
				String user_name =rs.getString(3);
				int hit =rs.getInt(4);
				String reg_date=rs.getString(5);
				int depth=rs.getInt(6);
				Long no = rs.getLong(7);
				
				BoardVo vo= new BoardVo();
				vo.setUser_no(user_no);
				vo.setTitle(title);
				vo.setUser_name(user_name);
				vo.setHit(hit);
				vo.setReg_date(reg_date);
				vo.setDepth(depth);
				vo.setNo(no);
				
				result.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("select_error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public BoardVo get(Long no) {
		BoardVo result = new BoardVo();
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = getConnection();
			
			String sql = 
				"select a.user_no, a.title ,b.name, a.hit ,date_format(reg_date, '%Y-%m-%d %h:%i:%s'),depth"
				+ " from board a, user b "
				+ "where a.user_no =b.no"
				+ " order by a.no desc" ;
			pstmt = connection.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				Long user_no = rs.getLong(1);
				String title =rs.getString(2);
				String user_name =rs.getString(3);
				int hit =rs.getInt(4);
				String reg_date=rs.getString(5);
				int depth=rs.getInt(6);
				
				BoardVo vo= new BoardVo();
				vo.setUser_no(user_no);
				vo.setTitle(title);
				vo.setUser_name(user_name);
				vo.setHit(hit);
				vo.setReg_date(reg_date);
				vo.setDepth(depth);
				
				result = vo;
			}
		} catch (SQLException e) {
			System.out.println("get_error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public BoardVo view(long no) {
		BoardVo result = new BoardVo();
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = getConnection();
			
			String sql = 
				"select no, title, contents, user_no, reg_date from board where no =?" ;
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1,no);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				Long no1= rs.getLong(1);
				String title =rs.getString(2);
				String contents =rs.getString(3);
				Long user_no = rs.getLong(4);
				String reg_date = rs.getString(5);
				
				BoardVo vo= new BoardVo();
				vo.setNo(no1);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setUser_no(user_no);
				vo.setReg_date(reg_date);
				
				result = vo;
			}
		} catch (SQLException e) {
			System.out.println("view_error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public boolean modify(BoardVo vo) {
		boolean result = false;

		Connection connection = null;
		PreparedStatement pstmt = null;
		

		try {
			connection = getConnection();

			String sql = "update board set title= ? , contents = ?, reg_date = now() where no = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3,vo.getNo());
			
			int count=pstmt.executeUpdate();
			
			result=(count==1);

		} catch (SQLException e) {
			System.out.println("modify_error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}

				if(pstmt != null) {
					pstmt.close();
				}

				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;		
	}
	public void delete(long no) {
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = getConnection();
			
			String sql =
				" delete" +
				"   from board" +
				"  where no = ?";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, no);

			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("delete_error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}
	
	
	
	
	
	private Connection getConnection() throws SQLException {
		Connection connection = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		
			String url = "jdbc:mariadb://192.168.1.73:3306/webdb?characterEncoding=utf8";
			connection = DriverManager.getConnection(url, "webdb", "webdb");
		
		} catch (ClassNotFoundException e) {
			System.out.println("Fail to Loading Driver:" + e);
		}
		
		return connection;
	}

}
