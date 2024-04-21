function openModel() {
    // Открываем модальное окно при нажатии на "Добавить склад"
    document.getElementById("add-warehouse-button").addEventListener("click", function () {
        document.getElementById("modal-backdrop").classList.remove("hidden");
        document.getElementById("add-warehouse-modal").classList.remove("hidden");
    });
}
function closeModel(){
    // Закрываем модальное окно при нажатии на крестик
    document.getElementById("close-modal").addEventListener("click", function () {
        clearForm();
        document.getElementById("modal-backdrop").classList.add("hidden");
        document.getElementById("add-warehouse-modal").classList.add("hidden");
    });

}
function closeModalOnSubmit() {
    // Закрываем модальное окно при отправке формы
    var myForm = document.querySelector('[data-form-id="my-form"]');
    myForm.addEventListener("submit", function (event) {
        event.preventDefault();

        // Получаем введенные данные
        const nameStock = document.getElementById("nameStock").value;
        const adressStock = document.getElementById("adressStock").value;

        // Выполняем действия с данными (например, отправка на сервер)
        // В данном случае, после успешной отправки формы, мы скрываем модальное окно

        setTimeout(function () {
            clearForm();
            document.getElementById("modal-backdrop").classList.add("hidden");
            document.getElementById("add-warehouse-modal").classList.add("hidden");
        }, 500);
    });
}

function clearForm() {
    document.getElementById("nameStock").value = "";
    document.getElementById("adressStock").value = "";
}
