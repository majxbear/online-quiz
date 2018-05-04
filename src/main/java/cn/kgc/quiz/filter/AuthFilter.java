package cn.kgc.quiz.filter;


import cn.kgc.quiz.dao.UserLoginDao;
import cn.kgc.quiz.dao.impl.UserLoginDaoImpl;
import cn.kgc.quiz.message.SimpleMsg;
import com.alibaba.fastjson.JSON;
import org.eclipse.jetty.util.StringUtil;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Priority(Priorities.AUTHENTICATION)
@Provider
@UserAuth
public class AuthFilter implements ContainerRequestFilter {

    private UserLoginDao userLoginDao = new UserLoginDaoImpl();

    public void filter(ContainerRequestContext req) throws IOException {
        String user = req.getHeaderString("username");
        String token = req.getHeaderString("token");
        if (StringUtil.isBlank(user) || StringUtil.isBlank(token)) {
            Response.ResponseBuilder rb = Response.status(Response.Status.FORBIDDEN)
                    .type(MediaType.APPLICATION_JSON).entity(JSON.toJSON(new SimpleMsg("no permission.")));
            req.abortWith(rb.build());
        }
        if (userLoginDao.findByUserAndToken(user, token) == null) {
            Response.ResponseBuilder rb = Response.status(Response.Status.FORBIDDEN)
                    .type(MediaType.APPLICATION_JSON).entity(JSON.toJSON(new SimpleMsg("no permission.")));
            req.abortWith(rb.build());
        }
    }
}
