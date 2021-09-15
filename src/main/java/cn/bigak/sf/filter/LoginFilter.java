package cn.bigak.sf.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.bigak.sf.entity.User;

public class LoginFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpReq = (HttpServletRequest) request;
		HttpServletResponse httpResp = (HttpServletResponse) response;
		HttpSession session = httpReq.getSession();

		if (httpReq.getRequestURL().indexOf(".api.action") != -1) {
			User user = (User) session.getAttribute("user");
			if (user == null) {
				httpResp.setCharacterEncoding("UTF-8");
				httpResp.sendError(1000, "用户未登录！");
//				String contextPath = httpReq.getContextPath();
//				httpResp.sendRedirect(contextPath + "/index.html");
				return;
			}
		}

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}
