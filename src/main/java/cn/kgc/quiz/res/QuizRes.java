package cn.kgc.quiz.res;

import cn.kgc.quiz.dao.*;
import cn.kgc.quiz.dao.impl.*;
import cn.kgc.quiz.display.QuizDisplay;
import cn.kgc.quiz.domain.*;
import cn.kgc.quiz.filter.AdminAuth;
import cn.kgc.quiz.filter.UserAuth;
import cn.kgc.quiz.message.SimpleMsg;
import cn.kgc.quiz.utils.QuizUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.eclipse.jetty.util.StringUtil;
import org.glassfish.jersey.internal.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Path("quiz")
public class QuizRes {

    private QuizDao quizDao = new QuizDaoImpl();
    private QuestionDao questionDao = new QuestionDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    private QuizExpectedDao expectedDao = new QuizExpectedDaoImpl();
    private QuizAttemptDao attemptDao = new QuizAttemptDaoImpl();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");

    @Path("")
    @PUT
    @AdminAuth
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(String params, @Context HttpServletRequest req) {
        try {
            JSONObject obj = JSON.parseObject(params);
            Quiz quiz = new Quiz();

            //名称和说明
            quiz.setName(obj.getString("name"));
            if (StringUtil.isNotBlank(obj.getString("intro")))
                quiz.setIntro(obj.getString("intro"));

            //考试类型，反馈方式
            if (StringUtil.isNotBlank(obj.getString("feedbackType")))
                quiz.setFeedbackType(Integer.parseInt(obj.getString("feedbackType")));
            if (StringUtil.isNotBlank(obj.getString("examType")))
                quiz.setExamType(Integer.parseInt(obj.getString("examType")));

            //分数反馈
            if (StringUtil.isNotBlank(obj.getString("lev1FeedbackText")))
                quiz.setLev1FeedbackText(obj.getString("lev1FeedbackText"));
            if (StringUtil.isNotBlank(obj.getString("lev2FeedbackText")))
                quiz.setLev2FeedbackText(obj.getString("lev2FeedbackText"));
            if (StringUtil.isNotBlank(obj.getString("lev3FeedbackText")))
                quiz.setLev3FeedbackText(obj.getString("lev3FeedbackText"));
            if (StringUtil.isNotBlank(obj.getString("lev4FeedbackText")))
                quiz.setLev4FeedbackText(obj.getString("lev4FeedbackText"));
            if (StringUtil.isNotBlank(obj.getString("lev5FeedbackText")))
                quiz.setLev5FeedbackText(obj.getString("lev5FeedbackText"));

            //开放时间 关闭时间
            if (StringUtil.isNotBlank(obj.getString("timeOpen")))
                quiz.setTimeOpen(dateFormat.parse(obj.getString("timeOpen")));
            if (StringUtil.isNotBlank(obj.getString("timeClose")))
                quiz.setTimeClose(dateFormat.parse(obj.getString("timeClose")));

            //答题控制
            if (StringUtil.isNotBlank(obj.getString("attemptsAllowed")))
                quiz.setAttemptsAllowed(Integer.parseInt(obj.getString("attemptsAllowed")));

            if (StringUtil.isNotBlank(obj.getString("minutesAllowed")))
                quiz.setMinutesAllowed(Integer.parseInt(obj.getString("minutesAllowed")));

            if (StringUtil.isNotBlank(obj.getString("timeClose")))
                quiz.setTimeClose(dateFormat.parse(obj.getString("timeClose")));
            if (StringUtil.isNotBlank(obj.getString("timeClose")))
                quiz.setTimeClose(dateFormat.parse(obj.getString("timeClose")));
            quiz.setSumGrades(0d);
            quizDao.save(quiz);
            return Response.ok(JSON.toJSONString(new SimpleMsg("Successfully modified."))).build();
        } catch (JSONException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(JSON.toJSONString(new SimpleMsg("only json data type are supported."))).build();
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
        for (String questionId : ids) {
            if (StringUtil.isNotBlank(questionId)) {
                Quiz quiz = quizDao.findByID(Long.parseLong(questionId));
                if (quiz != null)
                    quizDao.delete(quiz);
            }
        }
        return Response.ok(JSON.toJSONString(new SimpleMsg("Successfully deleted."))).build();
    }

    @Path("{id}")
    @POST
    @AdminAuth
    @Produces(MediaType.APPLICATION_JSON)
    public Response modify(@PathParam("id") String id, String params) {
        try {
            Quiz quiz = quizDao.findByID(Long.parseLong(id));
            if (quiz == null)
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new SimpleMsg("not found.")).build();
            JSONObject obj = JSON.parseObject(params);
            //名称和说明
            quiz.setName(obj.getString("name"));
            if (StringUtil.isNotBlank(obj.getString("intro")))
                quiz.setIntro(obj.getString("intro"));

            //考试类型，反馈方式
            if (StringUtil.isNotBlank(obj.getString("feedbackType")))
                quiz.setFeedbackType(Integer.parseInt(obj.getString("feedbackType")));
            if (StringUtil.isNotBlank(obj.getString("examType")))
                quiz.setExamType(Integer.parseInt(obj.getString("examType")));

            //分数反馈
            if (StringUtil.isNotBlank(obj.getString("lev1FeedbackText")))
                quiz.setLev1FeedbackText(obj.getString("lev1FeedbackText"));
            if (StringUtil.isNotBlank(obj.getString("lev2FeedbackText")))
                quiz.setLev2FeedbackText(obj.getString("lev2FeedbackText"));
            if (StringUtil.isNotBlank(obj.getString("lev3FeedbackText")))
                quiz.setLev3FeedbackText(obj.getString("lev3FeedbackText"));
            if (StringUtil.isNotBlank(obj.getString("lev4FeedbackText")))
                quiz.setLev4FeedbackText(obj.getString("lev4FeedbackText"));
            if (StringUtil.isNotBlank(obj.getString("lev5FeedbackText")))
                quiz.setLev5FeedbackText(obj.getString("lev5FeedbackText"));

            //开放时间 关闭时间
            if (StringUtil.isNotBlank(obj.getString("timeOpen")))
                quiz.setTimeOpen(dateFormat.parse(obj.getString("timeOpen")));
            if (StringUtil.isNotBlank(obj.getString("timeClose")))
                quiz.setTimeClose(dateFormat.parse(obj.getString("timeClose")));

            //答题控制
            if (StringUtil.isNotBlank(obj.getString("attemptsAllowed")))
                quiz.setAttemptsAllowed(Integer.parseInt(obj.getString("attemptsAllowed")));
            if (StringUtil.isNotBlank(obj.getString("minutesAllowed")))
                quiz.setMinutesAllowed(Integer.parseInt(obj.getString("minutesAllowed")));

            quizDao.save(quiz);
            return Response.ok(JSON.toJSONString(new SimpleMsg("Successfully modified."))).build();
        } catch (JSONException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(JSON.toJSONString(new SimpleMsg("only json data type are supported."))).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(JSON.toJSONString(new SimpleMsg(e.getMessage()))).build();
        }
    }

