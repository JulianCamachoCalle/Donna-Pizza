// Obtener lista de pizzas y mostrarlos en la tabla
function listarPizzas() {
    fetch('/api/v1/pizzas')
        .then(response => response.json())
        .then(data => {
            const pizzasList = document.getElementById("pizzas-list");
            pizzasList.innerHTML = ""; // Limpiar tabla antes de cargar datos

            data.forEach(pizza => {
                let row = `
                    <tr>
                        <td>${pizza.id_pizza}</td>
                        <td>${pizza.nombre}</td>
                        <td>${pizza.descripcion}</td>
                        <td>${pizza.precio}</td>
                        <td>${pizza.disponible === 1 ? 'Sí' : 'No'}</td>
                        <td>
                            <button class="btn btn-warning" onclick="cargarDatosPizza(${pizza.id_pizza})">Editar</button>
                            <button class="btn btn-danger" onclick="eliminarPizza(${pizza.id_pizza})">Eliminar</button>
                        </td>
                    </tr>`;
                pizzasList.innerHTML += row;
            });
        })
        .catch(error => console.error('Error al listar pizzas:', error));
}

document.addEventListener('DOMContentLoaded', listarPizzas);

// Función para guardar una pizza
function guardarPizza() {
    const pizza = {
        nombre: document.getElementById("nombre").value,
        descripcion: document.getElementById("descripcion").value,
        precio: document.getElementById("precio").value,
        disponible: document.getElementById("disponible-si").checked ? 1 : 0
    };

    fetch('/api/v1/pizzas', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(pizza)
    })
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                // Mostrar mensaje de error
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: data.mensaje || "Hubo un problema al agregar la pizza.",
                });
            } else {
                // Mostrar mensaje de éxito
                Swal.fire({
                    icon: 'success',
                    title: 'Pizza agregada',
                    text: data.mensaje || "La pizza se ha registrado exitosamente.",
                    timer: 2000,
                    showConfirmButton: false
                }).then(() => {
                    $('#pizzasModal').modal('hide'); // Ocultar el modal
                    listarPizzas(); // Refrescar la lista de pizzas
                });
            }
        })
        .catch(error => {
            // Mostrar mensaje de error en caso de fallo en la conexión
            Swal.fire({
                icon: 'error',
                title: 'Error de conexión',
                text: 'No se pudo conectar con el servidor. Intente nuevamente más tarde.',
            });
            console.error('Error al guardar la pizza:', error);
        });
}

// Cargar datos al Modal Actualizar
function cargarDatosPizza(id) {
    fetch(`/api/v1/pizzas/${id}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al obtener datos de la pizza');
            }
            return response.json();
        })
        .then(data => {
            if (data.error) {
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: data.mensaje,
                });
            } else {
                // Cargar datos en el modal
                document.getElementById("id_pizzaact").value = data.id_pizza;
                document.getElementById("nombreact").value = data.nombre;
                document.getElementById("descripcionact").value = data.descripcion;
                document.getElementById("precioact").value = data.precio;
                // Verificar el valor de 'disponible' y seleccionar el botón de radio correspondiente
                if (data.disponible === 1) {
                    document.getElementById("disponible-siact").checked = true;
                } else {
                    document.getElementById("disponible-noact").checked = true;
                }

                // Mostrar el modal
                $('#editarPizzaModal').modal('show');
            }
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Error de conexión',
                text: 'No se pudo cargar los datos de la pizza.',
            });
            console.error('Error al cargar datos de la pizza:', error);
        });
}

// Actualizar
function actualizarPizza() {
    const pizzaActualizada = {
        id: document.getElementById("id_pizzaact").value,
        nombre: document.getElementById("nombreact").value,
        descripcion: document.getElementById("descripcionact").value,
        precio: document.getElementById("precioact").value,
        disponible: document.getElementById("disponible-si").checked ? 1 : 0
    };

    fetch(`/api/v1/pizzas/${pizzaActualizada.id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(pizzaActualizada)
    })
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                // Mostrar mensaje de error
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: data.mensaje || "Hubo un problema al actualizar la pizza.",
                });
            } else {
                // Mostrar mensaje de éxito
                Swal.fire({
                    icon: 'success',
                    title: 'Pizza actualizada',
                    text: data.mensaje || "La pizza se ha actualizado exitosamente.",
                    timer: 2000,
                    showConfirmButton: false
                }).then(() => {
                    $('#editarPizzaModal').modal('hide'); // Ocultar el modal
                    listarPizzas(); // Refrescar la lista de pizzas
                });
            }
        })
        .catch(error => {
            // Mostrar mensaje de error en caso de fallo en la conexión
            Swal.fire({
                icon: 'error',
                title: 'Error de conexión',
                text: 'No se pudo conectar con el servidor. Intente nuevamente más tarde.',
            });
            console.error('Error al actualizar la pizza:', error);
        });
}

//Eliminar
function eliminarPizza(id) {
    Swal.fire({
        title: '¿Estás seguro?',
        text: "No podrás revertir esto!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sí, eliminar!'
    }).then((result) => {
        if (result.isConfirmed) {
            fetch(`/api/v1/pizzas/${id}`, {
                method: 'DELETE'
            })
                .then(response => response.json())
                .then(data => {
                    Swal.fire({
                        icon: data.error ? 'error' : 'success',
                        title: data.error ? 'Error' : 'Éxito',
                        text: data.mensaje,
                    }).then(() => {
                        if (!data.error) {
                            listarPizzas(); // Refrescar la lista de pizzas
                        }
                    });
                })
                .catch(error => {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error de conexión',
                        text: 'No se pudo eliminar la pizza.',
                    });
                    console.error('Error al eliminar la pizza:', error);
                });
        }
    });
}

function exportarExcel() {
    window.location.href = '/excelpizzas';
}

