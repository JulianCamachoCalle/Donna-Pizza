// Obtener lista de detalles de pedido y mostrarlos en la tabla
function listarDetallesPedido() {
    fetch('api/v1/detalles-pedido')
        .then(response => response.json())
        .then(data => {
            const detallesPedidoList = document.getElementById("detallesPedido-list");
            detallesPedidoList.innerHTML = ""; // Limpiar tabla antes de cargar datos

            data.forEach(detallesPedido => {
                let row = `
                    <tr>
                        <td>${detallesPedido.id_detalle}</td>
                        <td>${detallesPedido.id_pedido}</td>
                        <td>${detallesPedido.id_pizza}</td>
                        <td>${detallesPedido.id_pizza_familiar}</td>
                        <td>${detallesPedido.id_pasta}</td>
                        <td>${detallesPedido.id_entrada}</td>
                        <td>${detallesPedido.cantidad}</td>
                        <td>${detallesPedido.precio_unitario}</td>
                        <td>${detallesPedido.subtotal}</td>
                        <td>
                            <button class="btn btn-warning" onclick="cargarDatosDetallesPedido(${detallesPedido.id_detalle})">Editar</button>
                            <button class="btn btn-danger" onclick="eliminarDetallesPedido(${detallesPedido.id_pedido})">Eliminar</button>
                        </td>
                    </tr>`;
                detallesPedidoList.innerHTML += row;
            });
        })
        .catch(error => console.error('Error al listar detalles de pedido:', error));
}

document.addEventListener('DOMContentLoaded', listarDetallesPedido);

// Función para guardar un detalle de pedido
function guardarDetallesPedido() {
    const detallePedido = {
        id_pedido: document.getElementById("id_pedido").value,
        id_pizza: document.getElementById("id_pizza").value,
        id_pizza_familiar: document.getElementById("id_pizza_familiar").value,
        id_pasta: document.getElementById("id_pasta").value,
        id_entrada: document.getElementById("id_entrada").value,
        cantidad: document.getElementById("cantidad").value,
        precio_unitario: document.getElementById("precio_unitario").value,
        subtotal: document.getElementById("subtotal").value,

    };

    fetch('/api/v1/detalles-pedido', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(detallePedido)
    })
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                // Mostrar mensaje de error
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: data.mensaje || "Hubo un problema al agregar el detalles de pedido.",
                });
            } else {
                // Mostrar mensaje de éxito
                Swal.fire({
                    icon: 'success',
                    title: 'Detalle de pedido agregado',
                    text: data.mensaje || "El detalle de pedido se ha registrado exitosamente.",
                    timer: 2000,
                    showConfirmButton: false
                }).then(() => {
                    $('#detallesPedidoModal').modal('hide'); // Ocultar el modal
                    listarDetallesPedido(); // Refrescar la lista de detalles de pedido
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
            console.error('Error al guardar el detalle de pedido:', error);
        });
}

// Cargar datos al Modal Actualizar
function cargarDatosDetallePedido(id) {
    fetch(`/api/v1/detalles-pedido/${id}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al obtener datos del detalle de pedido');
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
                document.getElementById("id_detallesPedidoact").value = data.id_detalle;
                document.getElementById("id_pedidoact").value = data.id_pedido;
                document.getElementById("id_pizzaact").value = data.id_pizza;
                document.getElementById("id_pizza_familiaract").value = data.id_pizza_familiar;
                document.getElementById("id_pastaact").value = data.id_pasta;
                document.getElementById("id_entradaact").value = data.id_entrada;
                document.getElementById("cantidadact").value = data.cantidad;
                document.getElementById("precio_unitarioact").value = data.precio_unitario;
                document.getElementById("subtotalact").value = data.subtotal;
                // Mostrar el modal
                $('#editardetallesPedidoModal').modal('show');
            }
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Error de conexión',
                text: 'No se pudo cargar los datos del detalle de pedido.',
            });
            console.error('Error al cargar datos del detalle del pedido:', error);
        });
}

// Actualizar
function actualizarDetallesPedido() {
    const detallesPedidoActualizado = {
        id_pedido: document.getElementById("id_pedido").value,
        id_pizza: document.getElementById("id_pizza").value,
        id_pizza_familiar: document.getElementById("id_pizza_familiar").value,
        id_pasta: document.getElementById("id_pasta").value,
        id_entrada: document.getElementById("id_entrada").value,
        cantidad: document.getElementById("cantidad").value,
        precio_unitario: document.getElementById("precio_unitario").value,
        subtotal: document.getElementById("subtotal").value,
    };

    fetch(`/api/v1/detalles-pedido/${detallesPedidoActualizado.id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(detallesPedidoActualizado)
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
                    $('#editardetallesPedidoModal').modal('hide'); // Ocultar el modal
                    listarDetallesPedido(); // Refrescar la lista de detalles pedido
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
            console.error('Error al actualizar detalles del pedido:', error);
        });
}


//Eliminar
function eliminarDetallesPedido(id) {
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
            fetch(`/api/v1/detalles-pedido/${id}`, {
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
                            listarDetallesPedido(); // Refrescar la lista de detalles pedido
                        }
                    });
                })
                .catch(error => {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error de conexión',
                        text: 'No se pudo eliminar el detalle de pedido.',
                    });
                    console.error('Error al eliminar metodo de detalle de pedido:', error);
                });
        }
    });
}

function exportarExcel() {
    window.location.href = '/excelDetallesPedido';
}

