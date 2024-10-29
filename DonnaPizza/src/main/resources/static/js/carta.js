function cargarPizzas() {
    fetch('/api/v1/pizzas')
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al obtener las pizzas');
            }
            return response.json();
        })
        .then(data => {
            const pizzaContainer = document.getElementById('pizza-container');
            pizzaContainer.innerHTML = '';

            data.forEach(pizza => {
                const card = `
                            <div class="col-12 col-md-6 col-xl-4 mb-4 d-flex justify-content-center">
                                <div class="card border border-0 bg-success mx-3 my-0"
                                     style="width: 21.875rem; height: 23.125rem;" id="plato">
                                     <img src="/img/pizzas/${pizza.nombre}.jpg" class="card-img-top img-fluid" alt="${pizza.nombre}" 
                                            onerror="this.onerror=null; this.src='/img/pizzas/predeterminada.jpg';">
                                    <div class="card-body d-flex justify-content-center align-items-center flex-column m-0 p-0">
                                        <h5 class="card-title text-center m-0 p-0" id="nombre-plato">${pizza.nombre}</h5>
                                        <p class="card-text text-center mb-1 mx-1 p-0" id="ingredientes-plato">${pizza.descripcion}</p>
                                        <p class="card-text my-2 p-0" id="precio-plato">S/. ${pizza.precio}</p>
                                        <a class="btn btn-compra border border-0 px-2 py-1" th:href="@{/especificaciones}" role="button">Comprar</a>
                                    </div>
                                </div>
                            </div>`;
                pizzaContainer.innerHTML += card;
            });
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

// Llama a la función para cargar las pizzas al cargar la página
document.addEventListener('DOMContentLoaded', cargarPizzas);

