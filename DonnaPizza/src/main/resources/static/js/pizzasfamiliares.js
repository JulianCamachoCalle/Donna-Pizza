
function listarPizzasFamiliares() {
    fetch('/api/v1/pizzasfamiliares')
        .then(response => response.json())
        .then(data => {
            const pizzasfamiliaresList = document.getElementById("pizzasfamiliares-list");
            pizzasfamiliaresList.innerHTML = ""; // Limpiar tabla antes de cargar datos

            data.forEach(pizzafamiliares => {
                let row = `
                    <tr>
                        <td>${pizzafamiliares.id_pizza_familiar}</td>
                        <td>${pizzafamiliares.nombre}</td>
                        <td>${pizzafamiliares.descripcion}</td>
                        <td>${pizzafamiliares.precio}</td>
                        <td>${pizzafamiliares.disponible === 1 ? 'Sí' : 'No'}</td>
                        <td>
                            <button class="btn btn-warning" onclick="cargarDatosPizzaFamiliar(${pizzafamiliares.id_pizza_familiar})">Editar</button>
                            <button class="btn btn-danger" onclick="eliminarPizzaFamiliar(${pizzafamiliares.id_pizza_familiar})">Eliminar</button>
                        </td>
                    </tr>`;
                pizzasfamiliaresList.innerHTML += row;
            });
        })
        .catch(error => console.error('Error al listar pizzasfamiliares:', error));
}

document.addEventListener('DOMContentLoaded', listarPizzasFamiliares);

// Función para guardar una pizzafamiliar
function guardarPizzaFamiliar() {
    const pizzafamiliar = {
        nombre: document.getElementById("nombre").value,
        descripcion: document.getElementById("descripcion").value,
        precio: document.getElementById("precio").value,
        disponible: document.getElementById("disponible-si").checked ? 1 : 0
    };

    fetch('/api/v1/pizzasfamiliares', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(pizzafamiliar)
    })
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                // Mostrar mensaje de error
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: data.mensaje || "Hubo un problema al agregar la pizzafamiliar.",
                });
            } else {
                // Mostrar mensaje de éxito
                Swal.fire({
                    icon: 'success',
                    title: 'PizzaFamiliar agregada',
                    text: data.mensaje || "La pizzafamiliar se ha registrado exitosamente.",
                    timer: 2000,
                    showConfirmButton: false
                }).then(() => {
                    $('#pizzasfamiliaresModal').modal('hide'); // Ocultar el modal
                    listarPizzasFamiliares(); // Refrescar la lista de pizzasfamiliar
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
            console.error('Error al guardar la pizzafamiliar:', error);
        });
}

// Cargar datos al Modal Actualizar
function cargarDatosPizzaFamiliar(id) {
    fetch(`/api/v1/pizzasfamiliares/${id}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al obtener datos de la pizzafamiliar');
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
                document.getElementById("id_pizza_familiaract").value = data.id_pizza_familiar;
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
                $('#editarPizzaFamiliarModal').modal('show');
            }
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Error de conexión',
                text: 'No se pudo cargar los datos de la pizzafamiliar.',
            });
            console.error('Error al cargar datos de la pizzafamiliar:', error);
        });
}

// Actualizar
function actualizarPizzaFamiliar() {
    const pizzafamiliarActualizada = {
        id: document.getElementById("id_pizza_familiaract").value,
        nombre: document.getElementById("nombreact").value,
        descripcion: document.getElementById("descripcionact").value,
        precio: document.getElementById("precioact").value,
        disponible: document.getElementById("disponible-si").checked ? 1 : 0
    };

    fetch(`/api/v1/pizzasfamiliares/${pizzafamiliarActualizada.id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(pizzafamiliarActualizada)
    })
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                // Mostrar mensaje de error
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: data.mensaje || "Hubo un problema al actualizar la pizzafamiliar.",
                });
            } else {
                // Mostrar mensaje de éxito
                Swal.fire({
                    icon: 'success',
                    title: 'PizzaFamiliar actualizada',
                    text: data.mensaje || "La pizzafamiliar se ha actualizado exitosamente.",
                    timer: 2000,
                    showConfirmButton: false
                }).then(() => {
                    $('#editarPizzaFamiliarModal').modal('hide'); // Ocultar el modal
                    listarPizzasFamiliares(); // Refrescar la lista de pizzasfamiliares
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
            console.error('Error al actualizar la pizzafamiliar:', error);
        });
}


//Eliminar
function eliminarPizzaFamiliar(id) {
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
            fetch(`/api/v1/pizzasfamiliares/${id}`, {
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
                            listarPizzasFamiliares(); // Refrescar la lista de pizzas
                        }
                    });
                })
                .catch(error => {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error de conexión',
                        text: 'No se pudo eliminar la pizzafamiliar.',
                    });
                    console.error('Error al eliminar la pizzafamiliar:', error);
                });
        }
    });
}

function exportarExcel() {
    window.location.href = '/excelpizzasfamiliares';
}
