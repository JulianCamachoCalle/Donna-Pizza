// Obtener lista de pagos y mostrarlos en la tabla
function listarPagos() {
    fetch('/api/v1/pagos')
        .then(response => response.json())
        .then(data => {
            const pagosList = document.getElementById("pagos-list");
            pagosList.innerHTML = ""; // Limpiar tabla antes de cargar datos

            data.forEach(pago => {
                let row = `
                    <tr>
                        <td>${pago.id_pago}</td>
                        <td>${pago.id_pedido}</td>
                        <td>${pago.id_metodo_pago}</td>
                        <td>${pago.monto}</td>
                        <td>${pago.fecha}</td>
                        <td>
                            <button class="btn btn-warning" onclick="cargarDatosPagos(${pedido.id_pedido})">Editar</button>
                            <button class="btn btn-danger" onclick="eliminarIngrediente(${pedido.id_pedido})">Eliminar</button>
                        </td>
                    </tr>`;
                pagosList.innerHTML += row;
            });
        })
        .catch(error => console.error('Error al listar pagos:', error));
}

document.addEventListener('DOMContentLoaded', listarIngredientes);

// Función para guardar un pago
function guardarPago() {
    const pago = {
        id_pedido: document.getElementById("id_pedido").value,
        id_metodo_pago: document.getElementById("id_metodosPago").value,
        monto: document.getElementById("monto").value,
        fecha: document.getElementById("fecha").value,
    };

    fetch('/api/v1/pagos', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(pedido)
    })
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                // Mostrar mensaje de error
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: data.mensaje || "Hubo un problema al agregar el pedido.",
                });
            } else {
                // Mostrar mensaje de éxito
                Swal.fire({
                    icon: 'success',
                    title: 'pedido agregado',
                    text: data.mensaje || "El pedido se ha registrado exitosamente.",
                    timer: 2000,
                    showConfirmButton: false
                }).then(() => {
                    $('#pedidoModal').modal('hide'); // Ocultar el modal
                    listarPedidos(); // Refrescar la lista de pedidos
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
            console.error('Error al guardar pedido:', error);
        });
}

// Cargar datos al Modal Actualizar
function cargarDatosPedido(id) {
    fetch(`/api/v1/pedidos/${id}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al obtener datos del pedido');
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
                document.getElementById("id-pedidoact").value = data.id_pedido;
                document.getElementById("id-usuarioact").value = data.id_usuario;
                document.getElementById("id-clienteact").value = data.id_cliente;
                document.getElementById("fechaact").value = data.fecha;
                document.getElementById("totalact").value = data.total;
                document.getElementById("id-documento").value = data.id_documento;

                // Mostrar el modal
                $('#editarPedidoModal').modal('show');
            }
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Error de conexión',
                text: 'No se pudo cargar los datos del pedido.',
            });
            console.error('Error al cargar datos del pedido:', error);
        });
}

// Actualizar
function actualizarPago() {
    const pagoActualizado = {
        id: document.getElementById("id_pedidoact").value,
        id_usuario: document.getElementById("id_pedido").value,
        id_cliente: document.getElementById("id_cliente").value,
        fecha: document.getElementById("fecha").value,
        total: document.getElementById("total").value,
        id_documento: document.getElementById("id_documento").value,
    };

    fetch(`/api/v1/pagos/${pagoActualizado.id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(pagoActualizado)
    })
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                // Mostrar mensaje de error
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: data.mensaje || "Hubo un problema al actualizar el pago.",
                });
            } else {
                // Mostrar mensaje de éxito
                Swal.fire({
                    icon: 'success',
                    title: 'Pago actualizado',
                    text: data.mensaje || "El pago se ha actualizado exitosamente.",
                    timer: 2000,
                    showConfirmButton: false
                }).then(() => {
                    $('#editarPagoModal').modal('hide'); // Ocultar el modal
                    listarPagos(); // Refrescar la lista de pagos
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
            console.error('Error al actualizar pago:', error);
        });
}


//Eliminar
function eliminarPago(id) {
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
            fetch(`/api/v1/pagos/${id}`, {
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
                            listarPagos(); // Refrescar la lista de pagos
                        }
                    });
                })
                .catch(error => {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error de conexión',
                        text: 'No se pudo eliminar el pago.',
                    });
                    console.error('Error al eliminar pago:', error);
                });
        }
    });
}

function exportarExcel() {
    window.location.href = '/excelpagos';
}

