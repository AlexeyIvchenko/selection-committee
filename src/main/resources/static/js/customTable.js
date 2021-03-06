$(document).ready(function () {
    $('#usersTable').dataTable({
        "language": {
            "emptyTable": "Список пользователей пуст",
            "search": "Поиск:",
            "lengthMenu": "Показывать по _MENU_ записей",
            "info": "Всего _TOTAL_ записей",
            "paginate": {
                "first": "Первый",
                "last": "Последний",
                "next": "Следующая",
                "previous": "Предыдущая"
            }
        }
    });

    $('#recruitsTable').dataTable({
        "language": {
            "emptyTable": "Список абитуриентов пуст",
            "search": "Поиск:",
            "lengthMenu": "Показывать по _MENU_ записей",
            "info": "Всего _TOTAL_ записей",
            "paginate": {
                "first": "Первый",
                "last": "Последний",
                "next": "Следующая",
                "previous": "Предыдущая"
            },
        },
        "order": [[0, 'desc']]
    });


});