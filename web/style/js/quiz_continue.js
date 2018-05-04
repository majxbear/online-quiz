function showQuestion(obj){
    var index = $(obj).attr("data-attributes");
    $(".slider-container").fadeOut(50).eq(index).fadeIn(50);
    $(".question-nav a span").css({"height":"40px","width":"40px","border-color":"#F6F6F6"});
    $(".question-nav a span").eq(index).css({"height":"45px","width":"45px","border-color":"red"});
    return false;
}
(function($) {
    $.fn.jQuiz = function(settings) {
        var defaults = {
            quizId:0,
            feedbackType:0,
            minutesAllowed:-1,
            attemptId:null,
            userId:null,
            intro:'',
            quizName:'',
            quizSumGrades:0,
            questions: null
        };
        var config = $.extend(defaults, settings);

        if (config.questions === null || config.questions.length===0) {
            $(this).html('<div class="slider-container"><div class="question-body center v-center">测试中没有题目可供作答...</div></div>');
            return;
        }
        var superContainer = $(this),
        ques =[],
        introFob='',
        fixedIntro1='<div class="slider-container" data-attributes="-1">'+
            '<div class="intro-body">'+
                '<div class="intro-header center">测试Tips</div>',
        fixedIntro2='<div><span class="fa fa-warning warning "></span><p>每个题目分别点【提交答案】查看反馈，获取参考答案、题目解析等。</p></div>',
        contentFob = '',
        finishFob = '<div class="results-container slider-container none"><div class="question-body center"><div class="result-keeper center"></div> </div>'+
            '<div class="grade-feedback none"></div>'+
            '<div class="pn-nav"><div id="finish"><a><span class="fa fa-hand-o-right"></span>结束测试</a></div></div>'+
            '</div>',
        timeFob='<div class="countdownTime"><span id="countdownTime"></span>' +
            '<input type="button" id="submitTest" data-attributes="going" class="btn btn-success" value="提交测试"/></div>',
        questionNavFob='<div class="question-nav"><a><span>Intro</span></a>';
        superContainer.addClass('main-quiz-holder');
        introFob += fixedIntro1;
        if(config.intro!="")
            introFob+='<div class="teacher-edit-intro"><span class="fa fa-warning warning"></span>'+config.intro+'</div>';
        introFob += fixedIntro2;
        if(config.minutesAllowed==-1)
            timeFob='<div class="countdownTime">' +
                '<input type="button" id="submitTest" data-attributes="going" class="btn btn-success" value="提交测试"/></div>';
        if(config.feedbackType==2){
            introFob+='<div><span class="fa fa-warning warning "></span><p>作答时选择【我做对本题的概率】，做对可获得相应概率对应的奖励分，让你的成绩翻倍，错误要扣分哦。</p></div>';
        }
        if(config.feedbackType==3){
            introFob+='<div><span class="fa fa-warning warning "></span><p>仔细阅读反馈内容，如果仍有疑问，填写【想和老师进一步交流的问题】，教师会及时回复，并会获得10-100%的奖励分数。</p></div>';
        }
        introFob+='</div><div class="pn-nav">'+
                    '<div class="nav-next"><a><span class="fa fa-hand-o-right"></span>开始测试</a></div>'+
                   '</div></div>';
        if(config.questions instanceof Array)
            ques = config.questions;
        else if(config.questions instanceof Object)
            ques.push(config.questions);
        for (var i = 0; i < ques.length; i++) {
            if(ques[i].finished==1 ){
                var qGrade ="<span class='grade' grade='"+ques[i].grade.toFixed(2)+"'>"+(ques[i].grade).toFixed(2)+"分</span>";
                var qResult = "";
                if(ques[i].resultFlag==0){
                    qResult = "<span class='fa fa-2x fa-check completely-right'></span>";
                    questionNavFob+='<a ><span class="fa fa-check completely-right">'+(i+1)+'</span></a>';
                }else if(ques[i].resultFlag ==2){
                    qResult = "<span class='fa fa-2x fa-close wrong'></span>";
                    questionNavFob+='<a ><span class="fa fa-close wrong">'+(i+1)+'</span></a>';
                }
                contentFob += '<div class="slider-container" id="question-'+ques[i].id+'" resultFlag="'+ques[i].resultFlag+'" completed="0" questionId="'+ques[i].id+'" data-attributes="'+i+'">' +
                    '<div class="question-body">' +
                    '<div class="question-header row">' +
                    '<div class="col-md-12">' +
                    '<span class="q-seq">'+(i+1)+'</span>' +
                    '<span class="question-type">'+ques[i].qTypeName+'<label class="q-point">('+ques[i].defaultMark+'分)</label></span>'+
                    '</div>'+
                    '<div class="col-md-12">';
                contentFob += '<div class="question-title">'+ques[i].questionText+''+(qResult+qGrade)+ '</div>'+
                    '</div>' +
                    '</div>' +
                    '<ul class="answers">';
                for (var j = 0; j < ques[i].answers.length; j++) {
                    contentFob += '<li><label>';
                    if(ques[i].qTypeShortName=='sc'){
                        if(ques[i].answers[j].selected==0)
                            contentFob +='<input type="radio" checked="true" id="'+ques[i].answers[j].id+'" value="'+ques[i].answers[j].id+'"  name="answer-'+i+'">';
                        else
                            contentFob +='<input type="radio" id="'+ques[i].answers[j].id+'" value="'+ques[i].answers[j].id+'"  name="answer-'+i+'">';
                    }

                    else if(ques[i].qTypeShortName=='mc'){
                        if(ques[i].answers[j].selected==0)
                            contentFob +='<input type="checkbox" checked="checked" id="'+ques[i].answers[j].id+'" value="'+ques[i].answers[j].id+'"  name="answer">';
                        else
                            contentFob +='<input type="checkbox" id="'+ques[i].answers[j].id+'" value="'+ques[i].answers[j].id+'"  name="answer">';
                    }

                    contentFob +=ques[i].answers[j].answer ;
                    var aFeedback = "";
                    if(ques[i].answers[j].selected==0)
                        aFeedback = ques[i].answers[j].feedback;
                    if(typeof(aFeedback)=="undefined")
                        aFeedback = "";
                    contentFob +='</label><span class="special-feedback">'+aFeedback+'</span></li>';
                }
                contentFob += '</ul>';
                var certaintyLabels = '';

                if(config.feedbackType==2){
                    if(ques[i].certainty==0){
                        certaintyLabels='<label><input type="radio" checked name="certainty-'+i+'" value="0"/>不确定</label>'+
                            '<label><input type="radio" name="certainty-'+i+'" value="1"/>0~30%</label>'+
                            '<label><input type="radio" name="certainty-'+i+'" value="2" />30~60%</label>'+
                            '<label><input type="radio" name="certainty-'+i+'" value="3" />60~100%</label>';
                    }else if(ques[i].certainty==1){
                        certaintyLabels='<label><input type="radio" name="certainty-'+i+'" value="0"/>不确定</label>'+
                            '<label><input type="radio" checked name="certainty-'+i+'" value="1"/>0~30%</label>'+
                            '<label><input type="radio" name="certainty-'+i+'" value="2" />30~60%</label>'+
                            '<label><input type="radio"   name="certainty-'+i+'" value="3" />60~100%</label>';
                    }else if(ques[i].certainty==2){
                        certaintyLabels='<label><input type="radio" name="certainty-'+i+'" value="0"/>不确定</label>'+
                            '<label><input type="radio" name="certainty-'+i+'" value="1"/>0~30%</label>'+
                            '<label><input type="radio" checked   name="certainty-'+i+'" value="2" />30~60%</label>'+
                            '<label><input type="radio" name="certainty-'+i+'" value="3" />60~100%</label>';
                    }else if(ques[i].certainty==3){
                        certaintyLabels='<label><input type="radio" name="certainty-'+i+'" value="0"/>不确定</label>'+
                            '<label><input type="radio" name="certainty-'+i+'" value="1"/>0~30%</label>'+
                            '<label><input type="radio" name="certainty-'+i+'" value="2" />30~60%</label>'+
                            '<label><input type="radio" checked  name="certainty-'+i+'" value="3" />60~100%</label>';
                    }else{
                        certaintyLabels='<label><input type="radio" checked name="certainty-'+i+'" value="0"/>不确定</label>'+
                            '<label><input type="radio" name="certainty-'+i+'" value="1"/>0~30%</label>'+
                            '<label><input type="radio" name="certainty-'+i+'" value="2" />30~60%</label>'+
                            '<label><input type="radio" name="certainty-'+i+'" value="3" />60~100%</label>';
                    }
                    contentFob +='<div class="cbm row"><span class="col-md-12">我做对本题的概率</span>'+
                        '<div class="col-md-12">' +
                        certaintyLabels+
                        '</div> </div>';
                }
                if(config.feedbackType==3){
                    var award_point=ques[i].awardPoint,userIdea =ques[i].userIdea,teacherFeedback=ques[i].teacherFeedback;
                    if(typeof(userIdea)!="undefined" && userIdea!=""){
                        contentFob +='<div class="idea row"><span class="col-md-12">想和老师进一步交流的问题</span>'+
                            '<div class="col-md-12"><textarea rows="3" cols="50" name="userIdea" class="form-control">'+userIdea+'</textarea></div> </div>';
                    }
                    if(typeof(teacherFeedback)!="undefined" && teacherFeedback!=""){
                        contentFob +='<div class="cbm row"><span class="col-md-12">教师评价'+award_point+'</span>'+
                            '<div class="col-md-12"><textarea rows="3" cols="50" name="teacherFeedback" class="form-control">'+teacherFeedback+'</textarea></div> </div>';
                    }else if(typeof(teacherFeedback)=="undefined" &&award_point!="" && typeof(award_point)!="undefined" ){
                        contentFob +='<div class="cbm row"><span class="col-md-12">教师评价'+award_point+'</span></div>';
                    }
                }
                contentFob += '<div class="check-button"><span class="check-info"></span></div>';
                if(typeof(ques[i].generalFeedback)!="undefined" && ques[i].generalFeedback!='' )
                    contentFob+='<div class="general-feedback">'+ques[i].generalFeedback+'</div>';
            }else if(typeof  ques[i].finished == "undefined" || ques[i].finished==0 ){
                questionNavFob+='<a ><span>'+(i+1)+'</span></a>';
                contentFob += '<div class="slider-container" id="question-'+ques[i].id+'" resultFlag="" completed="" questionId="'+ques[i].id+'" data-attributes="'+i+'">' +
                    '<div class="question-body">' +
                    '<div class="question-header row">' +
                    '<div class="col-md-12">' +
                    '<span class="q-seq">'+(i+1)+'</span>' +
                    '<a class="flag" title="标记有疑问的题目" data-attributes="'+i+'"><span class="fa fa-flag"></span></a>' +
                    '<span class="question-type">'+ques[i].qTypeName+'<label class="q-point">('+ques[i].defaultMark+'分)</label></span>'+
                    '</div>'+
                    '<div class="col-md-12">'+
                    '<div class="question-title">'+ques[i].questionText + '</div>' +
                    '</div>' +
                    '</div>' +
                    '<ul class="answers">';
                for (var j = 0; j < ques[i].answers.length; j++) {
                    contentFob += '<li><label>';
                    if(ques[i].qTypeShortName=='sc')
                        contentFob +='<input type="radio" id="'+ques[i].answers[j].id+'" value="'+ques[i].answers[j].id+'"  name="answer-'+i+'">';
                    else if(ques[i].qTypeShortName=='mc')
                        contentFob +='<input type="checkbox" id="'+ques[i].answers[j].id+'" value="'+ques[i].answers[j].id+'"  name="answer-'+i+'">';
                    contentFob +=ques[i].answers[j].answer +
                        '</label><span class="special-feedback none"></span></li>';
                }
                contentFob += '</ul>';
                if(config.feedbackType==2){
                    contentFob +='<div class="cbm row"><span class="col-md-12">我做对本题的概率</span>'+
                        '<div class="col-md-12">' +
                        '<label><input type="radio" checked name="certainty-'+i+'" value="0"/>不确定</label>'+
                        '<label><input type="radio" name="certainty-'+i+'" value="1"/>0~30%</label>'+
                        '<label><input type="radio" name="certainty-'+i+'" value="2" />30~60%</label>'+
                        '<label><input type="radio" name="certainty-'+i+'" value="3" />60~100%</label>'+
                        '</div> </div>';
                }else if(config.feedbackType==3){
                    contentFob +='<div class="idea row none"><span class="col-md-12">想和老师进一步交流的问题</span>'+
                        '<div class="col-md-12"><textarea rows="3" cols="50" name="userIdea" class="form-control"/></div> </div>';
                }
                if(config.feedbackType==0 || config.feedbackType==1){
                    contentFob += '<div class="check-button"><input class="checkAnswer btn btn-success" type="button" value="提交答案" ><input class="retake btn btn-success" type="button" value="重新作答" ><span class="check-info"></span></div>';
                }else{
                    contentFob += '<div class="check-button"><input class="checkAnswer btn btn-success" type="button" value="提交答案" ><span class="check-info"></span></div>';
                    contentFob += '<div class="submit-idea none"><input class="submitIdea btn btn-warning" type="button" value="提交问题" ></div>';
                }

                contentFob+='<div class="general-feedback none"></div>';
            }

            contentFob += '<div class="pn-nav">';
            if (i !== 0) {
                contentFob += '<div class="nav-prev"><a><span class="fa fa-hand-o-left"></span>上一题</a></div>';
            }
            if (i < ques.length - 1) {
                contentFob += '<div class="nav-next"><a><span class="fa fa-hand-o-right"></span>下一题</a></div>';
            } else {
                contentFob += '<div class="final"><input class="btn btn-success small-btn" type="button" value="提交测试"/></div>';
            }
            contentFob += '</div></div></div>';
        }
        questionNavFob+='<a  class="result-a none"><span>Result</span></a></div>';
        superContainer.html(questionNavFob +timeFob+ introFob + contentFob + finishFob);

        var slidesList = superContainer.find('.slider-container');

        slidesList.hide().first().fadeIn(50);
        superContainer.find('li label').click(function() {
            var index = parseInt($(this).parents('.slider-container').attr("data-attributes"));
            superContainer.find('.question-nav a span').eq(index+1).css("background-color","#D1EFD1");
            $(this).parents(".slider-container").find(".check-button .check-info").html("");
            $(this).parents(".slider-container").attr("completed", 0);
        });

        //question nav
        superContainer.find('.question-nav a:eq(0) span').css({"height":"45px","width":"45px","border-color":"red"});
        superContainer.find('.question-nav a').click(function() {

            $(this).parents(".question-nav").find("a span").css({"height":"40px","width":"40px","border-color":"#F6F6F6"});
            $(this).find("span").css({"height":"45px","width":"45px","border-color":"red"});
            var i = $(this).text();
            if(i=="Result") {
                slidesList.hide();
                $('.results-container').fadeIn(50);
                return false;
            }
            var a=0;
            if(i!="Intro")
                a = parseInt(i);
            slidesList.hide().eq(a).fadeIn(50);
            return false;
        });

        //flag question
        superContainer.find('a.flag').click(function() {
            var index = parseInt($(this).attr("data-attributes"));
            var oSpan = $('.question-nav a').eq(++index).find("span")
            if(oSpan.hasClass("flagged")){
                oSpan.attr("class","");
            }else{
                oSpan.addClass("fa fa-flag flagged");
            }
            return false;
        });

        superContainer.find('.nav-next').click(function() {
            var index = parseInt($(this).parents('.slider-container').attr("data-attributes"));
            superContainer.find(".question-nav a span").css({"height":"40px","width":"40px","border-color":"#F6F6F6"});
            superContainer.find(".question-nav a span").eq(index+2).css({"height":"45px","width":"45px","border-color":"red"});
            $(this).parents('.slider-container').fadeOut(50,
                function() {
                    $(this).next().fadeIn(50);
                });
            return false;
        });
        superContainer.find('.nav-prev').click(function() {
            var index = parseInt($(this).parents('.slider-container').attr("data-attributes"));
            superContainer.find(".question-nav a span").css({"height":"40px","width":"40px","border-color":"#F6F6F6"});
            superContainer.find(".question-nav a span").eq(index).css({"height":"45px","width":"45px","border-color":"red"});
            $(this).parents('.slider-container').fadeOut(50,
                function() {
                    $(this).prev().fadeIn(50);
                });
            return false;
        });

        superContainer.find('#finish a').click(function(){
                window.close();
        });

        $(".submitIdea").click(function(){
            var o = $(this),userIdea,qAttemptId;
            qAttemptId = o.parent().attr("data-attributes");
            userIdea = o.parent().siblings(".idea").find("textarea").val();
            if(qAttemptId==0){
                layer.load('教师预览不会提交问题...',3);
                return;
            }
            if(userIdea==""){
                layer.alert('请先填写您的问题...', {icon: 5});
                return;
            }
            userIdea = encodeURI(userIdea);
            $.ajax({
                type: 'POST',
                dataType:'json',
                url: 'submitIdea.do',
                data: {"qAttemptId":qAttemptId,"userIdea":userIdea},
                success:function(data){
                    if(data=="ok"){
                        layer.load('提问成功，老师会及时回复。',3);
                        o.removeClass("block").addClass("none");
                    }


                },error:function(){
                    layer.load('链接服务器失败，请稍后尝试...',3);
                    return false;
                }
            });

        });

        //check answer
        $(".checkAnswer").click(function () {
            var o = $(this);
            var oSpan = $(this).parents(".slider-container").attr("completed");
            var oCheck = $(this).parent(".check-button").find("span");
            var parent = $(this).parents('.slider-container');
            if (oSpan == "") {
                oCheck.html("").show().html("请先填写答案...");
            } else if (oSpan == 0) {
                var userAnswer = [];
                var quizId, questionId;
                quizId = config.quizId;
                questionId = parent.attr("questionId");
                var oSelected = parent.find("input[name^=answer]:checked");
                for (var i = 0; i < oSelected.length; i++) {
                    userAnswer.push(oSelected.eq(i).val());
                }
                var certainty = 0;
                var oCertainty = parent.find(".cbm input[type='radio']:checked");
                if (typeof(oCertainty.html()) != "undefined")
                    certainty = oCertainty.val();
                $.ajax({
                    type: 'POST',
                    dataType: 'json',
                    url: '/service/question/check_answer',
                    headers: {
                        "username": getCookie("username"),
                        "token": getCookie("token"),
                        "type": getCookie("type")
                    },
                    data: JSON.stringify({
                        "quizId": quizId, "attemptId": config.attemptId,
                        "questionId": questionId, "userAnswer": userAnswer.toString(),
                        "certainty": certainty
                    }),
                    success: function (feedback) {
                        o.attr('disabled', "true");
                        o.addClass("none");
                        //0 right 1 partly right 2 wrong
                        var aResult, qResult, qAttemptId;
                        qAttemptId = feedback.questionAttemptId;
                        var ans = feedback.answers;
                        if (ans.length > 0) {
                            for (var i = 0; i < ans.length; i++) {
                                if (ans[i].resultFlag == 0)
                                    aResult = "<span class='fa fa-check completely-right'></span>";
                                else if (ans[i].resultFlag == 1 || ans[i].resultFlag == 2)
                                    aResult = "<span class='fa fa-close wrong'></span>";
                                if (typeof(ans[i].specialFeedback) != "undefined")
                                    aResult += ans[i].specialFeedback;
                                $("#" + ans[i].id).parent("label").next().html(aResult).removeClass("none").addClass("inline-block");
                            }
                        }

                        var qGrade = "<span class='grade' grade='" + feedback.grade + "'>" + (feedback.grade).toFixed(2) + "分</span>"
                        if (feedback.resultFlag == 0)
                            qResult = "<span class='wr fa fa-2x fa-check completely-right'></span>";
                        else if (feedback.resultFlag == 1)
                            qResult = "<span class='wr fa fa-2x fa-check partly-right'></span>";
                        else if (feedback.resultFlag == 2)
                            qResult = "<span class='wr fa fa-2x fa-close wrong'></span>";
                        var oQues = $("#question-" + questionId);
                        oQues.find(".question-header div div").append(qResult + qGrade);
                        oQues.attr("resultFlag", feedback.resultFlag);
                        var gf = feedback.generalFeedback;
                        if (gf != "" && typeof(gf) != "undefined")
                            $("#question-" + questionId).find(".general-feedback").html(gf).removeClass("none").addClass("block");
                        var aClass = "fa fa-check completely-right";
                        if (feedback.resultFlag == 2)
                            aClass = "fa fa-close wrong";
                        var aIndex = o.parents(".slider-container").index();
                        superContainer.find(".question-nav a").eq(aIndex - 2).find("span").addClass(aClass);
                        if (config.feedbackType == 3) {
                            o.parent().siblings(".idea").removeClass("none").addClass("block");
                            o.parent().siblings(".submit-idea").attr("data-attributes", qAttemptId).removeClass("none").addClass("block");
                            o.parent().remove();
                        }
                    },
                    error: function () {
                        oCheck.html("").show().html("链接服务器失败，请稍后尝试...");
                        return false;
                    }
                });
            }
            return false;
        });

        $(".retake").click(function(){
            var rf = $(this).parents(".slider-container").attr("completed");
            if(rf==""){
                layer.alert('您尚未作答当前题目...', {icon: 5});
                return false;
            }
            $(this).siblings(".checkAnswer").removeAttr("disabled");
            var items = $(this).parents(".slider-container").find("ul li label input");
            items.each(function(){
               $(this).removeAttr("checked");
            });
            var aIndex = $(this).parents(".slider-container").index();
            superContainer.find(".question-nav a").eq(aIndex-2).find("span").attr("class","");
            $(this).parents(".slider-container").attr("completed","");
            $(this).parents(".slider-container").find("ul li span.special-feedback").html("").removeClass("inline-block").addClass("none");
            $(this).parents(".slider-container").find(".question-title span.wr").remove();
            $(this).parents(".slider-container").find(".question-title span.grade").remove();
            $(this).parents(".slider-container").find(".general-feedback").html("").removeClass("block").addClass("none");

            return false;
        });
        var submitButton = $("#submitTest");

        submitButton.click(function(){
            superContainer.find('.final').click();
        });
        //finish quiz
        superContainer.find('.final').click(function () {
            var results = [],
                resultSet = '',
                score = 0,
                completed = 0;
            var oD = $("#quiz-container").find(".slider-container");
            for (var i = 1; i < oD.length - 1; i++) {
                if ($(oD[i]).attr("completed") == "" || $(oD[i]).attr("resultFlag") == "") {
                    completed = 1;
                    results.push({
                        "questionId": $(oD[i]).attr("questionId"),
                        "resultFlag": 2,
                        "grade": 0
                    });
                } else
                    results.push({
                        "questionId": $(oD[i]).attr("questionId"),
                        "resultFlag": $(oD[i]).attr("resultFlag"),
                        "grade": parseFloat($(oD[i]).find(".grade").attr("grade"))
                    });
            }
            if (submitButton.attr("data-attributes") == "going") {
                if (completed == 1) {
                    layer.alert('您还有题目没有提交答案，请先提交所有题目的答案...', {icon: 5});
                    return false;
                }
            }
            $("input.checkAnswer").attr("disabled", "disabled");
            $("input.retake").attr("disabled", "disabled");
            $("input[type='radio']").attr("disabled", "disabled");
            $("input[type='checkbox']").attr("disabled", "disabled");
            submitButton.attr("disabled", "true");
            $(this).find("input").attr("disabled", "true");
            for (var i = 0; i < results.length; i++) {
                score += results[i].grade;
                resultSet += '<div class="result-row">';
                if (results[i].resultFlag == 0)
                    resultSet += '<div class="result-right" onclick="showQuestion(this)" data-attributes="' + (i + 1) + '">#' + (i + 1) + '<span class="fa fa-check"></span></div>';
                else if (results[i].resultFlag == 1)
                    resultSet += '<div class="result-partly" onclick="showQuestion(this)" data-attributes="' + (i + 1) + '">#' + (i + 1) + '<span class="fa fa-check"></span></div>';
                else if (results[i].resultFlag == 2)
                    resultSet += '<div class="result-wrong" onclick="showQuestion(this)" data-attributes="' + (i + 1) + '">#' + (i + 1) + '<span class="fa fa-close"></span></div>';
                else
                    resultSet += '<div class="result-wrong" onclick="showQuestion(this)" data-attributes="' + (i + 1) + '">#' + (i + 1) + '<span class="fa fa-close"></span></div>';
                resultSet += '</div>';
            }
            $.ajax({
                type: 'POST',
                dataType: 'json',
                url: '/service/attempt/finish/quiz/' + config.attemptId,
                headers: {
                    "username": getCookie("username"),
                    "token": getCookie("token"),
                    "type": getCookie("type")
                },
                data: JSON.stringify({"quizId": config.quizId, "userSumGrades": score.toFixed(2)}),
                success: function (fb) {
                    layer.alert("答题保存成功，可安全关闭本页面！", {icon: 2});
                    $(".result-a").removeClass("none").addClass("inline-block");
                    if (typeof(fb.gradeFeedback) != "undefined" && fb.gradeFeedback != "")
                        $(".grade-feedback").html(fb.gradeFeedback).removeClass("none").addClass("block");
                },
                error: function (data) {
                    if (data.status == 403) {
                        layer.alert("认证失败，请重新登录！", {icon: 5});
                        window.location.href = "index.html";
                    }
                }
            });

            resultSet = '<h2 class="qTitle">本次测试得分： ' + score.toFixed(2) + '/' + (config.quizSumGrades).toFixed(2) + '</h2>' + resultSet;
            superContainer.find('.result-keeper').html(resultSet).show(50);

            superContainer.find(".question-nav a span").css({
                "height": "40px",
                "width": "40px",
                "border-color": "#F6F6F6"
            });
            superContainer.find(".question-nav a span").last().css({
                "height": "45px",
                "width": "45px",
                "border-color": "red"
            });
            $(this).parents('#quiz-container .slider-container').fadeOut(50,
                function () {
                    $(this).parents('#quiz-container').find(".slider-container").hide().last().fadeIn(50);
                });
            return false;
        });
    };
})(jQuery);