    @Path("{id}")
    @GET
    @AdminAuth
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") String id) {
        Quiz quiz = quizDao.findByID(Long.parseLong(id));
        if (quiz == null)
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(JSON.toJSONString(new SimpleMsg("not found."))).build();
        return Response.ok(JSON.toJSONString(quiz)).build();
    }

    @Path("")
    @GET
    @AdminAuth
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<Quiz> quizList = quizDao.findAll();
        if (quizList == null)
            return Response.ok("{}").build();
        return Response.ok(JSON.toJSONString(quizList)).build();
    }


    @Path("/questions/{id}")
    @GET
    @AdminAuth
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQuestionsOfQuiz(@PathParam("id") String id) {
        try {
            Quiz quiz = quizDao.findByID(Long.parseLong(id));
            if (quiz == null)
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(JSON.toJSONString(new SimpleMsg("not found."))).build();
            List<QuizQuestion> qqs = quizDao.findByQuizId(Long.parseLong(id));
            return Response.ok(JSON.toJSONString(qqs)).build();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(JSON.toJSONString(new SimpleMsg(e.getMessage()))).build();
        }
    }

    @Path("/questions/{id}")
    @POST
    @AdminAuth
    @Produces(MediaType.APPLICATION_JSON)
    public Response addQuestionsForQuiz(@PathParam("id") String id, String params) {
        try {
            Quiz quiz = quizDao.findByID(Long.parseLong(id));
            if (quiz == null)
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(JSON.toJSONString(new SimpleMsg("not found."))).build();
            JSONObject json = JSON.parseObject(params);
            String questionId = json.getString("questionId");
            if (StringUtil.isBlank(questionId)) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(JSON.toJSONString(new SimpleMsg("no questions."))).build();
            }
            String ques[] = questionId.split(",");
            for (String q : ques) {
                Question question = questionDao.findByID(Long.parseLong(q));
                if (question != null) {
                    QuizQuestion check = quizDao.findByQuizIdAndQuestionId(Long.parseLong(id), Long.parseLong(q));
                    if (check == null) {
                        QuizQuestion qq = new QuizQuestion();
                        qq.setQuestionId(question.getId());
                        qq.setQuizId(quiz.getId());
                        quizDao.saveQuestion(qq);
                        quiz.setSumGrades(quiz.getSumGrades() == 0 ? question.getDefaultMark() : quiz.getSumGrades() + question.getDefaultMark());
                        quizDao.save(quiz);
                    }
                }
            }
            return Response.ok(JSON.toJSONString(new SimpleMsg("Successfully added."))).build();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(JSON.toJSONString(new SimpleMsg(e.getMessage()))).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(JSON.toJSONString(new SimpleMsg(e.getMessage()))).build();
        }
    }

    @Path("/questions/{id}")
    @DELETE
    @AdminAuth
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteQuestionsForQuiz(@PathParam("id") String id, String params) {
        try {
            Quiz quiz = quizDao.findByID(Long.parseLong(id));
            if (quiz == null)
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(JSON.toJSONString(new SimpleMsg("not found."))).build();
            JSONObject json = JSON.parseObject(params);
            String questionId = json.getString("questionId");
            if (StringUtil.isBlank(questionId)) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(JSON.toJSONString(new SimpleMsg("no questions."))).build();
            }
            String ques[] = questionId.split(",");
            for (String q : ques) {
                QuizQuestion qq = quizDao.findById(Long.parseLong(q));
                if (qq != null) {
                    quizDao.deleteQuestion(qq);
                }
            }
            return Response.ok(JSON.toJSONString(new SimpleMsg("Successfully deleted."))).build();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(JSON.toJSONString(new SimpleMsg(e.getMessage()))).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(JSON.toJSONString(new SimpleMsg(e.getMessage()))).build();
        }
    }

    @Path("preview/{id}")
    @GET
    @AdminAuth
    @Produces(MediaType.APPLICATION_JSON)
    public Response preview(@PathParam("id") String id) {
        Quiz quiz = quizDao.findByID(Long.parseLong(id));
        if (quiz == null)
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(JSON.toJSONString(new SimpleMsg("not found."))).build();

        QuizDisplay quizDisplay = QuizUtils.quiz2Display(quizDao, quiz, 0l, 0l, quiz.getExamType(), quiz.getFeedbackType(), -1);
        return Response.ok(JSON.toJSONString(quizDisplay)).build();
    }

    @Path("start/{id}")
    @GET
    @UserAuth
    @Produces(MediaType.APPLICATION_JSON)
    public Response startQuiz(@PathParam("id") String id,@Context HttpServletRequest req) {
        try {
            Long expectedId = Long.parseLong(id);
            UserQuizExpected expected = expectedDao.find(expectedId);
            if (expected == null)
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(JSON.toJSONString(new SimpleMsg("not found."))).build();
            Long userId = expected.getQuizUserId();
            Quiz quiz = expected.getQuiz();
            List<UserQuizAttempt> attempts = attemptDao.findByQuizIdAndUserId(userId, quiz.getId());
            String uuidFlag = UUID.randomUUID().toString();
            UserQuizAttempt attempt = new UserQuizAttempt();
            attempt.setQuizId(quiz.getId());
            attempt.setQuizAttemptSeq(attempts == null ? 1 : attempts.size() + 1);
            attempt.setTimeStart(new Date());
            attempt.setQuizUserId(userId);
            attempt.setCurrentExamType(expected.getExamType());
            attempt.setCurrentFeedbackType(expected.getCurrentFeedbackType());
            attempt.setUuidFlag(uuidFlag);
            attempt.setSumGrades(0d);
            attemptDao.save(attempt);
            attempt = attemptDao.findByUUID(uuidFlag);
            QuizDisplay quizDisplay = QuizUtils.quiz2Display(quizDao,
                    quiz,
                    userId,attempt.getId() ,
                    quiz.getExamType(),  quiz.getFeedbackType(), quiz.getMinutesAllowed());
            return Response.ok(JSON.toJSONString(quizDisplay)).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(JSON.toJSONString(new SimpleMsg(e.getMessage()))).build();
        }
    }

    @Path("/publish/{id}")
    @POST
    @AdminAuth
    public Response publish(@PathParam("id") String id, String params) {
        try {
            Quiz quiz = quizDao.findByID(Long.parseLong(id));
            if (quiz == null)
                return Response.status(Response.Status.NOT_FOUND).build();
            JSONObject json = JSON.parseObject(params);
            if(StringUtil.isBlank(json.getString("attemptsAllowed"))||
                    StringUtil.isBlank(json.getString("minutesAllowed"))||
                    StringUtil.isBlank(json.getString("timeClose"))||
                    StringUtil.isBlank(json.getString("timeClose")))
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(JSON.toJSONString(new SimpleMsg("params not enough."))).build();
            Integer attemptsAllowed = Integer.parseInt(json.getString("attemptsAllowed"));
            Integer minutesAllowed = Integer.parseInt(json.getString("minutesAllowed"));
            Date timeOpen = dateFormat.parse(json.getString("timeOpen"));
            Date timeClose = dateFormat.parse(json.getString("timeClose"));

            List<QuizUser> userList = userDao.getAll();
            for (QuizUser user : userList) {
                if (expectedDao.find(user.getId(), quiz.getId()) != null)
                    continue;
                UserQuizExpected q = new UserQuizExpected();
                q.setQuizId(quiz.getId());
                q.setQuizUserId(user.getId());
                q.setAttemptsAllowed(attemptsAllowed);
                q.setMinutesAllowed(minutesAllowed);
                q.setCurrentFeedbackType(quiz.getFeedbackType());
                q.setExamType(quiz.getExamType());
                q.setTimeOpen(timeOpen);
                q.setTimeClose(timeClose);
                expectedDao.save(q);
            }
            return Response.ok(JSON.toJSONString(new SimpleMsg("Successfully published."))).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(JSON.toJSONString(new SimpleMsg(e.getMessage()))).build();
        }
    }

}
