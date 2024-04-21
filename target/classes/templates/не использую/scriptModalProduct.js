/*
function openModelProduct(element) {
    var productId = element.getAttribute("data-productid");

    // Выполните AJAX запрос к вашему контроллеру /product/{productId} для получения данных продукта
    fetch("/product/" + productId)
        .then(function(response) {
            if (response.ok) {
                return response.json();
            }
        })
        .then(function(productData) {
            // Здесь вы можете использовать productData для заполнения полей формы в модальном окне
            document.getElementById("productToEditId").value = productData.id;
            document.getElementById("nameProductP").value = productData.nameProduct;
            document.getElementById("descriptionP").value = productData.description;
            document.getElementById("totalPriceP").value = productData.totalPrice;
            document.getElementById("purchasePriceP").value = productData.purchasePrice;
            document.getElementById("countP").value = productData.count;
        })
        .catch(function(error) {
            console.error("Ошибка при получении данных о продукте: " + error);
        });

    // Затем откройте модальное окно
    document.getElementById("modal-backdrop-product").classList.remove("hidden");
    document.getElementById("add-warehouse-modal-product").classList.remove("hidden");
}

function closeModalOnSubmitProduct() {
    // Закрываем модальное окно при отправке формы
    var myFormProduct = document.querySelector('[data-form-id="my-form-product"]');
    myFormProduct.addEventListener("submit", function (event) {
        event.preventDefault();

        // Получаем введенные данные
        const nameStock = document.getElementById("nameStock").value;
        const adressStock = document.getElementById("adressStock").value;

        // Выполняем действия с данными (например, отправка на сервер)
        // В данном случае, после успешной отправки формы, мы скрываем модальное окно

        setTimeout(function () {
            clearFormProduct();
            document.getElementById("modal-backdrop-product").classList.add("hidden");
            document.getElementById("add-warehouse-modal-product").classList.add("hidden");
        }, 500);
    });
}

function clearFormProduct() {
    document.getElementById("nameStock").value = "";
    document.getElementById("adressStock").value = "";
}
*/




<!-- <img th:src="@{'/images/' + ${product.id}}" alt="Изображение продукта">-->







/*
<!--    <div th:id="'product-carousel-' + ${product.id}" class="carousel slide" data-ride="carousel">
    &lt;!&ndash; Индикаторы слайдов &ndash;&gt;
    <ul class="carousel-indicators">
        <th:block th:each="image, imageIndex : ${product.images}">
            <li th:attr="data-target='#product-carousel-' + ${product.id}, data-slide-to=${imageIndex.index}"
                th:classappend="${imageIndex.index == 0} ? 'active' : ''"></li>
        </th:block>
    </ul>
    &lt;!&ndash; Слайды карусели &ndash;&gt;
    <div class="carousel-inner">
        <th:block th:each="image : ${product.images}">
            <div th:classappend="${image == product.images[0]} ? 'carousel-item active' : 'carousel-item'">
                <img th:src="@{'/images/' +${product.id}+'/'+ ${image.id}}" alt="Изображение продукта" th:style="'max-height: 300px; max-width: 400px;'">
            </div>
        </th:block>
    </div>
    &lt;!&ndash; Управление каруселью &ndash;&gt;
    <a class="carousel-control-prev" th:attr="href='#product-carousel-' + ${product.id}" role="button" data-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
    </a>
    <a class="carousel-control-next" th:attr="href='#product-carousel-' + ${product.id}" role="button" data-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
    </a>
</div>-->*/
