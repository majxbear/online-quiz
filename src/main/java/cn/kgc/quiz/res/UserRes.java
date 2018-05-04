package cn.kgc.quiz.res;

import cn.kgc.quiz.dao.UserDao;
import cn.kgc.quiz.dao.impl.UserDaoImpl;
import cn.kgc.quiz.domain.QuizUser;
import cn.kgc.quiz.filter.AdminAuth;
import cn.kgc.quiz.message.SimpleMsg;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.eclipse.jetty.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("user")
public class UserRes {

    private UserDao userDao = new UserDaoImpl();

    @Path("")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @AdminAuth
    public Response add(String params, @Context HttpServletRequest req) {
        try {
            JSONObject obj = JSON.parseObject(params);
            QuizUser user = new QuizUser();
            user.setUsername(obj.getString("username"));
            user.setPassword(obj.getString("password"));
            user.setType(1);
            user.setRealname(obj.getString("realname"));
            user.setEmail(obj.getString("email"));
            userDao.save(user);
            return Response.ok(new SimpleMsg("Successfully created.")).build();
        } catch (JSONException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new SimpleMsg("only json data type are supported.")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(JSON.toJSONString(new SimpleMsg(e.getMessage()))).build();
        }
    }

    @Path("{id}")
    @DELETE
    @AdminAuth
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") String id) {
        String[] ids = id.split(",");
        for (String userId : ids) {
            if (StringUtil.isNotBlank(userId)) {
                QuizUser user = userDao.get(Long.parseLong(userId));
                if (user != null)
                    userDao.delete(user);
            }
        }

        return Response.ok(JSON.toJSONString(new SimpleMsg("Successfully deleted."))).build();
    }

    @Path("{id}")
    @POST
    @AdminAuth
    @Produces(MediaType.APPLICATION_JSON)
    public Response modify(String params, @PathParam("id") String id, @Context HttpServletRequest req) {
        try {
            JSONObject obj = JSON.parseObject(params);
            QuizUser user = userDao.get(Long.parseLong(id));
            if(user==null)
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new SimpleMsg("not found.")).build();
            user.setUsername(obj.getString("username"));
            user.setPassword(obj.getString("password"));
            user.setType(1);
            user.setRealname(obj.getString("realname"));
            user.setEmail(obj.getString("email"));
            userDao.save(user);
            return Response.ok(new SimpleMsg("Successfully modified.")).build();
        } catch (JSONException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new SimpleMsg("only json data type are supported.")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(JSON.toJSONString(new SimpleMsg(e.getMessage()))).build();
        }

    }

    @Path("{id}")
    @GET
    @AdminAuth
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") String id, @Context HttpServletRequest req) {
        QuizUser user = userDao.get(Long.parseLong(id));
        if(user==null)
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(JSON.toJSONString(new SimpleMsg("not found."))).build();
        return Response.ok(JSON.toJSONString(user)).build();
    }

    @Path("")
    @GET
    @AdminAuth
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<QuizUser> userList = userDao.getAll();
        return Response.ok(JSON.toJSONString(userList)).build();
    }
}
