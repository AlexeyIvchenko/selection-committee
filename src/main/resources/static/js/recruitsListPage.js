$(document).ready(function () {
    $('#recruitsTable').dataTable({
        "language": {
            "emptyTable": "Список абитуриентов пуст",
            "search": "Поиск:",
            "lengthMenu": "Показывать по _MENU_ записей",
            "info": "Всего _TOTAL_ записей",
            "paginate": {
                "first":      "Первый",
                "last":       "Последний",
                "next":       "Следующая",
                "previous":   "Предыдущая"
            },
        }
    });
});