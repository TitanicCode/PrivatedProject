package api.exception;

import api.dto.R;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



/**
 * 异常处理器
 * 
 */
@RestControllerAdvice
public class RRExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 自定义异常
	 */
	@ExceptionHandler(RRException.class)
	public R handleRRException(RRException e){
		R r = new R();
		r.put("code", e.getCode());
		r.put("msg", e.getMessage());

		return r;
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public R handleDuplicateKeyException(DuplicateKeyException e){
		logger.error(e.getMessage(), e);
		return R.error("数据库中已存在该记录");
	}

	@ExceptionHandler(AuthorizationException.class)
	public R handleAuthorizationException(AuthorizationException e){
		logger.error(e.getMessage(), e);
		return R.error("没有权限，请联系管理员授权");
	}


	@ExceptionHandler(UnknownAccountException.class)
	public R handleUnknownAccountException(UnknownAccountException e){
		logger.error(e.getMessage(), e);
		return R.error("账号不存在");
	}

	@ExceptionHandler(IncorrectCredentialsException.class)
	public R handleIncorrectCredentialsException(IncorrectCredentialsException e){
		logger.error(e.getMessage(), e);
		return R.error("密码不正确");
	}

	@ExceptionHandler(LockedAccountException.class)
	public R handleLockedAccountException(LockedAccountException e){
		logger.error(e.getMessage(), e);
		return R.error(e.getMessage());
	}


	@ExceptionHandler(Exception.class)
	public R handleException(Exception e){

		System.out.println("Exception...........................................");
		logger.error(e.getMessage(), e);
		return R.error();
	}

}
