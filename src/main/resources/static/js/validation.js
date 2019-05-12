$(document).ready(function () {
    var validLogin = false;
    var validPassword = false;
    var validRePassword = false;
    var hasRole = false;
    var loginRegex = /^[a-zA-Z][a-zA-Z0-9-_\.]{1,20}$/;
    var passwordRegex = /(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[А-ЯЁA-Z])(?=.*[а-яеa-z]).*$/;

    $("#form1").submit(function (event) {
        event.preventDefault();
        $('#loginMessage').hide();
        $('#passwordMessage').hide();
        $('#rePassMessage').hide();
        $('#roleMessage').hide();

        var login = $("#userLogin").val();
        var password = $("#userPassword").val();
        var rePassword = $("#rePassword").val();

        if (loginRegex.test(login) == false) {
            $("#userLogin").parent().removeClass("has-success").addClass("has-error");
            // $(".nameBlock").append("<span class='glyphicon glyphicon-remove form-control-feedback' aria-hidden='true'></span>");
            $(".nameBlock .glyphicon-ok").remove();
            $('#loginMessage').html("Логин пользователя не соответствует шаблону");
            $('#loginMessage').show();
        } else {
            $("#userLogin").parent().removeClass("has-error").addClass("has-success");
            // $(".nameBlock").append("<span class='glyphicon glyphicon-ok form-control-feedback' aria-hidden='true'></span>");
            $(".nameBlock .glyphicon-remove").remove();
            validLogin = true;
        }

        if (passwordRegex.test(password) == false) {
            $("#userPassword").parent().removeClass("has-success").addClass("has-error");
            // $(".passwordBlock").append("<span class='glyphicon glyphicon-remove form-control-feedback' aria-hidden='true'></span>");
            $(".passwordBlock .glyphicon-ok").remove();
            $('#passwordMessage').html("Пароль не соответствует шаблону");
            $('#passwordMessage').show();
        } else {
            $("#userPassword").parent().removeClass("has-error").addClass("has-success");
            // $(".passwordBlock").append("<span class='glyphicon glyphicon-ok form-control-feedback' aria-hidden='true'></span>");
            $(".passwordBlock .glyphicon-remove").remove();
            validPassword = true;

            if (password != rePassword) {
                $("#rePassword").parent().removeClass("has-success").addClass("has-error");
                // $(".rePassBlock").append("<span class='glyphicon glyphicon-remove form-control-feedback' aria-hidden='true'></span>");
                $(".rePassBlock .glyphicon-ok").remove();
                $('#rePassMessage').html("Пароли не совпадают");
                $('#rePassMessage').show();
            } else {
                $("#rePassword").parent().removeClass("has-error").addClass("has-success");
                $(".rePassBlock").append("<span class='glyphicon glyphicon-ok form-control-feedback' aria-hidden='true'></span>");
                $(".rePassBlock .glyphicon-remove").remove();
                validRePassword = true;
            }
        }

        $.each($("input[id='role']:checked"), function () {
            hasRole = true;
        });

        if (!hasRole) {
            $('#roleMessage').html("Права пользователя не заданы");
            $('#roleMessage').show();
        }

        if (validLogin && validPassword && validRePassword && hasRole) {
            $("#form1").unbind('submit').submit();
        }
    });
});