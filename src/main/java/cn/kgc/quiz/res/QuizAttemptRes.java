package cn.kgc.quiz.res;

import cn.kgc.quiz.QuizConstants;
import cn.kgc.quiz.dao.*;
import cn.kgc.quiz.dao.impl.*;
import cn.kgc.quiz.display.QuizDisplay;
import cn.kgc.quiz.display.ReviewAnswerDisplay;
import cn.kgc.quiz.display.ReviewQuestionDisplay;
import cn.kgc.quiz.display.ReviewQuizDisplay;
import cn.kgc.quiz.domain.*;
import cn.kgc.quiz.exception.BasicException;
import cn.kgc.quiz.filter.UserAuth;
import cn.kgc.quiz.message.FeedbackMsg;
import cn.kgc.quiz.message.SimpleMsg;
import cn.kgc.quiz.utils.QuizUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.eclipse.jetty.util.StringUtil;
import org.glassfish.jersey.internal.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("attempt")
public class QuizAttemptRes {

    private QuizDao quizDao = new QuizDaoImpl();
    private QuizAttemptDao attemptDao = new QuizAttemptDaoImpl();
    private QuizExpectedDao expectedDao = new QuizExpectedDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    private QuestionDao questionDao = new QuestionDaoImpl();
    private AnswerDao answerDao = new AnswerDaoImpl();
    private QuestionAttemptDao qAttemptDao = new QuestionAttemptDaoImpl();

