package cn.kgc.quiz.res;

import cn.kgc.quiz.QuizConstants;
import cn.kgc.quiz.dao.*;
import cn.kgc.quiz.dao.impl.*;
import cn.kgc.quiz.display.AnswerFeedbackDisplay;
import cn.kgc.quiz.display.FeedbackDisplay;
import cn.kgc.quiz.display.QuestionDisplay;
import cn.kgc.quiz.display.QuizDisplay;
import cn.kgc.quiz.domain.*;
import cn.kgc.quiz.filter.AdminAuth;
import cn.kgc.quiz.filter.UserAuth;
import cn.kgc.quiz.message.SimpleMsg;
import cn.kgc.quiz.utils.QuizUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.eclipse.jetty.util.StringUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Path("question")
public class QuestionRes {

    QuestionDao questionDao = new QuestionDaoImpl();
    AnswerDao answerDao = new AnswerDaoImpl();
    HintDao hintDao = new HintDaoImpl();
    QuizAttemptDao quizAttemptDao = new QuizAttemptDaoImpl();
    QuizDao quizDao = new QuizDaoImpl();
    QuestionAttemptDao attemptDao = new QuestionAttemptDaoImpl();

    @Path("")
    @PUT
    @AdminAuth
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(String params) {
        try {
            JSONObject obj = JSON.parseObject(params);
            Question question = new Question();
            question.setName(obj.getString("name"));
            question.setQuestionText(obj.getString("questionText"));
            if (StringUtil.isNotBlank(obj.getString("keywords")))
                question.setKeywords(obj.getString("keywords"));
            if (StringUtil.isNotBlank(obj.getString("generalFeedback")))
                question.setGeneralFeedback(obj.getString("generalFeedback"));
            question.setAttemptPenalty(Double.parseDouble(obj.getString("attemptPenalty")));
            question.setDefaultMark(Double.parseDouble(obj.getString("defaltMark")));
            question.setAttemptsAllowed(Integer.parseInt(obj.getString("attemptsAllowed")));
            question.setQuestionType(1);
            String uuidFlag = UUID.randomUUID().toString();
            question.setUuidFlag(uuidFlag);
            questionDao.save(question);
            question = questionDao.findByUuidFlag(uuidFlag);
            Long questionId = question.getId();

            //save answer
            if (StringUtil.isNotBlank(obj.getString("answers"))) {
                JSONArray answerArray = JSON.parseArray(obj.getString("answers"));
                if (answerArray != null && answerArray.size() > 0) {
                    for (int i = 0; i < answerArray.size(); i++) {
                        JSONObject answerJson = (JSONObject) answerArray.get(i);
                        String answerText = (String) answerJson.get("answerText");
                        if (StringUtil.isNotBlank(answerText)) {
                            Integer rightAnswer = answerJson.getInteger("rightAnswer");
                            String specialFeedback = (String) answerJson.get("specialFeedback");
                            Integer order = answerJson.getInteger("order");
                            QuestionAnswer answer = new QuestionAnswer();
                            answer.setQuestionId(questionId);
                            answer.setAnswer(answerText);
                            answer.setFeedback(specialFeedback);
                            answer.setAnswerOrder(order);
                            answer.setRightAnswer(rightAnswer);
                            answerDao.save(answer);
                        }
                    }
                }
            }

            //save hint
            if (StringUtil.isNotBlank(obj.getString("hints"))) {
                JSONArray hintArray = JSON.parseArray(obj.getString("hints"));
                if (hintArray != null && hintArray.size() > 0) {
                    for (int i = 0; i < hintArray.size(); i++) {
                        JSONObject hintJson = (JSONObject) hintArray.get(i);
                        String hintText = (String) hintJson.get("hit");
                        if (StringUtil.isNotBlank(hintText)) {
                            Integer order = hintJson.getInteger("order");
                            Hint hint = new Hint();
                            hint.setQuestionId(questionId);
                            hint.setHint(hintText);
                            hint.setHintOrder(order);
                            hintDao.save(hint);
                        }
                    }
                }
            }
            return Response.ok(JSON.toJSONString(new SimpleMsg("Successfully created."))).build();
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
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") String id) {
        String[] ids = id.split(",");
        for (String questionId : ids) {
            if (StringUtil.isNotBlank(questionId)) {
                Question question = questionDao.findByID(Long.parseLong(questionId));
                if (question != null)
                    questionDao.delete(question);
            }
        }
        return Response.ok(JSON.toJSONString(new SimpleMsg("Successfully deleted."))).build();
    }

    @Path("{id}")
    @POST
    @AdminAuth
    @Produces(MediaType.APPLICATION_JSON)
    public Response modify(String params,@PathParam("id") String id) {
        try {
            Question question = questionDao.findByID(Long.parseLong(id));
            if(question==null)
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new SimpleMsg("not found.")).build();
            JSONObject obj = JSON.parseObject(params);
            question.setName(obj.getString("name"));
            question.setQuestionText(obj.getString("questionText"));
            if (StringUtil.isNotBlank(obj.getString("keywords")))
                question.setKeywords(obj.getString("keywords"));
            if (StringUtil.isNotBlank(obj.getString("generalFeedback")))
                question.setGeneralFeedback(obj.getString("generalFeedback"));
            question.setAttemptPenalty(Double.parseDouble(obj.getString("attemptPenalty")));
            question.setDefaultMark(Double.parseDouble(obj.getString("defaltMark")));
            question.setAttemptsAllowed(Integer.parseInt(obj.getString("attemptsAllowed")));
            question.setQuestionType(1);
            String uuidFlag = UUID.randomUUID().toString();
            question.setUuidFlag(uuidFlag);
            questionDao.save(question);
            question = questionDao.findByUuidFlag(uuidFlag);
            Long questionId = question.getId();

            //save answer
            if (StringUtil.isNotBlank(obj.getString("answers"))) {
                JSONArray answerArray = JSON.parseArray(obj.getString("answers"));
                if (answerArray != null && answerArray.size() > 0) {
                    for (int i = 0; i < answerArray.size(); i++) {
                        JSONObject answerJson = (JSONObject) answerArray.get(i);
                        String answerText = (String) answerJson.get("answerText");
                        if (StringUtil.isNotBlank(answerText)) {
                            Integer rightAnswer = answerJson.getInteger("rightAnswer");
                            String specialFeedback = (String) answerJson.get("specialFeedback");
                            Integer order = answerJson.getInteger("order");
                            QuestionAnswer answer = new QuestionAnswer();
                            answer.setQuestionId(questionId);
                            answer.setAnswer(answerText);
                            answer.setFeedback(specialFeedback);
                            answer.setAnswerOrder(order);
                            answer.setRightAnswer(rightAnswer);
                            answerDao.save(answer);
                        }
                    }
                }
            }

            //save hint
            if (StringUtil.isNotBlank(obj.getString("hints"))) {
                JSONArray hintArray = JSON.parseArray(obj.getString("hints"));
                if (hintArray != null && hintArray.size() > 0) {
                    for (int i = 0; i < hintArray.size(); i++) {
                        JSONObject hintJson = (JSONObject) hintArray.get(i);
                        String hintText = (String) hintJson.get("hit");
                        if (StringUtil.isNotBlank(hintText)) {
                            Integer order = hintJson.getInteger("order");
                            Hint hint = new Hint();
                            hint.setQuestionId(questionId);
                            hint.setHint(hintText);
                            hint.setHintOrder(order);
                            hintDao.save(hint);
                        }
                    }
                }
            }
            return Response.ok(new SimpleMsg("Successfully modified.")).build();
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
        Question question = questionDao.findByID(Long.parseLong(id));
        if(question==null)
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(JSON.toJSONString(new SimpleMsg("not found."))).build();
        return Response.ok(JSON.toJSONString(question)).build();
    }

    @Path("")
    @GET
    @AdminAuth
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<Question> questions = questionDao.getAll();
        if(questions==null)
            return Response.ok("[]").build();
        return Response.ok(JSON.toJSONString(questions)).build();
    }

