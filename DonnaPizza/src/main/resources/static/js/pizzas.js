// pizzas.js
fetch('/api/v1/pizzas')
    .then(response => response.json())
    .then(data => {
        const container = document.getElementById('pizzasContainer');
        container.innerHTML = ''; // Limpia el contenedor
        data.forEach(pizza => {
            // Crear el HTML para cada carta de pizza
            const card = `
                <div class="card mb-4">
                    <img src="/images/pizza_default.jpg" class="card-img-top" alt="${pizza.nombre}">
                    <div class="card-body">
                        <h5 class="card-title">${pizza.nombre}</h5>
                        <p class="card-text">${pizza.descripcion}</p>
                        <p class="card-price">Precio: ${pizza.precio} €</p>
                        <button class="btn btn-primary">Comprar</button>
                    </div>
                </div>
            `;
            container.innerHTML += card;
        });
    })
    .catch(error => console.error('Error al cargar las pizzas:', error));
