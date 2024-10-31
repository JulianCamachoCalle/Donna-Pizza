// Obtener lista de ingredientes y mostrarlos en la tabla
function listarIngredientes() {
    fetch('/api/v1/ingredientes')
        .then(response => response.json())
        .then(data => {
            const ingredientesList = document.getElementById("pastas-list");
            ingredientesList.innerHTML = ""; // Limpiar tabla antes de cargar datos

            data.forEach(ingrediente => {
                let row = `
                    <tr>
                        <td>${ingrediente.id_ingrediente}</td>
                        <td>${ingrediente.nombre}</td>
                        <td>${ingrediente.cantidad_disponible}</td>
                        <td>
                            <button class="btn btn-warning" onclick="cargarDatosIngrediente(${ingrediente.id_ingrediente})">Editar</button>
                            <button class="btn btn-danger" onclick="eliminarIngrediente(${ingrediente.id_ingrediente})">Eliminar</button>
                        </td>
                    </tr>`;
                ingredientesList.innerHTML += row;
            });
        })
        .catch(error => console.error('Error al listar ingredientes:', error));
}

document.addEventListener('DOMContentLoaded', listarIngredientes);

// Función para guardar un ingrediente
function guardarIngrediente() {
    const ingrediente = {
        nombre: document.getElementById("nombre").value,
        cantidad_disponible: document.getElementById("cantdisponible").value
    };

    fetch('/api/v1/ingredientes', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(ingrediente)
    })
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                // Mostrar mensaje de error
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: data.mensaje || "Hubo un problema al agregar el ingrediente.",
                });
            } else {
                // Mostrar mensaje de éxito
                Swal.fire({
                    icon: 'success',
                    title: 'Ingrediente agregado',
                    text: data.mensaje || "El ingrediente se ha registrado exitosamente.",
                    timer: 2000,
                    showConfirmButton: false
                }).then(() => {
                    $('#pastaModal').modal('hide'); // Ocultar el modal
                    listarIngredientes(); // Refrescar la lista de ingredientes
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
            console.error('Error al guardar ingrediente:', error);
        });
}

// Cargar datos al Modal Actualizar
function cargarDatosIngrediente(id) {
    fetch(`/api/v1/ingredientes/${id}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al obtener datos del ingrediente');
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
                document.getElementById("id_ingredienteact").value = data.id_ingrediente;
                document.getElementById("nombreact").value = data.nombre;
                document.getElementById("cantdisponibleact").value = data.cantidad_disponible;

                // Mostrar el modal
                $('#editarIngredienteModal').modal('show');
            }
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Error de conexión',
                text: 'No se pudo cargar los datos del ingrediente.',
            });
            console.error('Error al cargar datos del ingrediente:', error);
        });
}

// Actualizar
function actualizarIngrediente() {
    const ingredienteActualizado = {
        id_ingrediente: document.getElementById("id_ingredienteact").value,
        nombre: document.getElementById("nombreact").value,
        cantidad_disponible: document.getElementById("cantdisponibleact").value
    };

    fetch(`/api/v1/ingredientes/${ingredienteActualizado.id_ingrediente}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(ingredienteActualizado)
    })
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                // Mostrar mensaje de error
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: data.mensaje || "Hubo un problema al actualizar el ingrediente.",
                });
            } else {
                // Mostrar mensaje de éxito
                Swal.fire({
                    icon: 'success',
                    title: 'Ingrediente actualizado',
                    text: data.mensaje || "El ingrediente se ha actualizado exitosamente.",
                    timer: 2000,
                    showConfirmButton: false
                }).then(() => {
                    $('#editarIngredienteModal').modal('hide'); // Ocultar el modal
                    listarIngredientes(); // Refrescar la lista de ingredientes
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
            console.error('Error al actualizar ingrediente:', error);
        });
}

// Eliminar
function eliminarIngrediente(id) {
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
            fetch(`/api/v1/ingredientes/${id}`, {
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
                            listarIngredientes(); // Refrescar la lista de ingredientes
                        }
                    });
                })
                .catch(error => {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error de conexión',
                        text: 'No se pudo eliminar el ingrediente.',
                    });
                    console.error('Error al eliminar ingrediente:', error);
                });
        }
    });
}
function exportarExcel() {
    window.location.href = '/excelingredientes';
}
