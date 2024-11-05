// Obtener lista de entradas y mostrarlos en la tabla
function listarEntrada() {
    fetch('/api/v1/entradas')
        .then(response => response.json())
        .then(data => {
            const entradasList = document.getElementById("entrada-list");
            entradasList.innerHTML = ""; // Limpiar tabla antes de cargar datos

            data.forEach(entrada => {
                let row = `
                    <tr>
                        <td>${entrada.id_entrada}</td>
                        <td>${entrada.nombre}</td>
                        <td>${entrada.descripcion}</td>
                        <td>${entrada.precio}</td>
                        <td>${entrada.disponible ? 'Disponible' : 'No Disponible'}</td>

                        <td>
                            <button class="btn btn-warning" onclick="cargarDatosEntrada(${entrada.id_entrada})">Editar</button>
                            <button class="btn btn-danger" onclick="eliminarEntrada(${entrada.id_entrada})">Eliminar</button>
                        </td>
                    </tr>`;
                entradasList.innerHTML += row;
            });
        })
        .catch(error => console.error('Error al listar entradas:', error));
}

document.addEventListener('DOMContentLoaded', listarEntrada);

// Función para guardar entrada
function guardarEntrada() {
    const entrada = {
        nombre: document.getElementById("nombre").value,
        descripcion: document.getElementById("descripcion").value,
        precio: document.getElementById("precio").value,
        disponible: document.getElementById("disponible").value === 'true'
    };

    fetch('/api/v1/entradas', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(entrada)
    })
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                // Mostrar mensaje de error
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: data.mensaje || "Hubo un problema al agregar entrada.",
                });
            } else {
                // Mostrar mensaje de éxito
                Swal.fire({
                    icon: 'success',
                    title: 'Entrada agregada',
                    text: data.mensaje || "Entrada se ha registrado exitosamente.",
                    timer: 2000,
                    showConfirmButton: false
                }).then(() => {
                    $('#entradaModal').modal('hide'); // Ocultar el modal
                    listarEntrada(); // Refrescar la lista de entrada
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
            console.error('Error al guardar entrada:', error);
        });
}

// Cargar datos al Modal Actualizar
function cargarDatosEntrada(id) {
    fetch(`/api/v1/entradas/${id}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al obtener datos de entrada');
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
                document.getElementById("id_entrada-en").value = data.id_entrada;
                document.getElementById("nombre-en").value = data.nombre;
                document.getElementById("descripcion-en").value = data.descripcion;
                document.getElementById("precio-en").value = data.precio;
                document.getElementById("disponible-en").value = data.disponible ? 'true' : 'false';

                // Mostrar el modal
                $('#editarEntradaModal').modal('show');
            }
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Error de conexión',
                text: 'No se pudo cargar los datos de entrada.',
            });
            console.error('Error al cargar datos de entrada:', error);
        });
}

// Actualizar
function actualizarEntrada() {
    const entradaActualizado = {
        id_entrada: document.getElementById("id_entrada-en").value,
        nombre: document.getElementById("nombre-en").value,
        descripcion: document.getElementById("descripcion-en").value,
        precio: document.getElementById("precio-en").value,
        disponible: document.getElementById("disponible-en").value === 'true'
    };

    fetch(`/api/v1/entradas/${entradaActualizado.id_entrada}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(entradaActualizado)
    })
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                // Mostrar mensaje de error
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: data.mensaje || "Hubo un problema al actualizar entrada.",
                });
            } else {
                // Mostrar mensaje de éxito
                Swal.fire({
                    icon: 'success',
                    title: 'Entrada actualizada',
                    text: data.mensaje || "Entrada se ha actualizado exitosamente.",
                    timer: 2000,
                    showConfirmButton: false
                }).then(() => {
                    $('#editarEntradaModal').modal('hide'); // Ocultar el modal
                    listarEntrada(); // Refrescar la lista de entradas
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
            console.error('Error al actualizar entrada:', error);
        });
}

//Eliminar
function eliminarEntrada(id) {
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
            fetch(`/api/v1/entradas/${id}`, {
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
                            listarEntrada(); // Refrescar la lista de entrada
                        }
                    });
                })
                .catch(error => {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error de conexión',
                        text: 'No se pudo eliminar entrada.',
                    });
                    console.error('Error al eliminar entrada:', error);
                });
        }
    });
}

function exportarExcel() {
    window.location.href = '/excelentradas';
}



