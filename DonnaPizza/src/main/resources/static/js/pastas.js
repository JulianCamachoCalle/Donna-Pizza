// Obtener lista de pastas y mostrarlas en la tabla
function listarPastas() {
    fetch('/api/v1/pastas')
        .then(response => response.json())
        .then(data => {
            const pastasList = document.getElementById("clientes-list");
            pastasList.innerHTML = ""; // Limpiar tabla antes de cargar datos

            data.forEach(pasta => {
                let row = `
                    <tr>
                        <td>${pasta.id_pasta}</td>
                        <td>${pasta.nombre}</td>
                        <td>${pasta.descripcion}</td>
                        <td>${pasta.precio}</td>
                        <td>${pasta.disponible ? 'Sí' : 'No'}</td>
                        <td>
                            <button class="btn btn-warning" onclick="cargarDatosPasta(${pasta.id_pasta})">Editar</button>
                            <button class="btn btn-danger" onclick="eliminarPasta(${pasta.id_pasta})">Eliminar</button>
                        </td>
                    </tr>`;
                pastasList.innerHTML += row;
            });
        })
        .catch(error => console.error('Error al listar pastas:', error));
}

document.addEventListener('DOMContentLoaded', listarPastas);

// Función para guardar una nueva pasta
function guardarPasta() {
    const pasta = {
        nombre: document.getElementById("nombre").value,
        descripcion: document.getElementById("apellido").value, // Asumiendo que "apellido" se usa para descripción
        precio: document.getElementById("email").value,
        disponible: document.getElementById("telefono").value // Cambiar si es booleano
    };

    fetch('/api/v1/pastas', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(pasta)
    })
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                // Mostrar mensaje de error
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: data.mensaje || "Hubo un problema al agregar la pasta.",
                });
            } else {
                // Mostrar mensaje de éxito
                Swal.fire({
                    icon: 'success',
                    title: 'Pasta agregada',
                    text: data.mensaje || "La pasta se ha registrado exitosamente.",
                    timer: 2000,
                    showConfirmButton: false
                }).then(() => {
                    $('#pastaModal').modal('hide'); // Ocultar el modal
                    listarPastas(); // Refrescar la lista de pastas
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
            console.error('Error al guardar pasta:', error);
        });
}

// Cargar datos al Modal Actualizar
function cargarDatosPasta(id) {
    fetch(`/api/v1/pastas/${id}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al obtener datos de la pasta');
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
                document.getElementById("id_pastaact").value = data.id_pasta;
                document.getElementById("nombreact").value = data.nombre;
                document.getElementById("apellidoact").value = data.descripcion;
                document.getElementById("emailact").value = data.precio;
                document.getElementById("disponibleact").value = data.disponible ? 'Sí' : 'No'; // Cambiar según el tipo de dato

                // Mostrar el modal
                $('#editarPastaModal').modal('show');
            }
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Error de conexión',
                text: 'No se pudo cargar los datos de la pasta.',
            });
            console.error('Error al cargar datos de la pasta:', error);
        });
}

// Actualizar
function actualizarPasta() {
    const pastaActualizada = {
        id: document.getElementById("id_pastaact").value,
        nombre: document.getElementById("nombreact").value,
        descripcion: document.getElementById("apellidoact").value,
        precio: document.getElementById("emailact").value,
        disponible: document.getElementById("disponibleact").value === 'Sí' // Cambiar según el tipo de dato
    };

    fetch(`/api/v1/pastas/${pastaActualizada.id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(pastaActualizada)
    })
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                // Mostrar mensaje de error
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: data.mensaje || "Hubo un problema al actualizar la pasta.",
                });
            } else {
                // Mostrar mensaje de éxito
                Swal.fire({
                    icon: 'success',
                    title: 'Pasta actualizada',
                    text: data.mensaje || "La pasta se ha actualizado exitosamente.",
                    timer: 2000,
                    showConfirmButton: false
                }).then(() => {
                    $('#editarPastaModal').modal('hide'); // Ocultar el modal
                    listarPastas(); // Refrescar la lista de pastas
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
            console.error('Error al actualizar pasta:', error);
        });
}

// Eliminar
function eliminarPasta(id) {
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
            fetch(`/api/v1/pastas/${id}`, {
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
                            listarPastas(); // Refrescar la lista de pastas
                        }
                    });
                })
                .catch(error => {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error de conexión',
                        text: 'No se pudo eliminar la pasta.',
                    });
                    console.error('Error al eliminar pasta:', error);
                });
        }
    });
}
