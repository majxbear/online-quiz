package cn.kgc.quiz.res;

import cn.kgc.quiz.dao.UserDao;
import cn.kgc.quiz.dao.UserLoginDao;
import cn.kgc.quiz.dao.impl.UserDaoImpl;
import cn.kgc.quiz.dao.impl.UserLoginDaoImpl;
import cn.kgc.quiz.domain.QuizUser;
import cn.kgc.quiz.domain.UserLogin;
import cn.kgc.quiz.message.LoginMsg;
import cn.kgc.quiz.message.SimpleMsg;
import com.alibaba.fastjson.JSON;
import org.eclipse.jetty.util.StringUtil;
import org.glassfish.jersey.internal.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;

@Path("auth")
public class UserLoginRes {

    private UserDao userDao = new UserDaoImpl();
    private UserLoginDao userLoginDao = new UserLoginDaoImpl();

    @Path("login")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@Context HttpServletRequest req) {
        String user = req.getHeader("username");
        String pass = req.getHeader("password");
        if (StringUtil.isBlank(user) || StringUtil.isBlank(pass)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(JSON.toJSONString(new SimpleMsg("username or password cannot be empty.")))
                    .build();
        }

        QuizUser sysUser = userDao.getByUsernameAndPassword(user, pass);
        if (sysUser == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(JSON.toJSONString(new SimpleMsg("username or password may be wrong.")))
                    .build();
        }

        String token = Base64.encodeAsString(user + " " + pass);
        UserLogin userLogin = userLoginDao.findByUserAndToken(user, token);
        if (userLogin != null) {
            return Response.status(Response.Status.OK)
                    .entity(JSON.toJSONString(new LoginMsg(userLogin.getUsername(), sysUser.getType(), userLogin.getToken())))
                    .build();
        }
        UserLogin login = new UserLogin();
        login.setUsername(user);
        login.setToken(token);
        login.setLoginTime(new Date());
        userLoginDao.save(login);
        return Response.status(Response.Status.OK)
                .entity(JSON.toJSONString(new LoginMsg(user, sysUser.getType(), token)))
                .build();
    }

    @Path("logout")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(@Context HttpServletRequest req) {

        String user = req.getHeader("username");
        String token = req.getHeader("token");
        if (StringUtil.isBlank(user) || StringUtil.isBlank(token)) {
            return Response.status(Response.Status.OK)
                    .entity(JSON.toJSONString(new SimpleMsg("Successfully logout.")))
                    .build();
        }

        UserLogin userLogin = userLoginDao.findByUserAndToken(user, token);
        if (userLogin != null) {
            userLoginDao.delete(userLogin);
            return Response.status(Response.Status.OK)
                    .entity(JSON.toJSONString(new SimpleMsg("Successfully logout.")))
                    .build();
        }
        return Response.status(Response.Status.OK)
                .entity(JSON.toJSONString(new SimpleMsg("Successfully logout.")))
                .build();

    }
}