    @Path("check_answer")
    @POST
    @UserAuth
    public Response checkAnswer(String params) {
        try {
            JSONObject json = JSON.parseObject(params);
            if ( StringUtil.isBlank(json.getString("attemptId"))
                    || StringUtil.isBlank(json.getString("questionId"))
                    || StringUtil.isBlank(json.getString("userAnswer")))
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(JSON.toJSONString(new SimpleMsg("params missed."))).build();
            Long attemptId = Long.parseLong(json.getString("attemptId"));
            Long questionId = Long.parseLong(json.getString("questionId"));
            Question question = questionDao.findByID(questionId);
            if(question==null)
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(JSON.toJSONString(new SimpleMsg("not found."))).build();
            int resultFlag = QuizConstants.USER_ANSWER_RIGHT;
            Double grade = question.getDefaultMark();
            String userAnswer = json.getString("userAnswer");

            FeedbackDisplay feedbackDisplay = new FeedbackDisplay();
            List<AnswerFeedbackDisplay> ds = new ArrayList<AnswerFeedbackDisplay>(0);
            List<QuestionAnswer> answerList = answerDao.findRightAnswer(question.getId());
            if (answerList != null && answerList.size() > 0) {
                Integer questionType = question.getQuestionType();
                if (questionType==1) {
                    Long userAnswerId = Long.parseLong(userAnswer);
                    QuestionAnswer rightA = answerList.get(0);
                    if (userAnswerId != rightA.getId().intValue()) {
                        resultFlag = QuizConstants.USER_ANSWER_WRONG;
                    }
                    AnswerFeedbackDisplay d = new AnswerFeedbackDisplay();
                    d.setId(userAnswerId);
                    d.setResultFlag(resultFlag);
                    d.setSpecialFeedback(answerDao.findByID(userAnswerId).getFeedback());
                    ds.add(d);
                } else if (questionType==2) {
                    Long[] anIds = QuizUtils.str2long(userAnswer.split(","));
                    if (anIds.length != answerList.size()) {
                        resultFlag = QuizConstants.USER_ANSWER_WRONG;
                    }
                    //get feedback for each user's answers
                    for (Long id : anIds) {
                        QuestionAnswer a = answerDao.findByID(id);
                        AnswerFeedbackDisplay d = new AnswerFeedbackDisplay();
                        d.setId(id);
                        if (a == null)
                            continue;
                        Integer rFlag = 0;
                        if (a.getRightAnswer() != 0) {
                            resultFlag = 2;
                            rFlag = 1;
                        }
                        d.setResultFlag(rFlag);
                        d.setSpecialFeedback(a.getFeedback());
                        ds.add(d);
                    }
                }
            }
            if (resultFlag == QuizConstants.USER_ANSWER_WRONG)
                grade = 0d;

            if (StringUtil.isNotBlank(json.getString("certainty"))) {
                Integer certainty = Integer.parseInt(json.getString("certainty"));
                if (certainty != 0) {
                    if (certainty == 1) {
                        if (resultFlag == QuizConstants.USER_ANSWER_RIGHT)
                            grade = grade + question.getDefaultMark() * 0.3;
                        else
                            grade = grade - question.getDefaultMark() * 0.3;
                    } else if (certainty == 2) {
                        if (resultFlag == QuizConstants.USER_ANSWER_RIGHT)
                            grade = grade + question.getDefaultMark() * 0.6;
                        else
                            grade = grade - question.getDefaultMark() * 0.6;
                    } else if (certainty == 3) {
                        if (resultFlag == QuizConstants.USER_ANSWER_RIGHT)
                            grade = grade + question.getDefaultMark() * 1;
                        else
                            grade = grade - question.getDefaultMark() * 1;
                    }
                }
            }
            String uuid = UUID.randomUUID().toString();
            Long questionAttemptId =0l;
            if (attemptId != 0) {
                UserQuizAttempt quizAttempt = quizAttemptDao.findById(attemptId);
                if (quizAttempt == null) {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity(JSON.toJSONString(new SimpleMsg("quiz attempt not found."))).build();
                }
                UserQuestionAttempt questionAttempt = null;
                questionAttempt = attemptDao.findByQuizAttemptIdAndQuestionId(attemptId, questionId);
                if (questionAttempt == null) {
                    questionAttempt = new UserQuestionAttempt();
                }
                //save student's question attempt
                questionAttempt.setQuestionId(questionId);
                questionAttempt.setGrade(grade);
                questionAttempt.setResultFlag(resultFlag);
                questionAttempt.setUserQuizAttemptId(attemptId);
                questionAttempt.setUserAnswer(userAnswer);
                questionAttempt.setCompleted(QuizConstants.QUESTION_ATTEMPT_FINISHED);
                questionAttempt.setReplyStatus(0);

                questionAttempt.setUuidFlag(uuid);
                if (StringUtil.isNotBlank(json.getString("userIdea")))
                    questionAttempt.setUserIdea(URLDecoder.decode(json.getString("userIdea"), "utf-8"));
                if (StringUtil.isNotBlank(json.getString("certainty")))
                    questionAttempt.setCertainty(Integer.parseInt(json.getString("certainty")));
                attemptDao.save(questionAttempt);
                quizAttempt.setSumGrades(quizAttempt.getSumGrades() == null ? grade : quizAttempt.getSumGrades() + grade);
                quizAttemptDao.save(quizAttempt);
                UserQuestionAttempt attempt = attemptDao.findByUUID(uuid);
                if(attempt!=null)
                    questionAttemptId=attempt.getId();
            }

            feedbackDisplay.setAnswers(ds);
            feedbackDisplay.setResultFlag(resultFlag);
            feedbackDisplay.setGeneralFeedback(question.getGeneralFeedback());
            feedbackDisplay.setQuestionAttemptId(questionAttemptId);
            feedbackDisplay.setGrade(grade);

            return Response.ok(JSON.toJSONString(feedbackDisplay)).build();
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
    public Response preview(@PathParam("id") String id) {
        try {
            Question question = questionDao.findByID(Long.parseLong(id));
            QuestionDisplay display = QuizUtils.question2Display(question);
            QuizDisplay quizDisplay = new QuizDisplay();
            quizDisplay.setQuizId(0l);
            quizDisplay.setAttemptId(0l);
            quizDisplay.setExamType(0);
            quizDisplay.setMinutesAllowed(-1);
            quizDisplay.setQuizSumGrades(question.getDefaultMark());
            quizDisplay.setQuizSumGrades(question.getDefaultMark());
            quizDisplay.setFeedbackType(0);
            List<QuestionDisplay> displaySet = new ArrayList<QuestionDisplay>();
            displaySet.add(display);
            quizDisplay.setQuestions(displaySet);
            return Response.ok( JSON.toJSONString(quizDisplay)).build();
        } catch (NumberFormatException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(JSON.toJSONString(new SimpleMsg(e.getMessage()))).build();
        }
    }
}
