function cargarPizzas() {
    fetch('/api/v1/pizzas')
        .then(response => response.json())
        .then(data => {
            const pizzaContainer = document.getElementById('pizza-container');
            pizzaContainer.innerHTML = '';

            data.forEach(pizza => {
                const card = `
                <div class="col-12 col-md-6 col-xl-4 mb-4 d-flex justify-content-center">
                    <div class="card border border-0 bg-success mx-3 my-0" style="width: 21.875rem; height: 23.125rem;" id="plato">
                        <img src="/img/pizzas/${pizza.nombre} (Mediana).jpg" class="card-img-top img-fluid" alt="${pizza.nombre}" onerror="this.onerror=null; this.src='/img/pizzas/predeterminada.jpg';">
                        <div class="card-body d-flex justify-content-center align-items-center flex-column m-0 p-0">
                            <h5 class="card-title text-center m-0 p-0" id="nombre-plato">${pizza.nombre}</h5>
                            <p class="card-text text-center mb-1 mx-1 p-0" id="ingredientes-plato">${pizza.descripcion}</p>
                            <p class="card-text my-2 p-0" id="precio-plato">S/. ${pizza.precio}</p>
                            <button class="btn btn-compra border border-0 px-2 py-1" onclick="openPizzaModal(${pizza.id_pizza})">Comprar</button>
                        </div>
                    </div>
                </div>`;
                pizzaContainer.innerHTML += card;
            });
        });
}

function openPizzaModal(pizzaId) {
    fetch(`/api/v1/pizzas/${pizzaId}`)
        .then(response => response.json())
        .then(data => {
            document.getElementById('modal-pizza-image').src = `/img/pizzas/${data.nombre}.jpg`;
            document.getElementById('modal-pizza-name').textContent = data.nombre;
            document.getElementById('modal-pizza-description').textContent = data.descripcion;
            document.getElementById('modal-price-familiar').textContent = parseFloat(data.precioFamiliar).toFixed(2);
            document.getElementById('modal-price-media').textContent = data.precio;

            document.getElementById('modal-quantity').value = 1;
            const pizzaModal = new bootstrap.Modal(document.getElementById('pizzaModal'));
            pizzaModal.show();
        });
}

// Función para actualizar la cantidad de la pizza en el modal
function updateQuantity(change) {
    const quantityInput = document.getElementById('modal-quantity');
    let quantity = parseInt(quantityInput.value);
    quantity = Math.max(1, quantity + change);
    quantityInput.value = quantity;
}

// Función para agregar una pizza al carrito
function addToCart() {
    const name = document.getElementById('modal-pizza-name').textContent;
    const size = document.querySelector('input[name="size"]:checked').value;
    const price = size === 'Familiar' ? document.getElementById('modal-price-familiar').textContent : document.getElementById('modal-price-media').textContent;
    const quantity = parseInt(document.getElementById('modal-quantity').value);

    let cart = JSON.parse(localStorage.getItem('cart')) || [];
    const existingItem = cart.find(item => item.name === name && item.size === size);

    if (existingItem) {
        existingItem.quantity += quantity;
    } else {
        cart.push({ name, size, price, quantity });
    }

    localStorage.setItem('cart', JSON.stringify(cart));
    loadCart();

    // Mostrar el SweetAlert de confirmación
    Swal.fire({
        title: '¡Producto agregado!',
        html: `<strong>${quantity} x ${name} (${size})</strong> ha sido añadido a tu carrito.`,
        icon: 'success',
        confirmButtonText: 'Aceptar',
        customClass: {
            confirmButton: 'btn btn-success',
        },
        buttonsStyling: false
    });
}



// Cargar y mostrar el carrito en el modal
function loadCart() {
    const cart = JSON.parse(localStorage.getItem('cart')) || [];
    const cartItemsList = document.getElementById('cart-items');
    cartItemsList.innerHTML = '';

    cart.forEach((item, index) => {
        const li = document.createElement('li');
        li.className = "list-group-item d-flex justify-content-between align-items-center";
        li.textContent = `${item.quantity} x ${item.name} (${item.size}) - S/. ${item.price}`;

        const removeButton = document.createElement('button');
        removeButton.className = "btn btn-outline-danger btn-sm";
        removeButton.textContent = "Eliminar";
        removeButton.onclick = () => removeFromCart(index);
        li.appendChild(removeButton);
        cartItemsList.appendChild(li);
    });
}

// Alternar la visibilidad del modal
function toggleCartModal() {
    loadCart();
    const cartModal = new bootstrap.Modal(document.getElementById('cartModal'));
    cartModal.show();
}

// Función para eliminar un producto del carrito
function removeFromCart(index) {
    let cart = JSON.parse(localStorage.getItem('cart')) || [];
    cart.splice(index, 1);
    localStorage.setItem('cart', JSON.stringify(cart));
    loadCart();
}


// Llama a la función para cargar las pizzas al cargar la página
document.addEventListener('DOMContentLoaded', cargarPizzas)