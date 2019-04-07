$(document).ready(function () {

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


    $('#education').change(
        function () {
            $.getJSON("http://localhost:8000/cities", {
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

    $('#region').change(
        function () {
            $.getJSON("http://localhost:8000/user/faculties", {
                regionId: $(this).val(),
                ajax: 'true'
            }, function (data) {
                var html = '<span class="input-group-addon"><i' +
                    ' class="glyphicon glyphicon-home"></i></span>';
                html += '<select name="firstPriority" class="form-control"' +
                    ' required id="firstPriority" data-bv-field="firstPriority">';
                var len = data.length;
                for (var i = 0; i < len; i++) {
                    html += '<option value="';
                    html += data[i].facultyId;
                    html += '">';
                    html += data[i].facultyName;
                    html += '</option>';
                }
                html += '</select>';
                $('#educationDiv').html(html);
            });
        });

    $('.mask-passport-number').mask('99 99 999999');
    $('.mask-certificate-number').mask('999 9999 9999999');
    $('.mask-year').mask('9999');
});