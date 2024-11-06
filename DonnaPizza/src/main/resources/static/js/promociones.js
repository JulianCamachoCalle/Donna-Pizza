//Desarrollo de anthony
// Obtener lista de promociones y mostrarlas en la tabla
function listarPromociones() {
    fetch('/api/v1/promociones')
        .then(response => response.json())
        .then(data => {
            const promocionesList = document.getElementById("promociones-list");
            promocionesList.innerHTML = ""; // Limpiar tabla antes de cargar datos

            data.forEach(promocion => {
                let row = `
                    <tr>
                        <td>${promocion.id_promocion}</td>
                        <td>${promocion.nombre}</td>
                        <td>${promocion.descripcion}</td>
                        <td>${promocion.descuento}</td>
                        <td>${promocion.requisitos}</td>
                        <td>${promocion.activo ? 'Sí' : 'No'}</td>
                        <td>
                            <button class="btn btn-warning" onclick="cargarDatosPromocion(${promocion.id_promocion})">Editar</button>
                            <button class="btn btn-danger" onclick="eliminarPromocion(${promocion.id_promocion})">Eliminar</button>
                        </td>
                    </tr>`;
                promocionesList.innerHTML += row;
            });
        })
        .catch(error => console.error('Error al listar promociones:', error));
}

document.addEventListener('DOMContentLoaded', listarPromociones);

// Función para guardar una promoción
function guardarPromocion() {
    const promocion = {
        nombre: document.getElementById("nombre").value,
        descripcion: document.getElementById("descripcion").value,
        descuento: parseFloat(document.getElementById("descuento").value),
        requisitos: document.getElementById("requisitos").value,
        activo: document.getElementById("activo").value === 'true'
    };

    fetch('/api/v1/promociones', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(promocion)
    })
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                // Mostrar mensaje de error
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: data.mensaje || "Hubo un problema al agregar la promoción.",
                });
            } else {
                // Mostrar mensaje de éxito
                Swal.fire({
                    icon: 'success',
                    title: 'Promoción agregada',
                    text: data.mensaje || "La promoción se ha registrado exitosamente.",
                    timer: 2000,
                    showConfirmButton: false
                }).then(() => {
                    $('#promocionModal').modal('hide'); // Ocultar el modal
                    listarPromociones(); // Refrescar la lista de promociones
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
            console.error('Error al guardar promoción:', error);
        });
}

// Cargar datos al Modal Actualizar
function cargarDatosPromocion(id) {
    fetch(`/api/v1/promociones/${id}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al obtener datos de la promoción');
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
                document.getElementById("id_promocion-prom").value = data.id_promocion;
                document.getElementById("nombre-prom").value = data.nombre;
                document.getElementById("descripcion-prom").value = data.descripcion;
                document.getElementById("descuento-prom").value = data.descuento;
                document.getElementById("requisitos-prom").value = data.requisitos;
                document.getElementById("activo-prom").value = data.activo;

                // Mostrar el modal
                $('#editarPromocionModal').modal('show');
            }
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Error de conexión',
                text: 'No se pudo cargar los datos de la promoción.',
            });
            console.error('Error al cargar datos de la promoción:', error);
        });
}

// Actualizar
function actualizarPromocion() {
    const promocionActualizada = {
        id_promocion: document.getElementById("id_promocion-prom").value,
        nombre: document.getElementById("nombre-prom").value,
        descripcion: document.getElementById("descripcion-prom").value,
        descuento: parseFloat(document.getElementById("descuento-prom").value),
        requisitos: document.getElementById("requisitos-prom").value,
        activo: document.getElementById("activo-prom").value === 'true'
    };

    fetch(`/api/v1/promociones/${promocionActualizada.id_promocion}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(promocionActualizada)
    })
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                // Mostrar mensaje de error
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: data.mensaje || "Hubo un problema al actualizar la promoción.",
                });
            } else {
                // Mostrar mensaje de éxito
                Swal.fire({
                    icon: 'success',
                    title: 'Promoción actualizada',
                    text: data.mensaje || "La promoción se ha actualizado exitosamente.",
                    timer: 2000,
                    showConfirmButton: false
                }).then(() => {
                    $('#editarPromocionModal').modal('hide'); // Ocultar el modal
                    listarPromociones(); // Refrescar la lista de promociones
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
            console.error('Error al actualizar promoción:', error);
        });
}

// Eliminar
function eliminarPromocion(id) {
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
            fetch(`/api/v1/promociones/${id}`, {
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
                            listarPromociones(); // Refrescar la lista de promociones
                        }
                    });
                })
                .catch(error => {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error de conexión',
                        text: 'No se pudo eliminar la promoción.',
                    });
                    console.error('Error al eliminar promoción:', error);
                });
        }
    });
}

// Exportar a Excel
function exportarExcel() {
    window.location.href = '/excelpromociones'; // Asegúrate de que esta ruta esté configurada en tu backend
}
