// Obtener lista de metodos  de pago y mostrarlos en la tabla
function listarMetodosPago() {
    fetch('/api/v1/metodosPago')
        .then(response => response.json())
        .then(data => {
            const metodosPagoList = document.getElementById("metodosPago-list");
            metodosPagoList.innerHTML = ""; // Limpiar tabla antes de cargar datos

            data.forEach(metodosPago => {
                let row = `
                    <tr>
                        <td>${metodosPago.id_MetodosPago}</td>
                        <td>${metodosPago.nombre}</td>
                        <td>${metodosPago.descripcion}</td>
                        <td>
                            <button class="btn btn-warning" onclick="cargarDatosMetodosPago(${metodosPago.id_metodosPago})">Editar</button>
                            <button class="btn btn-danger" onclick="eliminarMetodosPago(${metodosPago.id_metodosPago})">Eliminar</button>
                        </td>
                    </tr>`;
                metodosPagoList.innerHTML += row;
            });
        })
        .catch(error => console.error('Error al listar metodos de pago:', error));
}

document.addEventListener('DOMContentLoaded', listarMetodosPago);

// Función para guardar un metodo de pago
function guardarMetodosPago() {
    const metodosPago = {
        nombre: document.getElementById("nombre").value,
        description: document.getElementById("descripcion").value,
    };

    fetch('/api/v1/metodosPago', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(metodosPago)
    })
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                // Mostrar mensaje de error
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: data.mensaje || "Hubo un problema al agregar el metodo de pago.",
                });
            } else {
                // Mostrar mensaje de éxito
                Swal.fire({
                    icon: 'success',
                    title: 'Metodo de pago agregado',
                    text: data.mensaje || "El metodo de pago se ha registrado exitosamente.",
                    timer: 2000,
                    showConfirmButton: false
                }).then(() => {
                    $('#metodosPagoModal').modal('hide'); // Ocultar el modal
                    listarMetodosPago(); // Refrescar la lista de metodos de pago
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
            console.error('Error al guardar el metodo de pago:', error);
        });
}

// Cargar datos al Modal Actualizar
function cargarDatosMetodosPago(id) {
    fetch(`/api/v1/metodosPago/${id}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al obtener datos del metodo de pago');
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
                document.getElementById("id_metodosPagoact").value = data.id_metodosPago;
                document.getElementById("nombreact").value = data.nombre;
                document.getElementById("descripcionact").value = data.description;

                // Mostrar el modal
                $('#editarMetodosPagoModal').modal('show');
            }
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Error de conexión',
                text: 'No se pudo cargar los datos del metodo de pado.',
            });
            console.error('Error al cargar datos del metodo de pago:', error);
        });
}

// Actualizar
function actualizarMetodosPago() {
    const metodosPagoActualizado = {
        id: document.getElementById("id_metodosPagoact").value,
        nombre: document.getElementById("nombreact").value,
        description: document.getElementById("descripcionact").value,
    };

    fetch(`/api/v1/metodosPago/${metodosPagoActualizado.id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(metodosPagoActualizado)
    })
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                // Mostrar mensaje de error
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: data.mensaje || "Hubo un problema al actualizar el metodo de pago.",
                });
            } else {
                // Mostrar mensaje de éxito
                Swal.fire({
                    icon: 'success',
                    title: 'Metodo de pago actualizado',
                    text: data.mensaje || "El metodo de pago se ha actualizado exitosamente.",
                    timer: 2000,
                    showConfirmButton: false
                }).then(() => {
                    $('#editarMetodosPagoModal').modal('hide'); // Ocultar el modal
                    listarMetodosPago(); // Refrescar la lista de metodos de pago
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
            console.error('Error al actualizar metodos de pago:', error);
        });
}


//Eliminar
function eliminarMetodosPago(id) {
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
            fetch(`/api/v1/metodosPago/${id}`, {
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
                            listarMetodosPago(); // Refrescar la lista de metodos de pago
                        }
                    });
                })
                .catch(error => {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error de conexión',
                        text: 'No se pudo eliminar el metodo de pago.',
                    });
                    console.error('Error al eliminar metodo de pago:', error);
                });
        }
    });
}

function exportarExcel() {
    window.location.href = '/excelmetodosPago';
}

