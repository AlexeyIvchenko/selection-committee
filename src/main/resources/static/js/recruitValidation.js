$(document).ready(function () {

    $('#userForm').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            userLogin: {
                message: 'Имя введено некорректно',
                validators: {
                    notEmpty: {
                        message: 'Поле не может быть пустым'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z][a-zA-Z0-9-_\.]{1,20}$/,
                        message: 'Логин может содержать буквы, цифры и знак подчеркивания'
                    }

                }
            },
            userPassword: {
                validators: {
                    notEmpty: {
                        message: 'Поле не может быть пустым'
                    },
                    identical: {
                        field: 'confirmPassword',
                        message: 'Пароли не совпадают'
                    },
                    regexp: {
                        regexp: /(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[А-ЯЁA-Z])(?=.*[а-яеa-z]).*$/,
                        message: 'Введите надежный пароль'
                    }
                }
            },
            confirmPassword: {
                validators: {
                    notEmpty: {
                        message: 'Поле не может быть пустым'
                    },
                    identical: {
                        field: 'userPassword',
                        message: 'Пароли не совпадают'
                    }
                }
            },
            roles: {
                validators: {
                    notEmpty: {
                        message: 'Выберите роль пользователя'
                    }
                }
            },
        }
    });

    $('#editPasswordForm').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            oldPassword: {
                validators: {
                    notEmpty: {
                        message: 'Поле не может быть пустым'
                    }
                }
            },
            newPassword: {
                validators: {
                    notEmpty: {
                        message: 'Поле не может быть пустым'
                    },
                    identical: {
                        field: 'confirmPassword',
                        message: 'Пароли не совпадают'
                    },
                    regexp: {
                        regexp: /(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[А-ЯЁA-Z])(?=.*[а-яеa-z]).*$/,
                        message: 'Введите надежный пароль'
                    }
                }
            },
            confirmPassword: {
                validators: {
                    notEmpty: {
                        message: 'Поле не может быть пустым'
                    },
                    identical: {
                        field: 'newPassword',
                        message: 'Пароли не совпадают'
                    }
                }
            }
        }
    });

    $('#registerForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            surname: {
                message: 'Фамилия введена некорректно',
                validators: {
                    notEmpty: {
                        message: 'Поле не может быть пустым'
                    },
                    stringLength: {
                        min: 2,
                        max: 30,
                        message: 'Фамилия должна содержать от 2 до 30 символов'
                    },
                    regexp: {
                        regexp: /^[А-ЯA-Z][а-яa-zА-ЯA-Z\-]{0,}(\s[А-ЯA-Z][а-яa-zА-ЯA-Z\-]{1,})?$/,
                        message: 'Фамилия может содержать только буквы и знак "-"'
                    }
                }
            },
            name: {
                message: 'Имя введено некорректно',
                validators: {
                    notEmpty: {
                        message: 'Поле не может быть пустым'
                    },
                    stringLength: {
                        min: 2,
                        max: 30,
                        message: 'Имя должно содержать от 2 до 30 символов'
                    },
                    regexp: {
                        regexp: /^[А-ЯA-Z][а-яa-zА-ЯA-Z\-]{0,}(\s[А-ЯA-Z][а-яa-zА-ЯA-Z\-]{1,})?$/,
                        message: 'Имя может содержать только буквы и знак "-"'
                    }
                }
            },
            secondName: {
                message: 'Отчество введено некорректно',
                validators: {
                    stringLength: {
                        min: 2,
                        max: 30,
                        message: 'Отчество должно содержать от 2 до 30 символов'
                    },
                    regexp: {
                        regexp: /^[А-ЯA-Z][а-яa-zА-ЯA-Z\-]{0,}(\s[А-ЯA-Z][а-яa-zА-ЯA-Z\-]{1,})?$/,
                        message: 'Отчество может содержать только буквы и знак "-"'
                    }
                }
            },
            birthday: {
                message: 'Дата рождения введена некорректно',
                validators: {
                    notEmpty: {
                        message: 'Поле не может быть пустым'
                    },
                    date: {
                        format: 'DD/MM/YYYY',
                        message: 'Дата рождения не соответствует шаблону'
                    }
                }
            },
            sex: {
                validators: {
                    notEmpty: {
                        message: 'Выберите одно из двух значений'
                    }
                }
            },
            familyStatus: {
                validators: {
                    notEmpty: {
                        message: 'Выберите одно из двух значений'
                    }
                }
            },
            nationality: {
                validators: {
                    notEmpty: {
                        message: 'Поле не может быть пустым'
                    }
                }
            },

            city: {
                message: 'Название города введено некорректно',
                validators: {
                    notEmpty: {
                        message: 'Поле не может быть пустым'
                    }
                    // stringLength: {
                    //     min: 2,
                    //     max: 20,
                    //     message: 'Название города должно содержать от 2 до 20 символов'
                    // },
                    // regexp: {
                    //     regexp: /^[А-ЯA-Z][а-яa-zА-ЯA-Z\-]{0,}(\s[А-ЯA-Z][а-яa-zА-ЯA-Z\-]{1,})?$/,
                    //     message: 'Название города может содержать только буквы и знак "-"'
                    // }
                }
            },
            villageName: {
                message: 'Название деревни/села введено некорректно',
                validators: {
                    stringLength: {
                        min: 2,
                        max: 30,
                        message: 'Название деревни/села должно содержать от 2 до 30 символов'
                    },
                    regexp: {
                        regexp: /^[А-ЯA-Z][а-яa-zА-ЯA-Z\-]{0,}(\s[А-ЯA-Z][а-яa-zА-ЯA-Z\-]{1,})?$/,
                        message: 'Название деревни/села может содержать только буквы и знак "-"'
                    }
                }
            },
            streetName: {
                message: 'Название улицы введено некорректно',
                validators: {
                    notEmpty: {
                        message: 'Поле не может быть пустым'
                    },
                    stringLength: {
                        min: 2,
                        max: 30,
                        message: 'Название улицы должно содержать от 2 до 40 символов'
                    }
                }
            },
            houseNumber: {
                message: 'Номер дома введен некорректно',
                validators: {
                    notEmpty: {
                        message: 'Поле не может быть пустым'
                    },
                    stringLength: {
                        min: 2,
                        max: 30,
                        message: 'Название улицы должно содержать от 2 до 40 символов'
                    },
                    regexp: {
                        regexp: /\d+[a-z/\d]*/,
                        message: 'Номер дома может быть следующих форматов: 78, 8, 99а, 82/1'
                    }
                }
            },
            blockNumber: {
                message: 'Корпус введен некорректно',
                validators: {
                    regexp: {
                        regexp: /^[1-9][0-9]?$/,
                        message: 'Корпус дома может быть числом от 1 до 99'
                    }
                }
            },
            apartmentNumber: {
                message: 'Номер квартиры введен некорректно',
                validators: {
                    regexp: {
                        regexp: /^([1-9][0-9]{0,2})$/,
                        message: 'Номер квартиры может быть числом от 1 до 999'
                    }
                }
            },
            passportNumber: {
                message: 'Поле заполнено некорректно',
                validators: {
                    notEmpty: {
                        message: 'Поле не может быть пустым'
                    }
                }
            },
            passportIssuedBy: {
                message: 'Поле заполнено некорректно',
                validators: {
                    notEmpty: {
                        message: 'Поле не может быть пустым'
                    }
                }
            },
            passportDate: {
                message: 'Дата выдачи пасспорта введена некорректно',
                validators: {
                    notEmpty: {
                        message: 'Поле не может быть пустым'
                    },
                    date: {
                        format: 'DD/MM/YYYY',
                        message: 'Дата выдачи пасспорта не соответствует шаблону'
                    }
                }
            }
        }
    });

    $('#examForm').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            scoreMath: {
                validators: {
                    notEmpty: {
                        message: 'Поле не может быть пустым'
                    },
                    regexp: {
                        regexp: /^[0-9]$|^[1-9][0-9]$|^(100)$/,
                        message: 'Введите корректное кол-во баллов'
                    }
                }
            },
            scoreRusLang: {
                validators: {
                    notEmpty: {
                        message: 'Поле не может быть пустым'
                    },
                    regexp: {
                        regexp: /^[0-9]$|^[1-9][0-9]$|^(100)$/,
                        message: 'Введите корректное кол-во баллов'
                    }
                }
            },
            scorePhysics: {
                validators: {
                    notEmpty: {
                        message: 'Поле не может быть пустым'
                    },
                    regexp: {
                        regexp: /^[0-9]$|^[1-9][0-9]$|^(100)$/,
                        message: 'Введите корректное кол-во баллов'
                    }
                }
            },
            scoreForeignLang: {
                validators: {
                    notEmpty: {
                        message: 'Поле не может быть пустым'
                    },
                    regexp: {
                        regexp: /^[0-9]$|^[1-9][0-9]$|^(100)$/,
                        message: 'Введите корректное кол-во баллов'
                    }
                }
            },
            scoreHistory: {
                validators: {
                    notEmpty: {
                        message: 'Поле не может быть пустым'
                    },
                    regexp: {
                        regexp: /^[0-9]$|^[1-9][0-9]$|^(100)$/,
                        message: 'Введите корректное кол-во баллов'
                    }
                }
            },
            scoreSocial: {
                validators: {
                    notEmpty: {
                        message: 'Поле не может быть пустым'
                    },
                    regexp: {
                        regexp: /^[0-9]$|^[1-9][0-9]$|^(100)$/,
                        message: 'Введите корректное кол-во баллов'
                    }
                }
            },
            scoreLiterature: {
                validators: {
                    notEmpty: {
                        message: 'Поле не может быть пустым'
                    },
                    regexp: {
                        regexp: /^[0-9]$|^[1-9][0-9]$|^(100)$/,
                        message: 'Введите корректное кол-во баллов'
                    }
                }
            },

        }
    });

    $('#certificateForm').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            scoreMath: {
                validators: {
                    notEmpty: {
                        message: 'Поле не может быть пустым'
                    },
                    regexp: {
                        regexp: /^[0-9]$|^[1-9][0-9]$|^(100)$/,
                        message: 'Введите корректное кол-во баллов'
                    }
                }
            },
            scoreRusLang: {
                validators: {
                    notEmpty: {
                        message: 'Поле не может быть пустым'
                    },
                    regexp: {
                        regexp: /^[0-9]$|^[1-9][0-9]$|^(100)$/,
                        message: 'Введите корректное кол-во баллов'
                    }
                }
            },
            scorePhysics: {
                validators: {
                    notEmpty: {
                        message: 'Поле не может быть пустым'
                    },
                    regexp: {
                        regexp: /^[0-9]$|^[1-9][0-9]$|^(100)$/,
                        message: 'Введите корректное кол-во баллов'
                    }
                }
            },
            scoreForeignLang: {
                validators: {
                    notEmpty: {
                        message: 'Поле не может быть пустым'
                    },
                    regexp: {
                        regexp: /^[0-9]$|^[1-9][0-9]$|^(100)$/,
                        message: 'Введите корректное кол-во баллов'
                    }
                }
            },
            scorePhysicalCulture: {
                validators: {
                    notEmpty: {
                        message: 'Поле не может быть пустым'
                    },
                    regexp: {
                        regexp: /^[0-9]$|^[1-9][0-9]$|^(100)$/,
                        message: 'Введите корректное кол-во баллов'
                    }
                }
            },
            scoreHistory: {
                validators: {
                    notEmpty: {
                        message: 'Поле не может быть пустым'
                    },
                    regexp: {
                        regexp: /^[0-9]$|^[1-9][0-9]$|^(100)$/,
                        message: 'Введите корректное кол-во баллов'
                    }
                }
            },
            scoreSocial: {
                validators: {
                    notEmpty: {
                        message: 'Поле не может быть пустым'
                    },
                    regexp: {
                        regexp: /^[0-9]$|^[1-9][0-9]$|^(100)$/,
                        message: 'Введите корректное кол-во баллов'
                    }
                }
            },
            scoreLiterature: {
                validators: {
                    notEmpty: {
                        message: 'Поле не может быть пустым'
                    },
                    regexp: {
                        regexp: /^[0-9]$|^[1-9][0-9]$|^(100)$/,
                        message: 'Введите корректное кол-во баллов'
                    }
                }
            },

        }
    });

    $('#extranceTestForm').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            hbResult: {
                validators: {
                    notEmpty: {
                        message: 'Поле не может быть пустым'
                    },
                    regexp: {
                        regexp: /^(100|[1-9]?[0-9])$/,
                        message: 'Введите корректное кол-во подтягиваний к турнику (0-100)'
                    }
                }
            },
            run100mResult: {
                validators: {
                    notEmpty: {
                        message: 'Поле не может быть пустым'
                    },
                    regexp: {
                        regexp: /^[0-9]{1,2}([.][0-9]{1,2})?$/,
                        message: 'Введите корректное время'
                    }
                }
            },
            run3kmResult: {
                validators: {
                    notEmpty: {
                        message: 'Поле не может быть пустым'
                    },
                    regexp: {
                        regexp: /^[0-9]{1,2}([.][0-9]{1,2})?$/,
                        message: 'Введите корректное время'
                    }
                }
            },
            prof_group: {
                validators: {
                    notEmpty: {
                        message: 'Поле не может быть пустым'
                    },
                    regexp: {
                        regexp: /^[1-4]$/,
                        message: 'Введите корректную группу профотбора'
                    }
                }
            },
        }
    });


    $('#region').change(
        function () {
            $.getJSON("http://192.168.0.102:8000/cities", {
                regionId: $(this).val(),
                ajax: 'true'
            }, function (data) {
                var html = '<span class="input-group-addon"><i' +
                    ' class="glyphicon glyphicon-home"></i></span>';
                html += '<select name="address.city" class="form-control"' +
                    ' required id="address.city" data-bv-field="address.city">';
                var len = data.length;
                for (var i = 0; i < len; i++) {
                    html += '<option value="';
                    html += data[i].cityId;
                    html += '">';
                    html += data[i].cityName;
                    html += '</option>';
                }
                html += '</select>';
                $('#regionDiv').html(html);
            });
        });

    $('#firstPriorityFaculty').change(
        function () {
            $.getJSON("http://192.168.0.102:8000/user/specialties", {
                facultyId: $(this).val(),
                ajax: 'true'
            }, function (data) {
                $('#specialtyDiv1').html('');
                var html = '<span class="input-group-addon"><i' +
                    ' class="glyphicon glyphicon-home"></i></span>';
                html += '<select name="firstPriority" class="form-control"' +
                    ' required id="firstPriority" data-bv-field="firstPriority">';
                var len = data.length;
                html += '<option>...</option>';
                for (var i = 0; i < len; i++) {
                    html += '<option value="';
                    html += data[i].specialtyId;
                    html += '">';
                    html += data[i].specialtyName;
                    html += '</option>';
                }
                html += '</select>';
                $('#specialtyDiv1').html(html);
            });
        });

    $('#secondPriorityFaculty').change(
        function () {
            $.getJSON("http://192.168.0.102:8000/user/specialties", {
                facultyId: $(this).val(),
                ajax: 'true'
            }, function (data) {
                $('#specialtyDiv2').html('');
                var html = '<span class="input-group-addon"><i' +
                    ' class="glyphicon glyphicon-home"></i></span>';
                html += '<select name="secondPriority" class="form-control"' +
                    ' required id="secondPriority" data-bv-field="secondPriority">';
                var len = data.length;
                html += '<option>...</option>';
                for (var i = 0; i < len; i++) {
                    html += '<option value="';
                    html += data[i].specialtyId;
                    html += '">';
                    html += data[i].specialtyName;
                    html += '</option>';
                }
                html += '</select>';
                $('#specialtyDiv2').html(html);
            });
        });

    $('#thirdPriorityFaculty').change(
        function () {
            $.getJSON("http://192.168.0.102:8000/user/specialties", {
                facultyId: $(this).val(),
                ajax: 'true'
            }, function (data) {
                $('#specialtyDiv3').html('');
                var html = '<span class="input-group-addon"><i' +
                    ' class="glyphicon glyphicon-home"></i></span>';
                html += '<select name="thirdPriority" class="form-control"' +
                    ' required id="thirdPriority" data-bv-field="thirdPriority">';
                var len = data.length;
                html += '<option>...</option>';
                for (var i = 0; i < len; i++) {
                    html += '<option value="';
                    html += data[i].specialtyId;
                    html += '">';
                    html += data[i].specialtyName;
                    html += '</option>';
                }
                html += '</select>';
                $('#specialtyDiv3').html(html);
            });
        });

    $('#company').change(
        function () {
            $.getJSON("http://192.168.0.102:8000/user/platoons", {
                companyId: $(this).val(),
                ajax: 'true'
            }, function (data) {
                $('#platoonDiv').html('');
                var html = '<span class="input-group-addon"><i' +
                    ' class="glyphicon glyphicon-home"></i></span>';
                html += '<select name="recruitPlatoon" class="form-control"' +
                    ' required id="recruitPlatoon" data-bv-field="recruitPlatoon">';
                var len = data.length;
                html += '<option>...</option>';
                for (var i = 0; i < len; i++) {
                    html += '<option value="';
                    html += data[i].platoonId;
                    html += '">';
                    html += data[i].platoonNumber;
                    html += '</option>';
                }
                html += '</select>';
                $('#platoonDiv').html(html);
            });
        });

    $('#faculty').change(
        function () {
            $.getJSON("http://192.168.0.102:8000/user/specialtiesNew", {
                facultyId: $(this).val(),
                ajax: 'true'
            }, function (data) {
                $('#specialtyDiv').html('');
                var html = '<span class="input-group-addon"><i' +
                    ' class="glyphicon glyphicon-home"></i></span>';
                html += '<select name="specialty" class="form-control"' +
                    ' required id="specialty" data-bv-field="specialty">';
                var len = data.length;
                html += '<option>...</option>';
                for (var i = 0; i < len; i++) {
                    html += '<option value="';
                    html += data[i].specialtyId;
                    html += '">';
                    html += data[i].specialtyName;
                    html += '</option>';
                }
                html += '</select>';
                $('#specialtyDiv').html(html);
            });
        });

    $('.mask-passport-number').mask('99 99 999999');
    $('.mask-certificate-number').mask('999 9999 9999999');
    $('.mask-year').mask('9999');

    $('.delete').bootstrap_confirm_delete();
});