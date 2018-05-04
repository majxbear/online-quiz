(function($) {
    $.fn.jQuiz = function(settings) {
        var defaults = {
            quizId:0,
            feedbackType:0,
            userSumGrades:0,
            quizSumGrades:0,
            gradeFeedback:'',
            attemptId:null,
            userId:null,
            intro:'',
            quizName:'',
            questions: null
        };
        var config = $.extend(defaults, settings);

        if (config.questions === null || config.questions.length===0) {
            $(this).html('<div class="slider-container"><div class="question-body center v-center">测试中没有任何题目...</div></div>');
            return;
        }
        var superContainer = $(this),
            ques =[],
            introFob='',
            fixedIntro1='<div class="slider-container" data-attributes="-1">'+
                '<div class="intro-body">'+
                '<div class="intro-header center">测试Tips</div>',
            fixedIntro2= '<div><span class="fa fa-warning warning "></span><p>每个题目分别点【提交答案】查看反馈，获取参考答案、题目解析等。</p></div>',
            contentFob = '',
            finishFob = '',
            timeFob='<div class="countdownTime">' +
                '<input type="button" id="finishReview" class="btn btn-success" value="结束回顾"/></div>',
            questionNavFob='<div class="question-nav"><a><span>Intro</span></a>';
        if(typeof(config.gradeFeedback)!="undefined" && config.gradeFeedback!="")
            finishFob+='<div class="results-container slider-container"><div class="question-body center v-center"><h2 class="qTitle" style="line-height: 200px">本次测试得分： ' + (config.userSumGrades).toFixed(2) +'/'+(config.quizSumGrades).toFixed(2)+ '</h2></div>'+
                '<div class="grade-feedback">'+config.gradeFeedback+'</div>'+
            '</div>';
        else
            finishFob+='<div class="results-container slider-container"><div class="question-body center v-center"><h2 class="qTitle" style="line-height: 200px">本次测试得分： ' + (config.userSumGrades).toFixed(2) +'/'+(config.quizSumGrades).toFixed(2)+ '</h2></div>'+
                '</div>';
        superContainer.addClass('main-quiz-holder');
        introFob += fixedIntro1;
        if(config.intro!="")
            introFob+='<div class="teacher-edit-intro"><span class="fa fa-warning warning"></span>'+config.intro+'</div>';
        introFob += fixedIntro2;
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
            var qGrade ="<span class='grade'>"+(ques[i].grade).toFixed(2)+"分</span>";
            var qResult = "";
            if(ques[i].resultFlag==0){
                qResult = "<span class='fa fa-2x fa-check completely-right'></span>";
                questionNavFob+='<a ><span class="fa fa-check completely-right">'+(i+1)+'</span></a>';
            }else if(ques[i].resultFlag==1){
                qResult = "<span class='fa fa-2x fa-check partly-right'></span>";
                questionNavFob+='<a ><span class="fa fa-check partly-right">'+(i+1)+'</span></a>';
            }else if(ques[i].resultFlag ==2){
                qResult = "<span class='fa fa-2x fa-close wrong'></span>";
                questionNavFob+='<a ><span class="fa fa-close wrong">'+(i+1)+'</span></a>';
            }
            contentFob += '<div class="slider-container" id="question-'+ques[i].id+'" resultFlag="" completed="" questionId="'+ques[i].id+'" data-attributes="'+i+'">' +
                '<div class="question-body">' +
                    '<div class="question-header row">' +
                        '<div class="col-md-12">' +
                            '<span class="q-seq">'+(i+1)+'</span>' +
                            '<span class="question-type">'+ques[i].qTypeName+'<label class="q-point">('+ques[i].defaultMark+'分)</label></span>'+
                        '</div>'+
                    '<div class="col-md-12">';
          contentFob += '<div class="question-title">'+ques[i].questionText +''+(qResult+qGrade)+ '</div>'+
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

                contentFob +=html_decode(format_path(back_quot(ques[i].answers[j].answer),config.editorPath)) ;
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
            if(typeof(ques[i].generalFeedback)!="undefined" && ques[i].generalFeedback!='' )
                contentFob+='<div class="general-feedback">'+ques[i].generalFeedback+'</div>';
            contentFob += '<div class="pn-nav">';
            if (i !== 0) {
                contentFob += '<div class="nav-prev"><a><span class="fa fa-hand-o-left"></span>上一题</a></div>';
            }
            if (i < ques.length - 1) {
                contentFob += '<div class="nav-next"><a><span class="fa fa-hand-o-right"></span>下一题</a></div>';
            } else {
                contentFob += '<div class="nav-next"><a><span class="fa fa-hand-o-right"></span>查看分数和反馈</a></div>';
            }
            contentFob += '</div></div></div>';
        }
        questionNavFob+='<a  class="result-a"><span>Result</span></a></div>';
        superContainer.html(questionNavFob + timeFob+introFob + contentFob+finishFob);

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
            $(this).find("span").css({"height":"45px","width":"45px","border-color":"red"})
            var i = $(this).text();
            var a=0;
            if(i=="Result") {
                slidesList.hide();
                $('.results-container').fadeIn(50);
                return false;
            }
            if(i!="Intro")
                a = parseInt(i);
            $('.slider-container').hide().eq(a).fadeIn(50);
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
        $("#finishReview").click(function(){
            window.close();
        });

    };
})(jQuery);