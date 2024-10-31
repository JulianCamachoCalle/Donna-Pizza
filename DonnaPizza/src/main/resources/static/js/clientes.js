// Obtener lista de clientes y mostrarlos en la tabla
function listarClientes() {
    fetch('/api/v1/clientes')
        .then(response => response.json())
        .then(data => {
            const clientesList = document.getElementById("clientes-list");
            clientesList.innerHTML = ""; // Limpiar tabla antes de cargar datos

            data.forEach(cliente => {
                let row = `
                    <tr>
                        <td>${cliente.id_cliente}</td>
                        <td>${cliente.nombre}</td>
                        <td>${cliente.apellido}</td>
                        <td>${cliente.email}</td>
                        <td>${cliente.telefono}</td>
                        <td>${cliente.direccion}</td>
                        <td>
                            <button class="btn btn-warning" onclick="cargarDatosCliente(${cliente.id_cliente})">Editar</button>
                            <button class="btn btn-danger" onclick="eliminarCliente(${cliente.id_cliente})">Eliminar</button>
                        </td>
                    </tr>`;
                clientesList.innerHTML += row;
            });
        })
        .catch(error => console.error('Error al listar clientes:', error));
}

document.addEventListener('DOMContentLoaded', listarClientes);

// Función para guardar un cliente
function guardarCliente() {
    const cliente = {
        nombre: document.getElementById("nombre").value,
        apellido: document.getElementById("apellido").value,
        email: document.getElementById("email").value,
        telefono: document.getElementById("telefono").value,
        direccion: document.getElementById("direccion").value
    };

    fetch('/api/v1/clientes', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(cliente)
    })
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                // Mostrar mensaje de error
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: data.mensaje || "Hubo un problema al agregar el cliente.",
                });
            } else {
                // Mostrar mensaje de éxito
                Swal.fire({
                    icon: 'success',
                    title: 'Cliente agregado',
                    text: data.mensaje || "El cliente se ha registrado exitosamente.",
                    timer: 2000,
                    showConfirmButton: false
                }).then(() => {
                    $('#clienteModal').modal('hide'); // Ocultar el modal
                    listarClientes(); // Refrescar la lista de clientes
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
            console.error('Error al guardar cliente:', error);
        });
}

// Cargar datos al Modal Actualizar
function cargarDatosCliente(id) {
    fetch(`/api/v1/clientes/${id}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al obtener datos del cliente');
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
                document.getElementById("id_clienteact").value = data.id_cliente;
                document.getElementById("nombreact").value = data.nombre;
                document.getElementById("apellidoact").value = data.apellido;
                document.getElementById("emailact").value = data.email;
                document.getElementById("telefonoact").value = data.telefono.replace("+51 ", ""); // Remover el prefijo para editar
                document.getElementById("direccionact").value = data.direccion;

                // Mostrar el modal
                $('#editarClienteModal').modal('show');
            }
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Error de conexión',
                text: 'No se pudo cargar los datos del cliente.',
            });
            console.error('Error al cargar datos del cliente:', error);
        });
}

// Actualizar
function actualizarCliente() {
    const clienteActualizado = {
        id: document.getElementById("id_clienteact").value,
        nombre: document.getElementById("nombreact").value,
        apellido: document.getElementById("apellidoact").value,
        email: document.getElementById("emailact").value,
        telefono: document.getElementById("telefonoact").value,
        direccion: document.getElementById("direccionact").value
    };

    fetch(`/api/v1/clientes/${clienteActualizado.id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(clienteActualizado)
    })
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                // Mostrar mensaje de error
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: data.mensaje || "Hubo un problema al actualizar el cliente.",
                });
            } else {
                // Mostrar mensaje de éxito
                Swal.fire({
                    icon: 'success',
                    title: 'Cliente actualizado',
                    text: data.mensaje || "El cliente se ha actualizado exitosamente.",
                    timer: 2000,
                    showConfirmButton: false
                }).then(() => {
                    $('#editarClienteModal').modal('hide'); // Ocultar el modal
                    listarClientes(); // Refrescar la lista de clientes
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
            console.error('Error al actualizar cliente:', error);
        });
}

//Eliminar
function eliminarCliente(id) {
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
            fetch(`/api/v1/clientes/${id}`, {
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
                            listarClientes(); // Refrescar la lista de clientes
                        }
                    });
                })
                .catch(error => {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error de conexión',
                        text: 'No se pudo eliminar el cliente.',
                    });
                    console.error('Error al eliminar cliente:', error);
                });
        }
    });
}

function exportarExcel() {
    window.location.href = '/excelclientes';
}