    @Path("")
    @GET
    @UserAuth
    public Response getAttempted(@Context HttpServletRequest req) {
        String user = req.getHeader("username");
        String token = req.getHeader("token");
        String password = Base64.decodeAsString(token).split(" ")[1];
        QuizUser quizUser = userDao.getByUsernameAndPassword(user, password);
        if (quizUser == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        List<UserQuizAttempt> attempts = attemptDao.findByUserId(quizUser.getId());
        //to solve $ref problem
        //SerializerFeature.DisableCircularReferenceDetect
        return Response.ok(JSON.toJSONString(attempts,SerializerFeature.DisableCircularReferenceDetect)).build();
    }

    @Path("/expected")
    @GET
    @UserAuth
    public Response getExpectedQuiz(@Context HttpServletRequest req) {
        String user = req.getHeader("username");
        String token = req.getHeader("token");
        String password = Base64.decodeAsString(token).split(" ")[1];
        QuizUser quizUser = userDao.getByUsernameAndPassword(user, password);
        if (quizUser == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        List<UserQuizExpected> quizs = expectedDao.findByUserId(quizUser.getId());
        return Response.ok(JSON.toJSONString(quizs)).build();
    }

    @Path("/finish/exam/{id}")
    @POST
    @UserAuth
    public Response attemptSubmit(@PathParam("id") String id, String params) {
        Long attemptId = Long.parseLong(id);
        Double userSumGrades = 0d;
        UserQuizAttempt attempt = attemptDao.findById(attemptId);
        if (attempt == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        JSONObject json = JSON.parseObject(params);
        if (StringUtil.isBlank(json.getString("userResults"))) {
            attempt.setState(QuizConstants.QUESTION_ATTEMPT_FINISHED);
            attempt.setTimeFinish(new Date());
            attempt.setSumGrades(0d);
            attemptDao.save(attempt);
            return Response.ok(JSON.toJSONString(new SimpleMsg("Successfully submitted."))).build();
        }
        JSONArray answerArray = JSON.parseArray(json.getString("userResults"));
        if (answerArray != null && answerArray.size() > 0) {
            for (int i = 0; i < answerArray.size(); i++) {
                JSONObject obj = (JSONObject) answerArray.get(i);
                Long questionId = obj.getLong("questionId");
                String userAnswer = obj.getString("userAnswer");
                Question question = questionDao.findByID(questionId);
                if (question == null)
                    continue;
                int resultFlag = QuizConstants.USER_ANSWER_RIGHT;
                Double qGrade = 0d;
                UserQuestionAttempt questionAttempt = new UserQuestionAttempt();
                questionAttempt.setUserQuizAttemptId(attemptId);
                questionAttempt.setUserAnswer(userAnswer);
                questionAttempt.setQuestionId(questionId);
                List<QuestionAnswer> answerList = answerDao.findRightAnswer(question.getId());
                if (answerList != null && answerList.size() > 0) {
                    if (StringUtil.isBlank(userAnswer)) {
                        resultFlag = QuizConstants.USER_ANSWER_WRONG;
                    } else {
                        String shortName = "sc";
                        Integer questionType = question.getQuestionType();
                        if (questionType == 2)
                            shortName = "mc";
                        if (shortName.equals("sc")) {
                            Long userAnswerId = Long.parseLong(userAnswer);
                            QuestionAnswer rightA = answerList.get(0);
                            if (userAnswerId != rightA.getId().intValue())
                                resultFlag = QuizConstants.USER_ANSWER_WRONG;
                        } else if (shortName.equals("mc")) {
                            String anIds[] = userAnswer.split(",");
                            if (anIds.length != answerList.size()) {
                                resultFlag = QuizConstants.USER_ANSWER_WRONG;
                            }
                            for (String aid : anIds) {
                                QuestionAnswer an = answerDao.findByID(Long.parseLong(aid));
                                if (an == null)
                                    continue;
                                if (an.getRightAnswer() != 0)
                                    resultFlag = QuizConstants.USER_ANSWER_WRONG;
                            }
                        }
                    }
                }
                questionAttempt.setResultFlag(resultFlag);
                if (resultFlag == QuizConstants.USER_ANSWER_RIGHT)
                    qGrade = question.getDefaultMark();
                questionAttempt.setGrade(qGrade);
                qAttemptDao.save(questionAttempt);
                userSumGrades += qGrade;
            }
        }

        attempt.setState(QuizConstants.QUESTION_ATTEMPT_FINISHED);
        attempt.setTimeFinish(new Date());
        attempt.setSumGrades(userSumGrades);
        attemptDao.save(attempt);
        return Response.ok(JSON.toJSONString(new SimpleMsg("Successfully submitted."))).build();
    }

    @Path("/finish/quiz/{id}")
    @POST
    @UserAuth
    public Response attemptQuizSubmit(@PathParam("id") String id, String params) {
        if(StringUtil.isBlank(params))
            return Response.status(Response.Status.BAD_REQUEST).build();
        JSONObject json = JSON.parseObject(params);
        if(StringUtil.isBlank(json.getString("quizId")))
            return Response.status(Response.Status.BAD_REQUEST).build();
        Quiz quiz = quizDao.findByID(Long.parseLong(json.getString("quizId")));
        if(quiz==null)
            return Response.status(Response.Status.NOT_FOUND).build();
        Long attemptId = Long.parseLong(id);
        Double userSumGrades;
        Double quizSumGrades;

        userSumGrades = Double.parseDouble(json.getString("userSumGrades"));
        quizSumGrades = quiz.getSumGrades();
        Double rate = 0d;
        String gradeFeedback = "";
        //judge the score and get grade feedback
        if (userSumGrades == 0)
            gradeFeedback = quiz.getLev5FeedbackText();
        if (quizSumGrades != 0)
            rate = userSumGrades / quizSumGrades;

        if (rate >= 0.9)
            gradeFeedback = quiz.getLev1FeedbackText();
        else if (rate >= 0.8 && rate < 0.9)
            gradeFeedback = quiz.getLev2FeedbackText();
        else if (rate >= 0.7 && rate < 0.8)
            gradeFeedback = quiz.getLev3FeedbackText();
        else if (rate >= 0.6 && rate < 0.7)
            gradeFeedback = quiz.getLev4FeedbackText();
        else if (rate < 0.6)
            gradeFeedback = quiz.getLev5FeedbackText();
        if (gradeFeedback == null)
            gradeFeedback = "";

        if (attemptId != 0) {
            UserQuizAttempt attempt = attemptDao.findById(attemptId);
            if (attempt == null)
                return Response.status(Response.Status.NOT_FOUND).build();
            attempt.setState(QuizConstants.QUESTION_ATTEMPT_FINISHED);
            attempt.setTimeFinish(new Date());
            attempt.setSumGrades(userSumGrades);
            attempt.setGradeFeedback(gradeFeedback);
            attemptDao.save(attempt);
        }
        return Response.ok(JSON.toJSONString(new FeedbackMsg(gradeFeedback,userSumGrades))).build();
    }

    @Path("/review/{id}")
    @GET
    @UserAuth
    public Response review(@PathParam("id") String id) {
        try {
            Long attemptId = Long.parseLong(id);
            UserQuizAttempt quizAttempt = attemptDao.findById(attemptId);
            if (quizAttempt == null)
                return Response.status(Response.Status.NOT_FOUND).build();
            ReviewQuizDisplay quizDisplay = null;
            List<UserQuestionAttempt> questionAttemptList = qAttemptDao.findByAttemptId(attemptId);
            Quiz quiz = quizDao.findByID(quizAttempt.getQuizId());
            if (questionAttemptList == null || questionAttemptList.size() <= 0)
                return Response.status(Response.Status.NOT_FOUND).build();
            quizDisplay = new ReviewQuizDisplay();
            //general properties
            quizDisplay.setQuizId(quiz.getId());
            quizDisplay.setQuizName(quiz.getName());
            quizDisplay.setIntro(quiz.getIntro());
            quizDisplay.setAttemptId(attemptId);
            quizDisplay.setExamType(quizAttempt.getCurrentExamType());
            quizDisplay.setFeedbackType(quizAttempt.getCurrentFeedbackType());
            quizDisplay.setUserSumGrades(quizAttempt.getSumGrades());
            quizDisplay.setQuizSumGrades(quiz.getSumGrades());
            quizDisplay.setGradeFeedback(quizAttempt.getGradeFeedback());
            List<ReviewQuestionDisplay> questionDisplays = new ArrayList<ReviewQuestionDisplay>(0);

            // questions
            for (UserQuestionAttempt q : questionAttemptList) {
                Question question = questionDao.findByID(q.getQuestionId());
                if (question == null)
                    continue;
                ReviewQuestionDisplay qd = new ReviewQuestionDisplay();
                qd.setDefaultMark(question.getDefaultMark());
                //TODO：sc and mc
                qd.setqTypeName("单项选择题");
                qd.setqTypeShortName("sc");
                qd.setQuestionText(question.getQuestionText());
                qd.setGeneralFeedback(question.getGeneralFeedback());
                qd.setGrade(q.getGrade());
                qd.setResultFlag(q.getResultFlag());
                qd.setCertainty(q.getCertainty());
                qd.setAwardRate(q.getAwardRate());
                qd.setAwardPoint(q.getAwardPoint());
                qd.setUserIdea(q.getUserIdea());
                qd.setTeacherFeedback(q.getTeacherFeedback());

                List<ReviewAnswerDisplay> answerDisplays = new ArrayList<ReviewAnswerDisplay>(0);
                //answers
                List<QuestionAnswer> answers = question.getQuestionAnswers();
                Long[] userAnswerIds = new Long[]{};
                if (StringUtil.isNotBlank(q.getUserAnswer()))
                    userAnswerIds = QuizUtils.str2long(q.getUserAnswer().split(","));
                if (answers != null && answers.size() > 0) {
                    for (QuestionAnswer an : answers) {
                        ReviewAnswerDisplay ad = new ReviewAnswerDisplay();
                        ad.setAnswer(an.getAnswer());
                        ad.setFeedback(an.getFeedback());
                        if (QuizUtils.elementInArray(an.getId(), userAnswerIds))
                            ad.setSelected(0);
                        else
                            ad.setSelected(1);
                        answerDisplays.add(ad);
                    }
                }
                qd.setAnswers(answerDisplays);
                questionDisplays.add(qd);
            }
            quizDisplay.setQuestions(questionDisplays);
            return Response.ok(JSON.toJSONString(quizDisplay)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(JSON.toJSONString(new SimpleMsg(e.getMessage())))
                    .build();
        }

    }

    @Path("/continue/{id}")
    @GET
    @UserAuth
    public Response attemptRedo(@PathParam("id") String id) {
        try {
            Long attemptId = Long.parseLong(id);
            UserQuizAttempt quizAttempt = attemptDao.findById(attemptId);
            if(quizAttempt==null)
                return Response.status(Response.Status.NOT_FOUND).build();
            Quiz quiz = quizAttempt.getQuiz();
            List<UserQuestionAttempt> questionAttemptList = qAttemptDao.findByAttemptId(attemptId);
            if(questionAttemptList==null||questionAttemptList.size()==0) {
                QuizDisplay quizDisplay = QuizUtils.quiz2Display(quizDao, quizAttempt.getQuiz(),
                        quizAttempt.getQuizUserId(), quizAttempt.getId(),
                        quizAttempt.getCurrentExamType(), quizAttempt.getCurrentFeedbackType(), quizAttempt.getAttemptsRemained());
                if (quizDisplay == null)
                    return Response.status(Response.Status.BAD_REQUEST).build();
                return Response.ok(JSON.toJSONString(quizDisplay)).build();
            }else{
                List<ReviewQuestionDisplay> questionDisplays = new ArrayList<ReviewQuestionDisplay>(0);
                List<QuizQuestion> qqs = quizDao.findByQuizId(quizAttempt.getQuiz().getId());
                if(qqs==null || qqs.size()==0)
                    return Response.status(Response.Status.NOT_FOUND).build();
                for (UserQuestionAttempt q : questionAttemptList) {
                    Question question = questionDao.findByID(q.getQuestionId());
                    if (question == null)
                        continue;
                    Long finishedId = question.getId();
                    for(int i=0;i<qqs.size();i++){
                        if(qqs.get(i).getQuestion()!=null){
                            if(qqs.get(i).getQuestion().getId()==finishedId)
                                qqs.remove(i);
                        }
                    }
                    ReviewQuestionDisplay qd = new ReviewQuestionDisplay();
                    qd.setFinished(1);
                    qd.setId(question.getId());
                    qd.setDefaultMark(question.getDefaultMark());
                    //TODO: sc and mc
                    qd.setqTypeName("单项选择题");
                    qd.setqTypeShortName("sc");
                    qd.setQuestionText(question.getQuestionText());
                    qd.setGeneralFeedback(question.getGeneralFeedback());
                    qd.setGrade(q.getGrade());
                    qd.setResultFlag(q.getResultFlag());
                    qd.setCertainty(q.getCertainty());
                    qd.setAwardRate(q.getAwardRate());
                    qd.setAwardPoint(q.getAwardPoint());
                    qd.setUserIdea(q.getUserIdea());
                    qd.setTeacherFeedback(q.getTeacherFeedback());

                    List<ReviewAnswerDisplay> answerDisplays = new ArrayList<ReviewAnswerDisplay>(0);
                    //answers
                    List<QuestionAnswer> answers = question.getQuestionAnswers();
                    Long[] userAnswerIds = new Long[]{};
                    if (StringUtil.isNotBlank(q.getUserAnswer()))
                        userAnswerIds = QuizUtils.str2long(q.getUserAnswer().split(","));
                    if (answers != null && answers.size() > 0) {
                        for (QuestionAnswer an : answers) {
                            ReviewAnswerDisplay ad = new ReviewAnswerDisplay();
                            ad.setId(an.getId());
                            ad.setAnswer(an.getAnswer());
                            ad.setFeedback(an.getFeedback());
                            if (QuizUtils.elementInArray(an.getId(), userAnswerIds))
                                ad.setSelected(0);
                            else
                                ad.setSelected(1);
                            answerDisplays.add(ad);
                        }
                    }
                    qd.setAnswers(answerDisplays);
                    questionDisplays.add(qd);
                }
                if(qqs.size()>0){
                    for(QuizQuestion qq:qqs){
                        Question question = qq.getQuestion();
                        ReviewQuestionDisplay qd = new ReviewQuestionDisplay();
                        qd.setFinished(0);
                        qd.setId(question.getId());
                        qd.setDefaultMark(question.getDefaultMark());
                        qd.setqTypeName("单项选择题");
                        qd.setqTypeShortName("sc");
                        qd.setQuestionText(question.getQuestionText());

                        List<QuestionAnswer> answerSet = question.getQuestionAnswers();
                        List<ReviewAnswerDisplay> answerDisplaySet = new ArrayList<ReviewAnswerDisplay>(0);
                        for (QuestionAnswer answer : answerSet) {
                            ReviewAnswerDisplay answerDisplay = new ReviewAnswerDisplay();
                            answerDisplay.setId(answer.getId());
                            answerDisplay.setAnswer(answer.getAnswer());
                            answerDisplay.setAnswerOrder(answer.getAnswerOrder());
                            answerDisplaySet.add(answerDisplay);
                        }
                        qd.setAnswers(answerDisplaySet);
                        questionDisplays.add(qd);
                    }
                }
                ReviewQuizDisplay quizDisplay = new ReviewQuizDisplay();
                quizDisplay.setQuizId(quiz.getId());
                quizDisplay.setQuizName(quiz.getName());
                quizDisplay.setIntro(quiz.getIntro());
                quizDisplay.setAttemptId(attemptId);
                quizDisplay.setQuizSumGrades(quiz.getSumGrades());
                quizDisplay.setExamType(quizAttempt.getCurrentExamType());
                quizDisplay.setFeedbackType(quizAttempt.getCurrentFeedbackType());
                quizDisplay.setMinutesAllowed(quizAttempt.getQuiz().getMinutesAllowed());
                quizDisplay.setQuestions(questionDisplays);
                return Response.ok(JSON.toJSONString(questionDisplays)).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(JSON.toJSONString(new SimpleMsg(e.getMessage())))
                    .build();
        }
    }


}
