$(document).ready(function () {
    $('#recruitsTable').dataTable({
        "language": {
            "emptyTable": "Список абитуриентов пуст",
            "search": "Поиск:",
            "lengthMenu": "Отображать по _MENU_ абитуриентов",
            "info": "Showing _START_ to _END_ of _TOTAL_ entries",
            "infoEmpty": "Showing 0 to 0 of 0 entries",
        }
    });
});