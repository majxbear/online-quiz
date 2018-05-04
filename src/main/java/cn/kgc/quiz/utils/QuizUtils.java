package cn.kgc.quiz.utils;

import cn.kgc.quiz.dao.QuizDao;
import cn.kgc.quiz.display.AnswerDisplay;
import cn.kgc.quiz.display.QuestionDisplay;
import cn.kgc.quiz.display.QuizDisplay;
import cn.kgc.quiz.domain.Question;
import cn.kgc.quiz.domain.QuestionAnswer;
import cn.kgc.quiz.domain.Quiz;
import cn.kgc.quiz.domain.QuizQuestion;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QuizUtils {
    public static JSONArray string2json(String originString) {

        originString = originString.replaceAll("\\\\&quot;", "\\\"");
        originString = originString.replaceAll("&quot;", "");
        originString = originString.substring(1, originString.length() - 1);

        StringBuilder builder = new StringBuilder("{");
        builder.append("'entities':[");
        builder.append(originString);
        builder.append("]}");

        JSONObject entities = JSON.parseObject(builder.toString());
        JSONArray array = entities.getJSONArray("entities");

        return array;
    }

    public static QuestionDisplay question2Display(Question question) {
        if (question != null) {
            QuestionDisplay display = new QuestionDisplay();
            display.setId(question.getId());
            display.setQuestionText(question.getQuestionText());
            display.setDefaultMark(question.getDefaultMark());
            display.setqTypeName("单项选择题");
            display.setqTypeShortName("sc");

            //from answers to displayAnswers
            List<QuestionAnswer> answerSet = question.getQuestionAnswers();
            List<AnswerDisplay> answerDisplaySet = new ArrayList<AnswerDisplay>(0);
            for (QuestionAnswer answer : answerSet) {
                AnswerDisplay answerDisplay = new AnswerDisplay();
                answerDisplay.setId(answer.getId());
                answerDisplay.setAnswer(answer.getAnswer());
                answerDisplay.setAnswerOrder(answer.getAnswerOrder());
                answerDisplaySet.add(answerDisplay);
            }

            display.setAnswers(answerDisplaySet);
            return display;
        } else
            return null;
    }

    public static Long[] str2long(String[] str) {
        Long[] a = new Long[str.length];
        try {
            for (int i = 0; i < str.length; i++)
                a[i] = Long.parseLong(str[i]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
        return a;
    }

    public static QuizDisplay quiz2Display(QuizDao quizDao, Quiz quiz, Long userId, Long attemptId, Integer examType, Integer feedbackType, Integer minutesAllowed) throws RuntimeException {
        if (quiz == null)
            return null;
        QuizDisplay quizDisplay = new QuizDisplay();
        quizDisplay.setQuizId(quiz.getId());
        quizDisplay.setExamType(examType);
        quizDisplay.setFeedbackType(feedbackType);

        quizDisplay.setQuizName(quiz.getName());
        quizDisplay.setIntro(quiz.getIntro());

        quizDisplay.setQuizSumGrades(quiz.getSumGrades());
        quizDisplay.setMinutesAllowed(minutesAllowed);

        quizDisplay.setUserId(userId);
        quizDisplay.setAttemptId(attemptId);

        List<QuizQuestion> qqs = quizDao.findByQuizId(quiz.getId());
        List<QuestionDisplay> questions = new ArrayList<QuestionDisplay>(0);
        for (QuizQuestion qq : qqs) {
            Question q = qq.getQuestion();
            QuestionDisplay display = QuizUtils.question2Display(q);
            questions.add(display);
        }
        quizDisplay.setQuestions(questions);


        return quizDisplay;
    }

    public static Date str2Date(String str) {
        Date date;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
        return date;
    }

    public static boolean elementInArray(Long o, Long[] os) {
        boolean flag = false;
        for (Long l : os) {
            if (o == l.longValue()) {
                return true;
            }
        }
        return flag;
    }


}
