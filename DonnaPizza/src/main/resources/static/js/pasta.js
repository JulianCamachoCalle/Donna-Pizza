// Obtener lista de pastas y mostrarlas en la tabla
function listarPasta() {
    fetch('/api/v1/pastas')
        .then(response => response.json())
        .then(data => {
            const pastaList = document.getElementById("pasta-list");
            pastaList.innerHTML = ""; // Limpiar tabla antes de cargar datos

            data.forEach(pasta => {
                let row = `
                    <tr>
                        <td>${pasta.id_pasta}</td>
                        <td>${pasta.descripcion}</td>
                        <td>${pasta.precio}</td>
                        <td>${pasta.disponible ? 'Disponible' : 'No Disponible'}</td>

                        <td>
                            <button class="btn btn-warning" onclick="cargarDatosPasta(${pasta.id_pasta})">Editar</button>
                            <button class="btn btn-danger" onclick="eliminarPasta(${pasta.id_pasta})">Eliminar</button>
                        </td>
                    </tr>`;
                pastaList.innerHTML += row;
            });
        })
        .catch(error => console.error('Error al listar pastas:', error));
}

document.addEventListener('DOMContentLoaded', listarPasta);

// Función para guardar pasta
function guardarPasta() {
    const pasta = {
        descripcion: document.getElementById("descripcion").value,
        precio: document.getElementById("precio").value,
        disponible: document.getElementById("disponible").value === 'true'
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
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: data.mensaje || "Hubo un problema al agregar la pasta.",
                });
            } else {
                Swal.fire({
                    icon: 'success',
                    title: 'Pasta agregada',
                    text: data.mensaje || "La pasta se ha registrado exitosamente.",
                    timer: 2000,
                    showConfirmButton: false
                }).then(() => {
                    $('#pastaModal').modal('hide'); // Ocultar el modal
                    listarPasta(); // Refrescar la lista de pastas
                });
            }
        })
        .catch(error => {
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
                throw new Error('Error al obtener datos de pasta');
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
                document.getElementById("id_entrada-en").value = data.id_pasta;
                document.getElementById("descripcion-pasta").value = data.descripcion;
                document.getElementById("precio-pasta").value = data.precio;
                document.getElementById("disponible-pasta").value = data.disponible ? 'true' : 'false';

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
            console.error('Error al cargar datos de pasta:', error);
        });
}

// Actualizar
function actualizarPasta() {
    const pastaActualizada = {
        id_pasta: document.getElementById("id_entrada-en").value,
        descripcion: document.getElementById("descripcion-pasta").value,
        precio: document.getElementById("precio-pasta").value,
        disponible: document.getElementById("disponible-pasta").value === 'true'
    };

    fetch(`/api/v1/pastas/${pastaActualizada.id_pasta}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(pastaActualizada)
    })
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: data.mensaje || "Hubo un problema al actualizar la pasta.",
                });
            } else {
                Swal.fire({
                    icon: 'success',
                    title: 'Pasta actualizada',
                    text: data.mensaje || "La pasta se ha actualizado exitosamente.",
                    timer: 2000,
                    showConfirmButton: false
                }).then(() => {
                    $('#editarPastaModal').modal('hide'); // Ocultar el modal
                    listarPasta(); // Refrescar la lista de pastas
                });
            }
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Error de conexión',
                text: 'No se pudo conectar con el servidor. Intente nuevamente más tarde.',
            });
            console.error('Error al actualizar pasta:', error);
        });
}

// Eliminar pasta
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
                            listarPasta(); // Refrescar la lista de pastas
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

// Exportar a Excel
function exportarExcel() {
    window.location.href = '/excelpasta';
}
