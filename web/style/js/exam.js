(function($) {
    $.fn.jQuiz = function(settings) {
        var defaults = {
            quizId:0,
            attemptId:null,
            minutesAllowed:-1,
            userId:null,
            intro:'',
            quizName:'',
            questions: null
        };
        var config = $.extend(defaults, settings);

        if (config.questions === null || config.questions.length===0) {
            $(this).html('<div class="slider-container"><div class="center v-center">测试中没有题目可供作答...</div></div>');
            return;
        }
        var superContainer = $(this),
        ques =[],
        introFob='',fixedIntro1='<div class="slider-container" data-attributes="-1">'+
            '<div class="intro-body">'+
                '<div class="intro-header center">测试Tips</div>' ,
        fixedIntro2='<div><span class="fa fa-warning warning "></span><p>测试中不要关闭和刷新页面，本考试只有一次作答机会。</p></div>'+
                '<div><span class="fa fa-warning warning "></span><p>所有题目完成后，请点击【提交测试】，否则答案无法保存！</p></div>',
        contentFob = '',
        finishFob = '<div class="results-container slider-container"><div class="question-body center v-center">考试已提交，考试成绩稍后发布。</div>'+
            '<div class="pn-nav"><div id="finish"><a><span class="fa fa-hand-o-right"></span>结束测试</a></div></div>'+
            '</div>',
        timeFob='<div class="countdownTime"><span id="countdownTime"></span>' +
                '<input type="button" id="submitTest" class="btn btn-success" value="提交测试"/></div>',
        questionNavFob='<div class="question-nav"><a><span>Intro</span></a>';
        introFob += fixedIntro1;
        if(config.intro!="")
            introFob+='<div class="teacher-edit-intro"><span class="fa fa-warning warning"></span>'+config.intro+'</div>';
        introFob += fixedIntro2;
        superContainer.addClass('main-quiz-holder');
        if(config.minutesAllowed==-1)
            timeFob='<div class="countdownTime">' +
                '<input type="button" id="submitTest" class="btn btn-success" value="提交测试"/></div>';

        introFob+='</div><div class="pn-nav">'+
                    '<div class="nav-next"><a><span class="fa fa-hand-o-right"></span>开始测试</a></div>'+
                   '</div></div>';
        if(config.questions instanceof Array)
            ques = config.questions;
        else if(config.questions instanceof Object)
            ques.push(config.questions);
        for (var i = 0; i < ques.length; i++) {
            questionNavFob+='<a ><span>'+(i+1)+'</span></a>';
            contentFob += '<div class="slider-container" id="question-'+ques[i].id+'" completed="" questionId="'+ques[i].id+'" data-attributes="'+i+'">' +
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
                    '</label><span class="special-feedback"></li></span>';
            }
            contentFob += '</ul>';
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
        questionNavFob+='</div>';
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
            var i = $(this).text();
            var a=0;
            if(i!="Intro")
                a = parseInt(i);
            $('.slider-container').hide().eq(a).fadeIn(50);
            $(this).parents(".question-nav").find("a span").css({"height":"40px","width":"40px","border-color":"#F6F6F6"});
            $(this).find("span").css({"height":"45px","width":"45px","border-color":"red"})
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

        superContainer.find('#finish a').click(function(){
            window.close();
        })

        $("#submitTest").click(function(){
            superContainer.find('.final').click();
        });

        //finish quiz
        superContainer.find('.final').click(function(){
            var sButton = $(this).find("input");
            sButton.attr("disabled", "true");

            var results = [],completed=0;
            var oD = $("#quiz-container").find(".slider-container");
            for(var i=1;i<oD.length-1;i++){
                var userAnswer = [];
                if($(oD[i]).attr("completed")=="")
                    completed=1;
                var oSelected = $(oD[i]).find("input:checked");
                $.each(oSelected,function(i){
                    userAnswer.push($(this).val())
                })
                results.push({"questionId":$(oD[i]).attr("questionId"),"userAnswer":userAnswer.toString()});
            }

            if(completed==1){
                if (confirm("还有题目没有完成，是否坚持提交？"))
                    completed=0;
                else{
                    sButton.removeAttr("disabled");
                    return false;
                }
            }
            $("#submitTest").attr("disabled","true");
            $(this).find("input").attr("disabled", "true");
            $(this).parents('#quiz-container .slider-container').fadeOut(50,
                function() {
                    $(this).parents('#quiz-container').find(".slider-container").hide().last().fadeIn(50);
                });
            if(config.attemptId!=0){
                $.ajax({
                    type: 'POST',
                    dataType: 'json',
                    url: '/service/attempt/finish/exam/'+config.attemptId,
                    headers: {
                        "username": getCookie("username"),
                        "token": getCookie("token"),
                        "type": getCookie("type")
                },
                    data:  JSON.stringify({ "userResults":results}),
                    success:function(){
                        layer.alert("答题保存成功，可安全关闭本页面！", {icon: 2});
                    },
                    error:function () {
                        if (data.status == 403) {
                            layer.alert("认证失败，请重新登录！", {icon: 5});
                            window.location.href = "index.html";
                        }
                    }
                });
            }
            return false;
        });
    };
})(jQuery);