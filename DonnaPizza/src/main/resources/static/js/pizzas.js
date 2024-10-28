// pizzasfamiliares.js
fetch('/api/v1/pizzasfamiliares')
    .then(response => response.json())
    .then(data => {
        const container = document.getElementById('pizzasfamiliaresContainer');
        container.innerHTML = ''; // Limpia el contenedor
        data.forEach(pizzafamiliar => {
            // Crear el HTML para cada carta de pizza
            const card = `
                <div class="card mb-4">
                    <img src="/images/pizzafamiliar_default.jpg" class="card-img-top" alt="${pizzafamiliar.nombre}">
                    <div class="card-body">
                        <h5 class="card-title">${pizzafamiliar.nombre}</h5>
                        <p class="card-text">${pizzafamiliar.descripcion}</p>
                        <p class="card-price">Precio: ${pizzafamiliar.precio} €</p>
                        <button class="btn btn-primary">Comprar</button>
                    </div>
                </div>
            `;
            container.innerHTML += card;
        });
    })
    .catch(error => console.error('Error al cargar las pizzasfamiliares:', error));
