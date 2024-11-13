// Obtener lista de pedidos y mostrarlos en la tabla
function listarPedidos() {
    fetch('/api/v1/pedidos')
        .then(response => response.json())
        .then(data => {
            const pedidosList = document.getElementById("pedidos-list");
            pedidosList.innerHTML = ""; // Limpiar tabla antes de cargar datos

            data.forEach(pedido => {
                let row = `
                    <tr>
                        <td>${pedido.id_pedido}</td>
                        <td>${pedido.id_usuario}</td>
                        <td>${pedido.id_cliente}</td>
                        <td>${pedido.fecha}</td>
                        <td>${pedido.total}</td>
                        <td>${pedido.id_documento}</td>
                        <td>
                            <button class="btn btn-warning" onclick="cargarDatosPedido(${pedido.id_pedido})">Editar</button>
                            <button class="btn btn-danger" onclick="eliminarIngrediente(${pedido.id_pedido})">Eliminar</button>
                        </td>
                    </tr>`;
                pedidosList.innerHTML += row;
            });
        })
        .catch(error => console.error('Error al listar pedidos:', error));
}

document.addEventListener('DOMContentLoaded', listarIngredientes);

// Función para guardar un pedido
function guardarPedido() {
    const pedido = {
        id_usuario: document.getElementById("id_pedido").value,
        id_cliente: document.getElementById("id_cliente").value,
        fecha: document.getElementById("fecha").value,
        total: document.getElementById("total").value,
        id_documento: document.getElementById("id_documento").value,
    };

    fetch('/api/v1/pedidos', {
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
function actualizarPedido() {
    const pedidoActualizado = {
        id: document.getElementById("id_pedidoact").value,
        id_usuario: document.getElementById("id_pedido").value,
        id_cliente: document.getElementById("id_cliente").value,
        fecha: document.getElementById("fecha").value,
        total: document.getElementById("total").value,
        id_documento: document.getElementById("id_documento").value,
    };

    fetch(`/api/v1/pedidos/${pedidoActualizado.id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(pedidoActualizado)
    })
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                // Mostrar mensaje de error
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: data.mensaje || "Hubo un problema al actualizar el pedido.",
                });
            } else {
                // Mostrar mensaje de éxito
                Swal.fire({
                    icon: 'success',
                    title: 'Pedido actualizado',
                    text: data.mensaje || "El pedido se ha actualizado exitosamente.",
                    timer: 2000,
                    showConfirmButton: false
                }).then(() => {
                    $('#editarPedidoModal').modal('hide'); // Ocultar el modal
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
            console.error('Error al actualizar pedido:', error);
        });
}


//Eliminar
function eliminarPedido(id) {
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
            fetch(`/api/v1/pedidos/${id}`, {
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
                            listarPedidos(); // Refrescar la lista de pedidos
                        }
                    });
                })
                .catch(error => {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error de conexión',
                        text: 'No se pudo eliminar el pedido.',
                    });
                    console.error('Error al eliminar pedido:', error);
                });
        }
    });
}

function exportarExcel() {
    window.location.href = '/excelpedidos';
}

