// Obtener lista de documentos y mostrarlos en la tabla
function listarDocumentos() {
    fetch('/api/v1/documentos') // Cambiar a la ruta correcta para documentos
        .then(response => response.json())
        .then(data => {
            const documentosList = document.getElementById("documentos-list"); // Cambiar el ID a documentos-list
            documentosList.innerHTML = ""; // Limpiar tabla antes de cargar datos

            data.forEach(documento => {
                let row = `
                    <tr>
                        <td>${documento.idDocumento}</td> <!-- Cambiar el nombre de la propiedad según tu modelo -->
                        <td>${documento.tipoDocumento}</td> <!-- Cambiar el nombre de la propiedad según tu modelo -->
                        <td>
                            <button class="btn btn-warning" onclick="cargarDatosDocumento(${documento.idDocumento})">Editar</button>
                            <button class="btn btn-danger" onclick="eliminarDocumento(${documento.idDocumento})">Eliminar</button>
                        </td>
                    </tr>`;
                documentosList.innerHTML += row;
            });
        })
        .catch(error => console.error('Error al listar documentos:', error));
}

document.addEventListener('DOMContentLoaded', listarDocumentos);

// Función para guardar un documento
function guardarDocumento() {
    const documento = {
        tipoDocumento: document.getElementById("tipo_documento").value // Cambiar el ID al correcto
    };

    fetch('/api/v1/documentos', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(documento)
    })
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                // Mostrar mensaje de error
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: data.mensaje || "Hubo un problema al agregar el documento.",
                });
            } else {
                // Mostrar mensaje de éxito
                Swal.fire({
                    icon: 'success',
                    title: 'Documento agregado',
                    text: data.mensaje || "El documento se ha registrado exitosamente.",
                    timer: 2000,
                    showConfirmButton: false
                }).then(() => {
                    $('#documentoModal').modal('hide'); // Ocultar el modal
                    listarDocumentos(); // Refrescar la lista de documentos
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
            console.error('Error al guardar documento:', error);
        });
}

// Cargar datos al Modal Actualizar
function cargarDatosDocumento(id) {
    fetch(`/api/v1/documentos/${id}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al obtener datos del documento');
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
                document.getElementById("id_documentoact").value = data.idDocumento; // Cambiar al ID correcto
                document.getElementById("tipo_documentoact").value = data.tipoDocumento; // Cambiar al ID correcto

                // Mostrar el modal
                $('#editarDocumentoModal').modal('show');
            }
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Error de conexión',
                text: 'No se pudo cargar los datos del documento.',
            });
            console.error('Error al cargar datos del documento:', error);
        });
}

// Actualizar
function actualizarDocumento() {
    const documentoActualizado = {
        id: document.getElementById("id_documentoact").value, // Cambiar al ID correcto
        tipoDocumento: document.getElementById("tipo_documentoact").value // Cambiar al ID correcto
    };

    fetch(`/api/v1/documentos/${documentoActualizado.id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(documentoActualizado)
    })
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                // Mostrar mensaje de error
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: data.mensaje || "Hubo un problema al actualizar el documento.",
                });
            } else {
                // Mostrar mensaje de éxito
                Swal.fire({
                    icon: 'success',
                    title: 'Documento actualizado',
                    text: data.mensaje || "El documento se ha actualizado exitosamente.",
                    timer: 2000,
                    showConfirmButton: false
                }).then(() => {
                    $('#editarDocumentoModal').modal('hide'); // Ocultar el modal
                    listarDocumentos(); // Refrescar la lista de documentos
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
            console.error('Error al actualizar documento:', error);
        });
}

// Eliminar
function eliminarDocumento(id) {
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
            fetch(`/api/v1/documentos/${id}`, {
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
                            listarDocumentos(); // Refrescar la lista de documentos
                        }
                    });
                })
                .catch(error => {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error de conexión',
                        text: 'No se pudo eliminar el documento.',
                    });
                    console.error('Error al eliminar documento:', error);
                });
        }
    });
}
function exportarExcel() {
    window.location.href = '/exceldocumentos';
}
